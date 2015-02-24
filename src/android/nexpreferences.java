package com.larachao.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.Profile;
import android.provider.Telephony;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;

public class nexpreferences extends CordovaPlugin{
	public static final String ACTION_ADD_STORE = "store";
	public static final String ACTION_ADD_FETCH = "fetch";
	public static final String ACTION_ADD_REMOVEALL = "removeAll";
	public static final String ACTION_ADD_REMOVE = "remove";
	public static final String ACTION_INVOKE_SMS = "sms";
	public static final String ACTION_INVOKE_CONTACT = "addContact";
	public static final String ACTION_GET_CONTACT_INFO = "getContactInfo";
	public static final String ACTION_GET_CONTACT_ALL = "getAllContacts";
	public static final String ACTION_GET_MODEL = "getPhoneModel";
	public static final String ACTION_INVOKE_CALL = "call";
	
	public static final String CONST_KEY = "key"; //also for NUM
	public static final String CONST_VALUE = "value";	
	public static final int SAVE_CONTACT = 1;
	
	private static final int COMMIT_FAILED = 2;
	private static final int FETCH_NOTFOUND = 0;
	private static final int CODE_ERROR = 3;	

	public CallbackContext cb = null;
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException{
		JSONObject options = args.getJSONObject (0);
		String key = options.getString(CONST_KEY);
		this.cb = callbackContext;
		if(action.equals(ACTION_ADD_STORE)){
			Log.d("nexpreferences", "test");
			return this.store(key, options.getString(CONST_VALUE), callbackContext);
				
		}else if(action.equals(ACTION_ADD_FETCH)){
			
			return this.fetch(key, callbackContext);
			
		}else if(action.equals(ACTION_ADD_REMOVE)){
			//Supplying null as the value is equivalent to calling remove(String) with this key.
			//http://developer.android.com/reference/android/content/SharedPreferences.Editor.html#putString(java.lang.String, java.lang.String)
			return this.store(key, null, callbackContext);
			
		}else if(action.equals(ACTION_ADD_REMOVEALL)){
			
		} else if(action.equals(ACTION_INVOKE_SMS)){
			return this.invokeSms(key,callbackContext);
			

		}else if(action.equals(ACTION_INVOKE_CONTACT)){
			return this.invokeContacts(key, options.getString(CONST_VALUE), callbackContext);			
		}else if(action.equals(ACTION_GET_CONTACT_INFO)){			
			String[] arrStr = key.split(",");
			return this.myCallerId(arrStr, callbackContext);
			/*PluginResult result = new PluginResult(PluginResult.Status.NO_RESULT);
		    result.setKeepCallback(true);
		    this.callbackContext.sendPluginResult(result);
		    return true;*/
			
		}else if(action.equals(ACTION_GET_MODEL)){
			return getDeviceName(callbackContext);
		}else if(action.equals(ACTION_INVOKE_CALL)){
			return this.invokeCall(key,callbackContext);
		}
		
		return false;
	}
	
	private boolean getDeviceName(final CallbackContext cb){
		try{
			String manufacturer = Build.MANUFACTURER;
			String model = Build.MODEL;
			if (model.startsWith(manufacturer)) {
				cb.success(new JSONObject().put("model", model));//capitalize(model));
			} else {
				cb.success(new JSONObject().put("model", manufacturer + " " + model));//capitalize(manufacturer) + " " + model);
			}
		}catch(Exception ex){
			/*try{
				cb.error(createErrorObj(CODE_ERROR,ex.getMessage()));
			}catch(JSONException jex){
				jex.printStackTrace();
			}*/
			ex.printStackTrace();
		}
		return true;
	}
	/*private String capitalize(String s) {
	  if (s == null || s.length() == 0) {
	    return "";
	  }
	  char first = s.charAt(0);
	  if (Character.isUpperCase(first)) {
	    return s;
	  } else {
	    return Character.toUpperCase(first) + s.substring(1);
	  }
	} */

	public boolean store(final String key ,final String value,final CallbackContext cb){
		cordova.getThreadPool().execute(new Runnable() {public void run() {
			//get sharepreference instance of current cordova activity (which is only one)
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(cordova.getActivity());
			Editor editor = sp.edit();
			editor.putString (key, value);
			if (editor.commit()) {				
				cb.success();
			} else {
				try {
					cb.error(createErrorObj(COMMIT_FAILED, "Cannot commit change"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}});
		return true;
	}
	
	public boolean fetch(final String key, final CallbackContext cb){
		//let's only store JSON.
		cordova.getThreadPool().execute(new Runnable() {public void run() {
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(cordova.getActivity());
			try{				
				if(sp.contains(key)){					
					if(sp.getString(key, null) != null){
						//returnVal
						try {
							String temp = sp.getString(key, null);
							//retrieve name?
							if(temp.indexOf('{')==0){
								/*JSONObject obj =new ;
								//for missedcalls only
								if()
								obj.put("disp", myCallerId_Internal(obj.getString("num")));
								Log.d("fetch", "fetchingName for num->" + obj.getString("num") + " name->" + obj.getString("disp"));*/
								cb.success(new JSONObject(temp));
							}else if (temp.indexOf('[') ==0){								
								/*JSONArray arr = new JSONArray(temp);
								for (int i = 0; i < arr.length(); i++) {																
									arr.getJSONObject(i).put("disp", myCallerId_Internal(arr.getJSONObject(i).getString("num")));
									Log.d("fetch", "fetchingName for num->" + arr.getJSONObject(i).getString("num") + " name->" + arr.getJSONObject(i).getString("disp"));
								}*/
								cb.success(new JSONArray(temp));

							}else{
								cb.success(temp);//allow flag storing
								//cb.error(createErrorObj(CODE_ERROR, "unknown storage type"));
							}
													
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}else{
						//error null
						try {
							cb.error(createErrorObj(FETCH_NOTFOUND, "sp.key is null"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block						
							e.printStackTrace();
						}
					}
				}else{
					//error null
					try {
						cb.error(createErrorObj(FETCH_NOTFOUND, "sp does not contain key"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}catch(Exception ex){
				//error code error
				try {
					cb.error(createErrorObj(CODE_ERROR, "Synthax error"));
					ex.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}});
						
		return true;
	}
	
	//error reporting
	private JSONObject createErrorObj(int code, String message) throws JSONException {
		JSONObject errorObj = new JSONObject();
		errorObj.put("code", code);
		errorObj.put("err", message);
		return errorObj;
	}

	//test 24Feb15 - not implemeneting (bad use case)
	public boolean invokeCall(final String num, final CallbackContext cb){
		try {
	        final Intent callIntent = new Intent(Intent.ACTION_CALL);
	        Activity activity = cordova.getActivity();
	        callIntent.setData(Uri.parse("tel:" + num));
	        activity.startActivity(callIntent);
	        
        }catch (Exception e){
            try {
				cb.error(createErrorObj(CODE_ERROR, "Dialing, call failed"));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}

	public boolean invokeSms(final String num, final CallbackContext cb){
		//key == num
		cordova.getThreadPool().execute(new Runnable() {public void run() {			
			try{
				Intent intent;
		        Activity activity = cordova.getActivity();

		        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // Android 4.4 and up
		        {
		            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(activity);

		            intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + Uri.encode(num)));	            

		            // Can be null in case that there is no default, then the user would be able to choose any app that supports this intent.
		            if (defaultSmsPackageName != null) {
		                intent.setPackage(defaultSmsPackageName);
		            }
		        } else {
		            intent = new Intent(Intent.ACTION_VIEW);
		            intent.setType("vnd.android-dir/mms-sms");
		            intent.putExtra("address", num);	            
		        }

		        activity.startActivity(intent);
		        cb.success();
	        }
	       catch(Exception ex){
	       		try {
					cb.error(createErrorObj(CODE_ERROR, "Synthax error"));
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	       }
		}});
		return true;		
	}
	

	public boolean invokeContacts(final String num, final String insert, final CallbackContext cb){
		//key == num
		cordova.getThreadPool().execute(new Runnable() {public void run() {			
			try{				
		        Activity activity = cordova.getActivity();
		        String intAction = Intent.ACTION_INSERT;
		        String contType =ContactsContract.RawContacts.CONTENT_TYPE;
		        if(!Boolean.parseBoolean(insert)){
		        	intAction = Intent.ACTION_INSERT_OR_EDIT;
		        	contType = ContactsContract.RawContacts.CONTENT_ITEM_TYPE;
		        }
		        Intent intent = new Intent(intAction);
				intent.setType(contType);
				intent.putExtra(ContactsContract.Intents.Insert.PHONE, num);
				intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, "NexLabs");

		        //close activity after save completed
		        if (Integer.valueOf(Build.VERSION.SDK_INT) > 14)
		        	intent.putExtra("finishActivityOnSaveCompleted", true); // only for 4.0.3 onwards
		        activity.startActivity(intent);
		        //activity.startActivityForResult(intent, SAVE_CONTACT);
		        cb.success();
	       }
	       catch(Exception ex){
	       		try {
					cb.error(createErrorObj(CODE_ERROR, "Synthax error"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ex.printStackTrace();
	       }
		}});			
		return true;	
		
	}
		
	public boolean myCallerId(final String[] lstNum, final CallbackContext cb){	
		cordova.getThreadPool().execute(new Runnable() {public void run() {			
		try{
			
			String res = "";
			final ContentResolver resolver = cordova.getActivity().getContentResolver();			
			for(String i : lstNum){
				if( i.trim() != ""){					
					Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(i.trim()));
					//Log.d("myCallerId", "uri for num->"+uri.toString());
					try{
						//http://developer.android.com/training/contacts-provider/retrieve-names.html
						Cursor cr = resolver.query(uri, new String[]{Profile.DISPLAY_NAME_PRIMARY}, null, null, null);						
						if(cr.moveToFirst()){									
							Log.d("myCallerId", "num-> " + i + " | cr.getString(0)->" + cr.getString(0));
							if(res != ""){ res += ",";}
							res += "{\"num\":\"" + i.trim() + "\", \"name\":\"" + cr.getString(0) + "\"}";														
						}else{
							//Log.d("myCallerId", "cr.getString(0) is null");
						}
						cr.close();
					}catch(Exception ex){

					}
				}
			}
			res = "[" + res + "]";			
			cb.success(res);
			
		}catch(Exception ex){
			try{
				Log.d("myCallerId", "catch error->" + ex.getMessage());
				cb.error(createErrorObj(CODE_ERROR, ex.getMessage()));
			}
			catch(Exception e){
				// TODO Auto-generated catch block
				Log.d("myCallerId", "e error->" + e.getStackTrace());
				e.printStackTrace();
			}
		}
	}});
	return true;
}
	
}
