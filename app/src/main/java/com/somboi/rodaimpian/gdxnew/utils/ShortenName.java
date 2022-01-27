package com.somboi.rodaimpian.gdxnew.utils;

public class ShortenName {
    public static String execute(String name){
        name = name.toUpperCase();
        if (name.length()>14){
            return name.substring(0,11);
        }
        return name;
    }
}
