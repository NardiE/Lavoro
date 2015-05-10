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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edoardo.intrablet.database.Articolo;
import com.example.edoardo.intrablet.database.Intervento;
import com.example.edoardo.intrablet.database.MySqlLiteHelper;
import com.example.edoardo.intrablet.database.OperazioniCorrenti;
import com.example.edoardo.intrablet.database.SottoIt;
import com.example.edoardo.intrablet.database.TipiIntervento;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class NewInterventActivity extends ActionBarActivity {

    static final int PICK_CLIENT_REQUEST = 1;
    private int operazionecorrente;
    private int operazioneprecedente = OperazioniCorrenti.NOOP;
    private int idTecnico;
    private int ID;
    private int chiusa;
    ArrayList<Articolo> articoli;
    ArrayList<SottoIt> operazioni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intervent);

        // setto il titolo
        getActionBar().setTitle("Intervento");
        getSupportActionBar().setTitle("Intervento");

        //Devo distinguere se ho un nuovo intervento oppure se ne sto modificando uno esistente
        SharedPreferences sharedpreferences = getSharedPreferences(Settings.preferences, Context.MODE_PRIVATE);
        MySqlLiteHelper mysql = new MySqlLiteHelper(this);
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


        Intent intent = getIntent();
        operazionecorrente = Integer.parseInt(intent.getStringExtra("OP"));


        if(operazionecorrente == OperazioniCorrenti.CHIAMATEAPERTE || operazionecorrente == OperazioniCorrenti.CHIAMATECHIUSE || operazionecorrente == OperazioniCorrenti.MODIFICAINTERVENTO){

            operazioneprecedente = operazionecorrente;

            if(intent.getStringExtra("CHIUSA") != null && !intent.getStringExtra("CHIUSA").equals("") ){
                chiusa = Integer.parseInt(intent.getStringExtra("CHIUSA"));
            }

            if(intent.getStringExtra("OPP")!= null && !intent.getStringExtra("OPP").equals("")){
                operazioneprecedente = Integer.parseInt(intent.getStringExtra("OPP"));
            }

            ID = Integer.parseInt(intent.getStringExtra("ID"));
            Intervento it = mysql.getIntervento(ID).get(0);

            ((EditText) findViewById(R.id.edttxtMotivoInt)).setText(it.getMotivoChiamata());
            ((TextView) findViewById(R.id.txtvwTipo)).setText(it.getHwsw() + "W");
            ((TextView) findViewById(R.id.txtvwDataOra)).setText("" + sdf.format(it.getData()));
            ((TextView) findViewById(R.id.txtvwIndirizzo)).setText("" + it.getIndirizzoCliente());
            ((TextView) findViewById(R.id.txtvwLocalita)).setText("" + it.getLocalitaCliente());
            ((TextView) findViewById(R.id.txtvwTel)).setText("" + it.getTelefonoCliente());
            ((TextView) findViewById(R.id.txtvwFax)).setText("" + it.getFaxCliente());
            ((TextView) findViewById(R.id.txtvwNumero)).setText("" + it.getNumero());
            ((TextView) findViewById(R.id.txtvwHideClientID)).setText("" + it.getIdCliente());
            ((TextView) findViewById(R.id.txtvwClientCod)).setText("" + it.getCodiceCliente());
            ((TextView) findViewById(R.id.txtvwRagioneSociale)).setText("" + it.getRagSocialeCliente());
            ((CheckBox) findViewById(R.id.chkbxTrasferisci)).setChecked((it.getChiusa() == 2));
            ((CheckBox) findViewById(R.id.chkbxNoTMR)).setChecked((it.getNonTrasferire() == 1));

            if(operazionecorrente == OperazioniCorrenti.CHIAMATECHIUSE){
                // disabilito bottone cambio cliente, inserimento articolo inserimento operazionetra e cambio il testo di conferma
                ((Button) findViewById(R.id.btncerca)).setVisibility(View.INVISIBLE);
                ((Button) findViewById(R.id.btnInserisciArticolo)).setVisibility(View.INVISIBLE);
                ((Button) findViewById(R.id.btnInserisciOperazionetra)).setVisibility(View.INVISIBLE);
                ((Button) findViewById(R.id.btnInserisciIntervento)).setText("Annulla Modifiche");
            }

        }

        //if(Modifica){
        // non mostrare il cambio di cliente
        //}

        // assegno un ID negativo all' Intervento
        if(operazionecorrente == OperazioniCorrenti.INSERISCIINTERVENTO){
            int minID = 0;
            idTecnico = Integer.parseInt(sharedpreferences.getString(TipiConfigurazione.idTecnico, ""));
            ArrayList<Intervento> interventi = mysql.getAllIntervento();
            for(Intervento it: interventi){
                if(minID > it.getId()){
                    minID = it.getId();
                }
            }
            if(minID > 0) ID  = -1;
            else ID = minID -1;

            String idUnivoco = Utility.idUnivoco(idTecnico);
            String HWSW = sharedpreferences.getString(TipiConfigurazione.tipoInterventi, "");
            String numero = "";
            Date data = new Date();
            Date dataprevista = new Date();

            int idCliente = 0;
            int nontrasferire = 0;
            int chiusa = 0;
            String codiceCliente = "";
            String ragioneSocialeCliente = "";
            String telefono = "";
            String localita = "";
            String indirizzo = "";
            String fax = "";
            String motivochiamata = "";
            String noteassegnatore = "";

            Intervento it = new Intervento(ID,ID,HWSW,numero,data,dataprevista,idCliente,codiceCliente,ragioneSocialeCliente,indirizzo,localita,telefono,fax,motivochiamata,noteassegnatore,nontrasferire,chiusa,idUnivoco);

            mysql.addIntervento(it);

            // carico le impostazioni
            // da implementare trasferimento tecnico
            ((EditText) findViewById(R.id.edttxtMotivoInt)).setText(it.getMotivoChiamata());
            ((TextView) findViewById(R.id.txtvwTipo)).setText(HWSW + "W");
            ((TextView) findViewById(R.id.txtvwDataOra)).setText("" + sdf.format(data));
            ((TextView) findViewById(R.id.txtvwIndirizzo)).setText("" + indirizzo);
            ((TextView) findViewById(R.id.txtvwLocalita)).setText("" + localita);
            ((TextView) findViewById(R.id.txtvwTel)).setText("" + telefono);
            ((TextView) findViewById(R.id.txtvwFax)).setText("" + fax);
            ((TextView) findViewById(R.id.txtvwNumero)).setText("" + numero);
            ((CheckBox) findViewById(R.id.chkbxTrasferisci)).setChecked((it.getChiusa() == 2));
            ((CheckBox) findViewById(R.id.chkbxNoTMR)).setChecked((it.getNonTrasferire() == 1));
        }

        //popolo le listview relative agli Articoli comune a modifiche inserimenti e ritorni da Operazioni o Articoli
        ListAdapter a = aggiornaListaArticoli(mysql);
        ListView artlist = ((ListView) findViewById(R.id.lstvwArticoli));
        artlist.setAdapter(a);
        registerForContextMenu(artlist);

        ListAdapter b = aggiornaListaOperazioni(mysql);
        ListView oplist = ((ListView) findViewById(R.id.lstvwOperazioniint));
        oplist.setAdapter(b);
        registerForContextMenu(oplist);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_intervent, menu);
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

    public void cercaCliente(View view) {
        Intent i = new Intent(this, CercaClienti.class);
        startActivityForResult(i,PICK_CLIENT_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_CLIENT_REQUEST) {
            if(resultCode == RESULT_OK){
                try {
                    String id = data.getStringExtra("ID");
                    String nome = data.getStringExtra("NOME");
                    String cod = data.getStringExtra("CODICE");
                    ((TextView) findViewById(R.id.txtvwHideClientID)).setText(id);
                    ((TextView) findViewById(R.id.txtvwClientCod)).setText(cod);
                    ((TextView) findViewById(R.id.txtvwRagioneSociale)).setText(nome);
                }catch (Exception e){
                    e.toString();
                }
            }
            if (resultCode == RESULT_CANCELED) {
            }
        }
    }

    public void insertArticle(View view) {
        aggiornaIntervento(view);
        Intent i = new Intent(this, AggiungiArticolo.class);
        i.putExtra("IDIt", "" + ID);
        i.putExtra("OP", "" + OperazioniCorrenti.NUOVOARTICOLO);
        if(operazioneprecedente == OperazioniCorrenti.CHIAMATEAPERTE){
            i.putExtra("OPP", "" + operazioneprecedente);
        }
        startActivity(i);

    }

    public void aggiornaIntervento(View view){
        MySqlLiteHelper mysql = new MySqlLiteHelper(this);
        Intervento it = mysql.getIntervento(ID).get(0);
        if( it != null) {
            //inserisco le informazioni per chiusa NoTMR e Trasferisci
            if(((CheckBox) findViewById(R.id.chkbxTrasferisci)).isChecked()){
                it.setChiusa(2);
            }else{
                it.setChiusa(chiusa);
            }

            it.setNonTrasferire(0);
            if(((CheckBox) findViewById(R.id.chkbxNoTMR)).isChecked()){
                it.setNonTrasferire(1);
            }


            it.setCodiceCliente(((TextView) findViewById(R.id.txtvwClientCod)).getText().toString());
            // se confermo l'intervento senza aver scelto un cliente
            if(!((TextView) findViewById(R.id.txtvwHideClientID)).getText().toString().equals("")){
                it.setIdCliente(Integer.parseInt(((TextView) findViewById(R.id.txtvwHideClientID)).getText().toString()));
            }
            else
                it.setIdCliente(0);
            it.setRagSocialeCliente(((TextView) findViewById(R.id.txtvwRagioneSociale)).getText().toString());
            it.setMotivoChiamata(((TextView) findViewById(R.id.edttxtMotivoInt)).getText().toString());
            mysql.updateIntervento(it);

        }else{Toast t = Toast.makeText(this,"ERRORE IMPREVISTO INTERVENTO NON TROVATO, Contattare lo sviluppatore", Toast.LENGTH_SHORT); t.show();}
    }

    public void confermaIntervento(View view) {
        // qui devo fare aggiornamento dell' intervento
        if(operazioneprecedente == OperazioniCorrenti.CHIAMATEAPERTE){
            aggiornaIntervento(view);
            Intent i = new Intent(this, OpenInterventActivity.class);
            i.putExtra("OP","" + OperazioniCorrenti.CHIAMATEAPERTE);
            startActivity(i);
            return;
        }
        if(operazionecorrente != OperazioniCorrenti.CHIAMATECHIUSE) {
            aggiornaIntervento(view);
            Intent i = new Intent(this, WorkActivity.class);
            startActivity(i);
            return;
        }
        Intent i = new Intent(this, OpenInterventActivity.class);
        i.putExtra("OP","" + OperazioniCorrenti.CHIAMATECHIUSE);
        startActivity(i);
    }

    private ListAdapter aggiornaListaOperazioni(MySqlLiteHelper mysql) {
        operazioni = mysql.getOperazioni(ID);

        ArrayList<HashMap<String, String>> data = new ArrayList<>();

        HashMap<String, String> objectmaptitle = new HashMap<>();
        objectmaptitle.put("LUOGO", "TI.");
        objectmaptitle.put("ID", "ID");
        objectmaptitle.put("DATA", "DATA");
        objectmaptitle.put("DATAINIZIO", "INIZIO");
        objectmaptitle.put("DATAFINE", "FINE");
        data.add(objectmaptitle);
        SimpleDateFormat sdfdata = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdfora = new SimpleDateFormat("HH:mm:ss");

        for(SottoIt op : operazioni){
            HashMap<String, String> objectmap = new HashMap<>();
            objectmap.put("LUOGO", "" + op.getLuogo());
            objectmap.put("ID", "" + op.getId());
            objectmap.put("DATA", "" + sdfdata.format(op.getDataInizio()));
            objectmap.put("DATAINIZIO", "" + sdfora.format(op.getDataInizio()));
            objectmap.put("DATAFINE", "" + sdfora.format(op.getDataFine()));
            data.add(objectmap);
        }

        String[] from = {"LUOGO","ID","DATA","DATAINIZIO","DATAFINE"};
        int[] to = {R.id.elementoOpLuogo, R.id.elementoOpID,R.id.elementoOpData, R.id.elementoOpDataInizio, R.id.elementoOpDataFine};

        SimpleAdapter adapter = new SimpleAdapter(
                getApplicationContext(),
                data,
                R.layout.elementooperazioni,
                from,
                to
        );

        return adapter;
    }

    private ListAdapter aggiornaListaArticoli(MySqlLiteHelper mysql) {
        articoli = mysql.getArticoliDaIntervento(ID);

        ArrayList<HashMap<String, Object>> data = new ArrayList<>();

        HashMap<String, Object> object1map = new HashMap<>();
        object1map.put("ID", "ID");
        object1map.put("MATRICOLA", "MAT.");
        object1map.put("CODICE", "COD.");
        object1map.put("DESCRIZIONE", "DESCR.");
        object1map.put("QUANTITA", "QT.");
        data.add(object1map);

        for(Articolo a : articoli){
            HashMap<String, Object> objectmap = new HashMap<>();
            objectmap.put("ID", a.getId());
            objectmap.put("MATRICOLA", a.getMatricola());
            objectmap.put("CODICE", a.getCodice());
            objectmap.put("DESCRIZIONE", a.getDescrizione());
            objectmap.put("QUANTITA", a.getQt());
            data.add(objectmap);
        }

        String[] from = {"ID","MATRICOLA","CODICE", "DESCRIZIONE", "QUANTITA"};
        int[] to = {R.id.elementoArtID, R.id.elementoArtMatr, R.id.elementoArtCod, R.id.elementoArtDescr, R.id.elementoArtQt};

        SimpleAdapter adapter = new SimpleAdapter(
                getApplicationContext(),
                data,
                R.layout.elementoarticolo,
                from,
                to
        );

        return adapter;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //se ho cliccato sulla lista delle operazioni
        if(v.getId() == R.id.lstvwOperazioniint){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            if(info.position == 0) return;
            // il primo elemento è la descrizione
            menu.setHeaderTitle(operazioni.get(info.position - 1).getId() + "");
            String[] menuitems = getResources().getStringArray(R.array.menu);
            for(int i = 0; i< menuitems.length; i++){
                menu.add(Menu.NONE, i, i, menuitems[i]);
            }
        }

        if(v.getId() == R.id.lstvwArticoli){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            if(info.position == 0) return;
            // il primo elemento è la descrizione
            menu.setHeaderTitle(articoli.get(info.position - 1).getDescrizione() + "");
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
        Integer ID = 0;


        ListView lw = (ListView) findViewById(((ListView)info.targetView.getParent()).getId());
        Adapter ad = lw.getAdapter();
        Object item1 = ad.getItem(info.position);

        if(lw.getId() == R.id.lstvwArticoli) {
            HashMap<String, Object> transf = (HashMap<String, Object>) item1;
            ID = (Integer) transf.get("ID");
        }
        if(lw.getId() == R.id.lstvwOperazioniint){
            HashMap<String, String> transf = (HashMap<String, String>) item1;
            ID = Integer.parseInt(transf.get("ID"));
        }

        MySqlLiteHelper mysql = new MySqlLiteHelper(getApplicationContext());

        switch(menuItemIndex) {
            case 0:
                //Modifica
                if(lw.getId() == R.id.lstvwArticoli) {
                    if(operazionecorrente == OperazioniCorrenti.CHIAMATECHIUSE){
                        Articolo art = mysql.getArticoli(ID).get(0);
                        Intent i = new Intent(this, AggiungiArticolo.class);
                        if(operazioneprecedente == OperazioniCorrenti.CHIAMATEAPERTE){
                            i.putExtra("OPP", "" + operazioneprecedente);
                        }
                        i.putExtra("OP", "" + OperazioniCorrenti.CHIAMATECHIUSE);
                        i.putExtra("IDIt", "" + art.getIDit());
                        i.putExtra("ID", "" + art.getId());
                        startActivity(i);
                        return true;
                    }

                    aggiornaIntervento(lw.getRootView());
                    // prendo il primo articolo
                    Articolo art = mysql.getArticoli(ID).get(0);
                    Intent i = new Intent(this, AggiungiArticolo.class);
                    if(operazioneprecedente == OperazioniCorrenti.CHIAMATEAPERTE){
                        i.putExtra("OPP", "" + operazioneprecedente);
                    }
                    i.putExtra("OP", "" + OperazioniCorrenti.MODIFICAARTICOLO);
                    i.putExtra("IDIt", "" + art.getIDit());
                    i.putExtra("ID", "" + art.getId());
                    startActivity(i);
                    return true;
                }
                if(lw.getId() == R.id.lstvwOperazioniint){
                    if(operazionecorrente == OperazioniCorrenti.CHIAMATECHIUSE){
                        SottoIt st = mysql.getSottoIntervento(ID).get(0);
                        Intent i = new Intent(this, AddOperation.class);
                        i.putExtra("OP", "" + OperazioniCorrenti.CHIAMATECHIUSE);
                        i.putExtra("IDIt", "" + st.getIdit());
                        i.putExtra("ID", "" + st.getId());
                        startActivity(i);
                        return true;
                    }

                    aggiornaIntervento(lw.getRootView());
                    SottoIt st = mysql.getSottoIntervento(ID).get(0);
                    Intent i = new Intent(this, AddOperation.class);
                    i.putExtra("OP", "" + OperazioniCorrenti.MODIFICAOPERAZIONEINTERVENTO);
                    i.putExtra("IDIt", "" + st.getIdit());
                    i.putExtra("ID", "" + st.getId());
                    startActivity(i);
                    return true;
                }

            case 1:
                //Elimina
                if(lw.getId() == R.id.lstvwOperazioniint){
                    if (ID < 0) {
                        boolean result = mysql.deleteOperazione(ID);

                        ListAdapter adapter = aggiornaListaOperazioni(mysql);

                        ListView mylist = (ListView) findViewById(R.id.lstvwOperazioniint);
                        mylist.setAdapter(adapter);
                        return result;
                    } else {
                        Toast mioToast = Toast.makeText(this,
                                "Non è possibile eliminare un Operazione non aggiunta da palmare",
                                Toast.LENGTH_LONG);

                        mioToast.show();
                        return true;
                    }
                }
                if(lw.getId() == R.id.lstvwArticoli){
                    if (ID < 0) {
                        boolean result = mysql.deleteArticolo(ID);

                        ListAdapter adapter = aggiornaListaArticoli(mysql);

                        ListView mylist = (ListView) findViewById(R.id.lstvwArticoli);
                        mylist.setAdapter(adapter);
                        return result;
                    } else {
                        Toast mioToast = Toast.makeText(this,
                                "Non è possibile eliminare un Articolo non aggiunto da palmare",
                                Toast.LENGTH_LONG);

                        mioToast.show();
                        return true;
                    }
                }
        }


        return true;
    }

    public void insertOperation(View view) {
        aggiornaIntervento(view);
        int operazione = OperazioniCorrenti.INSERIMENTOOPERAZIONEINTERVENTO;
        // recupero la ragione sociale del cliente dalla form
        String rag = ((TextView) findViewById(R.id.txtvwRagioneSociale)).getText().toString();
        // invio anche il codice anche se sembra non necessario
        String cod = ((TextView) findViewById(R.id.txtvwClientCod)).getText().toString();
        Intent i = new Intent(this, AddOperation.class);
        if(operazioneprecedente == OperazioniCorrenti.CHIAMATEAPERTE){
            i.putExtra("OPP", "" + operazioneprecedente);
        }
        i.putExtra("OP", "" + operazione);
        i.putExtra("IDIt", "" + ID);
        i.putExtra("RAG", "" + rag);
        i.putExtra("COD", "" + cod);
        startActivity(i);
    }
}
