package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

import java.util.Map;

public class WinnerDialog extends Dialog {

    public WinnerDialog(Skin skin, PlayerGuis winnerGui, TextureAtlas atlas, RodaImpianNew rodaImpianNew) {
        super(StringRes.MATCHFINISHED, skin);
        Table gifttable = new Table();
        final PlayerNew winner = winnerGui.getPlayerNew();
        Array<Image> giftImages = new Array<>();
        if (winner.getPlayerGifts() != null) {

            for (Map.Entry entry : winner.getPlayerGifts().entrySet()) {
                Image image = new Image(atlas.findRegion(GiftBonuses.getGiftRegion((int) entry.getValue())));
                giftImages.add(image);
            }
        }

        if (!giftImages.isEmpty()) {
            for (int i = 0; i < 6; i++) {
                if (giftImages.size > i) {
                    gifttable.add(giftImages.get(i)).size(75f, 75f);
                }
            }
        }

        Table mainTable = new Table();
        Label winnerIs = new Label(StringRes.PEMENANGIALAH, skin,"arial36");
        mainTable.add(winnerIs).center().row();
        mainTable.defaults().pad(10f);
        try {
            ProfilePic profilePic = (ProfilePic) winnerGui.getProfilePic().clone();
            mainTable.add(profilePic).row();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }


        Label name = new Label(winner.getName(), skin);
        name.setColor(Color.NAVY);
        mainTable.add(name).row();

        Label fullScore = new Label("$" + winner.getFullScore(), skin);
        fullScore.setColor(Color.GREEN);
        mainTable.add(fullScore).row();

        getContentTable().add(mainTable).row();
        getContentTable().add(gifttable).center().row();

        Table buttonTable = new Table();
        buttonTable.defaults().pad(10f);
        SmallButton menu = new SmallButton(StringRes.MAINMENU, skin);
        SmallButton leaderBoard = new SmallButton(StringRes.LEADERBOARD, skin);
        SmallButton exit = new SmallButton(StringRes.EXIT2, skin);
        buttonTable.add(menu).row();
        buttonTable.add(leaderBoard).row();
        buttonTable.add(exit).row();

        getContentTable().add(buttonTable).row();

    }

    @Override
    public float getPrefWidth() {
        return 850f;
    }
}
