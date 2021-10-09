package com.somboi.gdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.actor.ErrorDialog;
import com.somboi.gdx.actor.MenuBtn;
import com.somboi.gdx.assets.AssetDesc;
import com.somboi.gdx.assets.StringRes;
import com.somboi.gdx.base.ModeBase;

public class MenuButtons {
    private final RodaImpian rodaImpian;
    private final Skin skin;
    private final Table menuTable = new Table();
    private final Group menuGroup;
    private final MenuBtn vocals;
    private final MenuBtn spin;
    private final Table menuCompleteTable = new Table();

    public MenuButtons(Group menuGroup, RodaImpian rodaImpian, ModeBase modeBase) {
        this.skin = rodaImpian.getAssetManager().get(AssetDesc.SKIN);
        this.rodaImpian = rodaImpian;
        this.menuGroup = menuGroup;

        vocals = new MenuBtn(StringRes.VOKAL, skin);
        vocals.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (modeBase.getActivePlayer().currentScore >= 250) {
                    modeBase.showVocals();
                    modeBase.getActivePlayer().currentScore -= 250;
                    hideMenu();
                } else {
                    ErrorDialog errorDialog = new ErrorDialog(StringRes.NOTENOUGHMONEY, skin);
                    errorDialog.show(menuGroup.getStage());
                }

            }
        });

        spin = new MenuBtn(StringRes.PUTAR, skin);
        spin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.spinWheel();
                hideMenu();
            }
        });
        MenuBtn complete = new MenuBtn(StringRes.LENGKAPKAN, skin);
        complete.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                modeBase.completePuzzle();
                showCompleteMenu();
            }
        });
        menuTable.add(vocals);
        menuTable.add(spin);
        menuTable.add(complete);
        menuTable.setPosition(450f, 732f);
        menuTable.center();

        MenuBtn resetPuzzle = new MenuBtn(StringRes.RESET, skin);
        resetPuzzle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                modeBase.completePuzzle();
            }
        });

        MenuBtn showKeyboard = new MenuBtn(StringRes.SHOWKEYBOARD, skin);
        showKeyboard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setOnscreenKeyboardVisible(true);
            }
        });

        MenuBtn submitAnswers = new MenuBtn(StringRes.SUBMIT, skin);
        submitAnswers.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                modeBase.checkCompleteAnswer();
            }
        });

        menuCompleteTable.add(resetPuzzle);
        menuCompleteTable.add(showKeyboard);
        menuCompleteTable.add(submitAnswers);
        menuCompleteTable.setPosition(450f, 732f);
        menuCompleteTable.center();

    }

    public void showMenu(boolean haveConsonants, boolean haveVocals) {
        if (!haveConsonants) {
            spin.remove();
        }
        if (!haveVocals) {
            vocals.remove();
        }
        menuGroup.addActor(menuTable);
    }

    public void showCompleteMenu() {
        menuTable.remove();
        menuGroup.addActor(menuCompleteTable);
    }

    public void hideMenu() {
        menuCompleteTable.remove();
        menuTable.remove();
    }
}
