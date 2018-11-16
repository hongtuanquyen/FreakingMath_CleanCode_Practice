package com.firstapp.quyen.freakingmath_cleancode_practice;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameplayActivity extends AppCompatActivity implements View.OnClickListener {
    // Random random;
    TextView txtViewOperands;
    TextView txtViewResult;
    TextView txtPoint;
    ImageView imgArgee;
    ImageView imgDisargee;
    SeekBar seekBarTimer;
    Button btnMainMenu;
    Button btnRestart;
    Dialog dialog;

    private int result;
    private int operandA;
    private int operandB;
    private int point;
    private int timer_value;
    private boolean displayRightAnswer = true;
    private boolean displayPlusMark = true;
    private boolean rightanswer;
    private boolean yourchoice;
    private CountDownTimer countDownTimer;
    private ArrayList<Integer> arrayScore;


    TextView txtViewTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_gameplay_layout);
        txtViewOperands = findViewById(R.id.textView_PlusOperands);
        txtViewResult = findViewById(R.id.textView_Result);
        txtPoint = findViewById(R.id.textView_Point);
        imgArgee = findViewById(R.id.imgView_Agree);
        imgDisargee = findViewById(R.id.imgView_Disagree);
        seekBarTimer = findViewById(R.id.seekBar_Timer);
        seekBarTimer.setProgress(seekBarTimer.getMax());
        timer_value = seekBarTimer.getMax();

        txtViewTest = findViewById(R.id.textView_Test);
        arrayScore = new ArrayList<>();
        imgArgee.setOnClickListener(this);
        imgDisargee.setOnClickListener(this);
        startTimer();
        displayNumbers();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgView_Agree:
                yourchoice = true;
                if(yourchoice == rightanswer){
                    setAnotherRound();
                }
                else{
                    txtViewTest.setText(arrayScore.get(0).toString() + " " + arrayScore.get(1).toString() + " " + arrayScore.get(2).toString() + " " + arrayScore.get(3).toString() + " " + arrayScore.get(4).toString() + " ");
                    displayGameOverDialog();
                }
                break;
            case R.id.imgView_Disagree:
                yourchoice = false;
                if(yourchoice == rightanswer){
                    setAnotherRound();
                }
                else{
                    txtViewTest.setText(arrayScore.get(0).toString() + " " + arrayScore.get(1).toString() + " " + arrayScore.get(2).toString() + " " + arrayScore.get(3).toString() + " " + arrayScore.get(4).toString() + " ");
                    displayGameOverDialog();
                }
                break;
        }
    }

    public void setAnotherRound(){
        point++;
        txtPoint.setText("Point: " + point);
        displayNumbers();
        countDownTimer.cancel();
        timer_value = seekBarTimer.getMax();
        startTimer();
    }
    public void startTimer(){
        countDownTimer = new CountDownTimer(2000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer_value = timer_value - (seekBarTimer.getMax()*100/2000);
                seekBarTimer.setProgress(timer_value);
            }

            @Override
            public void onFinish() {
                txtViewTest.setText(arrayScore.get(0).toString() + " " + arrayScore.get(1).toString() + " " + arrayScore.get(2).toString() + " " + arrayScore.get(3).toString() + " " + arrayScore.get(4).toString() + " ");
                displayGameOverDialog();
            }
        }.start();
    }

    public void displayGameOverDialog(){
        dialog = new Dialog(GameplayActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_gameover_layout);
        dialog.show();
        countDownTimer.cancel();
        btnMainMenu = dialog.findViewById(R.id.button_MainMenu);
        btnRestart = dialog.findViewById(R.id.button_Restart);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                point = 0;
                txtPoint.setText("Point: " + point);
                displayNumbers();
                countDownTimer.cancel();
                timer_value = seekBarTimer.getMax();
                startTimer();
            }
        });

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                finish();
            }
        });
    }

    public void displayNumbers(){
        setValue();
        Random random = new Random();
        displayRightAnswer = random.nextBoolean();
        if(displayRightAnswer){
            setTextView();
            rightanswer = true;
        }
        else{
            rightanswer = false;
            displayPlusMark = random.nextBoolean();
            if(displayPlusMark){
                result += random.nextInt(2) + 1;
                setTextView();
            }
            else{
                result -= random.nextInt(2) + 1;
                if(result == 1 || result == 0)
                    result += 1;
                setTextView();
            }
        }
    }

    public  void setTextView(){
        txtViewOperands.setText(operandA + " + " + operandB);
        txtViewResult.setText("= " + result);
    }
    public void setValue(){
        Random random = new Random();
        result = random.nextInt(40) + 2;
        operandA = result - (random.nextInt(result -1 ) +1);
        operandB = result - operandA;
    }


    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        finish();
        super.onBackPressed();
    }


}
