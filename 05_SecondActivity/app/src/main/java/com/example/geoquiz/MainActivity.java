package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ISCHEATER = "isCheater";

    private static final int REQUEST_CODE_CHEAT = 0;


    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private int mCurrentIndex = 0;

    private ImageButton mNextButton;
    private TextView mQuestionTextView;
    private ImageButton mPrevButton;

    private float correctNumbers=0;
    private boolean mIsCheater= false;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

            mIsCheater=savedInstanceState.getBoolean(KEY_ISCHEATER,false);
            mQuestionBank[mCurrentIndex].setCheated(true);//防止看答案返回后再旋转QuizActivity 清除isChrated

        }

        mTrueButton = (Button)findViewById(R.id.true_button);
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());

        mTrueButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                      checkAnswer(true);

                    }
                }
        );

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(false);
                    }
                }
        );




        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mPrevButton = (ImageButton)findViewById(R.id.prev_button);

        mNextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;

                        mIsCheater = false;
                        if (mQuestionBank[mCurrentIndex].isCheated()){
                            mIsCheater=true;
                        }


                        updateQuestion();
                        mTrueButton.setEnabled(true);
                        mFalseButton.setEnabled(true);

                    }
                }
        );

        mPrevButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCurrentIndex = (Math.abs(mCurrentIndex - 1)) %  mQuestionBank.length;
                        updateQuestion();
                    }
                }
        );

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                       /// Intent intent = new Intent(MainActivity.this,CheatActivity.class);
                        boolean answerIsTrue  = mQuestionBank[mCurrentIndex].isAnswerTrue();
                        Intent intent =CheatActivity.newIntent(MainActivity.this
                        ,answerIsTrue);

                        //startActivity(intent);
                        startActivityForResult(intent,REQUEST_CODE_CHEAT);

                    }
                }
        );


        updateQuestion();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            mQuestionBank[mCurrentIndex].setCheated(mIsCheater);
        }
    }



    private void checkAnswer(boolean userPressedTrue)
    {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);

        if(mIsCheater)
        {
            Toast.makeText(MainActivity.this,R.string.judgment_toast,Toast.LENGTH_SHORT).show();
        }

        if(userPressedTrue == (mQuestionBank[mCurrentIndex].isAnswerTrue()))
        {
             Toast.makeText(MainActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
            correctNumbers++;
        }
        else
        {
            Toast.makeText(MainActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }
        if (mCurrentIndex==mQuestionBank.length-1){
            Toast.makeText(this,"You score: "+(correctNumbers/mQuestionBank.length)*100+"%",Toast.LENGTH_SHORT).show();
            correctNumbers=0;
        }


    }

    private void updateQuestion()
    {
        int question = mQuestionBank[mCurrentIndex].getTextResId();

        mQuestionTextView.setText(question);

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


}
