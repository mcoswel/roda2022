package com.somboi.rodaimpian.gdx.online.entities;

import com.somboi.rodaimpian.gdx.online.newentities.SessionRoom;

import java.util.List;

public class SessionList {
    public List<SessionRoom> sessionRoomList;
    public void add(SessionRoom sessionRoom){
        sessionRoomList.add(sessionRoom);
    }
}