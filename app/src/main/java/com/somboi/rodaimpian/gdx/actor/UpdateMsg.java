package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;

public class UpdateMsg extends Dialog {
    private final RodaImpian rodaImpian;

    public UpdateMsg(RodaImpian rodaImpian, String messages, Skin skin){
        super(StringRes.UPDATENEWS,skin);
        this.rodaImpian = rodaImpian;
        Label updateLabel = new Label(messages, skin){
            @Override
            public float getPrefWidth() {
                return 600f;
            }
        };
        updateLabel.setWrap(true);
        PlayerImage playerImage = new PlayerImage(rodaImpian.getAssetManager().get(AssetDesc.TEXTUREATLAS).findRegion("iconss"));
        this.getContentTable().defaults().pad(15f);
        this.getContentTable().add(playerImage).row();
        this.getContentTable().add(updateLabel).row();
        this.button(StringRes.UPDATENEWS,true);
        this.button(StringRes.NO3,false);
    }

    @Override
    protected void result(Object object) {
        if (object.equals(true)){
            rodaImpian.openPlayStore("com.somboi.rodaimpian");
        }
    }
}
