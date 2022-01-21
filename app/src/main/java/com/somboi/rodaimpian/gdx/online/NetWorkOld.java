package com.somboi.rodaimpian.gdx.online;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.somboi.rodaimpian.gdx.assets.QuestionsReady;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.WheelParam;
import com.somboi.rodaimpian.gdx.online.entities.BeginSpin;
import com.somboi.rodaimpian.gdx.online.entities.BonusHolder;
import com.somboi.rodaimpian.gdx.online.entities.BonusIndex;
import com.somboi.rodaimpian.gdx.online.entities.CheckAnswer;
import com.somboi.rodaimpian.gdx.online.entities.DisconnectPlayer;
import com.somboi.rodaimpian.gdx.online.entities.EnvelopeIndex;
import com.somboi.rodaimpian.gdx.online.entities.GameStateOld;
import com.somboi.rodaimpian.gdx.online.entities.PlaeyrStateOld;
import com.somboi.rodaimpian.gdx.online.entities.SessionList;
import com.somboi.rodaimpian.gdx.online.entities.StatusText;
import com.somboi.rodaimpian.gdx.online.entities.TilesOnline;
import com.somboi.rodaimpian.gdx.online.newentities.ClearSession;
import com.somboi.rodaimpian.gdx.online.newentities.CreateSessions;
import com.somboi.rodaimpian.gdx.online.newentities.FinishSpin;
import com.somboi.rodaimpian.gdx.online.newentities.RegisterPlayer;
import com.somboi.rodaimpian.gdx.online.newentities.RemovePlayer;
import com.somboi.rodaimpian.gdx.online.newentities.RemoveSession;
import com.somboi.rodaimpian.gdx.online.newentities.RodaSession;
import com.somboi.rodaimpian.gdx.online.newentities.SessionRoom;
import com.somboi.rodaimpian.gdx.online.newentities.SetActivePlayer;

import java.util.ArrayList;
import java.util.List;

public class NetWorkOld {
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(List.class);
        kryo.register(ArrayList.class);
        kryo.register(QuestionsReady.class);
        kryo.register(WheelParam.class);
        kryo.register(Player.class);
        kryo.register(GameStateOld.class);
        kryo.register(RegisterPlayer.class);
        kryo.register(StatusText.class);
        kryo.register(Float.class);
        kryo.register(ChatOnlineOld.class);
        kryo.register(CheckAnswer.class);
        kryo.register(Character.class);
        kryo.register(BeginSpin.class);
        kryo.register(PlaeyrStateOld.class);
        kryo.register(SessionRoom.class);
        kryo.register(SessionList.class);
        kryo.register(DisconnectPlayer.class);
        kryo.register(BonusHolder.class);
        kryo.register(EnvelopeIndex.class);
        kryo.register(BonusIndex.class);
        kryo.register(TilesOnline.class);
        kryo.register(CreateSessions.class);
        kryo.register(RegisterPlayer.class);
        kryo.register(RodaSession.class);
        kryo.register(RemoveSession.class);
        kryo.register(SetActivePlayer.class);
        kryo.register(RemovePlayer.class);
        kryo.register(FinishSpin.class);
        kryo.register(ClearSession.class);
      //  kryo.register(InvitePlayer.class);
    }
}
