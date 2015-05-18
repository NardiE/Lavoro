package com.example.edoardo.intrablet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.edoardo.intrablet.database.Intervento;
import com.example.edoardo.intrablet.database.MySqlLiteHelper;
import com.example.edoardo.intrablet.database.OperazioniCorrenti;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class ElencoInterventi extends ActionBarActivity {
    private int operazionecorrente;
    private int continuareID;
    ArrayList<Intervento> interventi = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_intervent);

        // setto il titolo
        getSupportActionBar().setTitle("Lista Interventi");

        Intent i = getIntent();
        operazionecorrente = Integer.parseInt(i.getStringExtra("OP"));

        if(operazionecorrente == OperazioniCorrenti.CHIAMATECHIUSE){
            ((TextView) findViewById(R.id.txtvwElenco)).setText("Interventi Chiusi");
        }

        ListView listainterventi = ((ListView) findViewById(R.id.lstvwInterventiElenco));

        ListAdapter simpleAdapter = aggiornaLista();

        listainterventi.setAdapter(simpleAdapter);

        registerForContextMenu(listainterventi);

    }

    public ListAdapter aggiornaLista(){
        MySqlLiteHelper mysql = new MySqlLiteHelper(this);

        if(operazionecorrente == OperazioniCorrenti.CHIAMATEAPERTE){
            interventi = mysql.getChiamateAperte();
        }
        if(operazionecorrente == OperazioniCorrenti.CHIAMATECHIUSE){
            interventi = mysql.getChiamateChiuse();
        }

        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);

        ArrayList<HashMap<String, String>> data = new ArrayList<>();

        for(Intervento it: interventi){
            HashMap<String, String> elementolista = new HashMap<>();
            elementolista.put("DATA", "" + sdf.format(it.getData()));
            elementolista.put("NUMERO", "" + it.getNumero().trim());
            elementolista.put("RAGSOCIALE", "" + it.getRagSocialeCliente().trim());
            elementolista.put("ID", "" + it.getId());

            data.add(elementolista);
        }

        String[] from = {"DATA", "NUMERO", "RAGSOCIALE", "ID"};
        int[] to = {R.id.elementoIntData, R.id.elementoIntNumero, R.id.elementoIntRag, R.id.elementoIntID};

        return new SimpleAdapter(getApplicationContext(), data, R.layout.elementointerventi,from, to);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_open_intervent, menu);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        // il primo elemento è la descrizione
        menu.setHeaderTitle(interventi.get(info.position).getData() + " " + interventi.get(info.position).getRagSocialeCliente() );
        String[] menuitems = getResources().getStringArray(R.array.menu);
        for(int i = 0; i< menuitems.length; i++){
            menu.add(Menu.NONE, i, i, menuitems[i]);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        Integer ID;
        MySqlLiteHelper mysql = new MySqlLiteHelper(getApplicationContext());

        ListView lw = (ListView) findViewById(((ListView)info.targetView.getParent()).getId());
        Adapter ad = lw.getAdapter();
        Object item1 = ad.getItem(info.position);

        @SuppressWarnings("unchecked")
        HashMap<String, String> transf = (HashMap<String, String>) item1;
        ID = Integer.parseInt(transf.get("ID"));


        switch(menuItemIndex) {
            case 0:
                Intent i = new Intent(this, DettaglioInterventi.class);
                i.putExtra("OP","" + operazionecorrente);
                i.putExtra("ID", "" + ID);
                startActivity(i);
                return true;
            case 1:
                continuareID = ID;
                //Elimina
                if(ID>=0){
                    Toast.makeText(this, "Non è possibile eliminare Interventi non generati da palmare", Toast.LENGTH_SHORT).show();
                }
                else{

                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("Avviso");
                    builder.setMessage("L'Eliminazione è irreversibile, Continuare?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MySqlLiteHelper mysql = new MySqlLiteHelper(ElencoInterventi.this);
                            mysql.deleteIntervento(continuareID);
                            mysql.deleteArticoli(continuareID);
                            mysql.deleteOperazioni(continuareID);
                            ListView listainterventi = ((ListView) findViewById(R.id.lstvwInterventiElenco));
                            ListAdapter simpleAdapter = aggiornaLista();
                            listainterventi.setAdapter(simpleAdapter);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("ANNULLA", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                return true;
        }


        return true;

        /*if(operazionecorrente == OperazioniCorrenti.CHIAMATEAPERTE){

        }
        if(operazionecorrente == OperazioniCorrenti.CHIAMATECHIUSE){

        }*/
    }
}
