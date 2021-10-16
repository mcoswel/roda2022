package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.gdx.entities.MatchRound;
import com.somboi.rodaimpian.gdx.online.CheckAnswer;

public class ConsonantKeyboard {
    private final Table consonantTable = new Table();
    private final Stage stage;
    private final Skin skin;
    private final MatchRound matchRound;
    private int count;

    public ConsonantKeyboard(Skin skin, MatchRound matchRound, Stage stage) {
        this.skin = skin;
        this.stage = stage;
        this.matchRound = matchRound;
    }


    private void updateTable() {
        consonantTable.clear();
        String consonants = matchRound.getConsonants();
        for (int i = 0; i < consonants.length(); i++) {
            TextButton textButton = new TextButton(String.valueOf(consonants.charAt(i)), skin) {
                @Override
                public float getPrefWidth() {
                    return 125f;
                }
            };
            final char cFinal = consonants.charAt(i);
            textButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {

                    if (matchRound.getGameRound() == 3) {
                        textButton.remove();
                        matchRound.addBonusString(cFinal);
                        count++;
                        if (count == 5) {
                            consonantTable.remove();
                            matchRound.showVocalKeyboard();
                        }
                        return;
                    }
              /*      StringBuilder builder = new StringBuilder(consonants);
                    builder.deleteCharAt(iFinal);
                    matchRound.setConsonants(builder.toString());*/
                    consonantTable.remove();

                    if (matchRound.isOnlinePlay()) {
                        CheckAnswer checkAnswer = new CheckAnswer();
                        checkAnswer.character = cFinal;
                        matchRound.getModeBase().sendObject(checkAnswer);
                    } else {
                        matchRound.checkAnswer(cFinal);
                    }


                }
            });
            consonantTable.add(textButton);

            if ((i + 1) % 7 == 0) {
                consonantTable.row();
            }
        }

        consonantTable.pack();
        consonantTable.setPosition(450f - consonantTable.getWidth() / 2f, 680f);
        consonantTable.center();
    }

    public void show() {
        updateTable();
        stage.addActor(consonantTable);
    }

    public void hide() {
        consonantTable.remove();
    }

}
