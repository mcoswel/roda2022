package com.somboi.rodaimpian.gdx.online;

public class TilesOnline {
    public char letter;
    public boolean revealed;
    public void setLetter(char letter) {
        this.letter = letter;
        if (letter == '\'') {
            revealed = true;
        }
        if (letter == '-') {
            revealed = true;
        }
        if (letter == ' ') {
            revealed = true;
        }
    }
}
