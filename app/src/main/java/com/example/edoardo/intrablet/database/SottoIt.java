package com.example.edoardo.intrablet.database;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Giacomo on 10/11/2014.
 * If you need some explanation write down a mail to
 * giacomo.maestrelli@gmail.com
 */
public class SottoIt {
    /***
     * Necessario per lavorare con i cursori Android
     */
    private final int _id;
    private int id;
    private String HWSW;
    private int idit;
    private int idTecnico;
    private String tecnico;
    private String ragSocialeCliente;
    private Date dataInizio;
    private Date dataFine;
    private String luogo;
    private String notaTecnico;
    private int nonFatturare;
    private int nonTrasferire;
    private int chiusa;
    private Integer idCliente;
    private String codiceCliente;
    private String idUnivoco;

    public SottoIt(JSONObject json){
        this._id = 0;
    }

    public SottoIt(int _id, int id, String HWSW, int idit, int idTecnico, String tecnico,
                   String ragSocialeCliente, Date dataInizio, Date dataFine, String luogo,
                   String notaTecnico, int nonFatturare, int nonTrasferire, int chiusa,
                   Integer idCliente, String codiceCliente, String idUnivoco)
    {
        this._id = _id;
        this.id = id;
        this.HWSW = HWSW;
        this.idit = idit;
        this.idTecnico = idTecnico;
        this.tecnico = tecnico;
        this.ragSocialeCliente = ragSocialeCliente;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.luogo = luogo;
        this.notaTecnico = notaTecnico;
        this.nonFatturare = nonFatturare;
        this.nonTrasferire = nonTrasferire;
        this.chiusa = chiusa;
        this.idCliente = idCliente;
        this.codiceCliente = codiceCliente;
        this.idUnivoco = idUnivoco;
    }

    public SottoIt(int _id, int id, String HWSW, int idit, int idTecnico, String tecnico,
                   String ragSocialeCliente, Date dataInizio, Date dataFine, String luogo,
                   String notaTecnico, int nonFatturare, int nonTrasferire, int chiusa, String codiceCliente, String idUnivoco)
    {
        this._id = _id;
        this.id = id;
        this.HWSW = HWSW;
        this.idit = idit;
        this.idTecnico = idTecnico;
        this.tecnico = tecnico;
        this.ragSocialeCliente = ragSocialeCliente;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.luogo = luogo;
        this.notaTecnico = notaTecnico;
        this.nonFatturare = nonFatturare;
        this.nonTrasferire = nonTrasferire;
        this.chiusa = chiusa;
        this.idCliente = null;
        this.codiceCliente = codiceCliente;
        this.idUnivoco = idUnivoco;
    }

    /***
     * Getter dell'identificatore interno utilizzato dal cursore Android
     * @return identificatore utilizzato dal cursore
     */
    public int get_id() {
        return _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHWSW() {
        return HWSW;
    }

    public void setHWSW(String HWSW) {
        this.HWSW = HWSW;
    }

    public int getIdit() {
        return idit;
    }

    public void setIdit(int idit) {
        this.idit = idit;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getRagSocialeCliente() {
        return ragSocialeCliente;
    }

    public void setRagSocialeCliente(String ragSocialeCliente) {
        this.ragSocialeCliente = ragSocialeCliente;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public String getNotaTecnico() {
        return notaTecnico;
    }

    public void setNotaTecnico(String notaTecnico) {
        this.notaTecnico = notaTecnico;
    }

    public int getNonFatturare() {
        return nonFatturare;
    }

    public void setNonFatturare(int nonFatturare) {
        this.nonFatturare = nonFatturare;
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

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getCodiceCliente() {
        return codiceCliente;
    }

    public void setCodiceCliente(String codiceCliente) {
        this.codiceCliente = codiceCliente;
    }

    public String getIdUnivoco() {
        return idUnivoco;
    }

    public void setIdUnivoco(String idUnivoco) {
        this.idUnivoco = idUnivoco;
    }
}
