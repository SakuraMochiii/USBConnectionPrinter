package com.wizarpos.usbconnectionprinter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PrinterUtil.init(this);
    }


    public void queryStatus(View view) {
        PrinterUtil.openDevice();
        try {
            PrinterUtil.queryStatus(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print(View view) {
        PrinterUtil.openDevice();
        try {
            PrinterUtil.printText(this, "In the spring of 2012, a group of payment industry veterans from payment, mobile communication," +
                    "and security industries got together. Thier expertise and accomplishments in the past 20+ years are : " + System.currentTimeMillis() + " print!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getInfo(View view) {
        PrinterUtil.openDevice();
        try {
            PrinterUtil.getProductInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
