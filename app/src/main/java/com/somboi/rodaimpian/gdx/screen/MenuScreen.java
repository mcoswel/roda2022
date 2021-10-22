package com.somboi.rodaimpian.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.PromoDialog;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.base.BaseScreen;
import com.somboi.rodaimpian.gdx.entities.LocalPlayMenu;
import com.somboi.rodaimpian.gdx.entities.MainMenuCreator;

public class MenuScreen extends BaseScreen {
    private final MainMenuCreator mainMenuCreator;
    private final LocalPlayMenu localPlayMenu;
    private final Group mainMenuGroup = new Group();
    private final Group localGroup = new Group();
    public MenuScreen(RodaImpian rodaImpian) {
        super(rodaImpian);
        mainMenuCreator = new MainMenuCreator(rodaImpian, mainMenuGroup, stage, this);
        localPlayMenu = new LocalPlayMenu(rodaImpian, localGroup,skin,textureAtlas, this);
        rodaImpian.setMenuCreator(mainMenuCreator);
        Image bg = new Image(assetManager.get(AssetDesc.BLURBG));
        bg.setSize(900f,1600f);
        bg.setPosition(0,0);
        Image titleBg = new Image(textureAtlas.findRegion("title"));
        titleBg.setSize(900f,365f);
        titleBg.setPosition(0, 1200f );
        stage.addActor(bg);
        stage.addActor(titleBg);
        stage.addActor(mainMenuGroup);

        if (rodaImpian.getPlayerOnline().timesplayed%10==0 && rodaImpian.getPlayerOnline().timesplayed>0) {
            PromoDialog promoDialog = new PromoDialog(skin, rodaImpian.getAssetManager().get(AssetDesc.TEXTUREATLAS).findRegion("sahiba")){
                @Override
                protected void result(Object object) {
                    if (object.equals(true)){
                        rodaImpian.openPlayStore("com.somboi.melayuscrabble");
                    }
                }
            };
            promoDialog.show(stage);
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        localGroup.remove();
        mainMenuCreator.show();
        stage.addActor(mainMenuGroup);

    }

    public void showLocal(){
        mainMenuGroup.remove();
        localPlayMenu.showFirstMenu();
        stage.addActor(localGroup);
    }
    @Override
    public void update(float delta) {
        super.update(delta);
        mainMenuCreator.loadLocalPic();
        localPlayMenu.loadLocalPic();
        localPlayMenu.update();
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            Gdx.app.exit();
        }
    }
}
