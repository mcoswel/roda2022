package com.somboi.rodaimpian.gdx.utils;

public class ShortenName {
    public static String execute(String name){
        if (name.length()>14){
            return name.substring(0,11);
        }
        return name;
    }
}
