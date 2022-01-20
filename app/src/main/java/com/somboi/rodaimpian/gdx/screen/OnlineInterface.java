package com.somboi.rodaimpian.gdx.screen;

import com.somboi.rodaimpian.gdx.actor.StatusLabel;
import com.somboi.rodaimpian.gdx.entities.WheelParam;
import com.somboi.rodaimpian.gdx.online.ChatOnlineOld;
import com.somboi.rodaimpian.gdx.online.entities.EnvelopeIndex;
import com.somboi.rodaimpian.gdx.online.newentities.CreateSessions;

public interface OnlineInterface {
    void checkDisconnected(String id);
    void setWheelParam(WheelParam wheelParam);
    void checkChar(Character c);
    void setActivePlayer(int index);
    void setWheelParamResult(WheelParam wheelParam);
    void setGiftOnline(int giftIndex);
    void hostLost();
    void startPlays();
    void showMenu();
    void showVocals();
    void roomFull();
    void kickedOut();
    void revealAll();
    void newRound();
    void updateSession(CreateSessions createSessions);
    void removeSession(String sessionID);
    void updateOwnSession();
    void queueChat(ChatOnlineOld chatOnlineOld);
    void setBonusOnline(int bonusIndex);
    void bonusRound();
    void openEnvelopes(EnvelopeIndex envelopeIndex);
    void checkBonusString(String holder);
    StatusLabel getStatus();

}
