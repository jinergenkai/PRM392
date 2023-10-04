package com.hungnmse160060.prm392_groupproject01_racingboy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<SeekBar> dogs = new ArrayList<SeekBar>();
    ArrayList<Gift> gifts = new ArrayList<Gift>();
    ArrayList<Drawable> animaFrame, giftFrame;
    HashMap<Integer, Integer> result = new HashMap<>();
    int dogs_size = 4, startPos = 20, endPos = 90, rank = 0, playerMoney = 100;
    TextView txtPlayerMoney, resultbet;
    ArrayList<TextView> rankPlayer = new ArrayList<>();
    ArrayList<TextView> betPlayer = new ArrayList<>();
    Button btnStart, btnReset;
    private Handler handler = new Handler();
    Random random = new Random();

    ConstraintLayout racetrack;

    MediaPlayer nhacnen, chosua1, chosua2, nhacwin, nhacthua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingMusic();

        racetrack = findViewById(R.id.container);
        resultbet = findViewById(R.id.txtResultBet);


        txtPlayerMoney= (TextView) findViewById(R.id.txtPlayerMoney);
        txtPlayerMoney.setEnabled(false);
        txtPlayerMoney.setText(String.valueOf(playerMoney));


        rankPlayer.add(findViewById(R.id.txtRankDog1));
        rankPlayer.add(findViewById(R.id.txtRankDog2));
        rankPlayer.add(findViewById(R.id.txtRankDog3));
        rankPlayer.add(findViewById(R.id.txtRankDog4));

        betPlayer.add(findViewById(R.id.txtBetDog1));
        betPlayer.add(findViewById(R.id.txtBetDog2));
        betPlayer.add(findViewById(R.id.txtBetDog3));
        betPlayer.add(findViewById(R.id.txtBetDog4));
        for (TextView textView : betPlayer) {
            textView.setText("0");
            textView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        int enteredBet = Integer.parseInt(s.toString());
                        if (enteredBet > playerMoney) {
                            s.replace(0, s.length(), "0");
                            Toast.makeText(MainActivity.this, "Số tiền cược không được vượt quá số tiền bạn có", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                    }
                }
            });
        }

        animaFrame = getAnimationList();
        giftFrame = getGiftList();

        for (int i = 0; i < dogs_size; i++) {
            SeekBar dog = CreateDog(i);
            SeekBar giftImg = new SeekBar(this);
            dog.setEnabled(false);
            giftImg.setEnabled(false);
//            giftImg.setThumb(resize(getResources().getDrawable(R.drawable.nothing), 50, 50));
            Gift gift = new Gift(i, giftImg, endPos, getResources().getDrawable(R.drawable.defaultgift));

            gifts.add(gift);
            dogs.add(dog);

            racetrack.addView(giftImg);
            racetrack.addView(dogs.get(i));
        }

        //button start, reset
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);

        btnStart.setOnClickListener((v) -> {
            if(!validateInput()){
                for (TextView textView : betPlayer) {
                    textView.setText(String.valueOf(0));
                }
                Toast.makeText(this, "Tổng số tiền cược phải bé hơn số tiền bạn đang có: " + playerMoney, Toast.LENGTH_SHORT).show();
                return;
            };
            ruttien();
            btnStart.setEnabled(false);
            btnReset.setEnabled(false);
            for (TextView textView : betPlayer) {
                textView.setEnabled(false);
            }
            chosua1.start();
            resultbet.setText("Running...");
            update(1);

        });

        btnReset.setOnClickListener((v) -> {
            reset();
        });

    }

    private void settingMusic() {
        //sound start
        nhacnen = MediaPlayer.create(this, R.raw.nhacnen);
        nhacnen.setLooping(true);
        float leftVolume = 0.6f; // Ví dụ: âm lượng 50% cho âm thanh trái
        float rightVolume = 0.6f; // Ví dụ: âm lượng 50% cho âm thanh phải
        nhacnen.setVolume(leftVolume, rightVolume);
        nhacnen.start();

        // tieng cho dua
        chosua1 = MediaPlayer.create(this, R.raw.dog1minutes);
        chosua1.setLooping(true);

        nhacwin = MediaPlayer.create(this, R.raw.win);
        nhacthua = MediaPlayer.create(this, R.raw.lose);
    }

    private boolean validateInput() {
        int x = 0;
        for (TextView textView : betPlayer) {
            x += Integer.parseInt(textView.getText().toString());
        }
        if (x>playerMoney){
            return false;
        }
        return true;
    }
    private void ruttien() {
        int sum = 0;
        for (TextView textView : betPlayer) {
            sum += Integer.parseInt(textView.getText().toString());
        }
        playerMoney -= sum;
        txtPlayerMoney.setText("" + playerMoney);
    }

    private int tongtien() {
        int sum = 0;
        for (TextView textView : betPlayer) {
            sum += Integer.parseInt(textView.getText().toString());
        }
        return sum + playerMoney;
    }

    //update screen each frame when click button start game
    private void update(int anim) {
        // if all dog not end
        if (!isEnd()) {

            // update spawn gift, random gift and when dog get gift enable buff of gift
            int i = 0;
            for (Gift item: gifts) {
                //when have gift in race, check when dog get gift
                if (item.getEnabled() && !item.getEffected()) {
                    int cur_pos_gift = item.getGiftImg().getProgress();
                    int cur_pos_dog = dogs.get(i).getProgress();
                    item.getGiftImg().setProgress(cur_pos_gift - 1);
                    if (cur_pos_gift <= cur_pos_dog+2) {
                        item.setEffected(true, endPos);
                    }
                    i++;
                    continue;
                }

                //don't spawn gift when near finish line
                if (dogs.get(i).getProgress() >= endPos - 10) {
                    i++;
                    continue;
                }

                // spawn 3 type of gift by random function
                int ran = random.nextInt(100);
                if (ran < 2) {
                    int type = random.nextInt(3);
//                    item.enableGift(type , giftFrame.get(type), endPos);
                    item.enableGift(type , getGiftFrame(type), endPos);
                }
                i++;
            }

            // update dog movement and when dog eat gift
            for (i = 0; i < dogs_size; i++) {
                SeekBar cur_dog = dogs.get(i);
                Gift cur_gift = gifts.get(i);
                if (cur_dog.getProgress() < endPos) {
                    boolean isFreeze = false;
                    int speedup = 0;
                    int teleport = 0;

                    //when dog have buff by gift
                    if (cur_gift.getEffected()) {
                        int bufftime = cur_gift.getBuffTime();
                        if (bufftime <= 0) {
                            cur_gift.resetGift(endPos);
                        }
                        else {
                            cur_gift.setBuffTime(bufftime - 1);
                            switch (cur_gift.getType()) {
                                case 0: speedup = 1; break;
                                case 1: teleport = 5; break;
                                case 2: isFreeze = true; break;
                            }
                        }
                    }

                    //update dog movement
                    dogs.get(i).setThumb(animaFrame.get(i*8 + anim%8));
                    if (isFreeze) {
                        cur_dog.setThumb(resize(getResources().getDrawable(R.drawable.dogfreezed)));
                    }
                    else  {
                        cur_dog.setProgress(Math.min(cur_dog.getProgress() + (random.nextInt(3)/2 + teleport) + speedup, endPos));
                    }
                }
            }

            //recursive
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    update(anim + 1);
                }
            }, 50);
        }
        //if all dog finish race.
        else {
            btnReset.setEnabled(true);
//            chosua1.stop();
            chosua1.pause();
            showResult();
        }
    }

    //show result when all dog finish race.
    private void showResult() {
        int i = 0;
        for (TextView item: rankPlayer) {
            item.setText(String.valueOf(result.get(i)));
            i++;
        }
        int winMoney = 0;
        String resultbetmessage = "\n";
        for (int j = 0; j < dogs_size; j++) {
            TextView item = rankPlayer.get(j);
            item.setText(String.valueOf(result.get(j)));
            int rank = result.get(j);
            //nhat an 2
            if (rank == 1) {
                int betAmount = Integer.parseInt(betPlayer.get(j).getText().toString());
                winMoney += betAmount*2;
                resultbetmessage += getNameDog(j) + " eat 2. ";
                //nhi an 1
            } else if (rank == 2) {
                int betAmount = Integer.parseInt(betPlayer.get(j).getText().toString());
                winMoney += betAmount;
                resultbetmessage += getNameDog(j) + " eat 1. ";
                //ba bet mat het
            } else if (rank == 3 || rank == 4) {
//                int betAmount = Integer.parseInt(betPlayer.get(j).getText().toString());
            }
        }
        int soTienTungCo = tongtien();
        playerMoney += winMoney;
        Toast.makeText(this, "Số tiền của bạn: " + playerMoney, Toast.LENGTH_SHORT).show();
        resultbetmessage = (soTienTungCo < playerMoney) ? "Win bet: +" + (playerMoney- soTienTungCo) + "$" + resultbetmessage : "Lose bet, try again!" + resultbetmessage;
        if (soTienTungCo < playerMoney) nhacwin.start();
        else nhacthua.start();
        resultbet.setText(resultbetmessage);
        txtPlayerMoney.setText(String.valueOf(playerMoney));
    }

    private void reset() {
        for (TextView textView : betPlayer) {
            textView.setEnabled(true);
        }
        btnStart.setEnabled(true);
        for (TextView item: rankPlayer) {
            item.setText("");
        }
        for (int i = 0; i < dogs_size; i++) {
            dogs.get(i).setProgress(startPos);
            dogs.get(i).setThumb(animaFrame.get(i * 8));

            gifts.get(i).resetGift(endPos);
        }
        resultbet.setText("Set money for Bet column and press Start to play");
        for (TextView item: betPlayer) {
            item.setText("0");
        }
        result.clear();
        rank = 0;

    }

    // check is all dog is finish race.
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

    // create seekbar dog
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
    private Drawable getGiftFrame(int type) {
        switch (type) {
            case 0: return resize(getResources().getDrawable(R.drawable.speedup), 50, 50);
            case 1: return resize(getResources().getDrawable(R.drawable.tele), 50, 50);
            case 2: return resize(getResources().getDrawable(R.drawable.freeze), 50, 50);
        }
        return getResources().getDrawable(R.drawable.defaultgift);
    }

    private String getNameDog(int type) {
        switch (type) {
            case 0: return "Yelo";
            case 1: return "Blu";
            case 2: return "Pin";
            case 3: return "Blan";
        }
        return "";
    }
}