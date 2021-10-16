package com.somboi.rodaimpian.gdx.online;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import com.esotericsoftware.kryonet.EndPoint;
import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.android.Rooms;
import com.somboi.rodaimpian.gdx.assets.QuestionsReady;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.WheelParam;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
    static public final int port = 5001;

    // This registers objects that are going to be sent over the network.
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(PlayerOnline.class);
        kryo.register(RegisterName.class);
        kryo.register(List.class);
        kryo.register(ArrayList.class);
        kryo.register(Rooms.class);
        kryo.register(MatchRoom.class);
        kryo.register(QuestionsReady.class);
        kryo.register(WheelParam.class);
        kryo.register(Player.class);
        kryo.register(GameState.class);
        kryo.register(RegisterPlayer.class);
        kryo.register(StatusText.class);
        kryo.register(Float.class);
        kryo.register(ChatOnline.class);
        kryo.register(CheckAnswer.class);
        kryo.register(Character.class);
        kryo.register(BeginSpin.class);

    }
}