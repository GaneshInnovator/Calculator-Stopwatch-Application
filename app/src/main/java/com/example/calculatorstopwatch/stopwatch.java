/*
 * Created by Ganesh P on 1/8/23, 6:45 PM
 * esakkiganeshtsy@gmail.com
 * Last modified 1/8/23, 6:45 PM
 * Copyright (c) 2023.
 * All rights reserved.
 */

package com.example.calculatorstopwatch;

import static android.icu.lang.UProperty.INT_START;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class stopwatch extends AppCompatActivity {

    TextView chronometer,tvinfo;
    ImageButton btStart,btStop,btshare;

    private boolean isResume;
    Handler handler;

    String time;
    String val;

    long tMilliSec,tStart,tBuff,tUpdate = 0L;
    int sec,min,MilliSec;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_Open,R.string.close_menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_calcualtor:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        //drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_stopwatch:
                        startActivity(new Intent(getApplicationContext(),stopwatch.class));
                        overridePendingTransition(0,0);
                        //drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_rateus:
                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                        Intent i = new Intent(Intent.ACTION_VIEW,uri);
                        try{
                            startActivity(i);
                        }
                        catch (Exception e){
                            Toast t = Toast.makeText(stopwatch.this, "Unable to open\n"+e.getMessage(), Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER, 0, 0);
                            t.show();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_shares:
                        try {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this cool Application");
                            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                            startActivity(Intent.createChooser(intent, "Share Via"));
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                        catch(Exception e){
                            Toast t = Toast.makeText(stopwatch.this, "Unable to share this app.", Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER, 0, 0);
                            t.show();
                        }
                        break;
                    case R.id.nav_info:
                        startActivity(new Intent(getApplicationContext(),About.class));
                        overridePendingTransition(0,0);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_exit:
                        onBackPressed();
                        break;

                }
                return true;
            }
        });



        chronometer = (TextView) findViewById(R.id.chronometer);
        tvinfo = (TextView) findViewById(R.id.tvinfo);

        btStart = (ImageButton) findViewById(R.id.bt_start);
        btStop = (ImageButton) findViewById(R.id.bt_stop);
        btshare = (ImageButton) findViewById(R.id.bt_share);

        handler = new Handler();

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!isResume){
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable,0);
                    isResume = true;
                    btStop.setVisibility(View.GONE);
                    btshare.setVisibility(View.INVISIBLE);
                    tvinfo.setVisibility(View.INVISIBLE);
                    btStart.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_baseline_pause_24
                    ));
                    tvinfo.setText("");
               }else{
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    isResume=false;
                    btStop.setVisibility(View.VISIBLE);
                    btshare.setVisibility(View.VISIBLE);
                    tvinfo.setVisibility(View.VISIBLE);
                    String html="<b>"+"DETAILS:"+"</b>";
                   tvinfo.setText(Html.fromHtml(html)+"\n"+
                    "\nYour time is "+time+"\nMinutes : "+min+"\nSeconds : "+sec+"\nMilliSeconds : "+MilliSec);
                    btStart.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_baseline_play_arrow_24
                    ));

               }
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume){
                    btshare.setVisibility(View.INVISIBLE);
                    tvinfo.setVisibility(View.INVISIBLE);
                    btStart.setImageDrawable(getResources().getDrawable(
                            R.drawable.ic_baseline_play_arrow_24
                    ));
                    tMilliSec = 0l;
                    tStart = 0L;
                    tBuff = 0L;
                    tUpdate = 0L;
                    sec = 0;
                    min = 0;
                    MilliSec = 0;
                    chronometer.setText("00:00:00");
                    tvinfo.setText("");
                }
            }
        });

        btshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume){
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Time");
                    intent.putExtra(Intent.EXTRA_TEXT, "My time is " + time);
                    startActivity(Intent.createChooser(intent, "Share Via"));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.stopWatch);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.calculator:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.stopWatch:
                        startActivity(new Intent(getApplicationContext(),stopwatch.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate/1000);
            min=sec/60;
            sec=sec%60;
            MilliSec = (int) (tUpdate%100);
            time = String.format(Locale.getDefault(),"%02d:%02d:%02d",min,sec,MilliSec);
            chronometer.setText(String.format("%02d",min)+":"
                    +String.format("%02d",sec)
                    +":"+String.format("%02d",MilliSec));
            handler.postDelayed(this,60);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_botton,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this cool Application");
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
            startActivity(Intent.createChooser(intent, "Share Via"));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override

    public void onBackPressed () {

        final AlertDialog.Builder builder = new AlertDialog.Builder(stopwatch.this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure do you want to exit this app?");
        builder.setCancelable(true);

        builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i ){

                finishAffinity();

            }
        } );
        builder.setNegativeButton("CANCEL" ,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }  );
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

    }

}