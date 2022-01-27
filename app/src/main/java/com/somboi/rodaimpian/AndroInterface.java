package com.somboi.rodaimpian;

import com.somboi.rodaimpian.activities.PlayerOnline;
import com.somboi.rodaimpian.gdxnew.interfaces.OnInterface;

import java.util.List;

public interface AndroInterface {
    void loginFacebook();

    void restart();

    void choosePhoto(int playerNo);

    void loginGmail();

    void getToken();

    void chatOnline(OnInterface onInterface, int guiIndex);

    void leaderboardActivity();

    void uploadToLeaderBoard();

    void chatActivity();

    void commentActivity();

    void logout();

    void getTopPlayers();
}
