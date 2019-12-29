package com.example.fleetmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import org.jetbrains.annotations.Nullable;


public class AlertDialogActivity extends Activity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();

        String title = i.getStringExtra("title");
        String msg=i.getStringExtra("msg");

        final MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.siren);
        mPlayer.start();


        final AlertDialog alertDialog = new AlertDialog.Builder(this , R.style.MyAlertDialogStyle).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.sos_new);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                mPlayer.stop();
                Intent a=new Intent(AlertDialogActivity.this,MainMenuActivity.class);
                startActivity(a);

            }
        });

        alertDialog.show();



    }
}
