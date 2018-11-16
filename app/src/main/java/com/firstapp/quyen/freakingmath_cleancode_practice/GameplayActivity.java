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

import java.util.Random;

public class GameplayActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtViewOperands;
    TextView txtViewResult;
    TextView txtPoint;
    TextView txtViewTest;
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
    private boolean chooseRightAnswer;
    private boolean choosePlusOperation;
    private boolean rightAnswer;
    private boolean playerChoice;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_gameplay_layout);
        initializeView();
        setViewOnClickListener();
        processValueDisplay();
        startTimer();
    }

    public void initializeView(){
        txtViewOperands = findViewById(R.id.textView_PlusOperands);
        txtViewResult = findViewById(R.id.textView_Result);
        txtPoint = findViewById(R.id.textView_Point);
        imgArgee = findViewById(R.id.imgView_Agree);
        imgDisargee = findViewById(R.id.imgView_Disagree);
        seekBarTimer = findViewById(R.id.seekBar_Timer);
        txtViewTest = findViewById(R.id.textView_Test);

        seekBarTimer.setProgress(seekBarTimer.getMax());
        timer_value = seekBarTimer.getMax();
    }

    public void setViewOnClickListener(){
        imgArgee.setOnClickListener(this);
        imgDisargee.setOnClickListener(this);
    }

    public void processValueDisplay(){
        prepareNumbers();

        //Xử lí quyết định hiển thị đúng hay sai kết quả
        Random random = new Random();
        chooseRightAnswer = random.nextBoolean();
        if(chooseRightAnswer){
            rightAnswer = true;
        }
        else{
            rightAnswer = false;
            // Xử lí kết quả là tổng hay hiệu
            choosePlusOperation = random.nextBoolean();
            if(choosePlusOperation){
                result += random.nextInt(2) + 1;
            }
            else{
                result -= random.nextInt(2) + 1;
                if(result == 1 || result == 0)
                    result += 1;
            }
        }
        displayNumbers();
    }

    public void prepareNumbers(){
        Random random = new Random();
        result = random.nextInt(40) + 2;
        operandA = result - (random.nextInt(result -1 ) +1);
        operandB = result - operandA;
    }

    public  void displayNumbers(){
        txtViewOperands.setText(operandA + " + " + operandB);
        txtViewResult.setText("= " + result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgView_Agree:
                playerChoice = true;
                processPlayerChoice(playerChoice);
                break;
            case R.id.imgView_Disagree:
                playerChoice = false;
                processPlayerChoice(playerChoice);
                break;
        }
    }

    public void processPlayerChoice(boolean playerChoice){
        if(playerChoice == rightAnswer){
            moveToAnotherRound();
        }
        else{
            countDownTimer.cancel();
            displayGameOverDialog();
        }
    }

    public void moveToAnotherRound(){
        //Update player's point
        point++;
        txtPoint.setText("Point: " + point);

        processValueDisplay();

        // Reset timer
        countDownTimer.cancel();
        timer_value = seekBarTimer.getMax();
        startTimer();
    }

    public void startTimer(){
        countDownTimer = new CountDownTimer(2000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update timer bar after 100ms
                timer_value = timer_value - (seekBarTimer.getMax()*100/2000);
                seekBarTimer.setProgress(timer_value);
            }

            @Override
            public void onFinish() {
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
        btnMainMenu = dialog.findViewById(R.id.button_MainMenu);
        btnRestart = dialog.findViewById(R.id.button_Restart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                point = 0;
                txtPoint.setText("Point: " + point);
                processValueDisplay();
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

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        finish();
        super.onBackPressed();
    }
}
