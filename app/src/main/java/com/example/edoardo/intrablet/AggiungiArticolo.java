package com.example.edoardo.intrablet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edoardo.intrablet.R;
import com.example.edoardo.intrablet.database.Articolo;
import com.example.edoardo.intrablet.database.MySqlLiteHelper;
import com.example.edoardo.intrablet.database.OperazioniCorrenti;

import java.util.ArrayList;

public class AggiungiArticolo extends ActionBarActivity {
    private int IDIt;
    private int ID;
    private int operazioneCorrente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_articolo);
        Intent intent = getIntent();
        IDIt = Integer.parseInt(intent.getStringExtra("IDIt"));
        operazioneCorrente = Integer.parseInt(intent.getStringExtra("OP"));
        ((TextView) findViewById(R.id.edttxtIdInterventoArticolo)).setText("" + IDIt);
        if(operazioneCorrente == OperazioniCorrenti.MODIFICAARTICOLO || operazioneCorrente == OperazioniCorrenti.CHIAMATECHIUSE){
            MySqlLiteHelper mysql = new MySqlLiteHelper(this);
            ID = Integer.parseInt(intent.getStringExtra("ID"));
            Articolo art = mysql.getArticoli(ID).get(0);
            ((EditText) findViewById(R.id.edttxtMatricola)).setText("" + art.getMatricola());
            ((EditText) findViewById(R.id.edttxtCodice)).setText("" + art.getCodice());
            ((EditText) findViewById(R.id.edttxtDescrizione)).setText("" + art.getDescrizione());
            ((EditText) findViewById(R.id.edttxtPrezzo)).setText("" + art.getPrezzo());
            ((EditText) findViewById(R.id.edttxtQuantità)).setText("" + art.getQt());
            Spinner myspinner = (Spinner) findViewById(R.id.spinner);
            myspinner.setSelection(((ArrayAdapter) myspinner.getAdapter()).getPosition(art.getTipointervento()));
        }
        if(operazioneCorrente == OperazioniCorrenti.CHIAMATECHIUSE){
            ((Button) findViewById(R.id.btnregistraart)).setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aggiungi_articolo, menu);
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

    public void aggiungiArticolo(View view) {
        int j = 0;
        MySqlLiteHelper mysql = new MySqlLiteHelper(this);
        ArrayList<Articolo> articoli = mysql.getAllArticolo();
        SharedPreferences sharedpreferences = getSharedPreferences(Settings.preferences, Context.MODE_PRIVATE);

        Integer idTecnico = Integer.parseInt(sharedpreferences.getString(TipiConfigurazione.idTecnico, ""));

        //assegno un ID all'articolo
        for(Articolo art : articoli){
            if(art.getId() < j){
                j = art.getId();
            }
        }

        j = j -1;

        Spinner myspinner = (Spinner) findViewById(R.id.spinner);

        String idUnivoco = Utility.idUnivoco(idTecnico);
        //j
        //Idit
        String matricola = ((EditText) findViewById(R.id.edttxtMatricola)).getText().toString();
        String codice = ((EditText) findViewById(R.id.edttxtCodice)).getText().toString();
        String descrizione = ((EditText) findViewById(R.id.edttxtDescrizione)).getText().toString();
        Float prezzo;
        if(((EditText) findViewById(R.id.edttxtPrezzo)).getText().toString().equals("")){
            ((EditText) findViewById(R.id.edttxtPrezzo)).setText("0.0");
        }
        try{prezzo = Float.parseFloat(((EditText) findViewById(R.id.edttxtPrezzo)).getText().toString());}
        catch(Exception e){
            Toast.makeText(this,"Formato Prezzo Non Corretto", Toast.LENGTH_SHORT).show();
            return;
        };
        Integer quantità;
        try{quantità = Integer.parseInt(((EditText) findViewById(R.id.edttxtQuantità)).getText().toString());}
        catch(Exception e){
            Toast.makeText(this,"Formato Quantità Non Corretto", Toast.LENGTH_SHORT).show();
            return;
        };
        String tipoInt = myspinner.getSelectedItem().toString();

        if(operazioneCorrente == OperazioniCorrenti.MODIFICAARTICOLO){
            Articolo vecchio = mysql.getArticoli(ID).get(0);
            Articolo nuovo = new Articolo(vecchio.getId(), vecchio.getIDit(), matricola, codice, descrizione, tipoInt, vecchio.getMerceologico(), vecchio.getGaranzia(), prezzo, vecchio.getId(), quantità, vecchio.getIdunivoco());
            mysql.updateArticolo(nuovo);
            Intent i = new Intent(this, NewInterventActivity.class);
            i.putExtra("OP","" + OperazioniCorrenti.RITORNO);
            i.putExtra("ID", "" + IDIt);
            startActivity(i);
        }

        if(operazioneCorrente == OperazioniCorrenti.NUOVOARTICOLO) {
            Articolo nuovo = new Articolo(j, IDIt, matricola, codice, descrizione, tipoInt, "", "", prezzo, j, quantità, idUnivoco);

            mysql.addArticolo(nuovo);
            Intent i = new Intent(this, NewInterventActivity.class);
            i.putExtra("OP","" + OperazioniCorrenti.RITORNO);
            i.putExtra("ID", "" + IDIt);
            startActivity(i);
        }

    }

    public void annullaArticolo(View view) {
        if(operazioneCorrente == OperazioniCorrenti.NUOVOARTICOLO || operazioneCorrente == OperazioniCorrenti.MODIFICAARTICOLO){
            Intent i = new Intent(this, NewInterventActivity.class);
            i.putExtra("OP", "" + OperazioniCorrenti.RITORNO);
            i.putExtra("ID", "" + IDIt);
            startActivity(i);
        }
        if(operazioneCorrente == OperazioniCorrenti.CHIAMATECHIUSE){
            Intent i = new Intent(this, NewInterventActivity.class);
            i.putExtra("OP", "" + OperazioniCorrenti.CHIAMATECHIUSE);
            i.putExtra("ID", "" + IDIt);
            startActivity(i);
        }
    }
}
