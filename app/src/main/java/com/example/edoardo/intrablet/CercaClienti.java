package com.example.edoardo.intrablet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.edoardo.intrablet.database.Cliente;
import com.example.edoardo.intrablet.database.MySqlLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unchecked")

public class CercaClienti extends ActionBarActivity {
    private ArrayList<Cliente> clientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerca_clienti);

        // setto il titolo
        getSupportActionBar().setTitle("Cerca Clienti");

        MySqlLiteHelper msqlh = new MySqlLiteHelper(this);
        clientList = (ArrayList)msqlh.getAllCliente();

        Adapter adapter = aggiornaAdapter();

        ListView lv = ((ListView)findViewById(R.id.lstvwContatti));
        lv.setAdapter((ListAdapter)adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                Cliente cl = clientList.get((int) id);
                int ide = cl.getId();
                String nom = cl.getRagSociale();
                i.putExtra("ID","" + Integer.toString(ide));
                i.putExtra("NOME","" + nom);
                i.putExtra("CODICE","" + cl.getCodice());
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    public ListAdapter aggiornaAdapter(){
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        for(Cliente c : clientList){
            HashMap<String, String> subentry = new HashMap<>();
            subentry.put("Nome",c.getRagSociale());
            data.add(subentry);
        }
        String[] from = {"Nome"};
        int[] to = {R.id.txtvwNomeCliente};

        return new SimpleAdapter(
                getApplicationContext(),
                data,//sorgente dati
                R.layout.elementocliente, //layout contenente gli id di "to"
                from,
                to);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cerca_clienti, menu);
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

    public void cercaCliente(View view) {
        MySqlLiteHelper msqlh = new MySqlLiteHelper(this);
        clientList = (ArrayList)msqlh.getClienti(((EditText) findViewById(R.id.edttxtCerca)).getText().toString());

        Adapter adapter = aggiornaAdapter();

        ListView lv = ((ListView)findViewById(R.id.lstvwContatti));
        lv.setAdapter((ListAdapter)adapter);
    }
}
