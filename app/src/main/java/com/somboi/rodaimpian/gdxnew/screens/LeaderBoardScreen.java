package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.somboi.rodaimpian.activities.PlayerOnline;
import com.somboi.rodaimpian.activities.RodaImpianNew;
import com.somboi.rodaimpian.gdxnew.actors.Bonuses;
import com.somboi.rodaimpian.gdxnew.actors.Gifts;
import com.somboi.rodaimpian.gdxnew.actors.ProfilePic;
import com.somboi.rodaimpian.gdxnew.assets.AssetDesc;
import com.somboi.rodaimpian.gdxnew.assets.GameConfig;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.interfaces.BoardInterface;
import com.somboi.rodaimpian.gdxnew.onlineclasses.GiftsNew;
import com.somboi.rodaimpian.gdxnew.utils.CopyPlayer;
import com.somboi.rodaimpian.saves.PlayerSaves;

public class LeaderBoardScreen extends ScreenAdapter implements BoardInterface {
    private final FitViewport hudViewport = new FitViewport(900f, 1600f);
    private final Stage stage = new Stage(hudViewport);
    private final RodaImpianNew rodaImpianNew;
    private final AssetManager assetManager;
    private final Skin skin;
    private final TextureAtlas atlas;
    private final PlayerSaves saves = new PlayerSaves();
    private final Label loadingLabel;
    private final Label leaderboardLabel;

    public LeaderBoardScreen(RodaImpianNew rodaImpianNew) {
        this.rodaImpianNew = rodaImpianNew;
        this.assetManager = rodaImpianNew.getAssetManager();
        this.skin = assetManager.get(AssetDesc.NEWSKIN);
        this.atlas = assetManager.get(AssetDesc.ATLAS);
        loadingLabel = new Label(StringRes.LOADING, skin);
        loadingLabel.setPosition(450f-loadingLabel.getWidth()/2f,900f);
        leaderboardLabel = new Label(StringRes.LEADERBOARD, skin);
        leaderboardLabel.setPosition(450f-leaderboardLabel.getWidth()/2f,1600f - leaderboardLabel.getHeight());
        rodaImpianNew.getLeaderBoardPlayers(this);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addActor(loadingLabel);
        stage.addActor(leaderboardLabel);
    }

    @Override
    public void render(float delta) {
        GameConfig.clearScreen();
        stage.act();
        stage.draw();
        stage.setDebugAll(true);
    }

    @Override
    public void updateTable(Array<PlayerOnline> playerOnlineArray) {
        Table table = new Table();

        if (playerOnlineArray.isEmpty()){
            loadingLabel.setText(StringRes.NORECORD);
            loadingLabel.setPosition(450f-loadingLabel.getWidth()/2f,900f);
        }else{
            int rank = 1;
            for (PlayerOnline playerOnline:playerOnlineArray){
                Table playerInfoTable =  new Table(){
                    @Override
                    public float getPrefWidth() {
                        return 880f;
                    }
                };
                PlayerNew playerNew = CopyPlayer.getPlayer(playerOnline);
                ProfilePic profilePic = new ProfilePic(atlas.findRegion("defaultavatar"),playerNew,1);

                playerInfoTable.add(new Label(""+rank+".",skin)).pad(5f).left();
                playerInfoTable.add(profilePic).size(150,150).left();

                playerInfoTable.add(new Label(playerNew.getName(),skin)).expand();
                Label scoreLabel = new Label("$"+playerNew.getBestScore(),skin);
                scoreLabel.setColor(Color.GOLDENROD);
                playerInfoTable.add(scoreLabel).expand();

                Table giftTable = new Table();
                for (int giftIndex: playerNew.getPlayerGifts()){
                    Image image = new Image(atlas.findRegion(Gifts.getGiftRegion(giftIndex)));
                    giftTable.add(image).size(80f,80f);
                }
                Table bonusTable = new Table();
                for (int bonusIndex: playerNew.getPlayerBonus()){
                    Image image = new Image(atlas.findRegion(Bonuses.getBonusRegion(bonusIndex)));
                    bonusTable.add(image).size(80f,80f);
                }

                Table rightTable = new Table();
                rightTable.add(bonusTable);
                rightTable.add(giftTable);

                Table playeTable = new Table(){
                    @Override
                    public float getPrefWidth() {
                        return 880f;
                    }

                    @Override
                    public float getPrefHeight() {
                        return 250f;
                    }
                };
                playeTable.add(playerInfoTable).row();
                playeTable.add(rightTable);
                playeTable.setBackground(skin.getDrawable("smallnormal"));
                table.add(playeTable).row();
                rank++;
            }

            Table content = new Table();
            ScrollPane scrollPane = new ScrollPane(table);
            content.add(scrollPane);
            content.setSize(900f, 1500f);
            content.setPosition(450f-content.getWidth()/2f, content.getY());
            stage.addActor(content);
        }

    }

    @Override
    public void resize(int width, int height) {
        hudViewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
