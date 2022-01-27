package com.somboi.rodaimpian.gdxnew.utils;

import com.somboi.rodaimpian.activities.PlayerOnline;
import com.somboi.rodaimpian.ui.Comment;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;
import com.somboi.rodaimpian.onlinemsg.NotifClient;
import com.somboi.rodaimpian.gdxnew.onlineclasses.PushNotif;

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
