package com.example.heroesmusic.controller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.heroesmusic.R;
import com.google.android.material.datepicker.MaterialTextInputPicker;

public class LyricEntryFragment extends Fragment {

    private ImageView mPlay;
    private EditText mLyricText;
    private SeekBar mSeekBar;
    private TextView mProgressText;
    private Button mEnterButton, mSaveButton;

    public LyricEntryFragment() {
        // Required empty public constructor
    }

    public static LyricEntryFragment newInstance() {
        LyricEntryFragment fragment = new LyricEntryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lyric_entry, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        mPlay = view.findViewById(R.id.lyric_play);
        mLyricText = view.findViewById(R.id.lyric_edit_text);
        mSeekBar = view.findViewById(R.id.lyric_seekBar);
        mProgressText = view.findViewById(R.id.lyric_progress);
        mEnterButton = view.findViewById(R.id.lyric_enter_button);
        mSaveButton = view.findViewById(R.id.lyric_save_button);
    }
}