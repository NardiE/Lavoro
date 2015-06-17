package com.example.edoardo.intrablet.database;

import android.app.AlertDialog;
import android.util.Log;

import com.example.edoardo.intrablet.Applicazione;
import com.example.edoardo.intrablet.Utility;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.UnsupportedCharsetException;
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
            Log.i(Applicazione.LOGTAGUP, "Inizio ricezione del risultato");
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);//, "ISO-8859-8");
            BufferedReader br = new BufferedReader(isr);
            String input;
            StringBuilder total = new StringBuilder();
            while((input = br.readLine()) != null){
                if(!input.equals("OK")){
                    total.append(input).append("\r\n");
                }
            }

            result = total.toString();
            Log.i(Applicazione.LOGTAGUP, "Fine ricezione del risultato");
            return result;

        }catch (IOException e){
            Log.e(Applicazione.LOGTAGUP, "Errore I/O: " + e.toString());
            return result;
        }
    }
}
