package com.example.heroesmusic.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.heroesmusic.R;
import com.example.heroesmusic.database.AppDatabase;
import com.example.heroesmusic.helper.Toaster;
import com.example.heroesmusic.model.Lyric;
import com.example.heroesmusic.utils.MediaPlayerGlobal;

import java.util.ArrayList;
import java.util.List;

public class LyricEntryFragment extends Fragment {

    private static final String MUSIC_ID = "com.example.heroesMusic.controller_lyricId";

    private ImageView mPlay;
    private EditText mLyricText;
    private SeekBar mSeekBar;
    private TextView mCurrentTime, mStartTime;
    private Button mEnterButton, mSaveButton, mDeleteButton, mStartRecordButton;

    private List<String> mTexts;
    private List<Integer> mProgress;
    private Long mLyricId;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler;
    private double startTime;
    private int mSaveProgress;

    public LyricEntryFragment() {
        // Required empty public constructor
    }

    public static LyricEntryFragment newInstance(Long musicId) {
        LyricEntryFragment fragment = new LyricEntryFragment();
        Bundle args = new Bundle();
        args.putLong(MUSIC_ID, musicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLyricId = getArguments().getLong(MUSIC_ID);
        }
        mTexts = new ArrayList<>();
        mProgress = new ArrayList<>();
        mMediaPlayer = ((MediaPlayerGlobal) getActivity().getApplication()).getMediaPlayer();
        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lyric_entry, container, false);
        findViews(view);
        handelSeekBar();
        listenerOfButtons();
        return view;
    }

    private void listenerOfButtons() {
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });
        mStartRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTime.setText("Start time : " + mCurrentTime.getText());
                mSaveProgress = mMediaPlayer.getCurrentPosition() ;
            }
        });
        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLyricText.getText().toString().equals("") && !mStartTime.getText().toString().equals("Start time :")) {
                    mTexts.add(mLyricText.getText().toString());
                    mProgress.add(mSaveProgress);
                    mLyricText.setText("");
                    mStartTime.setText(R.string.start_time);
                }else Toaster.makeToast(getContext(), "please enter text and time");
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTexts.size() == 0)
                    Toaster.makeToast(getActivity(), "please enter any text for music");
                else {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("you sure for save lyric ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Lyric lyric = new Lyric(mLyricId, mTexts, mProgress);
                                if (AppDatabase.getInstance(getContext()).favoriteDao().getLyricById(mLyricId) == null) {
                                    AppDatabase.getInstance(getContext()).favoriteDao().insertLyrics(lyric);
                                }else {
                                    AppDatabase.getInstance(getContext()).favoriteDao().updateLyric(lyric);
                                }
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        }).create();
                alertDialog.show();
            }
        }});
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppDatabase.getInstance(getContext()).favoriteDao().getLyricById(mLyricId) != null) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("you sure for delete lyric ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AppDatabase.getInstance(getContext()).favoriteDao().deleteLyricById(mLyricId);
                                    getActivity().finish();
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().finish();
                                }
                            })
                            .create();
                    alertDialog.show();
                }else {
                    Toaster.makeToast(getContext(), "this music has no lyric");
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPlay.setImageResource(mMediaPlayer.isPlaying() ? R.drawable.ic_pause_icon : R.drawable.ic_play_icon);
    }

    private void handelSeekBar() {
        mSeekBar.setMax(mMediaPlayer.getDuration());
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mMediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        mHandler.postDelayed(UpdateSongTime, 100);
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mMediaPlayer.getCurrentPosition();
            mCurrentTime.setText(createTimerLabel((int) startTime));
            mSeekBar.setProgress((int) startTime);
            mHandler.postDelayed(this, 100);
        }
    };

    public String createTimerLabel(int duration){
        String timerLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        timerLabel += min + ":" ;
        if (sec < 10) timerLabel += "0";
        timerLabel += sec;
        return timerLabel;
    }

    public void pause(){
        if(mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mPlay.setImageResource(R.drawable.ic_play_icon);
        }
        else {
            mMediaPlayer.start();
            mPlay.setImageResource(R.drawable.ic_pause_icon);
        }
    }

    private void findViews(View view) {
        mPlay = view.findViewById(R.id.lyric_play);
        mLyricText = view.findViewById(R.id.lyric_edit_text);
        mSeekBar = view.findViewById(R.id.lyric_seekBar);
        mCurrentTime = view.findViewById(R.id.lyric_progress);
        mEnterButton = view.findViewById(R.id.lyric_enter_button);
        mSaveButton = view.findViewById(R.id.lyric_save_button);
        mDeleteButton = view.findViewById(R.id.lyric_delete);
        mStartTime = view.findViewById(R.id.lyric_start_time_text);
        mStartRecordButton = view.findViewById(R.id.lyric_start_time_record);
    }
}