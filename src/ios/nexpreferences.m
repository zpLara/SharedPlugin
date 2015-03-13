#import "nexpreferences.h"
#import <sys/sysctl.h>

@implementation nexpreferences

- (void)store:(CDVInvokedUrlCommand*)command{
    NSLog(@"nexpreferences-store");
    __block CDVPluginResult *pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    NSLog(@"in Store @ nexpreferences");
    //get params
    NSDictionary* options=[[command arguments] objectAtIndex:0];
    if(!options){
        //catching error
        pluginResult=[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:@"store params not found"];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        NSLog(@"nexpreferences-store: resultsent");
    }else{
        
        //print params;
        NSString *key = [options objectForKey:@"key"];
        NSString *value = [options objectForKey:@"value"];
        NSLog(@"store:: key is %@, value is %@",key,value);
        
        //get NSUserDefaults (equivalent of sharedprefs)
        [self.commandDelegate runInBackground:^{
            
            @try{
                NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
                [defaults setObject:value forKey:key];
                NSLog(@"Store completed.");
                pluginResult= [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            }
            @catch(NSException * e){
                NSLog(@"nexpreferences-store: exception");
                pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:[e reason]];
            }@finally{
                NSLog(@"nexpreferences-store: resultsent");
                [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
                //print stored values
                NSUserDefaults *defaults_stored = [NSUserDefaults standardUserDefaults];
                NSString *stored_value = [defaults_stored objectForKey:key];
                NSLog(@"stored results: key is %@, value is%@", key, stored_value);
            }
        }];
    }
    
    
    
}


- (void)fetch:(CDVInvokedUrlCommand*)command{
    NSLog(@"nexpreferences-fetch");
    //CDVPluginResult* pluginResult= [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"plugin-fetch :)"];
    //print params;
    CDVPluginResult* pluginResult=nil;
    @try{
        //get params
        NSDictionary* options=[[command arguments] objectAtIndex:0];
        NSString *key = [options objectForKey:@"key"];
        NSUserDefaults *defaults =[NSUserDefaults standardUserDefaults];
        
        
        
        //debug: i stored an array instead, so i needa remove it
        //[[NSUserDefaults standardUserDefaults] removeObjectForKey:@"com.nexlabs.missedcalls"];
        NSString *fetched_value = [defaults objectForKey:key];
        
        if(fetched_value!=(id)[NSNull null]){
            
            NSLog(@"nexpreference-fetch.success:: key is %@, value is %@",key, fetched_value);
            
            pluginResult=[CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: fetched_value];
            
        }else{
            NSLog(@"nexpreference-fetch.failed:: key-> %@, value-> %@, not found",key, fetched_value);
            pluginResult=[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: @"sp.key is null"];
        }
    }
    @catch(NSException * e){
        NSLog(@"nexpreference-fetch.error:: %@", [e reason]);
        pluginResult=[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString: [e reason]];
        
    }@finally{
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        
        NSLog(@"nexpreferences-fetch: resultsent");
    }
    
}
- (void)remove:(CDVInvokedUrlCommand*)command{
    NSLog(@"nexpreferences-remove");
    //CDVPluginResult* pluginResult= [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"plugin-remove :)"];
    CDVPluginResult* pluginResult=[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    NSLog(@"nexpreferences-remove: resultsent");
}
- (void)sms:(CDVInvokedUrlCommand*)command{
    NSLog(@"nexpreferences-invokeSMS");
    //CDVPluginResult* pluginResult= [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"plugin-invokeSMS :)"];
    CDVPluginResult* pluginResult=[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    NSLog(@"nexpreferences-invokeSMS: resultsent");
}
- (void)addContact:(CDVInvokedUrlCommand*)command{
    NSLog(@"nexpreferences-invokeContact");
    CDVPluginResult* pluginResult=[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}
- (void)getContactInfo:(CDVInvokedUrlCommand*)command{
    NSLog(@"nexpreferences-getContactInfo");
    CDVPluginResult* pluginResult=[CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    NSLog(@"nexpreferences-getContactInfo: resultsent");
}
- (void)getPhoneModel:(CDVInvokedUrlCommand*)command{
    NSLog(@"nexpreferences-getPhoneModel");
    NSDictionary *jsonObj = [NSDictionary dictionaryWithObject:[[UIDevice currentDevice] model] forKey:@"model"];
    NSError *error;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:jsonObj options:0 error:&error];
    NSString *jsonString = [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
    NSLog(@"nexpreference-getPhoneModel:: %@", jsonString);
    CDVPluginResult* pluginResult=[CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: jsonString];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    NSLog(@"nexpreferences-getPhoneModel: resultsent");
}
- (void)appendMe:(CDVInvokedUrlCommand*)command{
    //Purpose (For Debug): to test appending json obj to json obj/arr
    //get existing
    //existing->json/dictionary/array
    //append to existing depending on appSetting
    
    
}
@end