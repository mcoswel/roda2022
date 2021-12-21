package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdxnew.actors.VannaHost;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

public class BaseGame {
    protected final Stage stage;
    protected final RodaImpianNew rodaImpianNew;
    protected final Array<PlayerNew> players = new Array<>();
    protected final VannaHost vannaHost;

    public BaseGame(Stage stage, RodaImpianNew rodaImpianNew) {
        this.stage = stage;
        this.rodaImpianNew = rodaImpianNew;
        vannaHost = new VannaHost(rodaImpianNew.getAssetManager().get(AssetDesc.ATLAS));
        stage.addActor(vannaHost);
    }

    public void addPlayers(){}

    public void wheelTurn(){}

    public void setPlayerPosition(){
        players.shuffle();
    }


}
