package com.example.bestbefore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.bestbefore.R;

public class NewFoodActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.bestbefore.REPLY";

    private EditText mEditFoodName;
    private TextView mBestBeforeDate;
    private EditText mQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_food);

        mEditFoodName = findViewById(R.id.edit_food_name);
        mQuantity = findViewById(R.id.edit_quantity);

        mBestBeforeDate = findViewById(R.id.edit_date);
        mBestBeforeDate.setInputType(InputType.TYPE_NULL);
        mBestBeforeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, mBestBeforeDate);
            }
        });

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                Bundle extras = new Bundle();

                if (TextUtils.isEmpty(mEditFoodName.getText()) || TextUtils.isEmpty(mBestBeforeDate.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String foodName = mEditFoodName.getText().toString();
                    int quantity = Integer.parseInt(mQuantity.getText().toString());
                    String bestBeforeDate = mBestBeforeDate.getText().toString();
                    extras.putString("FOOD_NAME", foodName);
                    extras.putInt("QUANTITY", quantity);
                    extras.putString("BEST_BEFORE_DATE", bestBeforeDate);
                    replyIntent.putExtras(extras);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    public void showDatePickerDialog(View v, TextView textView) {
        DialogFragment newFragment = new DatePickerFragment(textView);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}