package com.wednesday.machinetest.fragment;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.wednesday.machinetest.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PreviewDialog extends DialogFragment implements View.OnClickListener{

    private ImageButton pausebtn, playbtn;
    MediaPlayer mPlayer;
    private TextView startTime, songTime;
    private SeekBar songPrgs;
    private static int oTime =0, sTime =0, eTime =0, fTime = 5000, bTime = 5000;
    private Handler hdlr = new Handler();
    String previewUrl= null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.preview_dialog, container, false);;


        playbtn =view.findViewById(R.id.btnPlay);
        pausebtn =view.findViewById(R.id.btnPause);
        startTime =view.findViewById(R.id.txtStartTime);
        songTime =view.findViewById(R.id.txtSongTime);
        songPrgs =view.findViewById(R.id.sBar);
        if (getArguments() != null) {
            previewUrl = getArguments().getString("preview");
        }else {
            Toast.makeText(getContext(), "Audio not found", Toast.LENGTH_SHORT).show();
        }



        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playbtn.setOnClickListener(this);
        pausebtn.setOnClickListener(this);

    }
//    private Runnable UpdateSongTime = new Runnable() {
//        @Override
//        public void run() {
//            sTime = mPlayer.getCurrentPosition();
//            startTime.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(sTime),
//                    TimeUnit.MILLISECONDS.toSeconds(sTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sTime))) );
//            songPrgs.setProgress(sTime);
//            hdlr.postDelayed(this, 100);
//        }
//    };
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPlay:
                mPlayer = new MediaPlayer();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                Toast.makeText(getContext(), previewUrl, Toast.LENGTH_SHORT).show();
                Log.d("previewUrl","----->"+previewUrl);
                try {
                    mPlayer.setDataSource("https://audio-ssl.itunes.apple.com/itunes-assets/Music/7b/f5/4a/mzm.fsexolly.aac.p.m4a");
                } catch (IllegalArgumentException e) {
                    Toast.makeText(getContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (SecurityException e) {
                    Toast.makeText(getContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IllegalStateException e) {
                    Toast.makeText(getContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                eTime = mPlayer.getDuration();
                sTime = mPlayer.getCurrentPosition();
                if(oTime == 0){
                    songPrgs.setMax(eTime);
                    oTime =1;
                }
                songPrgs.setProgress(sTime);
//                hdlr.postDelayed(UpdateSongTime, 100);
                pausebtn.setEnabled(true);
                playbtn.setEnabled(false);
                Toast.makeText(getContext(), "Playing Audio", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnPause:
                if (mPlayer!=null){
                    if(mPlayer!=null && mPlayer.isPlaying()){
                        mPlayer.stop();
                    }
                    pausebtn.setEnabled(false);
                    playbtn.setEnabled(true);
                    Toast.makeText(getContext(),"Pausing Audio", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }




}
