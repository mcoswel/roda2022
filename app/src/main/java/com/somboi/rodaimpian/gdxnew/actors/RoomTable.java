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
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.games.ClientNew;
import com.somboi.rodaimpian.gdxnew.onlineclasses.JoinSession;
import com.somboi.rodaimpian.gdxnew.onlineclasses.RoomSession;

public class RoomTable extends Table {
    private final Skin skin;
    private final RoomSession roomSession;
    public RoomTable(Skin skin, TextureRegion defaultAvatar, RoomSession roomSession, ClientNew clientNew, PlayerNew playerNew) {
        this.skin = skin;
        this.roomSession = roomSession;
        setColor(Color.PINK);
        setBackground(skin.getDrawable("smallnormal"));
        Table leftTable = new Table();
        leftTable.defaults().pad(8f);
        leftTable.add(new RoomPic(defaultAvatar));

        Table rightTable = new Table();
        rightTable.defaults().center().pad(5f);

        if (roomSession.getPlayerList().get(0)!=null) {
            Label roomName = new Label(StringRes.ROOM + roomSession.getPlayerList().get(0).getName(), skin, "big");
            rightTable.add(roomName).center().row();
        }

        SmallButton joinButton = new SmallButton(StringRes.JOIN,skin);
        joinButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                JoinSession joinSession = new JoinSession();
                joinSession.setConnectionId(playerNew.getConnectionID());
                clientNew.sendObjects(joinSession);
            }
        });

        rightTable.add(joinButton).row();
        add(leftTable).size(280f, 380f);
        add(rightTable).size(580f, 380f);
    }

    private class RoomPic extends Image{
        public RoomPic(TextureRegion region) {
            super(region);
            setSize(200, 200);
            if (roomSession.getRoomUri()!=null){
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
