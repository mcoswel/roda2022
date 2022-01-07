package com.somboi.rodaimpian.gdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.gdx.actor.BonusGiftImg;
import com.somboi.rodaimpian.gdxnew.actors.Sparkling;

public class Gifts {
    private final TextureAtlas textureAtlas;
    private BonusGiftImg giftImages;
    private int giftIndex;
    private int giftsValue;
    private int index;
    private final Array<Integer> giftIntegers = new Array<>(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23});
    private final BonusGiftImg unopenedGift;
    private final BonusGiftImg openedGift;
    private final Sparkling sparkling;
    private final Group giftsGroup;
    private int overloadGift;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    public Gifts(TextureAtlas textureAtlas, Texture sparkle, Group giftsGroup) {
        this.textureAtlas = textureAtlas;
        this.giftsGroup = giftsGroup;
        unopenedGift = new BonusGiftImg(textureAtlas.findRegion("unopened_gift"));
        openedGift = new BonusGiftImg(textureAtlas.findRegion("opened_gift"));
        sparkling = new Sparkling(sparkle);
        giftIntegers.shuffle();
    }

    public void generateGifts(){
        giftIndex = giftIntegers.get(index);
        getGiftImages();
        index= (index+1)%(giftIntegers.size-1);
    }


    public void setGifts(){
        generateGifts();
        giftsGroup.addActor(unopenedGift);
        giftsGroup.addActor(sparkling);
    }

    public void setGiftsOnline(int giftIndex){
        giftsGroup.addActor(unopenedGift);
        giftsGroup.addActor(sparkling);
        getGiftImages(giftIndex);
    }

    public void showGifts(PlayerGui activePlayerGui){
        unopenedGift.remove();
        sparkling.remove();
        giftsGroup.addActor(openedGift);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                openedGift.remove();
            }
        },5f);
        int giftSize = activePlayerGui.getPlayer().gifts.size();
        Vector2 giftPos = new Vector2(activePlayerGui.getPlayerPos().x-25f+(50f*(giftSize%4)),activePlayerGui.getPlayerPos().y+(50f*overloadGift));
        if (giftSize!=0 && giftSize%4==0){
            overloadGift++;
        }

        ParallelAction firstActions = new ParallelAction(Actions.scaleBy(1.5f,1.5f,2f),Actions.moveTo(450f-giftImages.getWidth(),800f-giftImages.getHeight()/2f,2f));
        ParallelAction secondActions = new ParallelAction(Actions.sizeTo(50f,50f,2f),Actions.moveTo(giftPos.x,giftPos.y,2f));
        SequenceAction sequenceAction = new SequenceAction(firstActions, secondActions);
        giftImages.addAction(sequenceAction);
        giftsGroup.addActor(giftImages);
        activePlayerGui.getPlayer().gifts.add(giftIndex);
    }

    public BonusGiftImg getGiftImages() {
        if (giftIndex==1) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_b_ipong"));
            giftsValue = 1500;
        }else if (giftIndex==2){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_b_urut"));
            giftsValue = 35;
        }else if (giftIndex==3){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_bicycle"));
            giftsValue = 800;
        }else if (giftIndex==4){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_canned"));
            giftsValue = 45;
        }else if (giftIndex==5){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_drone"));
            giftsValue = 700;
        }else if (giftIndex==6){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_elvis"));
            giftsValue = 550;
        }else if (giftIndex==7){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_facial"));
            giftsValue = 400;
        }else if (giftIndex==8){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_kitchen"));
            giftsValue = 320;
        }else if (giftIndex==9){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_lepoo"));
            giftsValue = 125;
        }else if (giftIndex==10){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_makeup"));
            giftsValue = 150;
        }else if (giftIndex==11){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_pumba"));
            giftsValue = 375;
        }else if (giftIndex==12){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_razor"));
            giftsValue = 270;
        }else if (giftIndex==13){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_ricecooker"));
            giftsValue = 130;
        }else if (giftIndex==14){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_roles"));
            giftsValue = 990;
        }else if (giftIndex==15){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_shoe"));
            giftsValue = 80;
        }else if (giftIndex==16){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_socks"));
            giftsValue = 25;
        }else if (giftIndex==17){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_speaker"));
            giftsValue = 35;
        }else if (giftIndex==18){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_starbock"));
            giftsValue = 100;
        }else if (giftIndex==19){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_sweater"));
            giftsValue = 140;
        }else if (giftIndex==20){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_tablet"));
            giftsValue = 880;
        }else if (giftIndex==21){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_teddybear"));
            giftsValue = 90;
        }else if (giftIndex==22){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_ultraman"));
            giftsValue = 35;
        }else if (giftIndex==23){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_voucher"));
            giftsValue = 1000;
        }

        return giftImages;
    }

    public BonusGiftImg getGiftImages(int giftIndex) {
        if (giftIndex==1) {
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_b_ipong"));
            giftsValue = 1500;
        }else if (giftIndex==2){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_b_urut"));
            giftsValue = 35;
        }else if (giftIndex==3){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_bicycle"));
            giftsValue = 800;
        }else if (giftIndex==4){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_canned"));
            giftsValue = 45;
        }else if (giftIndex==5){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_drone"));
            giftsValue = 700;
        }else if (giftIndex==6){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_elvis"));
            giftsValue = 550;
        }else if (giftIndex==7){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_facial"));
            giftsValue = 400;
        }else if (giftIndex==8){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_kitchen"));
            giftsValue = 320;
        }else if (giftIndex==9){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_lepoo"));
            giftsValue = 125;
        }else if (giftIndex==10){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_makeup"));
            giftsValue = 150;
        }else if (giftIndex==11){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_pumba"));
            giftsValue = 375;
        }else if (giftIndex==12){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_razor"));
            giftsValue = 270;
        }else if (giftIndex==13){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_ricecooker"));
            giftsValue = 130;
        }else if (giftIndex==14){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_roles"));
            giftsValue = 990;
        }else if (giftIndex==15){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_shoe"));
            giftsValue = 80;
        }else if (giftIndex==16){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_socks"));
            giftsValue = 25;
        }else if (giftIndex==17){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_speaker"));
            giftsValue = 35;
        }else if (giftIndex==18){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_starbock"));
            giftsValue = 100;
        }else if (giftIndex==19){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_sweater"));
            giftsValue = 140;
        }else if (giftIndex==20){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_tablet"));
            giftsValue = 880;
        }else if (giftIndex==21){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_teddybear"));
            giftsValue = 90;
        }else if (giftIndex==22){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_ultraman"));
            giftsValue = 35;
        }else if (giftIndex==23){
            giftImages = new BonusGiftImg(textureAtlas.findRegion("5_g_voucher"));
            giftsValue = 1000;
        }

        return giftImages;
    }


    public int getGiftsValue() {
        return giftsValue;
    }

    public void cancel() {
        unopenedGift.remove();
        sparkling.remove();
    }

    public int getGiftIndex() {
        return giftIndex;
    }

    public void removeBox() {
        openedGift.remove();
        unopenedGift.remove();
    }
}
