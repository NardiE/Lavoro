package com.example.edoardo.intrablet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.edoardo.intrablet.database.OperazioniCorrenti;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class FirmaActivity extends ActionBarActivity {
    private String HWSW;
    private Integer intervento;
    private int operazioneCorrente;
    private int operazioneprecedente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma);

        Intent i = getIntent();
        HWSW = i.getStringExtra("HWSW");
        if(i.getStringExtra("OPP")!= null && !i.getStringExtra("OPP").equals("")){
            operazioneprecedente = Integer.parseInt(i.getStringExtra("OPP"));
        }
        operazioneCorrente = Integer.parseInt(i.getStringExtra("OP"));
        if(i.getStringExtra("IDIT") != null && !(i.getStringExtra("IDIT").equals("")))
            intervento = Integer.parseInt(i.getStringExtra("IDIT"));

        FirmaView vistaFirma = (FirmaView) findViewById(R.id.drawing);
        String path = Environment.getExternalStorageDirectory().toString();
        String filename = "firma" + intervento + HWSW + ".png";

        vistaFirma.setFilePath(path + "/" + filename);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void cancella(View view) {
        FirmaView vistaFirma = (FirmaView) findViewById(R.id.drawing);
        vistaFirma.delete();
    }

    public void salva(View view) {
        FirmaView vistaFirma = (FirmaView) findViewById(R.id.drawing);
        vistaFirma.firma();
        Bitmap toSave = vistaFirma.getCanvasBitmap();
        try{
            String path = Environment.getExternalStorageDirectory().toString();
            String file = "firma" + intervento + HWSW + ".png";
            OutputStream stream = new FileOutputStream(path + "/" + file);
            Bitmap resized = Bitmap.createScaledBitmap(toSave, FirmaView.rwt, FirmaView.rht, true);
            // ruoto
            resized = rotateImage(resized, -90);
            resized.compress(Bitmap.CompressFormat.PNG,100, stream);
            /*Utility.AndroidBmpUtil.save(resized, path + "/" + file);*/
        }catch (Exception e){
            e.printStackTrace();
        }

        if(operazioneCorrente == OperazioniCorrenti.NUOVOARTICOLO || operazioneCorrente == OperazioniCorrenti.MODIFICAARTICOLO){
            Intent i = new Intent(this, DettaglioInterventi.class);
            i.putExtra("OP", "" + OperazioniCorrenti.MODIFICAINTERVENTO);
            if(operazioneprecedente == OperazioniCorrenti.CHIAMATEAPERTE){
                i.putExtra("OPP", "" + operazioneprecedente);
            }
            i.putExtra("ID", "" + intervento);
            startActivity(i);
        }
        if(operazioneCorrente == OperazioniCorrenti.CHIAMATECHIUSE){
            Intent i = new Intent(this, DettaglioInterventi.class);
            i.putExtra("OP", "" + OperazioniCorrenti.CHIAMATECHIUSE);
            if(operazioneprecedente == OperazioniCorrenti.CHIAMATEAPERTE){
                i.putExtra("OPP", "" + operazioneprecedente);
            }
            i.putExtra("ID", "" + intervento);
            startActivity(i);
        }
    }

    public void annulla(View view) {
        if(operazioneCorrente == OperazioniCorrenti.NUOVOARTICOLO || operazioneCorrente == OperazioniCorrenti.MODIFICAARTICOLO){
            Intent i = new Intent(this, DettaglioInterventi.class);
            i.putExtra("OP", "" + OperazioniCorrenti.MODIFICAINTERVENTO);
            if(operazioneprecedente == OperazioniCorrenti.CHIAMATEAPERTE){
                i.putExtra("OPP", "" + operazioneprecedente);
            }
            i.putExtra("ID", "" + intervento);
            startActivity(i);
        }
        if(operazioneCorrente == OperazioniCorrenti.CHIAMATECHIUSE){
            Intent i = new Intent(this, DettaglioInterventi.class);
            i.putExtra("OP", "" + OperazioniCorrenti.CHIAMATECHIUSE);
            if(operazioneprecedente == OperazioniCorrenti.CHIAMATEAPERTE){
                i.putExtra("OPP", "" + operazioneprecedente);
            }
            i.putExtra("ID", "" + intervento);
            startActivity(i);
        }
    }

    public static  Bitmap rotateImage(Bitmap src, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        return bmp;
    }
}
