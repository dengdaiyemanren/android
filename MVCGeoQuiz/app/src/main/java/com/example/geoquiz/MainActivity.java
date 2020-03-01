package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private int mCurrentIndex = 0;

    private ImageButton mNextButton;
    private TextView mQuestionTextView;
    private ImageButton mPrevButton;


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
        setContentView(R.layout.activity_main);

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
                        mCurrentIndex = mCurrentIndex+1 % mQuestionBank.length;
                        updateQuestion();
                    }
                }
        );

        mPrevButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCurrentIndex = mCurrentIndex - 1 %  mQuestionBank.length;
                        updateQuestion();
                    }
                }
        );

        //updateQuestion();



    }

    private void checkAnswer(boolean userPressedTrue)
    {
        if(userPressedTrue == (mQuestionBank[mCurrentIndex].isAnswerTrue()))
        {
             Toast.makeText(MainActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MainActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }

    }

    private void updateQuestion()
    {
        int question = mQuestionBank[mCurrentIndex].getTextResId();

        mQuestionTextView.setText(question);

    }
}
