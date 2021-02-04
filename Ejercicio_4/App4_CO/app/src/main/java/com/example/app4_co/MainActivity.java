package com.example.app4_co;

import androidx.appcompat.app.AppCompatActivity;
import  android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import javax.xml.transform.Result;


public abstract class MainActivity extends AppCompatActivity {
    int peticion = 1;
    Uri url1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.autoCompleteToStart){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void grabar (View v){
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, peticion);
    }

    public void reproducir (View v){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, url1);
        mediaPlayer.start();
    }
}