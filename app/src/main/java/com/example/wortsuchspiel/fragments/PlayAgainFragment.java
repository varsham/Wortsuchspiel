package com.example.wortsuchspiel.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wortsuchspiel.MainActivity;
import com.example.wortsuchspiel.R;

public class PlayAgainFragment extends Fragment {

    Button playAgain;
    Button mainMenu;

    public PlayAgainFragment() {
        // Required empty public constructor
    }

    public static PlayAgainFragment newInstance() {
        PlayAgainFragment fragment = new PlayAgainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        playAgain = view.findViewById(R.id.playAgain);
        mainMenu = view.findViewById(R.id.mainMenu);

        // if playAgain is clicked:
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch back to the game fragment
                getParentFragmentManager().beginTransaction().replace(R.id.container, new EasyFragment()).commit();
            }
        });

        // if mainMenu is clicked, switch back to the main activity:
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_again, container, false);
    }
}