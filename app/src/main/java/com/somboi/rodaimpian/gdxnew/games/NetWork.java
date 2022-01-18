package com.somboi.rodaimpian.gdxnew.games;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

import java.util.ArrayList;
import java.util.List;

public class NetWork {
    public static void register(EndPoint endPoint){
        Kryo kryo = endPoint.getKryo();
        kryo.register(List.class);
        kryo.register(ArrayList.class);
        kryo.register(Float.class);
        kryo.register(PlayerNew.class);
        kryo.register(QuestionNew.class);
    }
}