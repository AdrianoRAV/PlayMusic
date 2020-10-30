package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    //private SeekBar seekBar;
    private AudioManager audioManager;
    private SeekBar seekVolume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekVolume = findViewById(R.id.seekVolume);

        //confira o audio mananger
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        //recuperando o valores de volume máximo atual
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);


        //configurar valores para Seekbar
        seekVolume.setMax(volumeMaximo);
        //configurar o progresso para Seekbar
        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress,AudioManager.FLAG_SHOW_UI);//essa flag faz configurações adicionais se colocar zero ele não modifica naa deixa como está

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.bach);
    }

    public void ExecutarPlay(View view){
        if(mediaPlayer != null){
            mediaPlayer.start();
        }
    }
    public void PausarMusica(View view){
        if (mediaPlayer != null){
            mediaPlayer.pause();
        }
    }
    public void StopMusica(View view){
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(getApplicationContext(),
                    R.raw.bach);
        }
    }
     // configurações para quando a activity for destruida , economiza recurso do usuario
    protected void onDestroy(){
        super.onDestroy();
        if (mediaPlayer !=null && mediaPlayer.isLooping()){
            mediaPlayer.stop();///para a musica
            mediaPlayer.release();// libera memoria,remove recursos de memoria do app
            mediaPlayer = null;
        }
    }

    //pausa a musica quando sai do app
    @Override
    protected void onStop(){
        if(mediaPlayer != null){
            mediaPlayer.pause();
        }
    }
}
