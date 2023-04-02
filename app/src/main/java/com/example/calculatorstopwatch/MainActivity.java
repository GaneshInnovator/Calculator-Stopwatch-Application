/*
 * Created by Ganesh P on 1/8/23, 6:45 PM
 * esakkiganeshtsy@gmail.com
 * Last modified 1/8/23, 6:45 PM
 * Copyright (c) 2023.
 * All rights reserved.
 * */

package com.example.calculatorstopwatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bac, bc, bp, bpow, bplus, bminus, bdiv, bmod, bequal, bdot, bbrac1, bbrac2, bsin, bcos, btan, bepow, bpi, bsqrt, blog, bln, binv;
    TextView tvsec,tvmain;
    int sum = 0;
    boolean Arithmetic,hasDot;
    String pi = "3.14159265";
    int dot,first=0;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvmain = findViewById(R.id.tvmain);

        tvmain.setMovementMethod(new ScrollingMovementMethod());
        tvmain.setEnabled(true);

        tvmain.setVerticalScrollBarEnabled(true);

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
                            Toast t = Toast.makeText(MainActivity.this, "Unable to open\n"+e.getMessage(), Toast.LENGTH_LONG);
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
                            Toast t = Toast.makeText(MainActivity.this, "Unable to share this app.", Toast.LENGTH_LONG);
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



        hasDot=false;

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        b0 = findViewById(R.id.b0);
        bac = findViewById(R.id.bac);
        bc = findViewById(R.id.bc);
        bplus = findViewById(R.id.bplus);
        bminus = findViewById(R.id.bminus);
        bdiv = findViewById(R.id.bdiv);
        bmod = findViewById(R.id.bmod);
        bequal = findViewById(R.id.bequal);
        bdot = findViewById(R.id.bdot);
        bpow= findViewById(R.id.bpow);
        bepow = findViewById(R.id.bepow);
        bsqrt = findViewById(R.id.bsqrt);
        bsin = findViewById(R.id.bsin);
        bcos = findViewById(R.id.bcos);
        btan = findViewById(R.id.btan);
        bpi = findViewById(R.id.bpi);
        bbrac1 = findViewById(R.id.bbrac1);
        bbrac2 = findViewById(R.id.bbrac2);
        blog = findViewById(R.id.blog);
        bln = findViewById(R.id.bln);
        binv = findViewById(R.id.binv);

        tvmain = findViewById(R.id.tvmain);
        tvsec = findViewById(R.id.tvsec);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String val = tvmain.getText().toString();
                    char last = 0;
                    if (Arithmetic == true) {
                        last = val.charAt(val.length() - 1);
                        if (last == ')') {
                            tvmain.setText(val + "×" + b1.getText().toString());
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(val + b1.getText().toString());
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else {
                        tvmain.setText(val + b1.getText().toString());
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String val = tvmain.getText().toString();
                    char last = 0;
                    if (Arithmetic == true) {
                        last = val.charAt(val.length() - 1);
                        if (last == ')') {
                            tvmain.setText(val + "×" + b2.getText().toString());
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(val + b2.getText().toString());
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else {
                        tvmain.setText(val + b2.getText().toString());
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String val = tvmain.getText().toString();
                    char last = 0;
                    if (Arithmetic == true) {
                        last = val.charAt(val.length() - 1);
                        if (last == ')') {
                            tvmain.setText(val + "×" + b3.getText().toString());
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(val + b3.getText().toString());
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else {
                        tvmain.setText(val + b3.getText().toString());
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String val = tvmain.getText().toString();
                    char last = 0;
                    if (Arithmetic == true) {
                        last = val.charAt(val.length() - 1);
                        if (last == ')') {
                            tvmain.setText(val + "×" + b4.getText().toString());
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(val + b4.getText().toString());
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else {
                        tvmain.setText(val + b4.getText().toString());
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String val = tvmain.getText().toString();
                    char last = 0;
                    if (Arithmetic == true) {
                        last = val.charAt(val.length() - 1);
                        if (last == ')') {
                            tvmain.setText(val + "×" + b5.getText().toString());
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(val + b5.getText().toString());
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else {
                        tvmain.setText(val + b5.getText().toString());
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String val = tvmain.getText().toString();
                    char last = 0;
                    if (Arithmetic == true) {
                        last = val.charAt(val.length() - 1);
                        if (last == ')') {
                            tvmain.setText(val + "×" + b6.getText().toString());
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(val + b6.getText().toString());
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else {
                        tvmain.setText(val + b6.getText().toString());
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String val = tvmain.getText().toString();
                    char last = 0;
                    if (Arithmetic == true) {
                        last = val.charAt(val.length() - 1);
                        if (last == ')') {
                            tvmain.setText(val + "×" + b7.getText().toString());
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(val + b7.getText().toString());
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else {
                        tvmain.setText(val + b7.getText().toString());
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String val = tvmain.getText().toString();
                    char last = 0;
                    if (Arithmetic == true) {
                        last = val.charAt(val.length() - 1);
                        if (last == ')') {
                            tvmain.setText(val + "×" + b8.getText().toString());
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(val + b8.getText().toString());
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else {
                        tvmain.setText(val + b8.getText().toString());
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String val = tvmain.getText().toString();
                    char last = 0;
                    if (Arithmetic == true) {
                        last = val.charAt(val.length() - 1);
                        if (last == ')') {
                            tvmain.setText(val + "×" + b9.getText().toString());
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(val + b9.getText().toString());
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else {
                        tvmain.setText(val + b9.getText().toString());
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String val = tvmain.getText().toString();
                    char last = 0;
                    if (Arithmetic == true) {
                        last = val.charAt(val.length() - 1);
                        if (last == ')') {
                            tvmain.setText(val + "×" + b0.getText().toString());
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(val + b0.getText().toString());
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else {
                        tvmain.setText(val + b0.getText().toString());
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        bdot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String val = tvmain.getText().toString();
                    if(Arithmetic==true) {
                        char last = val.charAt(val.length() - 1);
                        if (!hasDot) {
                            if (TextUtils.isEmpty(val) || last == '(') {
                                tvmain.setText(val + "0" + bdot.getText().toString());
                                Arithmetic = true;
                                onstring();
                            }
                            else if (last == ')') {
                                tvmain.setText(val + "×" + "0" + bdot.getText().toString());
                                Arithmetic = true;
                                onstring();
                            }
                            else {
                                tvmain.setText(val + bdot.getText().toString());
                                Arithmetic = true;
                                onstring();
                            }
                            hasDot = true;
                        }
                    }
                    else{
                        if (!hasDot) {
                            tvmain.setText(val + "0" + bdot.getText().toString());
                            Arithmetic = true;
                            onstring();
                            hasDot = true;
                        }
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        bplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String val = tvmain.getText().toString();
                    char last = val.charAt(val.length() -1);
                    if (last!='+')
                    {
                        tvmain.setText(val+bplus.getText().toString());
                        hasDot=false;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Please Enter a Number", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        bdiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String val = tvmain.getText().toString();
                    char last = val.charAt(val.length() -1);
                    if (last!='÷')
                    {
                        tvmain.setText(val+bdiv.getText().toString());
                        hasDot=false;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Please Enter a Number", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }
            }
        });
        bminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String val = tvmain.getText().toString();
                    if(TextUtils.isEmpty(val)){
                            tvmain.setText(val+"("+bminus.getText().toString());
                            hasDot=false;
                            onstring();
                    }
                    else{
                        char last = val.charAt(val.length() -1);
                        if(last!='-') {
                            tvmain.setText(val + bminus.getText().toString());
                            hasDot = false;
                            onstring();
                        }
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "SyntaxERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        bmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String val = tvmain.getText().toString();
                    char last = val.charAt(val.length() -1);
                    if (last!='×')
                    {
                        tvmain.setText(val+bmod.getText().toString());
                        hasDot=false;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Please Enter a Number", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }


            }
        });
        bpow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Arithmetic==true) {
                        String val = tvmain.getText().toString();
                        char last = val.charAt(val.length() - 1);
                        if (last == '+' || last == '-' || last == '×' || last == '÷' || last == '(') {
                            tvmain.setText(tvmain.getText().toString() +"2"+ "^" + "(");
                            Arithmetic=true;
                            onstring();
                        }
                        else {
                            tvmain.setText(tvmain.getText().toString() +"×"+"2"+ "^" + "(");
                            Arithmetic=true;
                            onstring();
                        }
                    }
                    else{
                        tvmain.setText(tvmain.getText().toString() +"2"+ "^" + "(");
                        Arithmetic=true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        bequal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Arithmetic==true) {
                        String val = tvmain.getText().toString();
                        String replacedString = val.replace('÷', '/').replace('×', '*');
                        double result = eval(replacedString);
                        String r = String.valueOf(result);
                        int length = r.length() - r.indexOf(".");
                        long Li=999999999999999L;
                        long Mi=-999999999999999L;
                        if(result<=Li && result>=0) {
                            r = BigDecimal.valueOf(result).toPlainString();
                            length = r.length() - r.indexOf(".");
                            if (r.endsWith(".0")) {
                                r = r.replace(".0", "");
                            }
                            if (length >8 && r.contains(".")) {
                                DecimalFormat formatter = new DecimalFormat("0.00000000");
                                r = formatter.format(result);
                            }
                        }
                        else if(result>=Mi && result<0){
                            r = BigDecimal.valueOf(result).toPlainString();
                            length = r.length() - r.indexOf(".");
                            if (r.endsWith(".0")) {
                                r = r.replace(".0", "");
                            }
                            if (length > 8 && r.contains(".")) {
                                DecimalFormat formatter = new DecimalFormat("0.00000000");
                                r = formatter.format(result);
                            }
                        }
                        tvmain.setText(val);
                        tvsec.setText(r);
                    }
                    else{
                        Toast t = Toast.makeText(MainActivity.this, "Please Enter a Number", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        t.show();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Invalid Format Used.", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tvmain.setText("");
                    tvsec.setText("0");
                    Arithmetic = false;
                    hasDot=false;
                    tvmain.scrollTo(0,0);
                }
                catch (Exception e){
                }

            }
        });
        bac.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    tvmain.setText("");
                    tvsec.setText("0");
                    Arithmetic = false;
                    hasDot=false;
                    tvmain.scrollTo(0,0);
                }
                catch (Exception e){
                }
                return true;
            }
        });
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String val = tvmain.getText().toString();
                    if (!val.equals(""))
                    {
                        char last = val.charAt(val.length() - 1);
                        if(last=='.') {
                            val = val.substring(0, val.length() - 1);
                            tvmain.setText(val);
                            hasDot = false;
                        }
                        else {
                            val = val.substring(0, val.length() - 1);
                            tvmain.setText(val);
                        }
                        if(TextUtils.isEmpty(val)){
                            Arithmetic = false;
                            tvmain.scrollTo(0,0);
                        }
                    }
                }
                catch (Exception e){

                }

            }
        });
        bbrac1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Arithmetic==true) {
                        String val = tvmain.getText().toString();
                        char last = val.charAt(val.length() - 1);
                        if (last == '+' || last == '-' || last == '×' || last == '÷' || last == '(') {
                            tvmain.setText(tvmain.getText()+"(");
                            Arithmetic=true;
                            hasDot=false;
                            onstring();
                        }
                        else {
                            tvmain.setText(tvmain.getText()+"×"+"(");
                            Arithmetic=true;
                            hasDot=false;
                            onstring();
                        }
                    }
                    else{
                        tvmain.setText(tvmain.getText()+"(");
                        Arithmetic=true;
                        hasDot=false;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        bbrac2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tvmain.setText(tvmain.getText()+")");
                    Arithmetic=true;
                    hasDot=false;
                    onstring();
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }
            }
        });
        bpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Arithmetic==true) {
                        String val = tvmain.getText().toString();
                        char last = val.charAt(val.length() - 1);
                        if (last == '+' || last == '-' || last == '×' || last == '÷' || last == '(') {
                            tvmain.setText(tvmain.getText()+pi);
                            Arithmetic = true;
                            hasDot=true;
                            onstring();
                        }
                        else {
                            tvmain.setText(tvmain.getText()+"×"+pi);
                            Arithmetic = true;
                            hasDot=true;
                            onstring();
                        }
                    }
                    else{
                        tvmain.setText(tvmain.getText()+pi);
                        Arithmetic = true;
                        hasDot=true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        bsin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Arithmetic==true) {
                        String val = tvmain.getText().toString();
                        char last = val.charAt(val.length() - 1);
                        if (last == '+' || last == '-' || last == '×' || last == '÷' || last == '(') {
                            tvmain.setText(tvmain.getText().toString() + "sin(");
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(tvmain.getText().toString() + "×" + "sin(");
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else{
                        tvmain.setText(tvmain.getText().toString() + "sin(");
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        bcos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Arithmetic==true) {
                        String val = tvmain.getText().toString();
                        char last = val.charAt(val.length() - 1);
                        if (last == '+' || last == '-' || last == '×' || last == '÷' || last == '(') {
                            tvmain.setText(tvmain.getText().toString() + "cos(");
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(tvmain.getText().toString() + "×" + "cos(");
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else{
                        tvmain.setText(tvmain.getText().toString() + "cos(");
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        btan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Arithmetic==true) {
                        String val = tvmain.getText().toString();
                        char last = val.charAt(val.length() - 1);
                        if (last == '+' || last == '-' || last == '×' || last == '÷' || last == '(') {
                            tvmain.setText(tvmain.getText().toString() + "tan(");
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(tvmain.getText().toString() + "×" + "tan(");
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else{
                        tvmain.setText(tvmain.getText().toString() + "tan(");
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        bepow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Arithmetic==true) {
                        String val = tvmain.getText().toString();
                        char last = val.charAt(val.length() - 1);
                        if (last == '+' || last == '-' || last == '×' || last == '÷' || last == '(') {
                            tvmain.setText(tvmain.getText().toString() + "e^(");
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(tvmain.getText().toString() + "×" + "e^(");
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else{
                        tvmain.setText(tvmain.getText().toString() + "e^(");
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }
            }
        });
        bsqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Arithmetic==true) {
                        String val = tvmain.getText().toString();
                        char last = val.charAt(val.length() - 1);
                        if (last == '+' || last == '-' || last == '×' || last == '÷' || last == '(') {
                            tvmain.setText(tvmain.getText().toString() + "√(");
                            Arithmetic = true;
                            onstring();
                        }
                        else {
                            tvmain.setText(tvmain.getText().toString() + "×" + "√(");
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else{
                        tvmain.setText(tvmain.getText().toString() + "√(");
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }
            }
        });
        binv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Arithmetic==true) {
                        String val = tvmain.getText().toString();
                        char last = val.charAt(val.length() - 1);
                        if (last == '+' || last == '-' || last == '×' || last == '÷' || last == '(') {
                            Toast t = Toast.makeText(MainActivity.this, "Invalid Format Used.", Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER, 0, 0);
                            t.show();
                        }
                        else {
                            tvmain.setText(tvmain.getText().toString() + "^" + "(-1)");
                            onstring();
                        }
                    }
                    else{
                        Toast t = Toast.makeText(MainActivity.this, "Please Enter a Number", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        t.show();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }
            }
        });
        bln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Arithmetic==true) {
                        String val = tvmain.getText().toString();
                        char last = val.charAt(val.length() - 1);
                        if (last == '+' || last == '-' || last == '×' || last == '÷' || last == '(') {
                            tvmain.setText(tvmain.getText().toString() + "ln(");
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(tvmain.getText().toString() + "×" + "ln(");
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else{
                        tvmain.setText(tvmain.getText().toString() + "ln(");
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Arithmetic==true) {
                        String val = tvmain.getText().toString();
                        char last = val.charAt(val.length() - 1);
                        if (last == '+' || last == '-' || last == '×' || last == '÷' || last == '(') {
                            tvmain.setText(tvmain.getText().toString() + "log(");
                            Arithmetic = true;
                            onstring();
                        } else {
                            tvmain.setText(tvmain.getText().toString() + "×" + "log(");
                            Arithmetic = true;
                            onstring();
                        }
                    }
                    else{
                        tvmain.setText(tvmain.getText().toString() + "log(");
                        Arithmetic = true;
                        onstring();
                    }
                }
                catch (Exception e){
                    Toast t = Toast.makeText(MainActivity.this, "Syntax ERROR", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                }

            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.calculator);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.calculator:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.stopWatch:
                        startActivity(new Intent(getApplicationContext(), stopwatch.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_botton, menu);
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

    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure do you want to exit this app?");
        builder.setCancelable(true);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finishAffinity();

            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

    }

    public void onstring(){
        String val = tvmain.getText().toString();
        int slen = val.length();
        int nl=149;
        if(slen>nl){
            Toast t = Toast.makeText(MainActivity.this, "Can't enter more than 150 characters.", Toast.LENGTH_LONG);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();
        }
    }

    //factorial
    int factorial(int n)
    {
        // find factorial
        return (n == 1 || n == 0) ? 1 : n * factorial(n - 1);

    }

    //evaluation
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z' || ch=='^'|| ch=='√') { // functions
                    while (ch >= 'a' && ch <= 'z' || ch=='^' || ch=='√') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else if (func.equals("e^")) x = Math.exp(x);
                    else if (func.equals("√")) x = Math.sqrt(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
                if (eat('^'))
                    x = Math.pow(x, parseFactor());// exponentiation;

                return x;
            }
        }.parse();
    }
}