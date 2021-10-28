package com.somboi.rodaimpian.gdx.utils;

import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.android.ui.Comment;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.online.NotifClient;
import com.somboi.rodaimpian.gdx.online.newentities.PushNotif;

public class SendNotif {

    public static void send(Comment comment, String targetFCM, PlayerOnline thisPlayer, boolean invite) {
        PushNotif pushNotif = new PushNotif();
        pushNotif.guestFcm = targetFCM;
        pushNotif.name = thisPlayer.name;
        pushNotif.hostUri = thisPlayer.picUri;
        if (invite) {
            pushNotif.title = thisPlayer.name+StringRes.LETSPLAY;
        }else{
            pushNotif.title = thisPlayer.name;
        }
        NotifClient notifClient = new NotifClient(pushNotif);
    }
}
