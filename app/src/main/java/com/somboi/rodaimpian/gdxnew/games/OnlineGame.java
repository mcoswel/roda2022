package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.actors.ChatBubble;
import com.somboi.rodaimpian.gdxnew.actors.PlayerGuis;
import com.somboi.rodaimpian.gdxnew.actors.PlayerMenu;
import com.somboi.rodaimpian.gdxnew.actors.SmallButton;
import com.somboi.rodaimpian.gdxnew.actors.UltraSmallBtn;
import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.interfaces.OnInterface;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ChatOnline;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ChooseVocal;
import com.somboi.rodaimpian.gdxnew.onlineclasses.GameState;
import com.somboi.rodaimpian.gdxnew.onlineclasses.KickPlayer;
import com.somboi.rodaimpian.gdxnew.onlineclasses.PlayerStates;
import com.somboi.rodaimpian.gdxnew.onlineclasses.RoomSession;

import java.util.ArrayList;
import java.util.List;

public class OnlineGame extends BaseGame {
    private RoomSession roomSession;
    private final OnInterface onInterface;
    private final Group onlineMenuGroup = new Group();
    private List<QuestionNew> questionNewList = new ArrayList<>();

    public OnlineGame(Stage stage, RodaImpianNew rodaImpianNew, OnInterface onInterface) {
        super(stage, rodaImpianNew);
        this.onInterface = onInterface;
        vannaHost.remove();
    }

    @Override
    public void addPlayers() {
        gameRound = 0;
        setUpNewRound();
        playerGuis.clear();
        tilesGroup.clear();
        tilesGroup.remove();
        onlineMenuGroup.clear();
        onlineMenuGroup.remove();
        stage.addActor(tilesGroup);
        stage.addActor(onlineMenuGroup);
        for (PlayerNew playerNew : roomSession.getPlayerList()) {
            playerGuis.add(setHumanGui(playerNew, 1));
        }
        for (PlayerGuis p : playerGuis) {
            if (p.getPlayerNew().getUid().equals(rodaImpianNew.getPlayer().getUid())) {
                selfGui = p;
            }
            p.getPlayerNew().setScore(0);
            p.getScoreLabel().setText("$" + p.getPlayerNew().getScore());
            p.getPlayerNew().setFullScore(0);
            p.updateFullScore(p.getPlayerNew().getFullScore());
            p.getPlayerNew().setAnimateScore(0);
        }
        for (int i = 0; i < playerGuis.size; i++) {
            PlayerGuis pgui = playerGuis.get(i);
            pgui.setPlayerIndex(i);
            pgui.setChatBubble(new ChatBubble(i, stage, skin));
            pgui.setPicFixPos();
            pgui.setChatBubble(new ChatBubble(i, stage, skin));
            stage.addActor(pgui.getProfilePic());
            if (i > 0 && onInterface.isHost()) {
                UltraSmallBtn kickBtn = new UltraSmallBtn(StringRes.KICK, skin);
                kickBtn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        KickPlayer kickPlayer = new KickPlayer();
                        kickPlayer.setRoomId(rodaImpianNew.getPlayer().getUid());
                        kickPlayer.setKickedPlayerID(pgui.getPlayerNew().getUid());
                        onInterface.sendObjects(kickPlayer);
                    }
                });
                kickBtn.setPosition(pgui.getPosition().x, pgui.getPosition().y + 255f);
                onlineMenuGroup.addActor(kickBtn);
            }
            tilesGroup.addActor(pgui.getScoreLabel());
            tilesGroup.addActor(pgui.getFulLScoreLabel());
            //tilesGroup.addActor(playerGuis.get(i).getFreeTurn());
            tilesGroup.addActor(pgui.getNameLabel());
        }

        if (playerGuis.size < 2) {
            onInterface.updateStatusLabel(StringRes.WAITINGENTRY);
        } else {
            onInterface.updateStatusLabel(StringRes.WAITINGHOST);
            if (onInterface.isHost()) {
                createOnlineMenu();
            }
        }
        UltraSmallBtn chat = new UltraSmallBtn(StringRes.CHAT, skin);
        chat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpianNew.chatOnline(onInterface, selfGui.getPlayerIndex());
            }
        });
        chat.setPosition(selfGui.getPosition().x, 310f);
        stage.addActor(chat);
    }

    //startQuestion
    @Override
    public void startRound() {
        subjectLabel.remove();
        stage.addActor(vannaHost);
        stage.addActor(subjectLabel);
        vannaHost.dance();
        showQuestions();
        playerMenu = new PlayerMenu(stage, this, skin);
    }

    @Override
    public QuestionNew getCurrentQuestion() {
        return questionNewList.get(gameRound);
    }

    public void createOnlineMenu() {
        final Table onlineMenuTable = new Table();
        SmallButton start = new SmallButton(StringRes.START, skin);
        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onlineMenuGroup.remove();
                onInterface.sendObjects(GameState.OCCUPIED);
                onInterface.sendObjects(PlayerStates.START);
            }
        });
        onlineMenuTable.add(start);
        onlineMenuTable.pack();
        onlineMenuTable.setPosition(450f - onlineMenuTable.getWidth() / 2f, 750f);
        onlineMenuGroup.addActor(onlineMenuTable);
    }

    public void showChatOnline(ChatOnline chatOnline) {
        playerGuis.get(chatOnline.getGuiIndex()).chat(chatOnline.getText());
    }

    public void playerDisconnected(String uid) {
        for (PlayerGuis p : playerGuis) {
            if (p.getPlayerNew().getUid().equals(uid)) {
                p.getProfilePic().setColor(Color.BLUE);
                p.getPlayerNew().setDisconnect(true);
            }
        }
    }

    public void setQuestionNewList(List<QuestionNew> questionNewList) {
        this.questionNewList = questionNewList;
    }

    public void setRoomSession(RoomSession roomSession) {
        this.roomSession = roomSession;
    }

    @Override
    public void sendObject(Object o) {
        onInterface.sendObjects(o);
    }


    public boolean isTurn() {
        for (PlayerGuis p : playerGuis) {
            if (p.getPlayerNew().isTurn()) {
                return p.getPlayerNew().getUid().equals(rodaImpianNew.getPlayer().getUid());
            }
        }
        return false;
    }

    public void chooseVocal(ChooseVocal chooseVocal) {

        playerGuis.get(chooseVocal.getGuiIndex()).getPlayerNew().setScore(
                playerGuis.get(chooseVocal.getGuiIndex()).getPlayerNew().getScore() - 250
        );
        onInterface.sendObjects(PlayerStates.CHOOSEVOCAL);
    }

    public void vocalKeyboard() {
        playerMenu.createVocalTable();
    }
}
