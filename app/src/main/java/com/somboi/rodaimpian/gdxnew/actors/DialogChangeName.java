package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.screens.MainScreen;

public class DialogChangeName extends Dialog {
    private final Label nameLabel;
    private final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final Table keyTable = new Table();
    private int index;
    private String name="";

    public DialogChangeName(Skin skin, ProfileTable profileTable) {
        super(StringRes.CHANGENAME, skin);
        nameLabel = new Label(name, skin,"big");
        keyTable.defaults().pad(8f);
        for (char c : letters.toCharArray()) {
            SmallButton letter = new SmallButton(String.valueOf(c), skin);
            final char c1 = c;
            letter.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    addChar(c1);
                }
            });
            keyTable.add(letter).size(150f,100f);
            index++;
            if (c == '3'){
                keyTable.row();
            }
            if (c == '8'){
                keyTable.row();
            }
            if (c == '9'){
                SmallButton bspace = new SmallButton("<", skin);
                keyTable.add(bspace).size(150f,100f);
                bspace.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        deleteChar();
                    }
                });

                SmallButton okay = new SmallButton("OK", skin);
                keyTable.add(okay).size(150f,100f);
                okay.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (name.length()>0) {
                            profileTable.changeName(name);
                        }
                        DialogChangeName.this.hide();

                    }
                });
                keyTable.row();
            }
            if (index % 5 == 0 && index < 26) {
                keyTable.row();
            }
        }

        this.getContentTable().defaults().center();
        this.getContentTable().add(nameLabel).padTop(8f).padBottom(8f).row();
        this.getContentTable().add(keyTable);
    }

    @Override
    public float getPrefWidth() {
        return 900f;
    }

    private void addChar(char c) {
        if (name.length() < 13) {
            name += c;
        }
        updateName();
    }

    private void deleteChar() {
        if (name.length() > 0) {
            name = name.substring(0, name.length() - 1);
        } else {
            name = "";
        }
        updateName();
    }

    private void updateName(){
        nameLabel.setText(name);
    }
}
