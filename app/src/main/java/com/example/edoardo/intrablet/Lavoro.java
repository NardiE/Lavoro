package com.example.edoardo.intrablet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.edoardo.intrablet.database.OperazioniCorrenti;


public class Lavoro extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        // setto il titolo
        getSupportActionBar().setTitle("Menu Principale");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_work, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void openCloseIntervent(View view) {
        Intent i = new Intent(this, ElencoInterventi.class);
        i.putExtra("OP", "" + OperazioniCorrenti.CHIAMATECHIUSE);
        startActivity(i);
    }

    public void openNewIntervent(View view) {
        Intent i = new Intent(this, DettaglioInterventi.class);
        i.putExtra("OP", "" + OperazioniCorrenti.INSERISCIINTERVENTO);
        startActivity(i);
    }

    public void openNewActivity(View view) {
        Intent i = new Intent(this, ElencoTrasferimenti.class);
        startActivity(i);
    }

    public void openOpenActivity(View view) {
        Intent i = new Intent(this, ElencoInterventi.class);
        i.putExtra("OP", "" + OperazioniCorrenti.CHIAMATEAPERTE);
        startActivity(i);
    }
}
