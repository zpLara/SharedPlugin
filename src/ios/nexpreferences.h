//
//  CDVnexpreferences.h
//  SMARTBill
//
//  Created by nexlabs on 18/12/14.
//
//

#import <Cordova/CDV.h>

@interface nexpreferences : CDVPlugin

-(void)store:(CDVInvokedUrlCommand*)command;
-(void)fetch:(CDVInvokedUrlCommand*)command;
-(void)remove:(CDVInvokedUrlCommand*)command;
-(void)invokeSMS:(CDVInvokedUrlCommand*)command;
-(void)invokeContact:(CDVInvokedUrlCommand*)command;
-(void)getContactInfo:(CDVInvokedUrlCommand*)command;
@end

