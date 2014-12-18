//
//  CDVnexpreferences.h
//  SMARTBill
//
//  Created by nexlabs on 18/12/14.
//
//


#import <Cordova/CDVPlugin.h>

@interface nexpreferences : CDVPlugin

-(void)store:(CDVInvokedUrlCommand*)command;
-(void)fetch:(CDVInvokedUrlCommand*)command;
-(void)remove:(CDVInvokedUrlCommand*)command;
-(void)sms:(CDVInvokedUrlCommand*)command;
-(void)addContact:(CDVInvokedUrlCommand*)command;
-(void)getContactInfo:(CDVInvokedUrlCommand*)command;
-(void)getPhoneModel:(CDVInvokedUrlCommand*)command;
@end

