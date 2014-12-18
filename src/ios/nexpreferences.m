#import "nexpreferences.h"
#import <Cordova/CDV.h>


@implementation nexpreferences

- (void)store:(CDVInvokedUrlCommand*)command{
    NSLog(@"nexpreferences-store");
    //CDVPluginResult* pluginResult= [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"plugin-store :)"];
    CDVPluginResult* pluginResult=[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    NSLog(@"nexpreferences-store: resultsent");
}
- (void)fetch:(CDVInvokedUrlCommand*)command{
	NSLog(@"nexpreferences-fetch");
    //CDVPluginResult* pluginResult= [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"plugin-fetch :)"];
    CDVPluginResult* pluginResult=[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    NSLog(@"nexpreferences-fetch: resultsent");
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
    CDVPluginResult* pluginResult=[CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    NSLog(@"nexpreferences-getContactInfo: resultsent");
}

@end