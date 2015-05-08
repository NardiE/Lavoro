package com.example.edoardo.intrablet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.edoardo.intrablet.database.MySqlLiteHelper;
import com.example.edoardo.intrablet.database.SottoIt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class UploadActivity extends ActionBarActivity {

    private Socket socket;

    private String IP;
    private int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
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
            Intent i = new Intent(this, Settings.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void inviaDati(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences(Settings.preferences, Context.MODE_PRIVATE);
        String idTecnico = sharedpreferences.getString(TipiConfigurazione.idTecnico, "");
        String data = preparaDati(Integer.parseInt(idTecnico));
        MyClientTask mct = new MyClientTask("192.168.1.31", 1001, data);
        mct.execute();
    }

    public class MyClientTask extends AsyncTask<Void,Void,Void>{
        String addr;
        int port;
        String data;
        String response = "";

        MyClientTask(String addr, int port, String data){
            this.addr = addr;
            this.port = port;
            this.data = data;
        }

        @Override
        protected Void doInBackground(Void... params) {
            Socket socket = null;
            try{
                socket = new Socket(addr,port);
                socket.setSendBufferSize(8 * 10 * 1024);
                OutputStream os = socket.getOutputStream();
                PrintWriter pos = new PrintWriter(os, true);
                pos.write(data);
                pos.close();
                int datacount = data.length();
                datacount++;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //do something
            super.onPostExecute(aVoid);
        }
    }

    public String preparaDati(int idTecnico){

        MySqlLiteHelper mysql = new MySqlLiteHelper(this);
        ArrayList<SottoIt> operazioni = new ArrayList<>();
        try{
            operazioni = mysql.getAllOperazioni();}
        catch (Exception e){
            e.printStackTrace();
        }

        String result = new String();
        result = "";
        result = result + "#01" + idTecnico + "\r" + "\n";

        for(SottoIt st : operazioni){
            result = result + "#06";
            result = result + formattaNumero(st.getId(), 10);
            result = result + formattaNumero(st.getIdTecnico(), 10);
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

    private String formattaNumero(Integer n, int dim){
        String rn = new String();
        if(n == null){
            rn = "";
        }
        else rn = (new Integer(Math.abs(n))).toString();
        while(rn.length() < dim - 1){
            rn = "0" + rn;
        }
        if(n >= 0) return "+" + rn;
        else return "-" + rn;
    }

    private String formattaIntero(Integer n, int dim){
        String rn = new String();
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
        String ns = new String();
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



}
