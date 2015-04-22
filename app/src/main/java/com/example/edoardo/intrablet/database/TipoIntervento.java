package com.example.edoardo.intrablet.database;

/**
 * Created by Giacomo on 10/11/2014.
 * If you need some explanation write down a mail to
 * giacomo.maestrelli@gmail.com
 */
public class TipoIntervento {
    /***
     * Necessario per lavorare con i cursori Android
     */
    private final int _id;
    private String codice;
    private String descrizione;
    private int addebitabile;

    public TipoIntervento(int _id, String codice, String descrizione, int addebitabile) {

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
