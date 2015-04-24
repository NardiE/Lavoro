package com.example.edoardo.intrablet.database;
import android.content.Intent;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Giacomo on 10/11/2014.
 */
public class Intervento {

    private final int _id;
    private int id;
    private String hwsw;
    private String numero;
    private Date data;
    private Date dataPrevistaIntervento;
    /*fixme DESIGN DI MERDA: dati replicati! 3FN */
    private int idCliente;
    private String codiceCliente;
    private String ragSocialeCliente;
    private String indirizzoCliente;
    private String localitaCliente;
    private String telefonoCliente;
    private String faxCliente;
    /* termine*/
    private String motivoChiamata;
    private String noteAssegnatore;
    private int nonTrasferire;
    private int chiusa;
    private String idunivoco;

    public Intervento(JSONObject json){
        this._id = 0;
    }

    public Intervento(){
       _id = 0;
   };

    public Intervento(int _id, int id, String hwsw, String numero, Date data,
                      Date dataPrevistaIntervento, int idCliente, String codiceCliente,
                      String ragSocialeCliente, String indirizzoCliente, String localitaCliente,
                      String telefonoCliente, String faxCliente, String motivoChiamata,
                      String noteAssegnatore, int nonTrasferire, int chiusa, String idunivoco) {
        this._id = _id;
        this.id = id;
        this.hwsw = hwsw;
        this.numero = numero;
        this.data = data;
        this.dataPrevistaIntervento = dataPrevistaIntervento;
        this.idCliente = idCliente;
        this.codiceCliente = codiceCliente;
        this.ragSocialeCliente = ragSocialeCliente;
        this.indirizzoCliente = indirizzoCliente;
        this.localitaCliente = localitaCliente;
        this.telefonoCliente = telefonoCliente;
        this.faxCliente = faxCliente;
        this.motivoChiamata = motivoChiamata;
        this.noteAssegnatore = noteAssegnatore;
        this.nonTrasferire = nonTrasferire;
        this.chiusa = chiusa;
        this.idunivoco = idunivoco;
    }

    public int get_id() {
        return _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHwsw() {
        return hwsw;
    }

    public void setHwsw(String hwsw) {
        this.hwsw = hwsw;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataPrevistaIntervento() {
        return dataPrevistaIntervento;
    }

    public void setDataPrevistaIntervento(Date dataPrevistaIntervento) {
        this.dataPrevistaIntervento = dataPrevistaIntervento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getCodiceCliente() {
        return codiceCliente;
    }

    public void setCodiceCliente(String codiceCliente) {
        this.codiceCliente = codiceCliente;
    }

    public String getRagSocialeCliente() {
        return ragSocialeCliente;
    }

    public void setRagSocialeCliente(String ragSocialeCliente) {
        this.ragSocialeCliente = ragSocialeCliente;
    }

    public String getIndirizzoCliente() {
        return indirizzoCliente;
    }

    public void setIndirizzoCliente(String indirizzoCliente) {
        this.indirizzoCliente = indirizzoCliente;
    }

    public String getLocalitaCliente() {
        return localitaCliente;
    }

    public void setLocalitaCliente(String localitaCliente) {
        this.localitaCliente = localitaCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getFaxCliente() {
        return faxCliente;
    }

    public void setFaxCliente(String faxCliente) {
        this.faxCliente = faxCliente;
    }

    public String getMotivoChiamata() {
        return motivoChiamata;
    }

    public void setMotivoChiamata(String motivoChiamata) {
        this.motivoChiamata = motivoChiamata;
    }

    public String getNoteAssegnatore() {
        return noteAssegnatore;
    }

    public void setNoteAssegnatore(String noteAssegnatore) {
        this.noteAssegnatore = noteAssegnatore;
    }

    public int getNonTrasferire() {
        return nonTrasferire;
    }

    public void setNonTrasferire(int nonTrasferire) {
        this.nonTrasferire = nonTrasferire;
    }

    public int getChiusa() {
        return chiusa;
    }

    public void setChiusa(int chiusa) {
        this.chiusa = chiusa;
    }

    public String getIdunivoco() {
        return idunivoco;
    }

    public void setIdunivoco(String idunivoco) {
        this.idunivoco = idunivoco;
    }
}
