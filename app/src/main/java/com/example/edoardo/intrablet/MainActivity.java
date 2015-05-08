package com.example.edoardo.intrablet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.edoardo.intrablet.database.Cliente;
import com.example.edoardo.intrablet.database.MySqlLiteHelper;
import com.example.edoardo.intrablet.database.TipiIntervento;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Cliente> clienti = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            clienti.add(new Cliente(i,"Pippo", "0","1",i + " SRL",i));
        }
        MySqlLiteHelper msql = new MySqlLiteHelper(this);
        msql.deleteAllClienti();
        msql.deleteAllInterventi();
        msql.deleteAllSottoit();

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
            Intent i = new Intent(this, Settings.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDownload(View view) {
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivity(intent);
    }

    public void openUpload(View view) {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);
    }

    public void startWork(View view) {
        Intent intent = new Intent(this, WorkActivity.class);
        startActivity(intent);
    }
}
