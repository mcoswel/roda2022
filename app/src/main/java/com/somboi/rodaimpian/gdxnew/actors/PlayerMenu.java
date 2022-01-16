package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdxnew.games.BaseGame;

public class PlayerMenu {
    private final Stage stage;
    private final BaseGame baseGame;
    private final Skin skin;
    private final Table menuTable = new Table();
    private final Table completeTable = new Table();
    private final Array<Character> vocalLetter = new Array<>();
    private final Array<Character> consonantLetter = new Array<>();
    private final SmallButton vocal;
    private final SmallButton spin;
    private boolean bonusMode;
    private int consonantCounter;
    private int vocalCounter;
    private String bonusStringHolder = "";
    private final Logger logger = new Logger(this.getClass().getName(), 3);

    public PlayerMenu(Stage stage, BaseGame baseGame, Skin skin) {
        this.stage = stage;
        this.baseGame = baseGame;
        this.skin = skin;

        vocalLetter.addAll(GameConfig.VOCALS);
        consonantLetter.addAll(GameConfig.CONSONANTS);

        vocal = new SmallButton(StringRes.VOKAL, skin);
        vocal.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (baseGame.getActivePlayer().getScore() >= 250) {
                    clear();
                    baseGame.getActivePlayer().setScore(baseGame.getActivePlayer().getScore() - 250);
                    createVocalTable();
                } else {
                    ErrDiag errDiag = new ErrDiag(StringRes.NOTENOUGHMONEY, skin);
                    errDiag.show(stage);
                }
            }
        });
        spin = new SmallButton(StringRes.PUTAR, skin);
        spin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                clear();
                baseGame.spinWheel(false, false);
            }
        });
        SmallButton complete = new SmallButton(StringRes.LENGKAPKAN, skin);
        complete.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    if (baseGame.completePuzzle()) {
                        clear();
                    }
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        });
        SmallButton exit = new SmallButton(StringRes.EXIT, skin);
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                YesNoDiag yesNoDiag = new YesNoDiag(StringRes.STOPPLAYING, skin) {
                    @Override
                    public void yesFunc() {
                        baseGame.mainMenu();
                    }
                };
                yesNoDiag.show(stage);
            }
        });
        Table first = new Table();
        first.defaults().pad(5f);
        first.add(vocal).size(850f / 2f, 75f);
        first.add(spin).size(850f / 2f, 75f);
        Table second = new Table();
        second.defaults().pad(5f);
        second.add(complete).size(850f / 2f, 75f);
        second.add(exit).size(850f / 2f, 75f);
        createCompleteTable();

        menuTable.add(first).row();
        menuTable.add(second).row();
        menuTable.pack();
        menuTable.setPosition(450f - menuTable.getWidth() / 2f, 630f);
    }

    public void hideComplete() {
        completeTable.remove();
    }

    private void createCompleteTable() {
        Table first = new Table();
        first.defaults().pad(5f);
        SmallButton submitAnswer = new SmallButton(StringRes.SUBMIT, skin);
        submitAnswer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    baseGame.checkCompleteAnswer();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        });
        SmallButton keyboard = new SmallButton(StringRes.SHOWKEYBOARD, skin);
        keyboard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.input.setOnscreenKeyboardVisible(true);
            }
        });
        first.add(keyboard).size(850f / 2f, 75f);
        first.add(submitAnswer).size(850f / 2f, 75f);

        completeTable.add(first);
        completeTable.setPosition(450f - menuTable.getWidth() / 2f, 788f);
    }


    public boolean vocalAvailable() {
        return vocalLetter.size > 0;
    }

    public boolean consonantAvailable() {
        return consonantLetter.size > 0;
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

        int index = 0;
        for (Character character : consonantLetter) {
            String c = String.valueOf(character);
            final SmallButton smallButton = new SmallButton(c, skin);
            smallButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!bonusMode) {
                        baseGame.checkAnswer(character);
                        consDialog.hide();
                    } else {
                        bonusStringHolder += character;
                        consonantCounter++;
                        smallButton.remove();
                        if (consonantCounter == 5) {
                            consDialog.hide();
                            createVocalTable();
                        }
                    }
                }
            });
            consDialog.getContentTable().add(smallButton).size(150f, 100f);
            index++;
            if (index % 5 == 0) {
                consDialog.getContentTable().row();
            }
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


        for (Character character : vocalLetter) {
            String c = String.valueOf(character);
            final SmallButton smallButton = new SmallButton(c, skin);
            smallButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!bonusMode) {
                        baseGame.checkAnswer(character);
                        vocalDiag.hide();
                    } else {
                        bonusStringHolder += character;
                        vocalCounter++;
                        smallButton.remove();
                        if (vocalCounter == 2) {
                            vocalDiag.hide();
                            baseGame.checkBonusString(bonusStringHolder);
                        }
                    }
                }
            });
            vocalDiag.getContentTable().add(smallButton).size(150f, 100f);
        }
        vocalDiag.show(stage);
    }

    public void clear() {
        menuTable.remove();
    }

    public void showCompleteMenu() {
        stage.addActor(completeTable);
    }

    public void show() {
        if (!vocalAvailable()) {
            vocal.remove();
        }

        if (!consonantAvailable()) {
            spin.remove();
        }
        stage.addActor(menuTable);
    }

    public Array<Character> getVocalLetter() {
        return vocalLetter;
    }

    public Array<Character> getConsonantLetter() {
        return consonantLetter;
    }

    public void removeLetter(Character c) {
        Array<Character> toberemoved = new Array<>();
        for (Character character : vocalLetter) {
            if (character == c) {
                toberemoved.add(character);
            }
        }
        vocalLetter.removeAll(toberemoved, false);
        toberemoved.clear();

        for (Character character : consonantLetter) {
            if (character == c) {
                toberemoved.add(character);
            }
        }
        consonantLetter.removeAll(toberemoved, false);
        toberemoved.clear();
    }


    public void setBonusMode(boolean bonusMode) {
        this.bonusMode = bonusMode;
    }

    public boolean isBonusMode() {
        return bonusMode;
    }

}
