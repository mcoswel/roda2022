package com.somboi.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.assets.StringRes;
import com.somboi.gdx.entities.PlayerGui;
import com.somboi.gdx.screen.MatchScreen;

public class EndGameDialog extends Dialog {

    public EndGameDialog(Skin skin, PlayerGui winnerGui, TextureAtlas textureAtlas, RodaImpian rodaImpian) {
        super(StringRes.MATCHFINISHED, skin);
        Table table = new Table();
        table.defaults().pad(10f);
        table.center();

        Label winnerName = new Label(winnerGui.getPlayer().name,skin);
        winnerName.setColor(Color.GREEN);
        Label fullScoreLabel = new Label("$"+winnerGui.getPlayer().fullScore, skin);
        fullScoreLabel.setColor(Color.GOLDENROD);
        table.add(winnerGui.getImage()).row();
        table.add(winnerName).row();
        table.add(fullScoreLabel).row();

        Table giftsTable = new Table();
        if (!winnerGui.getPlayer().gifts.isEmpty()){
            int rowIndex = 0;
            for (Integer i: winnerGui.getPlayer().gifts){
                BonusGiftImg bonusGiftImg = BonusGiftImg.getGifts(textureAtlas,i);
                bonusGiftImg.setSize(75f,75f);
                giftsTable.add(bonusGiftImg);
                rowIndex++;
                if (rowIndex%4==0){
                    giftsTable.row();
                }
            }
            giftsTable.pack();
            table.add(giftsTable).row();

        }


        MenuBtn mainMenu = new MenuBtn(StringRes.MAINMENU, skin);
        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
              //  rodaImpian.setMatchScreen(new MatchScreen(rodaImpian));
                rodaImpian.gotoMenu();
            }
        });
        MenuBtn leaderBoard = new MenuBtn(StringRes.LEADERBOARD,skin);
        MenuBtn exit = new MenuBtn(StringRes.EXIT,skin);

        table.add(mainMenu).row();
        table.add(leaderBoard).row();
        table.add(exit).row();
        table.pack();

        this.getContentTable().add(table).width(table.getWidth()).height(table.getHeight()).center();

    }

    @Override
    public float getPrefWidth() {
        return 850f;
    }

}
