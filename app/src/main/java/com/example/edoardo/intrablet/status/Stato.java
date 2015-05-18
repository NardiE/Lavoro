package com.example.edoardo.intrablet.status;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
/**
 * Created by Giacomo on 30/10/2014.
 * If you need some explanation write down a mail to
 * giacomo.maestrelli@gmail.com
 */
public class Stato {

    private boolean chiuse;
    private boolean sw;

    public Stato(boolean chiuse,boolean software)
    {
        this.chiuse = chiuse;

        this.sw = software;
    }

    public boolean isSw() {
        return sw;
    }

    public boolean isChiuse() {
        return chiuse;
    }

    public String toString(){
        String str = "[";
        for (Field a : getClass().getDeclaredFields())
        {
            str = a.getName()+":";
            try {
                str += a.get(this);
            }catch(IllegalAccessException e){
                str+="N.A.";
            }
        }
        str+="]";
        return str;
    }
}
