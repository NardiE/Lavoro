package com.example.edoardo.intrablet.database;

import org.json.JSONObject;

@SuppressWarnings("unused")

public class TipiIntervento {
    /***
     * Necessario per lavorare con i cursori Android
     */
    private final int _id;
    private String codice;
    private String descrizione;
    private int addebitabile;

    public TipiIntervento(JSONObject json){
        this._id = 0;
    }

    public TipiIntervento(int _id, String codice, String descrizione, int addebitabile) {

        this._id = _id;
        this.codice = codice;
        this.descrizione = descrizione;
        this.addebitabile = addebitabile;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getAddebitabile() {
        return addebitabile;
    }

    public void setAddebitabile(int addebitabile) {
        this.addebitabile = addebitabile;
    }

}
