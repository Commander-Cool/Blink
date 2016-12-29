package com.capstone.blink.activities;

import android.app.Fragment;
import android.os.Bundle;

import com.capstone.blink.R;
import com.capstone.blink.fragments.MainActivityFragment;

public final class MainActivity extends BaseActivity {

    @Override
    protected Fragment createFragment() {
        return new MainActivityFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
