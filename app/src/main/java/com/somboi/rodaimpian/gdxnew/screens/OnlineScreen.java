package com.somboi.rodaimpian.gdxnew.screens;

import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdxnew.games.ClientNew;

public class OnlineScreen extends BaseScreenNew{
    public OnlineScreen(RodaImpianNew rodaImpianNew) {
        super(rodaImpianNew);
        ClientNew clientNew = new ClientNew(rodaImpianNew);
    }
}
