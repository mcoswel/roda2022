package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.somboi.rodaimpian.gdx.assets.StringRes;

public class ErrDiag extends Dialog {
    public ErrDiag(String errorText, Skin skin) {
        super(StringRes.ERRORTITLE, skin);
        Label label = new Label(errorText, skin);
        label.setAlignment(Align.center);
        label.setWrap(true);
        getContentTable().add(label).width(825f).row();
        button(StringRes.OK, true);
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        if (object.equals(true)){
            func();
        }
    }

    public void func(){
        hide();
    }

    @Override
    public float getPrefWidth() {
        return 850f;
    }
}
