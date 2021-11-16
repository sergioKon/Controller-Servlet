package com.utils.common;

public class Converter {
    public static  String removeApostrophe(String body) {
        if(body==null) {
            return "";
        }
        int iStart= body.indexOf("\"")+1;
        int iEnd= body.lastIndexOf("\"");
        if(  (iStart!=0 )  && (iEnd != -1)  ) {
            return body.substring(iStart,iEnd) ;
        }
        return body;
    }

    public static String addApostrophe(String value) {
        return "\"" + value +  "\"";
    }
}
