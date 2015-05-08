package com.example.edoardo.intrablet.database;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Giacomo on 10/11/2014.
 * If you need some explanation write down a mail to
 * giacomo.maestrelli@gmail.com
 *
 *
 */
public class Cliente implements Serializable{
    /*fixme problema singolare/plurale*/
    /***
     * Necessario per lavorare con i cursori Android
     */
    private final int _id;
    private String codice;
    private String codiceHW;
    private String codiceSW;
    private String ragSociale;
    private int id;

    public Cliente(JSONObject json){
        this._id = 0;
    }

    public Cliente(int _id, String codice, String codiceHW, String codiceSW, String ragSociale, int id) {
        this._id = _id;
        this.codice = codice;
        this.codiceHW = codiceHW;
        this.codiceSW = codiceSW;
        this.ragSociale = ragSociale;
        this.id = id;
    }

    /***
     * Getter dell'identificatore interno utilizzato dal cursore Android
     * @return identificatore utilizzato dal cursore
     */
    public int get_id() {
        return _id;
    }



    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getCodiceHW() {
        return codiceHW;
    }

    public void setCodiceHW(String codiceHW) {
        this.codiceHW = codiceHW;
    }

    public String getCodiceSW() {
        return codiceSW;
    }

    public void setCodiceSW(String codiceSW) {
        this.codiceSW = codiceSW;
    }

    public String getRagSociale() {
        return ragSociale;
    }

    public void setRagSociale(String ragSociale) {
        this.ragSociale = ragSociale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
