package com.somboi.rodaimpian.gdx.online;

import java.util.List;

public class SessionList {
    public List<SessionRoom> sessionRoomList;
    public void add(SessionRoom sessionRoom){
        sessionRoomList.add(sessionRoom);
    }
}