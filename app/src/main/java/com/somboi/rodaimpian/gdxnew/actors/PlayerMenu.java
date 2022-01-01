package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.games.BaseGame;

public class PlayerMenu {
    private final Stage stage;
    private final BaseGame baseGame;
    private final Skin skin;
    private final Table menuTable = new Table();
    private final Table vocalKey = new Table();
    private StringBuilder vocalLetter = new StringBuilder("AEIOU");
    private StringBuilder consonantLetter = new StringBuilder("BCDFGHJKLMNPQRSTVWXYZ");

    public PlayerMenu(Stage stage, BaseGame baseGame, Skin skin) {
        this.stage = stage;
        this.baseGame = baseGame;
        this.skin = skin;
        SmallButton vocal = new SmallButton(StringRes.VOKAL, skin);
        vocal.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (baseGame.getActivePlayer().getScore()>=250) {
                    clear();
                    baseGame.getActivePlayer().setScore(baseGame.getActivePlayer().getScore()-250);
                    createVocalTable();
                }else{
                    ErrDiag errDiag = new ErrDiag(StringRes.NOTENOUGHMONEY, skin);
                    errDiag.show(stage);
                }
            }
        });
        SmallButton spin = new SmallButton(StringRes.PUTAR, skin);
        spin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clear();
                baseGame.spinWheel();
            }
        });
        SmallButton complete = new SmallButton(StringRes.LENGKAPKAN, skin);
        SmallButton exit = new SmallButton(StringRes.EXIT, skin);
        Table first = new Table();
        first.defaults().pad(5f);
        first.add(vocal).size(850f / 2f, 75f);
        first.add(spin).size(850f / 2f, 75f);
        Table second = new Table();
        second.defaults().pad(5f);
        second.add(complete).size(850f / 2f, 75f);
        second.add(exit).size(850f / 2f, 75f);

        menuTable.add(first).row();
        menuTable.add(second).row();
        menuTable.pack();
        menuTable.setPosition(450f - menuTable.getWidth() / 2f, 630f);

    }


    public void createConsonantsTable() {
        final Dialog consDialog = new Dialog(StringRes.CONSONANTS, skin) {
            @Override
            public float getPrefWidth() {
                return 900f;
            }
        };
        consDialog.getContentTable().defaults().pad(8f);
        consDialog.getContentTable().padTop(8f);
        for (int i = 0; i < consonantLetter.length(); i++) {
            if (i > 0 && i % 5 == 0) {
                consDialog.getContentTable().row();
            }
            String c = String.valueOf(consonantLetter.charAt(i));
            final SmallButton smallButton = new SmallButton(c, skin);
            final int i2 = i;
            smallButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    baseGame.checkAnswer(c);
                    consonantLetter.deleteCharAt(i2);
                    consDialog.hide();
                }
            });
            consDialog.getContentTable().add(smallButton).size(150f, 100f);

        }
        consDialog.show(stage);

    }

    private void createVocalTable() {
        final Dialog vocalDiag = new Dialog(StringRes.VOKAL, skin) {
            @Override
            public float getPrefWidth() {
                return 900f;
            }
        };
        vocalDiag.getContentTable().defaults().pad(8f);
        vocalDiag.getContentTable().padTop(8f);

        for (int i = 0; i < vocalLetter.length(); i++) {
            String c = String.valueOf(vocalLetter.charAt(i));
            final SmallButton smallButton = new SmallButton(c, skin);
            final int i2 = i;
            smallButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    baseGame.checkAnswer(c);
                    vocalLetter.deleteCharAt(i2);
                    vocalDiag.hide();
                }
            });
            vocalDiag.getContentTable().add(smallButton).size(150f, 100f);
        }
        vocalDiag.show(stage);
    }

    public void clear() {
        menuTable.remove();
    }

    public void show() {
        stage.addActor(menuTable);
    }
}
