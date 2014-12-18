var nexpreferences =  {
    store: function(key, value, successCallback, errorCallback) {
        try{
            cordova.exec(
                successCallback, // success callback function
                errorCallback, // error callback function
                'nexpreferences', // mapped to our native Java class called "nexpreferences"
                'store', // with this action name
                [{                  // and this array of custom arguments to create our entry
                    "key": key,
                    "value": value
                }]
            );
        }catch(err){
            errorCallback('plugin not initialized: nexpreferences::store() ');
        }
    },
    fetch: function(key, successCallback, errorCallback) {
        try{
            cordova.exec(
                successCallback, // success callback function
                errorCallback, // error callback function
                'nexpreferences', // mapped to our native Java class called "nexpreferences"
                'fetch', // with this action name
                [{                  // and this array of custom arguments to create our entry
                    "key": key
                }]
            );
        }catch(err){
            errorCallback('plugin not initialized: nexpreferences::fetch()');
        }
    },
    remove: function(key, value, successCallback, errorCallback) {
        try{
            cordova.exec(
                successCallback, // success callback function
                errorCallback, // error callback function
                'nexpreferences', // mapped to our native Java class called "nexpreferences"
                'remove', // with this action name
                [{                  // and this array of custom arguments to create our entry
                    "key": key
                }]
            );
        }catch(err){
            errorCallback('plugins not initialized: nexpreferences::remove()');
        }
    },
    invokeSMS: function(key,successCallback,errorCallback){
        try{
            cordova.exec(
                successCallback, // success callback function
                errorCallback, // error callback function
                'nexpreferences', // mapped to our native Java class called "nexpreferences"
                'sms', // with this action name
                [{                  // and this array of custom arguments to create our entry
                    "key": key
                }]
            );
        }catch(err){
            errorCallback('plugins not initialized: nexpreferences::invokeSMS()');
        }

    },
    invokeContact: function(key, value, successCallback, errorCallback){
        try{
            cordova.exec(
                successCallback, // success callback function
                errorCallback, // error callback function
                'nexpreferences', // mapped to our native Java class called "nexpreferences"
                'addContact', // with this action name
                [{                  // and this array of custom arguments to create our entry
                    "key": key,
                    "value": value
                }]
            );
        }catch(err){
            errorCallback('plugins not initialized: nexpreferences::invokeContact()');
        }

    },
    getContactInfo:function(key, successCallback, errorCallback){
        try{  
            cordova.exec(
                successCallback, // success callback function
                errorCallback, // error callback function
                'nexpreferences', // mapped to our native Java class called "nexpreferences"
                'getContactInfo', // with this action name
                [{                  // and this array of custom arguments to create our entry
                    "key": key
                }]
            );
        }catch(err){
            errorCallback('plugins not initialized: nexpreferences::getContactInfo()');
        }
    },
    getDeviceName:function(successCallback,errorCallback){        
        try{            
            cordova.exec(
                successCallback, // success callback function
                errorCallback, // error callback function
                'nexpreferences', // mapped to our native Java class called "nexpreferences"
                'getPhoneModel', // with this action name
                [{                  // and this array of custom arguments to create our entry
                    "key": ""
                }]
            );
        }catch(err){
            errorCallback('plugins not initialized: nexpreferences::getDeviceName()');
        }
    }
}

if(!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.nexpreferences) {
    window.plugins.nexpreferences = nexpreferences;
}

if (typeof module != 'undefined' && module.exports) {
  module.exports = nexpreferences;
}