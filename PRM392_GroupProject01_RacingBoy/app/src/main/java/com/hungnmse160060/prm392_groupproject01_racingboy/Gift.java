package com.hungnmse160060.prm392_groupproject01_racingboy;

import android.graphics.drawable.Drawable;
import android.widget.SeekBar;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Gift {
    final SeekBar giftImg;
    int buffTime;
    int type; // speedup, tele, freeze
    Boolean isEnabled = false;
    Boolean isEffected = false;


    public Gift(int player, SeekBar giftImg, int startPos) {
        ConstraintLayout.LayoutParams view = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        view.height = 700;
        view.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        view.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        view.topMargin = 150 * (player);

        giftImg.setProgressDrawable(Drawable.createFromPath("@drawable/seekbar_template"));
        giftImg.setProgress(startPos);
        giftImg.setLayoutParams(view);
//        giftImg.setThumb();
        this.giftImg = giftImg;
        this.buffTime = 0;
        this.type = 0;
        this.isEnabled = false;
    }

    public void enableGift(int type, Drawable img, int startPos) {
        this.type = type;
        this.giftImg.setThumb(img);
        this.giftImg.setProgress(startPos, false);
        this.buffTime = getBuffTime(type);
        this.isEnabled = true;
    }

    public void resetGift(int startPos) {
        this.type = 0;
        this.buffTime = 0;
        this.isEffected = false;
//        this.giftImg.setThumb(null);
        this.giftImg.setProgress(startPos);
        this.isEnabled = false;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public Boolean getEffected() {
        return isEffected;
    }

    public int getBuffTime() {
        return buffTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setBuffTime(int buffTime) {
        this.buffTime = buffTime;
    }

    public void setEffected(Boolean effected) {
        this.giftImg.setThumb(null);
//        this.isEnabled = false;
        isEffected = effected;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public SeekBar getGiftImg() {
        return giftImg;
    }

    private int getBuffTime(int type) {
        switch (type) {
            //time speedup
            case 0: return 4;
            //time tele
            case 1: return 1;
            //freeze:
            case 2: return 10;
        }
        return 0;
    }
}
