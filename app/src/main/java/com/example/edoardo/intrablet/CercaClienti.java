package com.example.edoardo.intrablet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.edoardo.intrablet.database.Cliente;
import com.example.edoardo.intrablet.database.MySqlLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;


public class CercaClienti extends ActionBarActivity {
    private ArrayList<Cliente> clientList = new ArrayList<Cliente>();
    public static String EXTRAMESSAGE = "com.example.edoardo.intrablet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerca_clienti);

        // setto il titolo
        getActionBar().setTitle("Cerca Clienti");
        getSupportActionBar().setTitle("Cerca Clienti");

        MySqlLiteHelper msqlh = new MySqlLiteHelper(this);
        clientList = (ArrayList)msqlh.getAllCliente();
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        for(Cliente c : clientList){
            HashMap<String, String> subentry = new HashMap<>();
            subentry.put("Nome",c.getRagSociale());
            data.add(subentry);
        }
        String[] from = {"Nome"};
        int[] to = {R.id.txtvwNomeCliente};

        SimpleAdapter adapter=new SimpleAdapter(
                getApplicationContext(),
                data,//sorgente dati
                R.layout.elementocliente, //layout contenente gli id di "to"
                from,
                to);
        ListView lv = ((ListView)findViewById(R.id.lstvwContatti));
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                Cliente cl = clientList.get((int) id);
                int ide = cl.getId();
                String nom = cl.getRagSociale();
                i.putExtra("ID","" + new Integer(ide).toString());
                i.putExtra("NOME", nom);
                i.putExtra("CODICE", cl.getCodice());
                setResult(RESULT_OK, i);
                finish();
            }
        });
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
}
