package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.gdx.entities.MatchRound;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.online.entities.CheckAnswer;

public class VocalKeyboard {
    private final Skin skin;
    private final Table vocalTable = new Table();
    private final Stage stage;
    private final MatchRound matchRound;
    private int count;
    private boolean bonusRound;
    public VocalKeyboard(Skin skin, MatchRound matchRound, Stage stage) {
        this.skin = skin;
        this.stage = stage;
        this.matchRound = matchRound;
    }

    private void updateTable() {
        String vocals = matchRound.getVocals();
        vocalTable.clear();
        for (int i = 0; i < vocals.length(); i++) {
            TextButton textButton = new TextButton(String.valueOf(vocals.charAt(i)), skin) {
                @Override
                public float getPrefWidth() {
                    return 150f;
                }
            };
            final char cFinal = vocals.charAt(i);
            final int iFinal = i;
            textButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (bonusRound) {
                        textButton.remove();
                        matchRound.addBonusString(cFinal);
                        count++;
                        if (count == 2) {
                            vocalTable.remove();
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    matchRound.checkBonusString();

                                }
                            }, 2f);
                        }
                        return;
                    }
                    StringBuilder builder = new StringBuilder(vocals);
                    builder.deleteCharAt(iFinal);
                    matchRound.setVocals(builder.toString());
                    if (matchRound.getRodaImpian().getGameModes().equals(GameModes.ONLINE) && !bonusRound) {
                        CheckAnswer checkAnswer = new CheckAnswer();
                        checkAnswer.character = cFinal;
                      //  matchRound.getModeBase().sendObject(GameState.BUYVOCAL);
                        matchRound.getModeBase().sendObject(checkAnswer);
                    } else {
                        matchRound.checkAnswer(cFinal);
                    }
                    vocalTable.remove();


                }
            });
            vocalTable.add(textButton);

        }

        vocalTable.pack();
        vocalTable.setPosition(450f - vocalTable.getWidth() / 2f, 680f);
        vocalTable.center();
    }

    public void show() {
        updateTable();
        stage.addActor(vocalTable);
    }

    public void hide() {
        vocalTable.remove();
    }

    public void setBonusRound(boolean bonusRound) {
        this.bonusRound = bonusRound;
    }
}
