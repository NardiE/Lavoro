package com.example.edoardo.intrablet;

/**
 * Created by Edoardo on 30/04/2015.
 */
public class TrasfElement {
    String data;
    String luogo;
    String ragioneSociale;
    Integer ID;

    public TrasfElement(String data, String luogo, String ragioneSociale, Integer ID) {
        this.data = data;
        this.luogo = luogo;
        this.ragioneSociale = ragioneSociale;
        this.ID = ID;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
