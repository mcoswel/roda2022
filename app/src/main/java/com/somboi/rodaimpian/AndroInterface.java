package com.somboi.rodaimpian;

import com.somboi.rodaimpian.gdxnew.interfaces.OnInterface;
import com.somboi.rodaimpian.gdxnew.screens.MainScreen;

public interface AndroInterface {
    void loginFacebook();
    void restart();
    void choosePhoto(int playerNo);
    void loginGmail();
    void getToken();
    void chatOnline(OnInterface onInterface, int guiIndex);
}
