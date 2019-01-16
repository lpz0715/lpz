package com.lpz.playanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lpz.library.SmileView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DianZanActivity extends AppCompatActivity {


    @BindView(R.id.smileView)
    SmileView smileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dian_zan);
        ButterKnife.bind(this);

    }
}
