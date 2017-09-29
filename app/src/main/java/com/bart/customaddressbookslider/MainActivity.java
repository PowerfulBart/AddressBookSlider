package com.bart.customaddressbookslider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mShowLetterTv;
    private AddressBookSlider mSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        mSlider.setOnItemSelectedListener(new AddressBookSlider.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index, String selectedText) {
                mShowLetterTv.setText(selectedText);
            }
        });
    }

    private void initView() {

        mShowLetterTv = (TextView) findViewById(R.id.tv_show_letter);
        mSlider = (AddressBookSlider) findViewById(R.id.abs_address_slider);
    }
}
