package com.somboi.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.gdx.entities.MatchRound;

public class VocalKeyboard {
    private final Skin skin;
    private final Table vocalTable = new Table();
    private final Stage stage;
    private final MatchRound matchRound;

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
                    StringBuilder builder = new StringBuilder(vocals);
                    builder.deleteCharAt(iFinal);
                    matchRound.setVocals(builder.toString());
                    matchRound.checkAnswer(cFinal);
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

}
