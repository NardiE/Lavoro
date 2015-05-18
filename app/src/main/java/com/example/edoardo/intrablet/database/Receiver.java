package com.example.edoardo.intrablet.database;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by Edoardo on 12/05/2015.
 */
public class Receiver implements Callable {
    Socket socket;
    String result;

    public Receiver(Socket socket){
        this.socket = socket;
    }

    @Override
    public String call() throws Exception {
        try{
            /*     SOLUZIONE 1
            while((input = br.readLine()) != null) {
                if (!input.equals("OK")) {
                    Log.v("CAZZO", "Ricevuto " + input);
                    total.append(input).append("\r\n");
                }
            }
            result = total.toString();
            Log.v("CAZZO", "Ritorno");
            return result;
             */


            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String input;
            StringBuilder total = new StringBuilder();
            while((input = br.readLine()) != null){
                if(!input.equals("OK")){
                    Log.v("CAZZO", "Ricevuto " + input);
                    total.append(input).append("\r\n");
                }
            }
            result = total.toString();
            Log.v("CAZZO", "Ritorno");
            return result;
        }catch (IOException e){
            return result;
        }
    }
}
