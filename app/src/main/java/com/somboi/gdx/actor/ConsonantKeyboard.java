package com.somboi.gdx.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Logger;
import com.somboi.gdx.entities.MatchRound;

public class ConsonantKeyboard {
    private final Table consonantTable = new Table();
    private final Stage stage;
    private final Skin skin;
    private final MatchRound matchRound;
    public ConsonantKeyboard(Skin skin, MatchRound matchRound, Stage stage) {
        this.skin = skin;
        this.stage = stage;
        this.matchRound = matchRound;
    }


    private void updateTable(){
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
            final int iFinal = i;
            textButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    StringBuilder builder = new StringBuilder(consonants);
                    builder.deleteCharAt(iFinal);
                    matchRound.setConsonants(builder.toString());
                    matchRound.checkAnswer(cFinal);
                    consonantTable.remove();
                }
            });
            consonantTable.add(textButton);

            if ((i + 1) % 7 == 0) {
                consonantTable.row();
            }
        }

        consonantTable.pack();
        consonantTable.setPosition(450f-consonantTable.getWidth()/2f, 680f);
        consonantTable.center();
    }

    public void show(){
        updateTable();
        stage.addActor(consonantTable);
    }

    public void removeButton(Character c){
        for (Actor actor: consonantTable.getChildren()){
            if (actor instanceof TextButton){
                if (((TextButton) actor).getText().equals(String.valueOf(c))){
                    actor.remove();
                }
            }
        }
    }

}
