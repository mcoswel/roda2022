package com.somboi.rodaimpian.android.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.somboi.rodaimpian.R;

import java.util.List;

public class LoadBadge {
    private final Context context;

    public LoadBadge(Context context) {
        this.context = context;
    }
    public void bankrupt(int bankrupt, ImageView badgeBankrupt) {
        if (bankrupt > 25) {
            Glide.with(context).load(R.drawable.bankrupt_three).into(badgeBankrupt);
        } else if (bankrupt >15) {
            Glide.with(context).load(R.drawable.bankrupt_three).into(badgeBankrupt);
        }else if (bankrupt >5) {
            Glide.with(context).load(R.drawable.bankrupt_one).into(badgeBankrupt);
        }else{
            badgeBankrupt.setVisibility(View.GONE);
        }
    }

    public void topFan(int timesPlayed, ImageView badgeTopFan) {
        if (timesPlayed > 40) {
            Glide.with(context).load(R.drawable.topfan).into(badgeTopFan);
        }else if (timesPlayed > 30) {
            Glide.with(context).load(R.drawable.topfanthirty).into(badgeTopFan);
        }else if (timesPlayed > 20) {
            Glide.with(context).load(R.drawable.topfantwenty).into(badgeTopFan);
        }else if (timesPlayed > 10) {
            Glide.with(context).load(R.drawable.topfanten).into(badgeTopFan);
        }else if (timesPlayed > 5) {
            Glide.with(context).load(R.drawable.topfanfive).into(badgeTopFan);
        } else {
            badgeTopFan.setVisibility(View.GONE);
        }
    }

    public  void crown(int rank, ImageView badgeCrown) {
        if (rank == 1) {
            Glide.with(context).load(R.drawable.goldcrown).into(badgeCrown);
        } else if (rank == 2) {
            Glide.with(context).load(R.drawable.silvercrown).into(badgeCrown);
        } else if (rank == 3) {
            Glide.with(context).load(R.drawable.bronzecrown).into(badgeCrown);
        } else if (rank == 4) {
            Glide.with(context).load(R.drawable.woodcrown).into(badgeCrown);
        } else {
            badgeCrown.setVisibility(View.GONE);
        }
    }
    public void giftVisibility(ImageView[] g, List<Integer> gifts) {
        g[0].setVisibility(View.GONE);
        g[1].setVisibility(View.GONE);
        g[2].setVisibility(View.GONE);
        if (gifts.size() == 1) {
            g[0].setVisibility(View.VISIBLE);
            g[1].setVisibility(View.GONE);
            g[2].setVisibility(View.GONE);
        } else if (gifts.size() == 2) {
            g[0].setVisibility(View.VISIBLE);
            g[1].setVisibility(View.VISIBLE);
            g[2].setVisibility(View.GONE);
        } else if (gifts.size() == 3) {
            g[0].setVisibility(View.VISIBLE);
            g[1].setVisibility(View.VISIBLE);
            g[2].setVisibility(View.VISIBLE);
        }

    }

    public void giftImage(List<Integer> gifts, ImageView[] img) {
        if (gifts == null || gifts.isEmpty()) {
            return;
        }
        int index = 0;
        for (Integer integer : gifts) {
            if (integer == 1) {
                Glide.with(context).load(R.drawable.ipong).into(img[index]);
            } else if (integer == 2) {
                Glide.with(context).load(R.drawable.urut).into(img[index]);
            } else if (integer == 3) {
                Glide.with(context).load(R.drawable.bicycle).into(img[index]);
            } else if (integer == 4) {
                Glide.with(context).load(R.drawable.canned).into(img[index]);
            } else if (integer == 5) {
                Glide.with(context).load(R.drawable.drone).into(img[index]);
            } else if (integer == 6) {
                Glide.with(context).load(R.drawable.elvis).into(img[index]);
            } else if (integer == 7) {
                Glide.with(context).load(R.drawable.facial).into(img[index]);
            } else if (integer == 8) {
                Glide.with(context).load(R.drawable.kitchen).into(img[index]);
            } else if (integer == 9) {
                Glide.with(context).load(R.drawable.lepoo).into(img[index]);
            } else if (integer == 10) {
                Glide.with(context).load(R.drawable.makeup).into(img[index]);
            } else if (integer == 11) {
                Glide.with(context).load(R.drawable.pumba).into(img[index]);
            } else if (integer == 12) {
                Glide.with(context).load(R.drawable.razor).into(img[index]);
            } else if (integer == 13) {
                Glide.with(context).load(R.drawable.ricecooker).into(img[index]);
            } else if (integer == 14) {
                Glide.with(context).load(R.drawable.roles).into(img[index]);
            } else if (integer == 15) {
                Glide.with(context).load(R.drawable.shoe).into(img[index]);
            } else if (integer == 16) {
                Glide.with(context).load(R.drawable.socks).into(img[index]);
            } else if (integer == 17) {
                Glide.with(context).load(R.drawable.speaker).into(img[index]);
            } else if (integer == 18) {
                Glide.with(context).load(R.drawable.starbock).into(img[index]);
            } else if (integer == 19) {
                Glide.with(context).load(R.drawable.sweater).into(img[index]);
            } else if (integer == 20) {
                Glide.with(context).load(R.drawable.tablet).into(img[index]);
            } else if (integer == 21) {
                Glide.with(context).load(R.drawable.teddy).into(img[index]);
            } else if (integer == 22) {
                Glide.with(context).load(R.drawable.ultraman).into(img[index]);
            } else if (integer == 23) {
                Glide.with(context).load(R.drawable.voucher).into(img[index]);
            }


            index++;

            if (index == 2) {
                return;
            }
        }
    }

    public void bonusImages(List<Integer> bonuses, ImageView[] img) {
        if (bonuses == null || bonuses.isEmpty()) {
            return;
        }
        int index = 0;
        for (Integer integer : bonuses) {
            if (integer == 1) {
                Glide.with(context).load(R.drawable.motorcycle).into(img[index]);
            } else if (integer == 2) {
                Glide.with(context).load(R.drawable.neskopi).into(img[index]);
            } else if (integer == 3) {
                Glide.with(context).load(R.drawable.nintendo).into(img[index]);
            } else if (integer == 4) {
                Glide.with(context).load(R.drawable.polystation).into(img[index]);
            } else if (integer == 5) {
                Glide.with(context).load(R.drawable.predator).into(img[index]);
            } else if (integer == 6) {
                Glide.with(context).load(R.drawable.ring).into(img[index]);
            } else if (integer == 7) {
                Glide.with(context).load(R.drawable.toyoda).into(img[index]);
            } else if (integer == 8) {
                Glide.with(context).load(R.drawable.kawansakit).into(img[index]);
            } else if (integer == 9) {
                Glide.with(context).load(R.drawable.laptop).into(img[index]);
            } else if (integer == 10) {
                Glide.with(context).load(R.drawable.wash).into(img[index]);
            } else if (integer == 11) {
                Glide.with(context).load(R.drawable.lintah).into(img[index]);
            } else if (integer == 12) {
                Glide.with(context).load(R.drawable.microwave).into(img[index]);
            } else if (integer == 13) {
                Glide.with(context).load(R.drawable.car).into(img[index]);
            } else if (integer == 14) {
                Glide.with(context).load(R.drawable.chair).into(img[index]);
            } else if (integer == 15) {
                Glide.with(context).load(R.drawable.earring).into(img[index]);
            } else if (integer == 16) {
                Glide.with(context).load(R.drawable.ferraro).into(img[index]);
            } else if (integer == 17) {
                Glide.with(context).load(R.drawable.flattv).into(img[index]);
            } else if (integer == 18) {
                Glide.with(context).load(R.drawable.fridge).into(img[index]);
            } else if (integer == 19) {
                Glide.with(context).load(R.drawable.frog).into(img[index]);
            } else if (integer == 20) {
                Glide.with(context).load(R.drawable.golf).into(img[index]);
            } else if (integer == 21) {
                Glide.with(context).load(R.drawable.headphone).into(img[index]);
            } else if (integer == 22) {
                Glide.with(context).load(R.drawable.jersey).into(img[index]);
            } else if (integer == 23) {
                Glide.with(context).load(R.drawable.jewel).into(img[index]);
            }else if (integer == 24) {
                Glide.with(context).load(R.drawable.airasia).into(img[index]);
            }
            if (index == 2) {
                return;
            }
        }
    }

    public void bonusVisibility(ImageView[] b, List<Integer> bonuses) {
        b[0].setVisibility(View.GONE);
        b[1].setVisibility(View.GONE);
        b[2].setVisibility(View.GONE);

        if (bonuses.size() == 1) {
            b[0].setVisibility(View.VISIBLE);
            b[1].setVisibility(View.GONE);
            b[2].setVisibility(View.GONE);
        } else if (bonuses.size() == 2) {
            b[0].setVisibility(View.VISIBLE);
            b[1].setVisibility(View.VISIBLE);
            b[2].setVisibility(View.GONE);
        } else if (bonuses.size() == 3) {
            b[0].setVisibility(View.VISIBLE);
            b[1].setVisibility(View.VISIBLE);
            b[2].setVisibility(View.VISIBLE);
        }

    }
}
