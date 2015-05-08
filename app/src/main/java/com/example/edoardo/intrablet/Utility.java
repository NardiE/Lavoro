package com.example.edoardo.intrablet;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Edoardo on 29/04/2015.
 */
public class Utility {
    public static String idUnivoco(int idTecnico){
        Calendar c = Calendar.getInstance();
        String result = new String();
        result = "";
        result = formattaStringa("" + (c.get(Calendar.YEAR) % 100));
        result += formattaStringa("" + c.get(Calendar.MONTH));
        result += formattaStringa("" + c.get(Calendar.DAY_OF_MONTH));
        result += formattaStringa("" + c.get(Calendar.HOUR));
        result += formattaStringa("" + c.get(Calendar.MINUTE));
        result += formattaStringa("" + c.get(Calendar.SECOND));
        result += formattaStringa("" + idTecnico);
        result += formattaStringa("" + ((int)(Math.random()*89) + 10));
        return result;
    }

    public static String formattaStringa(String s){
        if(s.length() == 1) return 0 + s;
        return s;
    }
}
