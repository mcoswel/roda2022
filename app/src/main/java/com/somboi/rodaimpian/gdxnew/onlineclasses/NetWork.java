package com.somboi.rodaimpian.gdxnew.onlineclasses;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.wheels.WheelParams;

import java.util.ArrayList;
import java.util.List;

public class NetWork {
    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(List.class);
        kryo.register(ArrayList.class);
        kryo.register(Float.class);
        kryo.register(PlayerNew.class);
        kryo.register(QuestionNew.class);
        kryo.register(RoomSession.class);
        kryo.register(JoinSession.class);
        kryo.register(RoomLists.class);
        kryo.register(Disconnect.class);
        kryo.register(KickPlayer.class);
        kryo.register(ChatOnline.class);
        kryo.register(Occupied.class);
        kryo.register(ChooseLetter.class);
        kryo.register(WheelParams.class);
        kryo.register(PlayerStates.class);
        kryo.register(GameState.class);

    }
}