package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.entities.Bonus;
import com.somboi.rodaimpian.gdx.entities.PlayerGui;
import com.somboi.rodaimpian.gdx.screen.LoadingScreen;

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
                bonusGiftImg.setSize(50f,50f);
                giftsTable.add(bonusGiftImg);
                rowIndex++;
                if (rowIndex%4==0){
                    giftsTable.row();
                }
            }

            giftsTable.pack();
            table.add(giftsTable).row();
        }
        if (winnerGui.getPlayer().bonusIndex>0){
            BonusGiftImg bonusGiftImg = BonusGiftImg.getBonus(textureAtlas, winnerGui.getPlayer().bonusIndex);
            bonusGiftImg.setSize(50f,50f);
            table.add(bonusGiftImg).row();
        }



        MenuBtn mainMenu = new MenuBtn(StringRes.MAINMENU, skin);
        mainMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
              //  rodaImpian.setMatchScreen(new MatchScreen(rodaImpian));
                rodaImpian.restart();
            }
        });
        MenuBtn leaderBoard = new MenuBtn(StringRes.LEADERBOARD,skin);
        leaderBoard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.gotoLeaderBoard();
            }
        });
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
