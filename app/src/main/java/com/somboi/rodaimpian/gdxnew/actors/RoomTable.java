package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.utils.RoundMap;
import com.somboi.rodaimpian.gdxnew.interfaces.OnInterface;
import com.somboi.rodaimpian.gdxnew.onlineclasses.JoinSession;
import com.somboi.rodaimpian.gdxnew.onlineclasses.RoomSession;

public class RoomTable extends Table {
    private final Skin skin;
    private final RoomSession roomSession;
    private final OnInterface onInterface;

    public RoomTable(Skin skin, TextureRegion defaultAvatar, RoomSession roomSession, OnInterface onInterface) {
        this.skin = skin;
        this.roomSession = roomSession;
        this.onInterface = onInterface;
        setColor(Color.PINK);
        setBackground(skin.getDrawable("smallnormal"));
        Table leftTable = new Table();
        leftTable.defaults().pad(8f);
        leftTable.add(new RoomPic(defaultAvatar)).size(200f, 200f);

        Table rightTable = new Table();
        rightTable.defaults().center().pad(5f);

        if (!roomSession.getPlayerList().isEmpty()) {
            Label roomName = new Label(StringRes.ROOM + roomSession.getPlayerList().get(0).getName(), skin, "big");
            rightTable.add(roomName).center().row();
        }


        if (!roomSession.isOccupied()) {
            SmallButton joinButton = new SmallButton(StringRes.JOIN, skin);
            joinButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (onInterface.isBanned(roomSession.getRoomID())) {
                        ErrDiag errDiag = new ErrDiag(StringRes.SORRYYOUBAN, skin);
                        errDiag.show(getStage());
                    } else {
                        JoinSession joinSession = new JoinSession();
                        joinSession.setRoomID(roomSession.getRoomID());
                        onInterface.sendObjects(joinSession);
                    }
                }
            });
            rightTable.add(joinButton).row();
        } else {
            Label playing = new Label(StringRes.PLAYING, skin);
            rightTable.add(playing).row();
        }

        add(leftTable).size(280f, 225f);
        add(rightTable).size(580f, 225f);
    }

    private class RoomPic extends Image {
        public RoomPic(TextureRegion region) {
            super(region);
            setSize(200, 200);
            if (roomSession.getRoomUri() != null) {
                Pixmap.downloadFromUrl(roomSession.getRoomUri(), new Pixmap.DownloadPixmapResponseListener() {
                    @Override
                    public void downloadComplete(Pixmap pixmap) {
                        Texture round = new Texture(RoundMap.execute(new Texture(pixmap)));
                        setDrawable(new SpriteDrawable(new Sprite(round)));
                    }

                    @Override
                    public void downloadFailed(Throwable t) {

                    }
                });
            }
        }
    }
}
