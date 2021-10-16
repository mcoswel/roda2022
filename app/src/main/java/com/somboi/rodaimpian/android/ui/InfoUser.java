package com.somboi.rodaimpian.android.ui;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.somboi.rodaimpian.R;
import com.somboi.rodaimpian.android.PlayerOnline;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoUser extends Dialog {
    private final PlayerOnline thisPlayer;
    private TextView rank_txt;
    private TextView scoreTxt;
    private TextView nameTxt;
    private LoadBadge loadbadge;
    private CircleImageView profilePic;
    private Button ok;
    private ImageView[] bonus;
    private ImageView[] gifts;
    private Context context;
    private ImageView topfan;
    private ImageView badgeBankrupt;

    public InfoUser(@NonNull Context context, PlayerOnline thisPlayer) {
        super(context);
        this.context = context;
        this.setContentView(R.layout.info_dialog);
        this.thisPlayer = thisPlayer;

        profilePic = findViewById(R.id.profile_pic);
        scoreTxt = findViewById(R.id.bestscore);
        nameTxt = findViewById(R.id.nameInfo);
        rank_txt = findViewById(R.id.ranking);
        ok = findViewById(R.id.okbutton);
        topfan = findViewById(R.id.topfanbadge);
        loadbadge = new LoadBadge(context);
        gifts = new ImageView[]{findViewById(R.id.g0), findViewById(R.id.g1), findViewById(R.id.g2)};
        bonus = new ImageView[]{findViewById(R.id.b0), findViewById(R.id.b1), findViewById(R.id.b2)};
        badgeBankrupt = findViewById(R.id.bankruptbadge);
        init();
    }

    private void init() {
        scoreTxt.setText("Markah Terbaik: $" + thisPlayer.bestScore);
        rank_txt.setText("Ranking :" + thisPlayer.rank);
        nameTxt.setText(thisPlayer.name);
        Glide.with(context).load(thisPlayer.picUri).placeholder(R.drawable.default_avatar).into(profilePic);

        loadbadge.topFan(thisPlayer.timesplayed, topfan);
        if (thisPlayer.giftsList == null || thisPlayer.giftsList.isEmpty()) {
            for (ImageView c : gifts) {
                c.setVisibility(View.GONE);
            }
        } else {
            loadbadge.giftVisibility(gifts, thisPlayer.giftsList);
            loadbadge.giftImage(thisPlayer.giftsList, gifts);
        }
        if (thisPlayer.bonusList == null || thisPlayer.bonusList.isEmpty()) {
            for (ImageView c : bonus) {
                c.setVisibility(View.GONE);
            }
        } else {
            loadbadge.bonusVisibility(bonus, thisPlayer.bonusList);
            loadbadge.bonusImages(thisPlayer.bonusList, bonus);
        }
        loadbadge.bankrupt(thisPlayer.bankrupt, badgeBankrupt);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
                dismiss();

            }
        });
    }
}


