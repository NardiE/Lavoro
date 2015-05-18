package com.example.edoardo.intrablet.database;

        /*
        cv.put(DBUtility.TABARTICOLI.IDit.getSQLColonnaInfo().getSQLColumnName(),obj.getIDit());
        cv.put(DBUtility.TABARTICOLI.MATRICOLA.getSQLColonnaInfo().getSQLColumnName(),obj.getMatricola());
        cv.put(DBUtility.TABARTICOLI.CODICE.getSQLColonnaInfo().getSQLColumnName(),obj.getCodice());
        cv.put(DBUtility.TABARTICOLI.DESCRIZIONE.getSQLColonnaInfo().getSQLColumnName(),obj.getDescrizione());
        cv.put(DBUtility.TABARTICOLI.TIPOINTERVENTO.getSQLColonnaInfo().getSQLColumnName(),obj.getTipointervento());
        cv.put(DBUtility.TABARTICOLI.MERCEOLOGICO.getSQLColonnaInfo().getSQLColumnName(),obj.getMerceologico());
        cv.put(DBUtility.TABARTICOLI.GARANZIA.getSQLColonnaInfo().getSQLColumnName(),obj.getGaranzia());
        cv.put(DBUtility.TABARTICOLI.PREZZO.getSQLColonnaInfo().getSQLColumnName(),obj.getPrezzo());
        cv.put(DBUtility.TABARTICOLI.ID.getSQLColonnaInfo().getSQLColumnName(),obj.getId());
        cv.put(DBUtility.TABARTICOLI.QT.getSQLColonnaInfo().getSQLColumnName(),obj.getQt());
        cv.put(DBUtility.TABARTICOLI.IDUNIVOCO.getSQLColonnaInfo().getSQLColumnName(),obj.getIdunivoco());

               cv.put(DBUtility.TABINTERVENTI.ID.getSQLColonnaInfo().getSQLColumnName(),obj.getId());
        cv.put(DBUtility.TABINTERVENTI.HWSW.getSQLColonnaInfo().getSQLColumnName(),obj.getHwsw());
        cv.put(DBUtility.TABINTERVENTI.NUMERO.getSQLColonnaInfo().getSQLColumnName(),obj.getNumero());
        cv.put(DBUtility.TABINTERVENTI.DATA.getSQLColonnaInfo().getSQLColumnName(),sdf.format(obj.getData()));
        cv.put(DBUtility.TABINTERVENTI.DATAPREVISTAINTERVENTO.getSQLColonnaInfo().getSQLColumnName(),sdf.format(obj.getDataPrevistaIntervento()));
        cv.put(DBUtility.TABINTERVENTI.IDCLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getIdCliente());
        cv.put(DBUtility.TABINTERVENTI.CODICECLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getCodiceCliente());
        cv.put(DBUtility.TABINTERVENTI.RAGSOCIALECLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getRagSocialeCliente());
        cv.put(DBUtility.TABINTERVENTI.INDIRIZZOCLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getIndirizzoCliente());
        cv.put(DBUtility.TABINTERVENTI.LOCALITACLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getLocalitaCliente());
        cv.put(DBUtility.TABINTERVENTI.TELEFONOCLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getTelefonoCliente());
        cv.put(DBUtility.TABINTERVENTI.FAXCLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getFaxCliente());
        cv.put(DBUtility.TABINTERVENTI.MOTIVOCHIAMATA.getSQLColonnaInfo().getSQLColumnName(),obj.getMotivoChiamata());
        cv.put(DBUtility.TABINTERVENTI.NOTEASSEGNATORE.getSQLColonnaInfo().getSQLColumnName(),obj.getNoteAssegnatore());
        cv.put(DBUtility.TABINTERVENTI.NONTRASFERIRE.getSQLColonnaInfo().getSQLColumnName(),obj.getNonTrasferire());
        cv.put(DBUtility.TABINTERVENTI.CHIUSA.getSQLColonnaInfo().getSQLColumnName(),obj.getChiusa());
        cv.put(DBUtility.TABINTERVENTI.IDUNIVOCO.getSQLColonnaInfo().getSQLColumnName(),obj.getIdunivoco());

               cv.put(DBUtility.TABINTERVENTI.ID.getSQLColonnaInfo().getSQLColumnName(),obj.getId());
        cv.put(DBUtility.TABINTERVENTI.HWSW.getSQLColonnaInfo().getSQLColumnName(),obj.getHwsw());
        cv.put(DBUtility.TABINTERVENTI.NUMERO.getSQLColonnaInfo().getSQLColumnName(),obj.getNumero());
        cv.put(DBUtility.TABINTERVENTI.DATA.getSQLColonnaInfo().getSQLColumnName(),sdf.format(obj.getData()));
        cv.put(DBUtility.TABINTERVENTI.DATAPREVISTAINTERVENTO.getSQLColonnaInfo().getSQLColumnName(),sdf.format(obj.getDataPrevistaIntervento()));
        cv.put(DBUtility.TABINTERVENTI.IDCLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getIdCliente());
        cv.put(DBUtility.TABINTERVENTI.CODICECLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getCodiceCliente());
        cv.put(DBUtility.TABINTERVENTI.RAGSOCIALECLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getRagSocialeCliente());
        cv.put(DBUtility.TABINTERVENTI.INDIRIZZOCLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getIndirizzoCliente());
        cv.put(DBUtility.TABINTERVENTI.LOCALITACLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getLocalitaCliente());
        cv.put(DBUtility.TABINTERVENTI.TELEFONOCLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getTelefonoCliente());
        cv.put(DBUtility.TABINTERVENTI.FAXCLIENTE.getSQLColonnaInfo().getSQLColumnName(),obj.getFaxCliente());
        cv.put(DBUtility.TABINTERVENTI.MOTIVOCHIAMATA.getSQLColonnaInfo().getSQLColumnName(),obj.getMotivoChiamata());
        cv.put(DBUtility.TABINTERVENTI.NOTEASSEGNATORE.getSQLColonnaInfo().getSQLColumnName(),obj.getNoteAssegnatore());
        cv.put(DBUtility.TABINTERVENTI.NONTRASFERIRE.getSQLColonnaInfo().getSQLColumnName(),obj.getNonTrasferire());
        cv.put(DBUtility.TABINTERVENTI.CHIUSA.getSQLColonnaInfo().getSQLColumnName(),obj.getChiusa());
        cv.put(DBUtility.TABINTERVENTI.IDUNIVOCO.getSQLColonnaInfo().getSQLColumnName(),obj.getIdunivoco());

        cv.put(DBUtility.TABCLIENTI.CODICE.getSQLColonnaInfo().getSQLColumnName(),obj.getCodice());
        cv.put(DBUtility.TABCLIENTI.CODICEHW.getSQLColonnaInfo().getSQLColumnName(),obj.getCodiceHW());
        cv.put(DBUtility.TABCLIENTI.CODICESW.getSQLColonnaInfo().getSQLColumnName(),obj.getCodiceSW());
        cv.put(DBUtility.TABCLIENTI.RAGIONESOCIALE.getSQLColonnaInfo().getSQLColumnName(),obj.getRagSociale());
        cv.put(DBUtility.TABCLIENTI.ID.getSQLColonnaInfo().getSQLColumnName(),obj.getId());

        cv.put("ID",obj.getId());
        cv.put("HWSW",obj.getHWSW());
        cv.put("IDIT",obj.getIdit());
        cv.put("IDTECNICO",obj.getIdTecnico());
        cv.put("TECNICO",obj.getTecnico());
        cv.put("RAGSOCIALECLIENTE",obj.getRagSocialeCliente());
        cv.put("DATAINIZIO",sdf.format(obj.getDataInizio()));
        cv.put("DATAFINE",sdf.format(obj.getDataFine()));
        cv.put("LUOGO",obj.getLuogo());
        cv.put("NOTATECNICO",obj.getNotaTecnico());
        cv.put("NONFATTURARE",obj.getNonFatturare());
        cv.put("NONTRASFERIRE",obj.getNonTrasferire());
        cv.put("CHIUSA",obj.getChiusa());
        cv.put("IDCLIENTE",obj.getIdCliente());
        cv.put("CODICECLIENTE",obj.getCodiceCliente());
        cv.put("IDUNIVOCO",obj.getIdUnivoco());

        cv.put("CODICE",obj.getCodice());
        cv.put("DESCRIZIONE",obj.getDescrizione());
        cv.put("ADDEBITABILE",obj.getAddebitabile());

        */
import android.content.ContentValues;

import java.text.SimpleDateFormat;

/**
 * Created by Giacomo on 12/11/2014.
 * If you need some explanation write down a mail to
 * giacomo.maestrelli@gmail.com
 */
public class DBUtility {

    static final String TABELLE = "tabelle2.db";
    static final int DATABASE_VERSION = 1;
    static final String pk = "primarykey";


    /**Associazione NomiTabella alle Colonne e Oggetti Java*/
    public static enum BIJECTIONTABNAME{
        INTERVENTI(NOMITABELLE.INTERVENTI, TABINTERVENTI.values(),Intervento.class),
        SOTTOIT(NOMITABELLE.SOTTOIT, TABSOTTOIT.values(),SottoIt.class),
        ARTICOLI(NOMITABELLE.ARTICOLI, TABARTICOLI.values(),Articolo.class),
        TIPIINTERVENTO(NOMITABELLE.TIPIINTERVENTO, TABTIPIINTERVENTO.values(),TipiIntervento.class),
        CLIENTI(NOMITABELLE.CLIENTI, TABCLIENTI.values(),Cliente.class);

        SQLTabella[] arr;
        NOMITABELLE nome;
        Class clsObj;

        BIJECTIONTABNAME(NOMITABELLE nome, SQLTabella[] arrEnum,Class clsObj)
        {
            this.nome= nome;
            this.arr= arrEnum;
            this.clsObj= clsObj;
        }

        public NOMITABELLE getEnumTabella(){ return nome;}
        public String getNomeTabella(){ return nome.getTableName();}
        public SQLTabella[] getTabella(){
            switch (this.getEnumTabella()){
                case INTERVENTI: return  arr;
                case SOTTOIT: return  arr;
                case ARTICOLI: return  arr;
                case TIPIINTERVENTO: return arr;
                case CLIENTI: return  arr;
                default: return arr;
            }
        }

        public ContentValues ObjectToRow(Object obj)
        {
            if(obj!= null && clsObj.equals(obj.getClass()))
            {
                ContentValues cv = new ContentValues();
                if(clsObj.equals(Intervento.class))
                {
                    SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    Intervento exc = (Intervento)obj;
                    cv.put(TABINTERVENTI.ID.getSQLColonnaInfo().getSQLColumnName(),exc.getId());
                    cv.put(TABINTERVENTI.HWSW.getSQLColonnaInfo().getSQLColumnName(),exc.getHwsw());
                    cv.put(TABINTERVENTI.NUMERO.getSQLColonnaInfo().getSQLColumnName(),exc.getNumero());
                    if(exc.getData()!= null)cv.put(TABINTERVENTI.DATA.getSQLColonnaInfo().getSQLColumnName(),sdf.format(exc.getData()));
                    if(exc.getDataPrevistaIntervento() != null) cv.put(TABINTERVENTI.DATAPREVISTAINTERVENTO.getSQLColonnaInfo().getSQLColumnName(),sdf.format(exc.getDataPrevistaIntervento()));
                    cv.put(TABINTERVENTI.IDCLIENTE.getSQLColonnaInfo().getSQLColumnName(),exc.getIdCliente());
                    cv.put(TABINTERVENTI.CODICECLIENTE.getSQLColonnaInfo().getSQLColumnName(),exc.getCodiceCliente());
                    cv.put(TABINTERVENTI.RAGSOCIALECLIENTE.getSQLColonnaInfo().getSQLColumnName(),exc.getRagSocialeCliente());
                    cv.put(TABINTERVENTI.INDIRIZZOCLIENTE.getSQLColonnaInfo().getSQLColumnName(),exc.getIndirizzoCliente());
                    cv.put(TABINTERVENTI.LOCALITACLIENTE.getSQLColonnaInfo().getSQLColumnName(),exc.getLocalitaCliente());
                    cv.put(TABINTERVENTI.TELEFONOCLIENTE.getSQLColonnaInfo().getSQLColumnName(),exc.getTelefonoCliente());
                    cv.put(TABINTERVENTI.FAXCLIENTE.getSQLColonnaInfo().getSQLColumnName(),exc.getFaxCliente());
                    cv.put(TABINTERVENTI.MOTIVOCHIAMATA.getSQLColonnaInfo().getSQLColumnName(),exc.getMotivoChiamata());
                    cv.put(TABINTERVENTI.NOTEASSEGNATORE.getSQLColonnaInfo().getSQLColumnName(),exc.getNoteAssegnatore());
                    cv.put(TABINTERVENTI.NONTRASFERIRE.getSQLColonnaInfo().getSQLColumnName(),exc.getNonTrasferire());
                    cv.put(TABINTERVENTI.CHIUSA.getSQLColonnaInfo().getSQLColumnName(),exc.getChiusa());
                    cv.put(TABINTERVENTI.IDUNIVOCO.getSQLColonnaInfo().getSQLColumnName(),exc.getIdunivoco());
                    return cv;
                }
                else if(clsObj.equals(SottoIt.class))
                {
                    SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    SottoIt exc = (SottoIt)obj;
                    cv.put(TABSOTTOIT.ID.getSQLColonnaInfo().getSQLColumnName(),exc.getId());
                    cv.put(TABSOTTOIT.HWSW.getSQLColonnaInfo().getSQLColumnName(),exc.getHWSW());
                    cv.put(TABSOTTOIT.IDIT.getSQLColonnaInfo().getSQLColumnName(),exc.getIdit());
                    cv.put(TABSOTTOIT.IDTECNICO.getSQLColonnaInfo().getSQLColumnName(),exc.getIdTecnico());
                    cv.put(TABSOTTOIT.TECNICO.getSQLColonnaInfo().getSQLColumnName(),exc.getTecnico());
                    cv.put(TABSOTTOIT.RAGSOCIALECLIENTE.getSQLColonnaInfo().getSQLColumnName(),exc.getRagSocialeCliente());
                    cv.put(TABSOTTOIT.DATAINIZIO.getSQLColonnaInfo().getSQLColumnName(),sdf.format(exc.getDataInizio()));
                    cv.put(TABSOTTOIT.DATAFINE.getSQLColonnaInfo().getSQLColumnName(),sdf.format(exc.getDataFine()));
                    cv.put(TABSOTTOIT.LUOGO.getSQLColonnaInfo().getSQLColumnName(),exc.getLuogo());
                    cv.put(TABSOTTOIT.NOTATECNICO.getSQLColonnaInfo().getSQLColumnName(),exc.getNotaTecnico());
                    cv.put(TABSOTTOIT.NONFATTURARE.getSQLColonnaInfo().getSQLColumnName(),exc.getNonFatturare());
                    cv.put(TABSOTTOIT.NONTRASFERIRE.getSQLColonnaInfo().getSQLColumnName(),exc.getNonTrasferire());
                    cv.put(TABSOTTOIT.CHIUSA.getSQLColonnaInfo().getSQLColumnName(),exc.getChiusa());
                    cv.put(TABSOTTOIT.IDCLIENTE.getSQLColonnaInfo().getSQLColumnName(),exc.getIdCliente());
                    cv.put(TABSOTTOIT.CODICECLIENTE.getSQLColonnaInfo().getSQLColumnName(),exc.getCodiceCliente());
                    cv.put(TABSOTTOIT.IDUNIVOCO.getSQLColonnaInfo().getSQLColumnName(),exc.getIdUnivoco());
                    return cv;
                }else if(clsObj.equals(Articolo.class))
                {
                    Articolo exc = (Articolo)obj;
                    cv.put(TABARTICOLI.IDit.getSQLColonnaInfo().getSQLColumnName(),exc.getIDit());
                    cv.put(TABARTICOLI.MATRICOLA.getSQLColonnaInfo().getSQLColumnName(),exc.getMatricola());
                    cv.put(TABARTICOLI.CODICE.getSQLColonnaInfo().getSQLColumnName(),exc.getCodice());
                    cv.put(TABARTICOLI.DESCRIZIONE.getSQLColonnaInfo().getSQLColumnName(),exc.getDescrizione());
                    cv.put(TABARTICOLI.TIPOINTERVENTO.getSQLColonnaInfo().getSQLColumnName(),exc.getTipointervento());
                    cv.put(TABARTICOLI.MERCEOLOGICO.getSQLColonnaInfo().getSQLColumnName(),exc.getMerceologico());
                    cv.put(TABARTICOLI.GARANZIA.getSQLColonnaInfo().getSQLColumnName(),exc.getGaranzia());
                    cv.put(TABARTICOLI.PREZZO.getSQLColonnaInfo().getSQLColumnName(),exc.getPrezzo());
                    cv.put(TABARTICOLI.ID.getSQLColonnaInfo().getSQLColumnName(),exc.getId());
                    cv.put(TABARTICOLI.QT.getSQLColonnaInfo().getSQLColumnName(),exc.getQt());
                    cv.put(TABARTICOLI.IDUNIVOCO.getSQLColonnaInfo().getSQLColumnName(),exc.getIdunivoco());
                    return cv;
                }else if(clsObj.equals(TipiIntervento.class))
                {
                    TipiIntervento exc = (TipiIntervento) obj;
                    cv.put(TABTIPIINTERVENTO.ADDEBITABILE.getSQLColonnaInfo().getSQLColumnName(),exc.getAddebitabile());
                    cv.put(TABTIPIINTERVENTO.CODICE.getSQLColonnaInfo().getSQLColumnName(),exc.getCodice());
                    cv.put(TABTIPIINTERVENTO.DESCRIZIONE.getSQLColonnaInfo().getSQLColumnName(),exc.getDescrizione());
                    return cv;
                }else if(clsObj.equals(Cliente.class))
                {
                    Cliente exc = (Cliente) obj;
                    cv.put(TABCLIENTI.CODICE.getSQLColonnaInfo().getSQLColumnName(),exc.getCodice());
                    cv.put(TABCLIENTI.CODICEHW.getSQLColonnaInfo().getSQLColumnName(),exc.getCodiceSW());
                    cv.put(TABCLIENTI.CODICESW.getSQLColonnaInfo().getSQLColumnName(),exc.getCodiceHW());
                    cv.put(TABCLIENTI.RAGIONESOCIALE.getSQLColonnaInfo().getSQLColumnName(),exc.getRagSociale());
                    cv.put(TABCLIENTI.ID.getSQLColonnaInfo().getSQLColumnName(),exc.getId());
                    return cv;
                }
                else return null;
            }
            return null;
        }
    }

    /**Nomi delle tabelle (memorizzati come String)*/
    private static enum NOMITABELLE{INTERVENTI("INTERVENTI"),SOTTOIT("SOTTOIT"),ARTICOLI("ARTICOLI"),
        TIPIINTERVENTO("TIPIINTERVENTO"),CLIENTI("CLIENTI");
        private String _internalRep;

        NOMITABELLE(String _internalRep) {this._internalRep = _internalRep;}

        public String getTableName() {return _internalRep;}
    }

    /**Tipi sql usati nelle colonne*/
    protected static enum SQLTYPE{INTEGER("integer"),VARCHAR("varchar"),DATETIME("datetime"),FLOAT("float"),TEXT("text"),
        SMALLINT("smallint");
        private String declType;
        SQLTYPE(String declType) {this.declType = declType;}
        public String getDeclType() {return declType;}
    }

    /**Rapprestentazione di una tabella di interventi*/
    public static enum TABINTERVENTI implements SQLTabella
    {
        _id("_id", SQLTYPE.INTEGER,true,0),
        ID("ID", SQLTYPE.INTEGER,false,1),
        HWSW("HWSW", SQLTYPE.VARCHAR,1,false,2),
        NUMERO("NUMERO", SQLTYPE.INTEGER,false,3),
        DATA("DATA", SQLTYPE.DATETIME,false,4),
        DATAPREVISTAINTERVENTO("DATAPREVISTAINTERVENTO", SQLTYPE.DATETIME,false,5),
        IDCLIENTE("IDCLIENTE", SQLTYPE.INTEGER,false,6),
        CODICECLIENTE("CODICECLIENTE", SQLTYPE.VARCHAR,15,false,7),
        RAGSOCIALECLIENTE("RAGSOCIALECLIENTE", SQLTYPE.VARCHAR,60,false,8),
        INDIRIZZOCLIENTE("INDIRIZZOCLIENTE", SQLTYPE.VARCHAR,60,false,9),
        LOCALITACLIENTE("LOCALITACLIENTE", SQLTYPE.VARCHAR,30,false,10),
        TELEFONOCLIENTE("TELEFONOCLIENTE", SQLTYPE.VARCHAR,20,false,11),
        FAXCLIENTE("FAXCLIENTE", SQLTYPE.VARCHAR,20,false,12),
        MOTIVOCHIAMATA("MOTIVOCHIAMATA", SQLTYPE.TEXT,false,13),
        NOTEASSEGNATORE("NOTEASSEGNATORE", SQLTYPE.TEXT,false,14),
        NONTRASFERIRE("NONTRASFERIRE", SQLTYPE.SMALLINT,false,15),
        CHIUSA("CHIUSA", SQLTYPE.SMALLINT,false,16),
        IDUNIVOCO("IDUNIVOCO", SQLTYPE.VARCHAR,16,false,17);

        private final SQLColonna colonna;

        TABINTERVENTI(String _internalRep, SQLTYPE sqlType,boolean isPk, int _declorder) {
            this.colonna = new SQLColonna(_internalRep, sqlType, isPk, _declorder);
        }

        TABINTERVENTI(String _internalRep, SQLTYPE sqlType,int colLength,
                    boolean isPk ,int _declorder) {
            this.colonna = new SQLColonna(_internalRep, sqlType,colLength, isPk, _declorder);
        }

        public SQLColonnaTabellabile getSQLColonnaInfo(){return this.colonna;}

    }

    /**Rappresentazione tabella sotto interventi*/
    public static enum TABSOTTOIT implements SQLTabella
    {
        _id("_id", SQLTYPE.INTEGER,true,0),
        ID("ID", SQLTYPE.INTEGER,false,1),
        HWSW("HWSW", SQLTYPE.VARCHAR,1,false,2),
        IDIT("IDIT", SQLTYPE.INTEGER,false,3),
        IDTECNICO("IDTECNICO", SQLTYPE.INTEGER,false,4),
        TECNICO("TECNICO", SQLTYPE.VARCHAR,50,false,5),
        RAGSOCIALECLIENTE("RAGSOCIALECLIENTE", SQLTYPE.VARCHAR,60,false,6),
        DATAINIZIO("DATAINIZIO", SQLTYPE.DATETIME,false,7),
        DATAFINE("DATAFINE", SQLTYPE.DATETIME,false,8),
        LUOGO("LUOGO", SQLTYPE.VARCHAR,2,false,9),
        NOTATECNICO("NOTATECNICO", SQLTYPE.TEXT,false,10),
        NONFATTURARE("NONFATTURARE", SQLTYPE.SMALLINT,false,11),
        NONTRASFERIRE("NONTRASFERIRE", SQLTYPE.SMALLINT,false,12),
        CHIUSA("CHIUSA", SQLTYPE.SMALLINT,false,13),
        IDCLIENTE("IDCLIENTE", SQLTYPE.INTEGER,false,14),
        CODICECLIENTE("CODICECLIENTE", SQLTYPE.VARCHAR,15,false,15),
        IDUNIVOCO("IDUNIVOCO", SQLTYPE.VARCHAR,16,false,16);

        private final SQLColonna colonna;

        TABSOTTOIT(String _internalRep, SQLTYPE sqlType,boolean isPk, int _declorder) {
            this.colonna = new SQLColonna(_internalRep, sqlType, isPk, _declorder);
        }

        TABSOTTOIT(String _internalRep, SQLTYPE sqlType,int colLength,
                      boolean isPk ,int _declorder) {
            this.colonna = new SQLColonna(_internalRep, sqlType,colLength, isPk, _declorder);
        }


        public SQLColonnaTabellabile getSQLColonnaInfo(){return this.colonna;}

    }

    /**Rappresentazione tabella articoli*/
    public static  enum TABARTICOLI implements SQLTabella
    {
        _id("_id", SQLTYPE.INTEGER,true,0),
        IDit("IDit", SQLTYPE.INTEGER,false,1),
        MATRICOLA("MATRICOLA", SQLTYPE.VARCHAR,15,false,2),
        CODICE("CODICE", SQLTYPE.VARCHAR,15,false,3),
        DESCRIZIONE("DESCRIZIONE", SQLTYPE.VARCHAR,250,false,4),
        TIPOINTERVENTO("TIPOINTERVENTO", SQLTYPE.VARCHAR,5,false,5),
        MERCEOLOGICO("MERCEOLOGICO", SQLTYPE.VARCHAR,2,false,6),
        GARANZIA("GARANZIA", SQLTYPE.VARCHAR,5,false,7),
        PREZZO("PREZZO", SQLTYPE.FLOAT,false,8),
        ID("ID", SQLTYPE.INTEGER,false,9),
        QT("QT", SQLTYPE.INTEGER,false,10),
        IDUNIVOCO("IDUNIVOCO", SQLTYPE.VARCHAR,15,false,11);

        private final SQLColonna colonna;

        TABARTICOLI(String _internalRep, SQLTYPE sqlType,boolean isPk, int _declorder)
        {
            this.colonna = new SQLColonna(_internalRep, sqlType, isPk, _declorder);
        }

        TABARTICOLI(String _internalRep, SQLTYPE sqlType,int colLength,
                      boolean isPk ,int _declorder) {
            this.colonna = new SQLColonna(_internalRep, sqlType,colLength, isPk, _declorder);
        }

        public SQLColonnaTabellabile getSQLColonnaInfo(){return this.colonna;}
    }

    /**Rapresentazione tabella tipi di intervento*/
    public static enum TABTIPIINTERVENTO implements SQLTabella
    {   _id("_id", SQLTYPE.INTEGER,true,0),
        CODICE("CODICE", SQLTYPE.VARCHAR,2,false,1),
        DESCRIZIONE("DESCRIZIONE", SQLTYPE.VARCHAR,100,false,2),
        ADDEBITABILE("ADDEBITABILE", SQLTYPE.SMALLINT,false,3)
        ;

        private final SQLColonna colonna;

        TABTIPIINTERVENTO(String _internalRep, SQLTYPE sqlType,boolean isPk, int _declorder)
        {
            this.colonna = new SQLColonna(_internalRep, sqlType, isPk, _declorder);
        }

        TABTIPIINTERVENTO(String _internalRep, SQLTYPE sqlType,int colLength,
                    boolean isPk ,int _declorder) {
            this.colonna = new SQLColonna(_internalRep, sqlType,colLength, isPk, _declorder);
        }

        public SQLColonnaTabellabile getSQLColonnaInfo(){return this.colonna;}

    }

    /**Rappresentazione tabella clienti*/
    public static enum TABCLIENTI implements SQLTabella{
        _id("_id", SQLTYPE.INTEGER,true,0),
        CODICE("CODICE", SQLTYPE.VARCHAR,15,false,1),
        CODICEHW("CODICEHW", SQLTYPE.VARCHAR,15,false,2),
        CODICESW("CODICESW", SQLTYPE.VARCHAR,15,false,3),
        RAGIONESOCIALE("RAGIONESOCIALE", SQLTYPE.VARCHAR,61,false,4),
        ID("ID", SQLTYPE.INTEGER,false,10);

        private final SQLColonna colonna;

        TABCLIENTI(String _internalRep, SQLTYPE sqlType,boolean isPk, int _declorder)
        {
            this.colonna = new SQLColonna(_internalRep, sqlType, isPk, _declorder);
        }

        TABCLIENTI(String _internalRep, SQLTYPE sqlType,int colLength,
                          boolean isPk ,int _declorder) {
            this.colonna = new SQLColonna(_internalRep, sqlType,colLength, isPk, _declorder);
        }

        public SQLColonnaTabellabile getSQLColonnaInfo(){return this.colonna;}
    }

    public static String createTabelle(NOMITABELLE tabName,SQLTabella[] tabella)
    {
       String str= "CREATE TABLE "+ tabName.getTableName()+ "(";
       for(SQLTabella elem:tabella)
       {
            SQLColonnaTabellabile en=elem.getSQLColonnaInfo();
           str += en.getSQLColumnName() + " " + en.getFieldType()
                   + (en.getFieldLength()>0 ?"("+en.getFieldLength()+") ": " " );
           if(en.isPrimaryKey()){str+=pk+" ";}
           if(!elem.equals(tabella[tabella.length-1])){str+=", ";}
       }
       str+=")";
       return str;
    }

    public static String createTabelle(BIJECTIONTABNAME nomeTabella )
    {
        return createTabelle(nomeTabella.getEnumTabella(),nomeTabella.getTabella());
    }

    public static String dropTable(BIJECTIONTABNAME tabName){
        return "DROP TABLE IF EXISTS " + tabName.getNomeTabella();
    }

}
