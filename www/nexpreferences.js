var nexpreferences =  {
    store: function(key, value, successCallback, errorCallback) {
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
    },
    fetch: function(key, successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'nexpreferences', // mapped to our native Java class called "nexpreferences"
            'fetch', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "key": key
            }]
        );
    },
    remove: function(key, value, successCallback, errorCallback) {
        cordova.exec(
            successCallback, // success callback function
            errorCallback, // error callback function
            'nexpreferences', // mapped to our native Java class called "nexpreferences"
            'remove', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "key": key
            }]
        );
    },
    invokeSMS: function(key,successCallback,errorCallback){
        cordova.exec{
            successCallback, // success callback function
            errorCallback, // error callback function
            'nexpreferences', // mapped to our native Java class called "nexpreferences"
            'sms', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "key": key
            }]
        }

    },
    invokeContact: function(key, successCallback, errorCallback){
        cordova.exec{
            successCallback, // success callback function
            errorCallback, // error callback function
            'nexpreferences', // mapped to our native Java class called "nexpreferences"
            'addContact', // with this action name
            [{                  // and this array of custom arguments to create our entry
                "key": key
            }]
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