package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

public class PlayerGuis {
    private PlayerNew playerNew;
    private int playerIndex=0;
    private ProfilePic profilePic;
    private Vector2 position=new Vector2(0,0);
    private Label scoreLabel;
    private Label nameLabel;
    private Label fulLScoreLabel;
    private Label freeTurn;
    private ChatBubble chatBubble;
    public PlayerGuis() {

    }

    public PlayerNew getPlayerNew() {
        return playerNew;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public ProfilePic getProfilePic() {
        return profilePic;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPlayerNew(PlayerNew playerNew) {
        this.playerNew = playerNew;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
        setWheelTurnPosition();
    }

    public void setProfilePic(ProfilePic profilePic) {
        this.profilePic = profilePic;
    }

    private void setWheelTurnPosition(){
        profilePic.setPosition(450f - 250f - 50f, 800f);
        if (playerIndex == 2) {
            profilePic.setPosition(450f + 50f, 800f);
        } else if (playerIndex == 0) {
            profilePic.setPosition(450f - (250f / 2), 800f - 100f - 250f);
        }
        profilePic.setOrigin(450f - profilePic.getX(), 800f - profilePic.getY());
        nameLabel.setPosition((150f+(300 * playerIndex))-nameLabel.getWidth()/2f, 239f);
        scoreLabel.setPosition((150f+(300 * playerIndex))-scoreLabel.getWidth()/2f, 165);
        freeTurn.setPosition((150f+(300 * playerIndex))-freeTurn.getWidth()/2f, 75f);
        fulLScoreLabel.setPosition((150f+(300 * playerIndex))-fulLScoreLabel.getWidth()/2f, 11.2f);

    }

    public void animateShowBoard() {
        position = new Vector2(300 * playerIndex +25f, 314f);
        profilePic.addAction(new ParallelAction(
                Actions.moveTo(position.x, position.y,1f),
                Actions.rotateTo(0,1f)
        ));
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public void setScoreLabel(Label scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(Label nameLabel) {
        this.nameLabel = nameLabel;
    }

    public Label getFulLScoreLabel() {
        return fulLScoreLabel;
    }

    public void setFulLScoreLabel(Label fulLScoreLabel) {
        this.fulLScoreLabel = fulLScoreLabel;
    }

    public Label getFreeTurn() {
        return freeTurn;
    }

    public void setFreeTurn(Label freeTurn) {
        this.freeTurn = freeTurn;
    }

    public ChatBubble getChatBubble() {
        return chatBubble;
    }

    public void chat(String text){
        chatBubble.createBubble(text);
    }

    public void setChatBubble(ChatBubble chatBubble) {
        this.chatBubble = chatBubble;
    }

    public void update(float delta) {
        if (chatBubble!=null){
            chatBubble.updateChat(delta);
        }
        if (playerNew!=null && scoreLabel!=null){
            if (playerNew.getAnimateScore()<playerNew.getScore()){
                playerNew.setAnimateScore(playerNew.getAnimateScore()+10);
            }else{
                playerNew.setAnimateScore(playerNew.getScore());
            }
            scoreLabel.setText("$"+playerNew.getAnimateScore());
        }
    }
}
