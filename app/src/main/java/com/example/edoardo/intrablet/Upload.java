package com.example.edoardo.intrablet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edoardo.intrablet.database.Articolo;
import com.example.edoardo.intrablet.database.Cliente;
import com.example.edoardo.intrablet.database.Executor;
import com.example.edoardo.intrablet.database.Intervento;
import com.example.edoardo.intrablet.database.MySqlLiteHelper;
import com.example.edoardo.intrablet.database.Receiver;
import com.example.edoardo.intrablet.database.SottoIt;
import com.example.edoardo.intrablet.database.TipiIntervento;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Upload extends ActionBarActivity {

    private String IP;
    private int port;
    String result;
    String tipoInvio;
    Pair<Boolean,String> coppia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        // setto il titolo
        getSupportActionBar().setTitle("Sincronizza Database");

        //recupero IP e porta
        SharedPreferences sharedpreferences = getSharedPreferences(Impostazioni.preferences, Context.MODE_PRIVATE);

        IP = sharedpreferences.getString(TipiConfigurazione.ipServer, "");
        port = Integer.parseInt(sharedpreferences.getString(TipiConfigurazione.portaServer, "0"));

        tipoInvio = getIntent().getStringExtra("TipoInvio");

        if(tipoInvio.equals("clienti")){
            // setto il titolo
            getSupportActionBar().setTitle("Allinea Clienti");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_upload, menu);
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

    public void inviaDati(View view) {
        Toast.makeText(this, "Attendere Prego...", Toast.LENGTH_LONG).show();
        findViewById(R.id.btnInviaDati).setVisibility(View.INVISIBLE);
        findViewById(R.id.Attendere).setVisibility(View.VISIBLE);
        SharedPreferences sharedpreferences = getSharedPreferences(Impostazioni.preferences, Context.MODE_PRIVATE);
        String idTecnico = sharedpreferences.getString(TipiConfigurazione.idTecnico, "");
        if(IP.equals("") || port == 0){
            AlertDialog.Builder myb = Utility.creaDialogoVeloce(this, "Upload, Dati mancanti in Configurazione", "Messaggio di Errore");
            myb.create().show();
        }

        String data;
        if(tipoInvio.equals("clienti")){
            data = "#990\r\n#00\r\n";
        }
        else
            data = preparaDati(Integer.parseInt(idTecnico));

        Executor executor = new Executor(this, IP, port, data);
        Thread t = new Thread(executor);
        t.start();
    }

    public Void TaskComplete(Pair<Boolean,String> pair){
        coppia = pair;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.v("CAZZO", "Finito");
                boolean stato = coppia.first;
                result = coppia.second;
                if(stato == false){
                    AlertDialog.Builder myalert = Utility.creaDialogoVeloce(Upload.this, "Upload, Errore nella creazione della Socket", "Messaggio di Errore");
                    myalert.create().show();
                    findViewById(R.id.btnInviaDati).setVisibility(View.VISIBLE);
                    findViewById(R.id.Attendere).setVisibility(View.INVISIBLE);
                    return;
                }
                // inizio la gestione del testo
                if(result.equals("")) {
                    AlertDialog.Builder myalert = Utility.creaDialogoVeloce(Upload.this, "Upload, Errore in Ricezione Provare più Tardi", "Messaggio di Errore");
                    myalert.create().show();
                    findViewById(R.id.btnInviaDati).setVisibility(View.VISIBLE);
                    findViewById(R.id.Attendere).setVisibility(View.INVISIBLE);
                    return;
                }
                String test = result.substring(result.length() - 5);
                if(test.equals("#00\r\n") || test.equals("#99\r\n")){
                    letturadati();
                    findViewById(R.id.Attendere).setVisibility(View.INVISIBLE);
                    findViewById(R.id.pupazzo).setVisibility(View.VISIBLE);
                    findViewById(R.id.statopupazzo).setVisibility(View.VISIBLE);
                    return;
                }
                AlertDialog.Builder myalert = Utility.creaDialogoVeloce(Upload.this, "Upload, Errore in Ricezione Provare più Tardi", "Messaggio di Errore");
                myalert.create().show();
                findViewById(R.id.btnInviaDati).setVisibility(View.VISIBLE);
                findViewById(R.id.Attendere).setVisibility(View.INVISIBLE);
                return;
            }
        });
        return null;
    }

    public String preparaDati(int idTecnico){

        MySqlLiteHelper mysql = new MySqlLiteHelper(this);

        String result;
        result = "";
        result = result + "#01" + idTecnico + "\r" + "\n";

        ArrayList<Intervento> interventi = new ArrayList<>();
        interventi = mysql.getAllIntervento();

        for(Intervento it : interventi){
            result = result + "#02";
            result = result + formattaNumero(it.getId(), 10);
            result = result + formattaStringa(it.getHwsw(), 1);
            result = result + formattaNumero(Integer.parseInt(it.getNumero()), 10);
            result = result + formattaData(it.getData());
            result = result + formattaStringa(it.getCodiceCliente(), 15);
            result = result + formattaStringa(it.getRagSocialeCliente(), 60);
            result = result + formattaStringa(it.getIndirizzoCliente(), 60);
            result = result + formattaStringa(it.getLocalitaCliente(), 30);
            result = result + formattaStringa(it.getTelefonoCliente(), 20);
            result = result + formattaStringa(it.getFaxCliente(), 20);
            result = result + formattaNumero(it.getNonTrasferire(), 2);
            result = result + formattaNumero(it.getChiusa(), 2);
            result = result + formattaNumero(it.getIdCliente(), 10);
            result = result + formattaStringa(it.getIdunivoco(), 16);
            result = result + "\r" + "\n";

            result = result + "#03";
            if(!it.getMotivoChiamata().equals("") && it.getMotivoChiamata() != null){
                result = result + it.getMotivoChiamata()+ "\r" + "\n";
            }
            result = result + "^03" + "\r" + "\n";

            ArrayList<Articolo> articoli = mysql.getArticoliDaIntervento(it.getId());
            for(Articolo art : articoli){
                result = result + "#05";
                result = result + formattaStringa(art.getMatricola(), 15);
                result = result + formattaStringa(art.getCodice(), 15);
                result = result + formattaStringa(art.getDescrizione(), 15);
                result = result + formattaStringa(art.getTipointervento(), 5);
                result = result + formattaStringa(art.getMerceologico(), 2);
                result = result + formattaStringa(art.getGaranzia(), 5);
                result = result + formattaNumero(((Float) (art.getPrezzo() * 100)).intValue(), 10);
                result = result + formattaNumero(art.getIDit(), 10);
                result = result + formattaNumero(art.getId(), 10);
                result = result + formattaNumero(art.getQt(), 10);
                result = result + formattaStringa(art.getIdunivoco(), 16);
                result = result + "\r" + "\n";
            }

            ArrayList<SottoIt> operazioni = mysql.getOperazioni(it.getId());
            for(SottoIt op : operazioni){
                result = result + "#06";
                result = result + formattaNumero(op.getId(), 10);
                result = result + formattaNumero(op.getIdit(), 10);
                result = result + formattaNumero(op.getIdTecnico(), 10);
                result = result + formattaStringa(op.getTecnico(), 50);
                result = result + formattaStringa(op.getRagSocialeCliente(), 60);
                result = result + formattaData(op.getDataInizio());
                result = result + formattaData(op.getDataFine());
                result = result + formattaStringa(op.getLuogo(), 2);
                result = result + formattaNumero(op.getNonFatturare(), 2);
                result = result + formattaNumero(op.getNonTrasferire(), 2);
                result = result + formattaNumero(op.getChiusa(), 2);
                result = result + formattaStringa(op.getHWSW(), 1);
                result = result + formattaNumero(op.getIdCliente(), 10);
                result = result + formattaStringa(op.getCodiceCliente(), 15);
                result = result + formattaStringa(op.getIdUnivoco(), 16) + "\r" + "\n";
                result = result + "#07";
                if(!op.getNotaTecnico().equals("") && op.getNotaTecnico() != null){
                    result = result + op.getNotaTecnico() + "\r" + "\n";
                }
                result = result + "^07" + "\r" + "\n";
            }
        }

        ArrayList<SottoIt> operazioni = new ArrayList<>();
        operazioni = mysql.getOperazioni(0);

        for(SottoIt st : operazioni){
            result = result + "#06";
            result = result + formattaNumero(st.getId(), 10);
            result = result + formattaNumero(st.getIdit(), 10);
            result = result + formattaNumero(st.getIdTecnico(), 10);
            result = result + formattaStringa(st.getTecnico(), 50);
            result = result + formattaStringa(st.getRagSocialeCliente(), 60);
            result = result + formattaData(st.getDataInizio());
            result = result + formattaData(st.getDataFine());
            result = result + formattaStringa(st.getLuogo(), 2);
            result = result + formattaNumero(st.getNonFatturare(), 2);
            result = result + formattaNumero(st.getNonTrasferire(), 2);
            result = result + formattaNumero(st.getChiusa(), 2);
            result = result + formattaStringa(st.getHWSW(), 1);
            result = result + formattaNumero(st.getIdCliente(), 10);
            result = result + formattaStringa(st.getCodiceCliente(), 15);
            result = result + formattaStringa(st.getIdUnivoco(), 16) + "\r" + "\n";
            result = result + "#07";
            if(!st.getNotaTecnico().equals("")){
                result = result + st.getNotaTecnico() + "\r" + "\n";
            }
            result = result + "^07" + "\r" + "\n";
        }
        result = result + "#00" + "\r" + "\n";
        return result;
    }

    public boolean letturadati(){
        boolean finetrasferimento = false;
        boolean tipiinterventoeliminati = false;

        MySqlLiteHelper mysql = new MySqlLiteHelper(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ITALY);

        if(tipoInvio.equals("clienti")){
            Log.v("CAZZO", "Eliminati Clienti");
            mysql.deleteAllClienti();
        }

        if(tipoInvio.equals("dati")){
            mysql.deleteAllSottoit();
            mysql.deleteAllInterventi();
            mysql.deleteAllTipiIntervento();
            mysql.deleteAllArticoli();
        }

        String ls = "";
        int os = 0;
        int os1 = result.indexOf("\r\n", os);
        String s1;
        Integer Idit = 0;
        Integer IdSottoIt = 0;

        while (os1 > 0){
            s1 = result.substring(os, os1);
            String test = s1.substring(0,3);
            switch (s1.substring(0,3)){
                case "#11":  // intestazione
                    if(!tipiinterventoeliminati){
                        // se non ho ancora eliminato i tipi intervento li elimino
                        tipiinterventoeliminati = true;
                        mysql.deleteAllTipiIntervento();
                    }
                    String codice = s1.substring(3,5);
                    String descrizione = s1.substring(5,55);
                    test = aggiustaNumero(s1.substring(55, 57));
                    int addebitabile = Integer.parseInt(aggiustaNumero(s1.substring(55, 57)));
                    TipiIntervento tp = new TipiIntervento(0, codice, descrizione, addebitabile);
                    mysql.addTipoIntervento(tp);
                    break;
                case "#12": //testata intervento
                     Idit = Integer.parseInt(aggiustaNumero(s1.substring(3, 13)));
                    String HWSW = s1.substring(13,14);
                    String numero = s1.substring(14,24);
                    Date data = new Date(), dataprevista = new Date();
                    String dataprova = s1.substring(24, 19 + 24);
                    String dataprova2 = s1.substring(43, 43 + 19);
                    try{
                        data = sdf.parse(s1.substring(24, 19 + 24));
                        dataprevista = sdf.parse(s1.substring(43, 43 + 19));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    String codicecliente = s1.substring(62, 62 + 15);
                    String ragione = s1.substring(77, 77 + 60);
                    String indirizzo = s1.substring(137, 137 + 60);
                    String localita = s1.substring(197, 197 + 30);
                    String telefono = s1.substring(227, 227 + 20);
                    String fax = s1.substring(247, 247 + 20);
                    Integer nontrasferire = Integer.parseInt(aggiustaNumero(s1.substring(267, 267 + 2)));
                    Integer chiusa = Integer.parseInt(aggiustaNumero(s1.substring(269, 269 + 2)));
                    Integer idCliente = null;
                    if(!s1.substring(271,281).equals("")){
                        idCliente = Integer.parseInt(aggiustaNumero(s1.substring(271, 281)));
                    }
                    Intervento it = new Intervento(Idit, Idit, HWSW, numero, data, dataprevista, idCliente, codicecliente, ragione, indirizzo, localita, telefono, fax, "", "",nontrasferire, chiusa, "");
                    mysql.addIntervento(it);
                    break;
                case "#13":
                    ls = s1.substring(3, 3 + s1.length() - 3);
                    break;
                case "#14":
                    ls = s1.substring(3, 3 + s1.length() - 3);
                    break;
                case "#17":
                    ls = s1.substring(3, 3 + s1.length() - 3);
                    break;
                case "^13": //motivochiamata
                    it = mysql.getIntervento(Idit).get(0);
                    it.setMotivoChiamata(ls);
                    mysql.updateIntervento(it);
                    break;
                case "^14": //noteassegnatore
                    it = mysql.getIntervento(Idit).get(0);
                    it.setNoteAssegnatore(ls);
                    mysql.updateIntervento(it);
                    break;
                case "#15": //articoli
                    String matricola = s1.substring(3,18);
                    String codiceart = s1.substring(18, 18 + 15);
                    String descrizioneart = s1.substring(33, 33 + 15);
                    String tipointervento = s1.substring(48, 48 + 5);
                    String merceologico = s1.substring(53, 53 + 2);
                    String garanzia = s1.substring(55, 55 + 5);
                    float prezzo;
                    if(android.text.TextUtils.isDigitsOnly(s1.substring(60, 60 + 10))){
                        prezzo = Float.parseFloat(s1.substring(60, 60 + 10));
                    }else prezzo = 0;
                    Idit = Integer.parseInt(aggiustaNumero(s1.substring(70, 70 + 10)));
                    Integer ID = Integer.parseInt(aggiustaNumero(s1.substring(80, 80 + 10)));
                    Integer qt = Integer.parseInt(aggiustaNumero(s1.substring(90, 90 + 10)));
                    Articolo art = new Articolo(ID,Idit,matricola,codiceart,descrizioneart, tipointervento,merceologico,garanzia,prezzo,ID, qt, "");
                    mysql.addArticolo(art);
                    break;
                case "#16":
                    IdSottoIt = Integer.parseInt(aggiustaNumero(s1.substring(3, 13)));
                    Idit = Integer.parseInt(aggiustaNumero(s1.substring(13, 13 + 10)));
                    Integer idTecnico = Integer.parseInt(aggiustaNumero(s1.substring(23, 23 + 10)));
                    String tecnico = s1.substring(33, 33 + 50);
                    ragione = s1.substring(83, 83 + 60);
                    Date datainizio = new Date(), datafine = new Date();
                    try{
                        datainizio = sdf.parse(s1.substring(143, 143 + 19));
                        datafine = sdf.parse(s1.substring(162, 162 + 19));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    String luogo = s1.substring(181, 181 + 2);
                    Integer nonfatturare = Integer.parseInt(aggiustaNumero(s1.substring(183, 183 + 2)));
                    nontrasferire = Integer.parseInt(aggiustaNumero(s1.substring(185, 185 + 2)));
                    chiusa = Integer.parseInt(aggiustaNumero(s1.substring(187, 187 + 2)));
                    HWSW = s1.substring(189, 189 + 1);
                    idCliente = Integer.parseInt(aggiustaNumero(s1.substring(190, 190 + 10)));
                    codicecliente = s1.substring(200, 200 + 15);
                    SottoIt st = new SottoIt(IdSottoIt, IdSottoIt, HWSW, Idit, idTecnico, tecnico, ragione, datainizio, datafine, luogo, "", nonfatturare, nontrasferire, chiusa, idCliente, codicecliente, "");
                    mysql.addSottoIntervento(st);
                    break;
                case "^17":
                    st = mysql.getSottoIntervento(IdSottoIt).get(0);
                    st.setNotaTecnico(ls);
                    mysql.updateSottoIntervento(st);
                    break;
                case "#19":
                    break;
                case "#00":
                    finetrasferimento = true;
                default:
                    ls = ls + "\r\n" + s1;
            }
            os = os1 + 2;
            os1 = result.indexOf("\r\n", os);
        }

        if(finetrasferimento){
            ls = "";
            os=0;
            os1 = result.indexOf("\r\n", os);
            if(tipoInvio.equals("clienti")){
                while(os1 > 0){
                    s1 = result.substring(os, os1);
                    String test = s1.substring(0,3);
                    int i = 0;
                    switch (test){
                        case "#19": //anagrafica clienti
                            String codice = s1.substring(3,3 + 15);
                            String codiceHW = s1.substring(18, 18 + 15);
                            String codiceSW = s1.substring(33, 33 + 15);
                            String ragione = s1.substring(48, 48 + 61);
                            Integer ID = Integer.parseInt(aggiustaNumero(s1.substring(109, 109 + 10)));
                            Cliente c = new Cliente(ID, codice, codiceHW, codiceSW, ragione, ID);
                            mysql.addCliente(c);
                            Log.v("CAZZO", "Aggiunto Cliente" + i);
                            i++;
                    }
                    os = os1 + 2;
                    os1 = result.indexOf("\r\n", os);
                }
            }
        }

        return  true;
    }

    private String formattaNumero(Integer n, int dim){
        String rn;
        if(n == null){
            rn = "";
        }
        else rn = (new Integer(Math.abs(n))).toString();
        while(rn.length() < dim - 1){
            rn = "0" + rn;
        }
        if(n == null) return "+" + rn;
        if(n >= 0) return "+" + rn;
        else return "-" + rn;
    }

    private String formattaIntero(Integer n, int dim){
        String rn;
        if(n == null){
            rn = "";
        }
        else rn = n.toString();
        while(rn.length() < dim){
            rn = "0" + rn;
        }
        return rn;
    }

    private String formattaData(Date d){
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hours = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        if(d == null) return "0000-00-00 00:00:00";
        return formattaIntero(year,4)+ "-" + formattaIntero(month, 2) + "-" + formattaIntero(day, 2) + " " + formattaIntero(hours, 2) + ":" + formattaIntero(minute, 2) + ":" + formattaIntero(second, 2);
    }

    private String formattaStringa(String s,int dim){
        String ns;
        if(s == null){
            ns = "";
        }
        else {
            ns = s + "";
        }

        while (ns.length() < dim) {
            ns += " ";
        }
        return ns;
    }

    private String aggiustaNumero(String s){
        if(s.startsWith("+")) return s.substring(1);
        else return s;
    }



}
