package com.larachao.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class nexpreferences extends CordovaPlugin{
	public static final String ACTION_ADD_STORE = "store";
	public static final String ACTION_ADD_FETCH = "fetch";
	public static final String ACTION_ADD_REMOVEALL = "removeAll";
	public static final String ACTION_ADD_REMOVE = "remove";
	
	public static final String CONST_KEY = "key";
	public static final String CONST_VALUE = "value";
	
	private static final int COMMIT_FAILED = 2;
	private static final int FETCH_NOTFOUND = 0;
	
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException{
		JSONObject options = args.getJSONObject (0);
		String key = options.getString(CONST_KEY);
		if(action.equals(ACTION_ADD_STORE)){
			Log.d("nexpreferences", "action=store");
			return this.store(key, options.getString(CONST_VALUE), callbackContext);
				
		}else if(action.equals(ACTION_ADD_FETCH)){
			
			return this.fetch(key, callbackContext);
			
		}else if(action.equals(ACTION_ADD_REMOVE)){
			//Supplying null as the value is equivalent to calling remove(String) with this key.
			//http://developer.android.com/reference/android/content/SharedPreferences.Editor.html#putString(java.lang.String, java.lang.String)
			return this.store(key, null, callbackContext);
			
		}else if(action.equals(ACTION_ADD_REMOVEALL)){
			
		}
		
		return false;
	}
	
	public boolean store(final String key ,final String value,final CallbackContext cb){
		cordova.getThreadPool().execute(new Runnable() {public void run() {
			//get sharepreference instance of current cordova activity (which is only one)
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(cordova.getActivity());
			Editor editor = sp.edit();
			Log.d("nexpreferences", "get editor");
			editor.putString (key, value);
			Log.d("nexpreferences", "put string");
			if (editor.commit()) {
				Log.d("nexpreferences", "commit success");
				cb.success();
			} else {
				try {
					cb.error(createErrorObj(COMMIT_FAILED, "Cannot commit change"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Log.d("nexpreferences", "end store()");
		}});
		return true;
	}
	
	public boolean fetch(final String key, final CallbackContext cb){
		//let's only store JSON.
		cordova.getThreadPool().execute(new Runnable() {public void run() {
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(cordova.getActivity());
			JSONObject obj = null;
			if(sp.contains(key)){
				if(sp.getString(key, null) != null){
					//returnVal
					try {
						obj = new JSONObject(sp.getString(key,null));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					cb.success(obj);
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
			
		}});
						
		return true;
	}
	
	//error reporting
	private JSONObject createErrorObj(int code, String message) throws JSONException {
		JSONObject errorObj = new JSONObject();
		errorObj.put("code", code);
		errorObj.put("message", message);
		return errorObj;
	}
	
}
