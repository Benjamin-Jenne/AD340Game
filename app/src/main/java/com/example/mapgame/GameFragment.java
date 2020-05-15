package com.example.mapgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GameFragment extends Fragment {
    private TextView buttonEventTextView;
    private GameActivity.ButtonEvent buttonEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        buttonEventTextView = view.findViewById(R.id.buttonEventTextView);
        return view;
    }
    public void setButtonEvent(GameActivity.ButtonEvent buttonEvent){
        this.buttonEvent = buttonEvent;
        buttonEventTextView.setText(buttonEvent.buttonEventString);
    }
}
 