package com.example.ayadzeino.appesiea;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rachid on 29/12/2015.
 */
public class TestActivity extends AppCompatActivity {

    @OnClick(R.id.connect) void takeMeToLogin(){
        Intent secondeActivite = new Intent(TestActivity.this,LoginActivity.class);
        startActivity(secondeActivite);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

    }
    //Notification
    public void notification_test(View view){
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo_ballon)
                        .setContentTitle("Voici la notification")
                        .setContentText("Hello World!");
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, mBuilder.build());
        Toast.makeText(getApplicationContext(), getString(R.string.notif), Toast.LENGTH_LONG). show();
    }

}


