/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sales.httptest;

import com.framework.context.ApplicationContextBuilder;

/**
 *
 * @author nelson
 */
public class Test {
    public static void main(String[] args){
         String json= "{\n" +
"    \"session\": \"eyJ1c2VyTmFtZSI6IuWkp+S4sOaUtiIsInRva2VuIjoiODRiYmRmNTljYTAyNmI1Y2FkOWUyMThjZGFjMCIsInVzZXJJZCI6IjEiLCJvcmdVc2VySWQiOiItMSIsIm9yZ0lkIjoiLTEiLCJvcmdUeXBlIjoiLTEiLCJyZWdpb25JZCI6IjEiLCJjaGlsZE9yZ0lkcyI6Ii0xIn0=\",\n" +
"    \"encryptType\": \"1\",\n" +
"    \"data\": \"XXqbAyxuxBx/MehN6KqboU25WCxnoXrG2B+IvTtGlAweGTM4cqKBPoQ+o4SEJyyrdGzy80a+Qwe2\n" +
"f6tsPe8mUwp/LWjY/r0k/+RLL5yKb09HUrDwMf/14sE8J4EoeHh4U/ElRITzWZwWKyBM/SgUgyl3\n" +
"tuCIsiG0wWoeUoWn4C/3AFxe4Owb7Z0s/3oqFeHpHds8UN4ex/Y7dsnzeStdmzJPIaqFIJZ3kZ2l\n" +
"mfjV8a8bfxC3TRhR80NLN9cBC5bu\"\n" +
"}";
         json = json.replace("\n", "");
         System.out.println(json);
    }
}
