package com.example.edoardo.intrablet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class Impostazioni extends ActionBarActivity {
    public static String preferences = "preferenze";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedpreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE);

        EditText tipo = (EditText) findViewById(R.id.edttxtTipoConf);
        EditText nome = (EditText) findViewById(R.id.edttxtNomeConf);
        EditText id = (EditText) findViewById(R.id.edttxtIDConf);
        EditText ip = (EditText) findViewById(R.id.edttxtIPConf);
        EditText porta = (EditText) findViewById(R.id.edttxtPortaConf);
        EditText portafirme = (EditText) findViewById(R.id.edttxtPortaFirme);
        tipo.setText(sharedpreferences.getString(TipiConfigurazione.tipoInterventi, ""));
        nome.setText(sharedpreferences.getString(TipiConfigurazione.nomeTecnico, ""));
        id.setText(sharedpreferences.getString(TipiConfigurazione.idTecnico, ""));
        ip.setText(sharedpreferences.getString(TipiConfigurazione.ipServer,""));
        porta.setText(sharedpreferences.getString(TipiConfigurazione.portaServer,""));
        portafirme.setText(sharedpreferences.getString(TipiConfigurazione.firmePorte,""));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

    public void salvaConfigurazioni(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences(preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(TipiConfigurazione.tipoInterventi, ((EditText)findViewById(R.id.edttxtTipoConf)).getText().toString());
        editor.putString(TipiConfigurazione.idTecnico, ((EditText)findViewById(R.id.edttxtIDConf)).getText().toString());
        editor.putString(TipiConfigurazione.nomeTecnico, ((EditText) findViewById(R.id.edttxtNomeConf)).getText().toString());
        editor.putString(TipiConfigurazione.ipServer, ((EditText) findViewById(R.id.edttxtIPConf)).getText().toString());
        editor.putString(TipiConfigurazione.portaServer, ((EditText) findViewById(R.id.edttxtPortaConf)).getText().toString());
        editor.putString(TipiConfigurazione.firmePorte, ((EditText) findViewById(R.id.edttxtPortaFirme)).getText().toString());
        editor.apply();
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
