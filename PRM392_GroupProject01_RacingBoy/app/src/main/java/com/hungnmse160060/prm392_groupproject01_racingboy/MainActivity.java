package com.hungnmse160060.prm392_groupproject01_racingboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<SeekBar> dogs = new ArrayList<SeekBar>();
    ArrayList<Gift> gifts = new ArrayList<Gift>();
    ArrayList<Drawable> animaFrame, giftFrame;
    HashMap<Integer, Integer> result = new HashMap<>();
    int dogs_size = 4, startPos = 10, endPos = 90, rank = 0;
    ArrayList<TextView> rankPlayer = new ArrayList<>();
    Button btnStart, btnReset;
    private Handler handler = new Handler();
    Random random = new Random();

    ConstraintLayout racetrack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        racetrack = findViewById(R.id.container);

        rankPlayer.add(findViewById(R.id.txtRankDog1));
        rankPlayer.add(findViewById(R.id.txtRankDog2));
        rankPlayer.add(findViewById(R.id.txtRankDog3));
        rankPlayer.add(findViewById(R.id.txtRankDog4));
        animaFrame = getAnimationList();
        giftFrame = getGiftList();

        for (int i = 0; i < dogs_size; i++) {
            SeekBar dog = CreateDog(i);
            SeekBar giftImg = new SeekBar(this);
//            giftImg.setThumb(resize(getResources().getDrawable(R.drawable.nothing), 50, 50));
            Gift gift = new Gift(i, giftImg, endPos);

            gifts.add(gift);
            dogs.add(dog);

            racetrack.addView(giftImg);
            racetrack.addView(dogs.get(i));
        }

        //button start, reset
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);

        btnStart.setOnClickListener((v) -> {
            btnStart.setEnabled(false);
            btnReset.setEnabled(false);

            update(1);

        });

        btnReset.setOnClickListener((v) -> {
            reset();
        });

    }

    private void update(int anim) {
        if (!isEnd()) {
            int i = 0;
            for (Gift item: gifts) {
                if (item.getEnabled()) {
                    int cur_pos_gift = item.getGiftImg().getProgress();
                    int cur_pos_dog = dogs.get(i).getProgress();
                    item.getGiftImg().setProgress(cur_pos_gift - 1);
                    if (cur_pos_gift <= cur_pos_dog) {
                        item.setEffected(true);
                    }
                    i++;
                    continue;
                }
                int ran = random.nextInt(100);
                if (ran < 5) {
                    int type = random.nextInt(3);
                    gifts.get(i).enableGift(type , giftFrame.get(type), endPos);
                }
                i++;
            }

            for (i = 0; i < dogs_size; i++) {
                SeekBar cur_dog = dogs.get(i);
                Gift cur_gift = gifts.get(i);
                if (cur_dog.getProgress() < endPos) {
                    boolean isFreeze = false;
                    int speedup = 1;
                    int teleport = 0;

                    if (cur_gift.getEffected()) {
                        int bufftime = cur_gift.getBuffTime();
                        if (bufftime <= 0) {
                            cur_gift.resetGift(endPos);
                        }
                        else {
                            cur_gift.setBuffTime(bufftime - 1);
                            switch (cur_gift.getType()) {
                                case 0: speedup = 3; break;
                                case 1: teleport = 10; break;
                                case 2: isFreeze = true; break;
                            }
                        }
                    }

                    dogs.get(i).setThumb(animaFrame.get(i*8 + anim%8));
                    if (isFreeze) {
                        cur_dog.setThumb(resize(getResources().getDrawable(R.drawable.dogfreezed)));
                    }
                    else  {
                        cur_dog.setProgress(Math.min(cur_dog.getProgress() + (random.nextInt(3)/2 + teleport) * speedup, endPos));
                    }
                }
            }



            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    update(anim + 1);
                }
            }, 50);
        }
        else {

            btnStart.setEnabled(true);
            btnReset.setEnabled(true);

            showResult();
        }
    }

    private void showResult() {
        int i = 0;
        for (TextView item: rankPlayer) {
            item.setText(String.valueOf(result.get(i)));
            i++;
        }
    }

    private void reset() {
        for (TextView item: rankPlayer) {
            item.setText("");
        }
        for (int i = 0; i < dogs_size; i++) {
            dogs.get(i).setProgress(startPos);
            dogs.get(i).setThumb(animaFrame.get(i * 8));

            gifts.get(i).resetGift(endPos);
        }
        result.clear();
        rank = 0;

    }

    private boolean isEnd() {
        boolean allend = true;
        int index = 0;
        for (SeekBar item: dogs) {
            if (result.containsKey(index)) {
                index++;
                continue;
            }
            int pos = item.getProgress();
            if (pos < endPos) {
                allend = false;
            }
            else {
                rank++;
                result.put(index, rank);
            }
            index++;
        }
        return allend;
    }

    SeekBar CreateDog(int player) {
//        SeekBar Dog = findViewById(R.id.Dog1);
        SeekBar Dog = new SeekBar(this);
        ConstraintLayout.LayoutParams view = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        view.height = 700;
        view.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        view.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        view.topMargin = 150 * (player);

        Dog.setProgressDrawable(Drawable.createFromPath("@drawable/seekbar_template"));
        Dog.setThumb(animaFrame.get(player * 8));
        Dog.setLayoutParams(view);
        Dog.setProgress(startPos, true);
        return Dog;
    }
    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 100, 100, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
    private Drawable resize(Drawable image, int width, int height) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, width, height, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }

    private ArrayList<Drawable> getAnimationList() {
        ArrayList<Drawable> list = new ArrayList<>();
        list.add(resize(getResources().getDrawable(R.drawable.dog1)));
        list.add(resize(getResources().getDrawable(R.drawable.dog2)));
        list.add(resize(getResources().getDrawable(R.drawable.dog3)));
        list.add(resize(getResources().getDrawable(R.drawable.dog4)));
        list.add(resize(getResources().getDrawable(R.drawable.dog5)));
        list.add(resize(getResources().getDrawable(R.drawable.dog6)));
        list.add(resize(getResources().getDrawable(R.drawable.dog7)));
        list.add(resize(getResources().getDrawable(R.drawable.dog8)));

        list.add(resize(getResources().getDrawable(R.drawable.dogblue1)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblue2)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblue3)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblue4)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblue5)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblue6)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblue7)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblue8)));


        list.add(resize(getResources().getDrawable(R.drawable.dogpink1)));
        list.add(resize(getResources().getDrawable(R.drawable.dogpink2)));
        list.add(resize(getResources().getDrawable(R.drawable.dogpink3)));
        list.add(resize(getResources().getDrawable(R.drawable.dogpink4)));
        list.add(resize(getResources().getDrawable(R.drawable.dogpink5)));
        list.add(resize(getResources().getDrawable(R.drawable.dogpink6)));
        list.add(resize(getResources().getDrawable(R.drawable.dogpink7)));
        list.add(resize(getResources().getDrawable(R.drawable.dogpink8)));


        list.add(resize(getResources().getDrawable(R.drawable.dogblack1)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblack2)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblack3)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblack4)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblack5)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblack6)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblack7)));
        list.add(resize(getResources().getDrawable(R.drawable.dogblack8)));
        return list;
    }
    private ArrayList<Drawable> getGiftList() {
        ArrayList<Drawable> list = new ArrayList<Drawable>();
        list.add(resize(getResources().getDrawable(R.drawable.speedup), 50, 50));
        list.add(resize(getResources().getDrawable(R.drawable.tele), 50, 50));
        list.add(resize(getResources().getDrawable(R.drawable.freeze), 50, 50));
        return list;
    }
}