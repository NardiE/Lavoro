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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.edoardo.intrablet.database.MySqlLiteHelper;
import com.example.edoardo.intrablet.database.OperazioniCorrenti;
import com.example.edoardo.intrablet.database.SottoIt;
import com.example.edoardo.intrablet.database.TipiIntervento;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

@SuppressWarnings("unchecked")

public class DettaglioOperazioni extends ActionBarActivity {

    static int operazionecorrente;
    static int operazioneprecedente = OperazioniCorrenti.NOOP;
    static int idTecnico;
    private int IDIt;
    static int ID;
    static String IDUNIVOCO;
    static String HwSw;
    private static int PICK_CLIENT_REQUEST = 1;
    HashMap<String,Integer> tipiaddebitabili = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_operation);

        // setto il titolo
        getSupportActionBar().setTitle("Operazione");


        SharedPreferences sharedpreferences = getSharedPreferences(Impostazioni.preferences, Context.MODE_PRIVATE);
        MySqlLiteHelper mysql = new MySqlLiteHelper(this);

        Intent intent = getIntent();
        operazionecorrente = Integer.parseInt(intent.getStringExtra("OP"));
        if(intent.getStringExtra("OPP") != null && !intent.getStringExtra("OPP").equals("")){
            operazioneprecedente = Integer.parseInt(intent.getStringExtra("OPP"));
        }
        idTecnico = Integer.parseInt(sharedpreferences.getString(TipiConfigurazione.idTecnico, ""));

        // setto Tipo e Nome Tecnico (comune a tutti)
        ((TextView) findViewById(R.id.txtvwNomeTecnico)).setText(sharedpreferences.getString(TipiConfigurazione.nomeTecnico, ""));
        HwSw = sharedpreferences.getString(TipiConfigurazione.tipoInterventi, "");
        ((TextView) findViewById(R.id.txtvwHWSW)).setText(HwSw+"W");

        // carico lo spinner con i tipi intervento
        MySqlLiteHelper msql = new MySqlLiteHelper(this);
        ArrayList<TipiIntervento> tipi = (ArrayList)msql.getAllTipoIntervento();
        ArrayList<String> descrizioni = new ArrayList<>();
        for(TipiIntervento t : tipi){
            tipiaddebitabili.put(t.getCodice(),t.getAddebitabile());
            descrizioni.add(t.getCodice() + " " + t.getDescrizione());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, descrizioni);

        Spinner myspinner = (Spinner)findViewById(R.id.spinnerTipo);
        myspinner.setAdapter(adapter);

        // carico la form con la data
        SimpleDateFormat sdfdata = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);
        SimpleDateFormat sdfora = new SimpleDateFormat("HH:mm", Locale.ITALY);
        sdfora.setTimeZone(TimeZone.getDefault());
        ((TextView) findViewById(R.id.edttxtData)).setText(sdfdata.format(new Date()));
        ((TextView) findViewById(R.id.edttxtOraDa)).setText(sdfora.format(new Date()));
        ((TextView) findViewById(R.id.edttxtOraA)).setText(sdfora.format(new Date()));

        // se inserisco una operazione legata ad un intervento
        if(operazionecorrente == OperazioniCorrenti.INSERIMENTOOPERAZIONEINTERVENTO || operazionecorrente == OperazioniCorrenti.MODIFICAOPERAZIONEINTERVENTO){
            // devo salvare il cliente disabilitare la possibilita di cambio cliente e salvarmi l'IdIt
            ((TextView) findViewById(R.id.txtvwCodiceao)).setText(intent.getStringExtra("COD"));
            ((TextView) findViewById(R.id.txtvwNomeClienteao)).setText(intent.getStringExtra("RAG"));
            IDIt = Integer.parseInt(intent.getStringExtra("IDIt"));
            (findViewById(R.id.btnCambiaCliente)).setVisibility(View.INVISIBLE);
        }

        // se modifico un operazione legata ad un intervento
        if(operazionecorrente == OperazioniCorrenti.MODIFICAOPERAZIONEINTERVENTO || operazionecorrente == OperazioniCorrenti.CHIAMATECHIUSE){
            // prendo l'operazione
            ID = Integer.parseInt(intent.getStringExtra("ID"));

            SottoIt operazione = mysql.getSottoIntervento(ID).get(0);

            IDUNIVOCO = operazione.getIdUnivoco();
            // per i clienti se ho un intervento prendo il codice e la ragione sociale del cliente per adesso solo rag. sociale

            /*String cod = intent.getStringExtra("CODICECLIENTE");
            if(cod != null && !cod.equals(""))
                ((TextView) findViewById(R.id.txtvwCodiceao)).setText(cod);
            else ((TextView) findViewById(R.id.txtvwCodiceao)).setText(operazione.getCodiceCliente());*/

            if(intent.getStringExtra("RAGSOCCLIENTE")!= null && !intent.getStringExtra("RAGSOCCLIENTE").equals(""))
                ((TextView) findViewById(R.id.txtvwNomeClienteao)).setText(intent.getStringExtra("RAGSOCCLIENTE"));
            else
                ((TextView) findViewById(R.id.txtvwNomeClienteao)).setText(operazione.getRagSocialeCliente());

            myspinner.setSelection(((ArrayAdapter) myspinner.getAdapter()).getPosition(operazione.getLuogo()));

            SimpleDateFormat data = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);
            SimpleDateFormat ore = new SimpleDateFormat("HH:mm", Locale.ITALY);


            ((TextView) findViewById(R.id.edttxtData)).setText(data.format(operazione.getDataInizio()));
            ((TextView) findViewById(R.id.edttxtOraDa)).setText(ore.format(operazione.getDataInizio()));
            ((TextView) findViewById(R.id.edttxtOraA)).setText(ore.format(operazione.getDataFine()));


            ((EditText) findViewById(R.id.edttxtNoteTecnico)).setText(operazione.getNotaTecnico());
            ((CheckBox) findViewById(R.id.chkbxNonFatturare)).setChecked(false);
            if(operazione.getNonFatturare() == 1)
                ((CheckBox) findViewById(R.id.chkbxNonFatturare)).setChecked(true);
            ((CheckBox) findViewById(R.id.chkbxNonTrasferire)).setChecked(false);
            if(operazione.getNonTrasferire() == 1)
                ((CheckBox) findViewById(R.id.chkbxNonTrasferire)).setChecked(true);
            ((CheckBox) findViewById(R.id.chkbxChiudereIntervento)).setChecked(false);
            if(operazione.getChiusa() == 1)
                ((CheckBox) findViewById(R.id.chkbxChiudereIntervento)).setChecked(true);

        }
        if(operazionecorrente == OperazioniCorrenti.CHIAMATECHIUSE){
            IDIt = Integer.parseInt(intent.getStringExtra("IDIt"));
            (findViewById(R.id.btnCambiaCliente)).setVisibility(View.INVISIBLE);
            (findViewById(R.id.btnRegistra)).setVisibility(View.INVISIBLE);
        }

        // se modifico un operazione devo sovrascrivere i campi di default
        if(operazionecorrente == OperazioniCorrenti.MODIFICAOPERAZIONE){
            ID = Integer.parseInt(intent.getStringExtra("ID"));
            IDUNIVOCO = intent.getStringExtra("IDUNIVOCO");

            ((TextView) findViewById(R.id.txtvwCodiceao)).setText(intent.getStringExtra("CODICECLIENTE"));
            ((TextView) findViewById(R.id.txtvwNomeClienteao)).setText(intent.getStringExtra("RAGSOCCLIENTE"));
            myspinner.setSelection(((ArrayAdapter) myspinner.getAdapter()).getPosition(intent.getStringExtra("LUOGO")));

            SimpleDateFormat dataore = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
            SimpleDateFormat data = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);
            SimpleDateFormat ore = new SimpleDateFormat("HH:mm", Locale.ITALY);

            Date dataOre1 = new Date();
            Date dataOre2 = new Date();

            String datainizio = intent.getStringExtra("DATAINIZIO");
            String datafine = intent.getStringExtra("DATAFINE");

            try{
                dataOre1 = dataore.parse(datainizio);
                dataOre2 = dataore.parse(datafine);
            }catch (ParseException e){
                AlertDialog.Builder message = Utility.creaDialogoVeloce(this, "DettaglioOperazioni, Errore con le date", "Messaggio di Errore");
                message.create().show();
            }

            ((TextView) findViewById(R.id.edttxtData)).setText(data.format(dataOre1));
            ((TextView) findViewById(R.id.edttxtOraDa)).setText(ore.format(dataOre1));
            ((TextView) findViewById(R.id.edttxtOraA)).setText(ore.format(dataOre2));


            ((EditText) findViewById(R.id.edttxtNoteTecnico)).setText(intent.getStringExtra("NOTE"));
            ((CheckBox) findViewById(R.id.chkbxNonFatturare)).setChecked(false);
            if(Integer.parseInt(intent.getStringExtra("NONFATTURARE")) == 1)
                ((CheckBox) findViewById(R.id.chkbxNonFatturare)).setChecked(true);
            ((CheckBox) findViewById(R.id.chkbxNonTrasferire)).setChecked(false);
            if(Integer.parseInt(intent.getStringExtra("NONTRASFERIRE")) == 1)
                ((CheckBox) findViewById(R.id.chkbxNonTrasferire)).setChecked(true);
            ((CheckBox) findViewById(R.id.chkbxChiudereIntervento)).setChecked(false);
            if(Integer.parseInt(intent.getStringExtra("CHIUDERE")) == 1)
                ((CheckBox) findViewById(R.id.chkbxChiudereIntervento)).setChecked(true);
        }


        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cod = parent.getItemAtPosition(position).toString().substring(0,2);
                CheckBox nonfatturare = (CheckBox) findViewById(R.id.chkbxNonFatturare);
                nonfatturare.setChecked(false);
                if(tipiaddebitabili.get(cod) == 0){
                    nonfatturare.setChecked(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_operation, menu);
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

    public void scegliCliente(View view) {
        Intent i = new Intent(this, CercaClienti.class);
        startActivityForResult(i,PICK_CLIENT_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_CLIENT_REQUEST) {
            if(resultCode == RESULT_OK){
                String id = data.getStringExtra("IDENTIFICATIVO");
                String nome = data.getStringExtra("NOME");
                String codice = data.getStringExtra("CODICE");
                ((TextView) findViewById(R.id.txtvwHideIDao)).setText(id);
                ((TextView) findViewById(R.id.txtvwNomeClienteao)).setText(nome);
                ((TextView)findViewById(R.id.txtvwCodiceao)).setText(codice);
            }
        }
    }

    public void inserisciOperazione(View view) {

        if(operazionecorrente == OperazioniCorrenti.MODIFICAOPERAZIONEINTERVENTO ||operazionecorrente == OperazioniCorrenti.INSERIMENTOOPERAZIONE || operazionecorrente == OperazioniCorrenti.MODIFICAOPERAZIONE || operazionecorrente == OperazioniCorrenti.INSERIMENTOOPERAZIONEINTERVENTO){
            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
            MySqlLiteHelper mysql = new MySqlLiteHelper(this);

            int nuovoID = getNuovoId(mysql);

            Date datain = new Date();
            Date datafin = new Date();
            String data = ((TextView) findViewById(R.id.edttxtData)).getText().toString();
            String datainizio = ((TextView) findViewById(R.id.edttxtOraDa)).getText().toString();
            String datafine = ((TextView) findViewById(R.id.edttxtOraA)).getText().toString();
            datainizio = aggiustaData(datainizio);
            datafine = aggiustaData(datafine);
            try{
                datain = sdf.parse(data + " " + datainizio + ":00");
                datafin = sdf.parse(data + " " + datafine + ":00");
            }catch (Exception e){
                AlertDialog.Builder message = Utility.creaDialogoVeloce(this, "DettaglioOperazioni, Errore con le date", "Messaggio di Errore");
                message.create().show();
                return;
            }
            Spinner myspinner = (Spinner)findViewById(R.id.spinnerTipo);
            String HWSW = ((TextView)findViewById(R.id.txtvwHWSW)).getText().toString().substring(0,1);
            String nomeTecnico = ((TextView)findViewById(R.id.txtvwNomeTecnico)).getText().toString();
            String nomeCliente = ((TextView)findViewById(R.id.txtvwNomeClienteao)).getText().toString();
            String noteTecnico = ((EditText)findViewById(R.id.edttxtNoteTecnico)).getText().toString();
            String luogo = myspinner.getSelectedItem().toString().substring(0,2);
            int nonfatturare = ((CheckBox)findViewById(R.id.chkbxNonFatturare)).isChecked() ? 1 : 0;
            int nontrasferire = ((CheckBox)findViewById(R.id.chkbxNonTrasferire)).isChecked() ? 1 : 0;
            int chiudere = ((CheckBox)findViewById(R.id.chkbxChiudereIntervento)).isChecked() ? 1 : 0;
            int idIt = 0;
            String idUnivoco = Utility.idUnivoco(idTecnico);
            // creo le operazioni per i trasferimenti
            String andataDa = ((EditText)findViewById(R.id.edttxtAndataDa)).getText().toString();
            String andataA = ((EditText)findViewById(R.id.edttxtAndataA)).getText().toString();
            String ritornoDa = ((EditText)findViewById(R.id.edttxtRitornoDa)).getText().toString();
            String ritornoA = ((EditText)findViewById(R.id.edttxtRitornoA)).getText().toString();
            if(!andataA.equals("") && !andataDa.equals("")){
                String myluogo;
                SimpleDateFormat sdfandata = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
                Date andatada = new Date();
                Date andataa = new Date();
                try{
                    andatada = sdfandata.parse(data + " " + andataDa + ":00");
                    andataa = sdfandata.parse(data + " " + andataA + ":00");
                }catch (ParseException e){
                    AlertDialog.Builder message = Utility.creaDialogoVeloce(this, "DettaglioOperazioni, Errore con le date", "Messaggio di Errore");
                    message.create().show();
                    return;
                }
                if(nonfatturare == 1){
                    myluogo = "TN";
                }
                else myluogo = "TA";

                nuovoID = getNuovoId(mysql);
                int myidIt = 0;
                String mynoteTecnico = "";
                int mynontrasferire = 0;
                int mychiudere = 0;
                SottoIt nuovoandata;
                if(operazionecorrente == OperazioniCorrenti.INSERIMENTOOPERAZIONEINTERVENTO || operazionecorrente == OperazioniCorrenti.MODIFICAINTERVENTO){
                    nuovoandata = new SottoIt(nuovoID,nuovoID,HWSW,IDIt,idTecnico,nomeTecnico,nomeCliente,andatada,andataa,myluogo,mynoteTecnico,nonfatturare,mynontrasferire,mychiudere,null,"");
                }else
                    nuovoandata = new SottoIt(nuovoID,nuovoID,HWSW,myidIt,idTecnico,nomeTecnico,nomeCliente,andatada,andataa,myluogo,mynoteTecnico,nonfatturare,mynontrasferire,mychiudere,null,"");
                mysql.addSottoIntervento(nuovoandata);
            }

            if(!ritornoDa.equals("") && !ritornoDa.equals("")){
                SimpleDateFormat sdfritorno = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
                String myluogo;
                Date ritornoda = new Date();
                Date ritornoa = new Date();
                try{
                    ritornoda = sdfritorno.parse(data + " " + ritornoDa + ":00");
                    ritornoa = sdfritorno.parse(data + " " + ritornoA + ":00");
                }catch (ParseException e){
                    AlertDialog.Builder message = Utility.creaDialogoVeloce(this, "DettaglioOperazioni, Errore con le date", "Messaggio di Errore");
                    message.create().show();
                    return;
                }
                if(nonfatturare == 1){
                    myluogo = "TN";
                }
                else myluogo = "TA";

                nuovoID = getNuovoId(mysql);
                int myidIt = 0;
                String mynoteTecnico = "";
                int mynontrasferire = 0;
                int mychiudere = 0;
                SottoIt nuovoritorno;
                if(operazionecorrente == OperazioniCorrenti.INSERIMENTOOPERAZIONEINTERVENTO || operazionecorrente == OperazioniCorrenti.MODIFICAINTERVENTO){
                    nuovoritorno = new SottoIt(nuovoID,nuovoID,HWSW,IDIt,idTecnico,nomeTecnico,nomeCliente,ritornoda,ritornoa,myluogo,mynoteTecnico,nonfatturare,mynontrasferire,mychiudere,null,"");
                }else
                    nuovoritorno = new SottoIt(nuovoID,nuovoID,HWSW,myidIt,idTecnico,nomeTecnico,nomeCliente,ritornoda,ritornoa,myluogo,mynoteTecnico,nonfatturare,mynontrasferire,mychiudere,null,"");
                mysql.addSottoIntervento(nuovoritorno);
            }

            if(operazionecorrente == OperazioniCorrenti.INSERIMENTOOPERAZIONEINTERVENTO){
                nuovoID = getNuovoId(mysql);
                SottoIt nuovoint = new SottoIt(nuovoID,nuovoID,HWSW,IDIt,idTecnico,nomeTecnico,nomeCliente,datain,datafin, luogo, noteTecnico,nonfatturare, nontrasferire, chiudere, null, idUnivoco);
                mysql.addSottoIntervento(nuovoint);
                Intent i = new Intent(this, DettaglioInterventi.class);
                // dico che sto ritornando al Nuovo Intervento TODO implementare ritorno in OperazioniCorrenti
                i.putExtra("OP", "" + OperazioniCorrenti.MODIFICAINTERVENTO );
                int chiusa = ((CheckBox) findViewById(R.id.chkbxChiudereIntervento)).isChecked() ? 1 : 0;
                if(operazioneprecedente == OperazioniCorrenti.CHIAMATEAPERTE){
                    i.putExtra("OPP", "" + operazioneprecedente);
                }
                i.putExtra("CHIUSA", "" + chiusa);
                i.putExtra("ID", "" + IDIt);
                startActivity(i);
            }


            if(operazionecorrente == OperazioniCorrenti.MODIFICAOPERAZIONEINTERVENTO){
                SottoIt updateint = new SottoIt(ID, ID, HWSW, IDIt, idTecnico, nomeTecnico, nomeCliente, datain, datafin, luogo, noteTecnico, nonfatturare, nontrasferire, chiudere, null, IDUNIVOCO);
                mysql.updateSottoIntervento(updateint);
                Intent i = new Intent(this, DettaglioInterventi.class);
                // dico che sto ritornando al Nuovo Intervento
                i.putExtra("OP", "" + OperazioniCorrenti.MODIFICAINTERVENTO );
                if(operazioneprecedente == OperazioniCorrenti.CHIAMATEAPERTE){
                    i.putExtra("OPP", "" + operazioneprecedente);
                }
                int chiusa = ((CheckBox) findViewById(R.id.chkbxChiudereIntervento)).isChecked() ? 1 : 0;
                i.putExtra("CHIUSA", "" + chiusa);
                i.putExtra("ID", "" + IDIt);
                startActivity(i);
            }

            if(operazionecorrente == OperazioniCorrenti.MODIFICAOPERAZIONE){
                SottoIt updateint = new SottoIt(ID, ID, HWSW, idIt, idTecnico, nomeTecnico, nomeCliente, datain, datafin, luogo, noteTecnico, nonfatturare, nontrasferire, chiudere, null, IDUNIVOCO);
                mysql.updateSottoIntervento(updateint);
                Intent i = new Intent(this, ElencoTrasferimenti.class);
                startActivity(i);
            }

            if(operazionecorrente == OperazioniCorrenti.INSERIMENTOOPERAZIONE) {
                nuovoID = getNuovoId(mysql);
                SottoIt nuovoint = new SottoIt(nuovoID,nuovoID,HWSW,idIt,idTecnico,nomeTecnico,nomeCliente,datain,datafin, luogo, noteTecnico,nonfatturare, nontrasferire, chiudere, null, idUnivoco);
                mysql.addSottoIntervento(nuovoint);
                Intent i = new Intent(this, ElencoTrasferimenti.class);
                startActivity(i);
            }

        }
    }

    public static int getNuovoId(MySqlLiteHelper mysql){
        int nuovoID = -1;
        ArrayList<SottoIt> sottint = (ArrayList)mysql.getAllSottoIntervento();
        for(SottoIt s : sottint){
            if(s.getId() <= nuovoID){
                nuovoID = s.getId() - 1;
            }
        }
        return nuovoID;
    }

    public void annullaModifiche(View view) {
        if(operazionecorrente == OperazioniCorrenti.INSERIMENTOOPERAZIONE || operazionecorrente == OperazioniCorrenti.MODIFICAOPERAZIONE) {
            Intent i = new Intent(this, ElencoTrasferimenti.class);
            startActivity(i);
        }
        if(operazionecorrente == OperazioniCorrenti.CHIAMATECHIUSE){
            Intent i = new Intent(this, DettaglioInterventi.class);
            i.putExtra("OP", "" + OperazioniCorrenti.CHIAMATECHIUSE);
            i.putExtra("ID", "" + IDIt);
            startActivity(i);
        }
        if(operazionecorrente == OperazioniCorrenti.INSERIMENTOOPERAZIONEINTERVENTO || operazionecorrente == OperazioniCorrenti.MODIFICAOPERAZIONEINTERVENTO){
            Intent i = new Intent(this, DettaglioInterventi.class);
            i.putExtra("OP", "" + OperazioniCorrenti.MODIFICAINTERVENTO);
            int chiusa = ((CheckBox) findViewById(R.id.chkbxChiudereIntervento)).isChecked() ? 1 : 0;
            i.putExtra("CHIUSA", "" + chiusa);
            i.putExtra("ID", "" + IDIt);
            if(operazioneprecedente == OperazioniCorrenti.CHIAMATEAPERTE){
                i.putExtra("OPP", "" + operazioneprecedente);
            }
            startActivity(i);
        }
    }

    public String aggiustaData(String data){
        if(data.length() == 4){
            data = data.substring(0,2) +":" + data.substring(2,4);
        }
        return data;
    }
}
