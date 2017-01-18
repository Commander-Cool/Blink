package com.capstone.blink.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capstone.blink.R;
import com.capstone.blink.commands.Commands;
import com.capstone.blink.commands.Factors;
import com.capstone.blink.database.DatabaseChangeListener;
import com.capstone.blink.database.FirebaseDB;

public class MainActivityFragment extends Fragment {

    private int speed = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        FirebaseDB instance = new FirebaseDB();
        instance.onChange(new DatabaseChangeListener() {
            @Override
            public void onSuccess(String value) {
                switch (value){
                    case  Commands.ACCELERATE:
                        speed *= Factors.ACCELERATE;
                        break;
                    case Commands.SLOW_DOWN:
                        speed /= Factors.DECELERATE;
                        break;
                    case Commands.BRAKE:
                        speed = Factors.BRAKE_VALUE;
                        break;
                    case Commands.START:
                        speed *= Factors.STARTING_SPEED;
                        break;
                }
            }

            @Override
            public void onFail(String value) {

            }
        });
        return view;
    }
}
