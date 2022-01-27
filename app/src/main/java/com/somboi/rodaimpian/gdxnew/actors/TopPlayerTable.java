package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.somboi.rodaimpian.activities.PlayerOnline;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;

import java.util.List;

public class TopPlayerTable extends Table {
    private final Skin skin;
    private final List<PlayerOnline> topPlayerList;

    public TopPlayerTable(Skin skin, TextureRegion defaultAvatar, List<PlayerOnline> topPlayerList) {
        this.skin = skin;
        this.topPlayerList = topPlayerList;
        int rank = 1;
        Label title = new Label(StringRes.TOPPLAYER, skin);
        add(title).center().row();
        for (PlayerOnline p: topPlayerList){
            Table subTable = new Table(){
                @Override
                public float getPrefWidth() {
                    return 880f;
                }
            };
            SmallPic smallPic = new SmallPic(defaultAvatar, p.picUri);
            Label rankLbl = new Label(""+rank+".", skin);
            rankLbl.setColor(Color.YELLOW);
            Label nameLbl = new Label(p.name, skin);
            Label scoreLbl = new Label("$"+p.bestScore, skin);
            scoreLbl.setColor(Color.MAGENTA);

            subTable.add(rankLbl).width(50f).center();

            subTable.add(smallPic).size(100f,100f).left().padRight(30f).padLeft(30f);
            subTable.add(nameLbl).width(350f).left().padRight(20f);
            subTable.add(scoreLbl).width(330f).left().padRight(20f);
            add(subTable).padLeft(130f).row();
            rank++;
        }
        pack();
        setPosition(450f-getWidth()/2f,20f);
    }

    @Override
    public float getPrefWidth() {
        return 880f;
    }
}
