package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;

public class YesNoDiag extends Dialog {

    public YesNoDiag(String text, Skin skin) {
        super("", skin);
        Label label = new Label(text, skin);
        label.setWrap(true);
        getContentTable().defaults().pad(8f);
        getContentTable().add(label).width(800f).center().row();
        button(new SmallButton(StringRes.YES,skin){
            @Override
            public float getPrefWidth() {
                return 200f;
            }
        },true);
        button(new SmallButton(StringRes.NO, skin){
            @Override
            public float getPrefWidth() {
                return 200f;
            }
        }, false);

    }

    public void yesFunc(){
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        if (object.equals(true)){
            yesFunc();
        }else{
            hide();
        }
    }

    @Override
    public float getPrefWidth() {
        return 850f;
    }
}
