package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.screens.MainScreen;

public class ProfileTable extends Table {
    private final Label nameLabel;
    private final PlayerNew playerNew;
    private final MainScreen mainScreen;
    private final ProfilePic profilPic;
    private final int playerNo;
    public ProfileTable(TextureRegion defaultAvatar,
                        Skin skin,
                        PlayerNew playerNew,
                        int playerNo,
                        boolean isGold,
                        MainScreen mainScreen) {
        this.playerNew = playerNew;
        this.mainScreen = mainScreen;
        this.playerNo = playerNo;
        Table leftTable = new Table();
        leftTable.defaults().center().pad(5f);

        profilPic = new ProfilePic(defaultAvatar, playerNew.getPicUri(), playerNew, playerNo);
        leftTable.add(profilPic).size(250f, 250f).row();
        SmallButton uploadPhoto = new SmallButton(StringRes.CHOOSEPHOTO, skin);

        uploadPhoto.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainScreen.uploadPhoto(playerNo);
            }
        });
        leftTable.add(uploadPhoto).size(250f, 80f).row();


        Table rightTable = new Table();
        rightTable.defaults().center().pad(5f);

        nameLabel = new Label(playerNew.getName(), skin, "big");
        SmallButton changeName = new SmallButton(StringRes.CHANGENAME, skin);
        changeName.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                DialogChangeName dialogChangeName = new DialogChangeName( skin, ProfileTable.this);
                dialogChangeName.show(getStage());
            }
        });
        Label scoreLabel = new Label(StringRes.BESTSCORE, skin);
        scoreLabel.setColor(Color.PURPLE);
        Label scoreLabelNumber = new Label("$" + playerNew.getBestScore(), skin);
        rightTable.add(nameLabel).row();
        rightTable.add(changeName).row();

        if (playerNo != 1){
            scoreLabel.setText(StringRes.PLAYER_NAME);
            scoreLabelNumber.setText(""+playerNo);
        }
        rightTable.add(scoreLabel).row();
        rightTable.add(scoreLabelNumber).row();
        //leftTable.setBackground(skin.getDrawable("smallnormal"));
        this.setBackground(skin.getDrawable("smallnormal"));
        if (isGold) {
            this.setColor(Color.GOLDENROD);
        } else {
            this.setColor(Color.RED);
        }
        this.add(leftTable).size(280f, 380f);
        this.add(rightTable).size(580f, 380f);
        this.center();
        this.pack();
    }

    public void changeName(String name) {
        nameLabel.setText(name);
        playerNew.setName(name);
        mainScreen.savePlayer(playerNew, playerNo);
    }
    public void reloadPicUrl(String picUri){
        profilPic.reloadUrl(picUri);
    }
}
