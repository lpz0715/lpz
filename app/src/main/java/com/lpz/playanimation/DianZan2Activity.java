package com.lpz.playanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lpz.library.IOnCheckedChangeListener;
import com.lpz.library.SmileView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DianZan2Activity extends AppCompatActivity {

    @BindView(R.id.smileView)
    SmileView smileView;
    @BindView(R.id.smileView2)
    SmileView smileView2;
    @BindView(R.id.smileView3)
    SmileView smileView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dian_zan2);
        ButterKnife.bind(this);

        smileView.onSmileViewCheckedChangeListner(new IOnCheckedChangeListener() {
            @Override
            public void checkedChangeListener(boolean nowIsCheck) {
                if (nowIsCheck) {
                    smileView2.setChecked(false);
                    smileView3.setChecked(false);
                    smileView2.notifyChange();
                    smileView3.notifyChange();

                }
            }
        });

        smileView2.onSmileViewCheckedChangeListner(new IOnCheckedChangeListener() {
            @Override
            public void checkedChangeListener(boolean nowIsCheck) {
                if (nowIsCheck) {
                    smileView.setChecked(false);
                    smileView3.setChecked(false);
                    smileView.notifyChange();
                    smileView3.notifyChange();

                }
            }
        });
        smileView3.onSmileViewCheckedChangeListner(new IOnCheckedChangeListener() {
            @Override
            public void checkedChangeListener(boolean nowIsCheck) {
                if (nowIsCheck) {
                    smileView.setChecked(false);
                    smileView2.setChecked(false);
                    smileView.notifyChange();
                    smileView2.notifyChange();

                }
            }
        });
    }
}
