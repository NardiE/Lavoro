package com.example.edoardo.intrablet.database;


import org.json.JSONObject;

@SuppressWarnings("unused")

public class Articolo {
/*fixme problema singolare/plurale*/
    /***
     * Necessario per lavorare con i cursori Android
     */
     final int _id;
     int IDit;
     String matricola;
     String codice;
     String descrizione;
     String tipointervento;
     String merceologico;
     String garanzia;
     float prezzo;
     int id;
     int qt;
     String idunivoco;

    public Articolo(JSONObject json){
        this._id = 0;
    }

    public Articolo(int _id, int IDit, String matricola, String codice, String descrizione,
                    String tipointervento, String merceologico, String garanzia, float prezzo,
                    int id, int qt, String idunivoco)
    {
        this._id = _id;
        this.IDit = IDit;
        this.matricola = matricola;
        this.codice = codice;
        this.descrizione = descrizione;
        this.tipointervento = tipointervento;
        this.merceologico = merceologico;
        this.garanzia = garanzia;
        this.prezzo = prezzo;
        this.id = id;
        this.qt = qt;
        this.idunivoco = idunivoco;
    }

    /***
     * Getter dell'identificatore interno utilizzato dal cursore Android
     * @return identificatore utilizzato dal cursore
     */
    public int get_id() {
        return _id;
    }

    /**
     * Setter dell'identificatore interno utilizzato dal cursore Android
     * //@param _id
     */
    //public void set_id(int _id) {
       // this._id = _id;
    //}

    public int getIDit() {
        return IDit;
    }

    public void setIDit(int IDit) {
        this.IDit = IDit;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setMatricola(String matricola) {
        this.matricola = matricola;
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

    public String getTipointervento() {
        return tipointervento;
    }

    public void setTipointervento(String tipointervento) {
        this.tipointervento = tipointervento;
    }

    public String getMerceologico() {
        return merceologico;
    }

    public void setMerceologico(String merceologico) {
        this.merceologico = merceologico;
    }

    public String getGaranzia() {
        return garanzia;
    }

    public void setGaranzia(String garanzia) {
        this.garanzia = garanzia;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQt() {
        return qt;
    }

    public void setQt(int qt) {
        this.qt = qt;
    }

    public String getIdunivoco() {
        return idunivoco;
    }

    public void setIdunivoco(String idunivoco) {
        this.idunivoco = idunivoco;
    }
}
