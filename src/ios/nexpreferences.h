//
//  CDVnexpreferences.h
//  SMARTBill
//
//  Created by nexlabs on 18/12/14.
//
//

#import <Cordova/CDV.h>

@interface nexpreferences : CDVPlugin

-(void)store:(CDVInvokeUrlCommand*)command;
-(void)fetch:(CDVInvokeUrlCommand*)command;
-(void)remove:(CDVInvokeUrlCommand*)command;
-(void)invokeSMS:(CDVInvokeUrlCommand*)command;
-(void)invokeContact:(CDVInvokeUrlCommand*)command;
-(void)getContactInfo:(CDVInvokeUrlCommand*)command;
@end

