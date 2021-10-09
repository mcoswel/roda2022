package com.somboi.rodaimpian;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.somboi.gdx.entities.WheelParam;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
    static public final int port = 51888;
    // This registers objects that are going to be sent over the network.
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Wheel.class);
        kryo.register(RoomID.class);
        kryo.register(WheelParam.class);

    }


    static public class Wheel {
        public float wheelAngle;
    }
    static class RoomID {
        public String roomID;
    }

}