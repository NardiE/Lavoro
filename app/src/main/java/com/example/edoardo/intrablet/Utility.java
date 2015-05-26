package com.example.edoardo.intrablet;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Calendar;

public class Utility {
    public static String idUnivoco(int idTecnico){
        Calendar c = Calendar.getInstance();
        String result;
        result = "";
        result += formattaStringa("" + (c.get(Calendar.YEAR) % 100));
        result += formattaStringa("" + c.get(Calendar.MONTH));
        result += formattaStringa("" + c.get(Calendar.DAY_OF_MONTH));
        result += formattaStringa("" + c.get(Calendar.HOUR));
        result += formattaStringa("" + c.get(Calendar.MINUTE));
        result += formattaStringa("" + c.get(Calendar.SECOND));
        result += formattaStringa("" + idTecnico);
        result += formattaStringa("" + ((int)(Math.random()*89) + 10));
        return result;
    }

    public static String formattaStringa(String s){
        if(s.length() == 1) return 0 + s;
        return s;
    }

    public static AlertDialog.Builder creaDialogoVeloce(Context c, String message, String title){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(c);

        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        return dlgAlert;
    }
}
