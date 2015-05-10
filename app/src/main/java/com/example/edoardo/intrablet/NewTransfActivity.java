package com.example.edoardo.intrablet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;

import com.example.edoardo.intrablet.database.MySqlLiteHelper;
import com.example.edoardo.intrablet.database.OperazioniCorrenti;
import com.example.edoardo.intrablet.database.SottoIt;
import com.example.edoardo.intrablet.database.TipiIntervento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class NewTransfActivity extends ActionBarActivity {
    static final int ADD_OPERATION_REQUEST = 1;
    static final int MODIFY_OPERATION_REQUEST = 2;
    ArrayList<SottoIt> sottoit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transf);

        // setto il titolo
        getActionBar().setTitle("Operazioni");
        getSupportActionBar().setTitle("Operazioni");

        SharedPreferences sharedpreferences = getSharedPreferences(Settings.preferences, Context.MODE_PRIVATE);
        Button inserisci = (Button)findViewById(R.id.btnAggiungiTransf);        //setto il test HW SW del bottone
        inserisci.setText(inserisci.getText().toString() + " " + sharedpreferences.getString(TipiConfigurazione.tipoInterventi , "") + "W");

        MySqlLiteHelper mysql = new MySqlLiteHelper(this);

        ListAdapter adapter = aggiornaLista(mysql);

        ListView mylist = (ListView)findViewById(R.id.lstvwTrasferimenti);
        mylist.setAdapter(adapter);
        mylist.setLongClickable(true);
        registerForContextMenu(mylist);

        /*mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,Object> transf = (HashMap)parent.getItemAtPosition(position);

                Integer ID = (Integer)transf.get("ID");
                MySqlLiteHelper mysql = new MySqlLiteHelper(getApplicationContext());
                ArrayList<SottoIt> sint = (ArrayList)mysql.getSottoIntervento(ID);
                SottoIt sit = sint.get(0);

                String luogo = new String();

                ArrayList<TipiIntervento> tpit = (ArrayList)mysql.getAllTipoIntervento();
                for(TipiIntervento t: tpit){
                    if(t.getCodice().equals(sit.getLuogo())) luogo = t.getCodice() + " " + t.getDescrizione();

                }

                String operazionecorrente = "" + OperazioniCorrenti.MODIFICAOPERAZIONE;
                Intent i = new Intent(getApplicationContext(), AddOperation.class);
                i.putExtra("ID","" + sit.getId());
                i.putExtra("IDUNIVOCO", "" + sit.getIdUnivoco());
                i.putExtra("OP",operazionecorrente);
                i.putExtra("CODICECLIENTE", sit.getCodiceCliente());
                i.putExtra("RAGSOCCLIENTE", sit.getRagSocialeCliente());
                i.putExtra("LUOGO", luogo);

                SimpleDateFormat datore = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                i.putExtra("DATAINIZIO", datore.format(sit.getDataInizio()));
                i.putExtra("DATAFINE", datore.format(sit.getDataFine()));
                i.putExtra("NOTE", sit.getNotaTecnico());
                i.putExtra("NONFATTURARE", "" + sit.getNonFatturare());
                i.putExtra("NONTRASFERIRE", "" + sit.getNonTrasferire());
                i.putExtra("CHIUDERE", "" + sit.getChiusa());
                startActivityForResult(i, MODIFY_OPERATION_REQUEST);

            }
        });*/
    }

    private ListAdapter aggiornaLista(MySqlLiteHelper mysql) {
        sottoit = (ArrayList) mysql.getAllSottoIntervento();

        ArrayList<HashMap<String, Object>> data = new ArrayList<>();

        for(SottoIt s : sottoit){
            HashMap<String, Object> objectmap = new HashMap<>();
            String datain = new String();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            datain = sdf.format(s.getDataInizio());
            objectmap.put("DATA", datain);
            objectmap.put("LUOGO", s.getLuogo());
            objectmap.put("RAGSOC", s.getRagSocialeCliente());
            objectmap.put("ID", s.getId());
            data.add(objectmap);
        }

        String[] from = {"DATA","LUOGO","RAGSOC","ID"};
        int[] to = {R.id.txtvwDataTras, R.id.txtvwLuogoTras , R.id.txtvwRagioneSocialeTras, R.id.txtvwIDTras};

        SimpleAdapter adapter = new SimpleAdapter(
                getApplicationContext(),
                data,
                R.layout.elementotrasferimento,
                from,
                to
        );

        return adapter;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_transf, menu);
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

    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        //PER ADESSO NON SERVE
        if (requestcode == ADD_OPERATION_REQUEST){
            if(resultcode == RESULT_OK){
                // invalido la lista di trasferimenti e la ricarico
            }
        }
    }

    public void aggiungiOperazione(View view) {
        String operazionecorrente = "" + OperazioniCorrenti.INSERIMENTOOPERAZIONE;
        Intent i = new Intent(this, AddOperation.class);
        i.putExtra("OP",operazionecorrente);
        startActivityForResult(i,ADD_OPERATION_REQUEST);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if(v.getId() == R.id.lstvwTrasferimenti){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(sottoit.get(info.position).getId() + "");
            String[] menuitems = getResources().getStringArray(R.array.menu);
            for(int i = 0; i< menuitems.length; i++){
                menu.add(Menu.NONE, i, i, menuitems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();


        ListView lw = (ListView) findViewById(R.id.lstvwTrasferimenti);
        Adapter ad = lw.getAdapter();
        Object item1 = ad.getItem(info.position);
        HashMap<String, Object> transf = (HashMap<String, Object>) item1;
        Integer ID = (Integer) transf.get("ID");

        MySqlLiteHelper mysql = new MySqlLiteHelper(getApplicationContext());

        switch(menuItemIndex) {
            case 0:
                //Modifica
                ArrayList<SottoIt> sint = (ArrayList) mysql.getSottoIntervento(ID);
                SottoIt sit = sint.get(0);

                String luogo = new String();

                ArrayList<TipiIntervento> tpit = (ArrayList) mysql.getAllTipoIntervento();
                for (TipiIntervento t : tpit) {
                    if (t.getCodice().equals(sit.getLuogo()))
                        luogo = t.getCodice() + " " + t.getDescrizione();

                }

                String operazionecorrente = "" + OperazioniCorrenti.MODIFICAOPERAZIONE;
                Intent i = new Intent(getApplicationContext(), AddOperation.class);
                i.putExtra("ID", "" + sit.getId());
                i.putExtra("IDUNIVOCO", "" + sit.getIdUnivoco());
                i.putExtra("OP", operazionecorrente);
                i.putExtra("CODICECLIENTE", sit.getCodiceCliente());
                i.putExtra("RAGSOCCLIENTE", sit.getRagSocialeCliente());
                i.putExtra("LUOGO", luogo);

                SimpleDateFormat datore = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                i.putExtra("DATAINIZIO", datore.format(sit.getDataInizio()));
                i.putExtra("DATAFINE", datore.format(sit.getDataFine()));
                i.putExtra("NOTE", sit.getNotaTecnico());
                i.putExtra("NONFATTURARE", "" + sit.getNonFatturare());
                i.putExtra("NONTRASFERIRE", "" + sit.getNonTrasferire());
                i.putExtra("CHIUDERE", "" + sit.getChiusa());
                startActivityForResult(i, MODIFY_OPERATION_REQUEST);
                return true;

            case 1:
                //Elimina
                if(ID < 0){
                    boolean result = mysql.deleteSottoIntervento(ID);

                    ListAdapter adapter = aggiornaLista(mysql);

                    ListView mylist = (ListView)findViewById(R.id.lstvwTrasferimenti);
                    mylist.setAdapter(adapter);
                    return result;
                }
                else{
                    Toast mioToast = Toast.makeText(this,
                            "Non è possibile eliminare un Operazione non generata da palmare",
                            Toast.LENGTH_LONG);

                    mioToast.show();
                }
        }


    return true;
    }
}
