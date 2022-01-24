package com.somboi.rodaimpian.gdxnew.interfaces;

public interface OnInterface {
    void sendObjects(Object o);
    boolean isHost();
    boolean isBanned(String roomID);
    void updateStatusLabel(String text);
    boolean isTurn();
}
