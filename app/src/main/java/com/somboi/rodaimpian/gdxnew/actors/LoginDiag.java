package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.StringRes;

public class LoginDiag extends Dialog {
    private final RodaImpianNew rodaImpianNew;
    public LoginDiag(Skin skin, RodaImpianNew rodaImpianNew) {
        super(StringRes.LOGINFB, skin);
        this.rodaImpianNew = rodaImpianNew;
        Label label = new Label(StringRes.LOGINFBFORONLINE, skin);
        label.pack();
        label.setWrap(true);
        getContentTable().defaults().pad(8f);
        SmallButton fb = new SmallButton(StringRes.FACEBOOK,skin){
            @Override
            public float getPrefWidth() {
                return 260;
            }
        };
        SmallButton gmail = new SmallButton(StringRes.GMAIL,skin){
            @Override
            public float getPrefWidth() {
                return 260;
            }
        };
        SmallButton cancel = new SmallButton(StringRes.NO3,skin){
            @Override
            public float getPrefWidth() {
                return 260;
            }
        };
        getContentTable().add(label).width(800f).center().row();
       button(fb, "fb");
       button(gmail, "gmail");
       button(cancel, "cancel");
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        if (object.equals("fb")){
            rodaImpianNew.loginFacebook();
        }else if (object.equals("gmail")){
            rodaImpianNew.loginGmail();
        }
        hide();
    }

    @Override
    public float getPrefWidth() {
        return 850f;
    }
}
