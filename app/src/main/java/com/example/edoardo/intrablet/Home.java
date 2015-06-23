package com.example.edoardo.intrablet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.edoardo.intrablet.database.Cliente;
import com.example.edoardo.intrablet.database.MySqlLiteHelper;
import com.example.edoardo.intrablet.database.TipiIntervento;

import java.util.ArrayList;


public class Home extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setto il titolo
        getSupportActionBar().setTitle("Home");


        // Procedura di cleanUp
        /*MySqlLiteHelper msqlp = new MySqlLiteHelper(this);
        msqlp.deleteAllInterventi();
        msqlp.deleteAllSottoit();
        msqlp.deleteAllArticoli();
        msqlp.deleteAllClienti();*/

        // Se è il primo avvio
        //recupero IP e porta
        SharedPreferences sharedpreferences = getSharedPreferences(Impostazioni.preferences, Context.MODE_PRIVATE);

        if(sharedpreferences.getString(TipiConfigurazione.tipoInterventi, "").equals("") || sharedpreferences.getString(TipiConfigurazione.nomeTecnico,"").equals("") || sharedpreferences.getString(TipiConfigurazione.idTecnico,"").equals("") || sharedpreferences.getString(TipiConfigurazione.portaServer,"").equals("") || sharedpreferences.getString(TipiConfigurazione.firmePorte,"").equals("") || sharedpreferences.getString(TipiConfigurazione.ipServer,"").equals("")){
            ArrayList<Cliente> clienti = new ArrayList<>();
            for(int i = 0; i < 30; i++){
                clienti.add(new Cliente(i,"Pippo", "0","1",i + " SRL",i));
            }
            MySqlLiteHelper msql = new MySqlLiteHelper(this);
            msql.deleteAllClienti();
            for(Cliente c: clienti){
                msql.addCliente(c);
            }
            ArrayList<TipiIntervento> tipi = new ArrayList<>();
            tipi.add(new TipiIntervento(0,"FO","Formazione",0));
            tipi.add(new TipiIntervento(1,"AE","Assistenza esterna",1));
            tipi.add(new TipiIntervento(2,"AI","Assistenza interna",1));
            tipi.add(new TipiIntervento(1,"TN","Trasferimento non addebitabile",1));
            tipi.add(new TipiIntervento(2,"TA","Trasferimento addebitabile",1));
            msql.deleteAllTipiIntervento();
            for (TipiIntervento t: tipi){
                msql.addTipoIntervento(t);
            }

            msql.close();

            Intent i = new Intent(this, Impostazioni.class);
            startActivity(i);
        }
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
            Intent i = new Intent(this, Impostazioni.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.info){
            AlertDialog.Builder myb = Utility.creaDialogoVeloce(this, "Versione 1.2 \n\n Sviluppato per Signorini Software Service da Edoardo Nardi \n\n Per informazioni contattare: edoardo@signorini.it", "Informazioni");
            myb.create().show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDownload(View view) {
        Intent intent = new Intent(this, Upload.class);
        intent.putExtra("TipoInvio", "clienti");
        startActivity(intent);
    }

    public void openUpload(View view) {
        Intent intent = new Intent(this, Upload.class);
        intent.putExtra("TipoInvio", "dati");
        startActivity(intent);
    }

    public void startWork(View view) {
        Intent intent = new Intent(this, Lavoro.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
