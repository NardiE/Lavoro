package com.example.edoardo.intrablet.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Helper per interagire con Database Palmari.
 */

@SuppressWarnings("unused")

public class MySqlLiteHelper extends SQLiteOpenHelper {

    public MySqlLiteHelper(Context context) {
        super(context, DBUtility.TABELLE, null, DBUtility.DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        for(DBUtility.BIJECTIONTABNAME elem: DBUtility.BIJECTIONTABNAME.values())
        {
            sqLiteDatabase.execSQL(DBUtility.createTabelle(elem));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
    {
        for(DBUtility.BIJECTIONTABNAME elem: DBUtility.BIJECTIONTABNAME.values()){
            DBUtility.dropTable(elem);}
        onCreate(sqLiteDatabase);
    }

    /*START CRUD*/
        /*START ADD*/
    public void addArticolo(Articolo obj)
    {
        SQLiteDatabase sqldb= this.getWritableDatabase();
        ContentValues cv= DBUtility.BIJECTIONTABNAME.ARTICOLI.ObjectToRow(obj);
        sqldb.insert(DBUtility.BIJECTIONTABNAME.ARTICOLI.getNomeTabella(),null,cv);
        sqldb.close();
    }

    public void addCliente(Cliente obj)
    {
        SQLiteDatabase sqldb= this.getWritableDatabase();
        ContentValues cv= DBUtility.BIJECTIONTABNAME.CLIENTI.ObjectToRow(obj);
        sqldb.insert(DBUtility.BIJECTIONTABNAME.CLIENTI.getNomeTabella(),null,cv);
        sqldb.close();
    }

    public void addIntervento(Intervento obj)
    {
        SQLiteDatabase sqldb= this.getWritableDatabase();
        ContentValues cv= DBUtility.BIJECTIONTABNAME.INTERVENTI.ObjectToRow(obj);
        sqldb.insert(DBUtility.BIJECTIONTABNAME.INTERVENTI.getNomeTabella(),null,cv);
        sqldb.close();
    }

    public void addSottoIntervento(SottoIt obj)
    {
        SQLiteDatabase sqldb= this.getWritableDatabase();
        ContentValues cv= DBUtility.BIJECTIONTABNAME.SOTTOIT.ObjectToRow(obj);
        sqldb.insert(DBUtility.BIJECTIONTABNAME.SOTTOIT.getNomeTabella(),null,cv);
        sqldb.close();
    }

    public void addTipoIntervento(TipiIntervento obj){

        ContentValues cv= DBUtility.BIJECTIONTABNAME.TIPIINTERVENTO.ObjectToRow(obj);
        SQLiteDatabase sqldb= this.getWritableDatabase();
        sqldb.insert(DBUtility.BIJECTIONTABNAME.TIPIINTERVENTO.getNomeTabella(),null,cv);
        sqldb.close();
    }
        /*END ADD*/

        /*START GET*/

    public ArrayList<Cliente> getClienti(String contains){

        ArrayList<Cliente> clienti = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.CLIENTI.getNomeTabella()+ " WHERE RAGIONESOCIALE LIKE '%" + contains + "%'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Cliente contact = new Cliente(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getInt(5));
                // Adding contact to list
                clienti.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return clienti;
    }

    public ArrayList<SottoIt> getOperazioni(Integer IDIt){
        ArrayList<SottoIt> operazioniperintervento = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.SOTTOIT.getNomeTabella() + " WHERE IDit = " + IDIt;

        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                SottoIt st;
                Date datain = new Date();
                Date datafin = new Date();
                try{
                    datain = sdf.parse(cursor.getString(7));
                    datafin = sdf.parse(cursor.getString(8));
                }catch(Exception e){e.printStackTrace();}

                try{st = new SottoIt(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5),cursor.getString(6),datain, datafin,cursor.getString(9),cursor.getString(10),cursor.getInt(11),cursor.getInt(12),cursor.getInt(13),cursor.getInt(14),cursor.getString(15),cursor.getString(16));}
                catch(Exception e){
                    e.printStackTrace();
                    return null;
                }

                operazioniperintervento.add(st);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return operazioniperintervento;
    }

    // ritorna gli articoli che appartengono allo stesso intervento
    public ArrayList<Articolo> getArticoliDaIntervento(Integer IDIt){
        ArrayList<Articolo> articoliperintervento = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.ARTICOLI.getNomeTabella() + " WHERE IDit = " + IDIt;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Articolo art;
                try{art = new Articolo(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getFloat(8), cursor.getInt(9), cursor.getInt(10), cursor.getString(11));}
                catch(Exception e){
                    e.printStackTrace();
                    return null;}
                articoliperintervento.add(art);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return articoliperintervento;
    }

    public ArrayList<Intervento> getChiamateChiuse(){
        ArrayList<Intervento> chiamatechiuse = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.INTERVENTI.getNomeTabella() + " WHERE CHIUSA = " + 1 + " ORDER BY " + DBUtility.TABINTERVENTI.ID.getSQLColonnaInfo().getSQLColumnName() + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
        if (cursor.moveToFirst()) {
            do {
                Intervento it;
                Date data;
                Date dataprevista;
                try {
                    data = sdf.parse(cursor.getString(4));
                    dataprevista = sdf.parse(cursor.getString(5));
                } catch (Exception e) {
                    throw new NullPointerException();
                }
                it = new Intervento(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), data, dataprevista, cursor.getInt(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getInt(15), cursor.getInt(16), cursor.getString(17), cursor.getInt(18));
                chiamatechiuse.add(it);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return chiamatechiuse;
    }

    public ArrayList<Intervento> getChiamateAperte(){
        ArrayList<Intervento> chiamateaperte = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.INTERVENTI.getNomeTabella() + " WHERE CHIUSA = " + 0 + " ORDER BY " + DBUtility.TABINTERVENTI.ID.getSQLColonnaInfo().getSQLColumnName() + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
        if (cursor.moveToFirst()) {
           do{
                Intervento it;
                Date data;
                Date dataprevista;
                try {
                    data = sdf.parse(cursor.getString(4));
                    dataprevista = sdf.parse(cursor.getString(5));
                } catch (Exception e) {
                    throw new NullPointerException();
                }
                it = new Intervento(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), data, dataprevista, cursor.getInt(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getInt(15), cursor.getInt(16), cursor.getString(17), cursor.getInt(18));
                chiamateaperte.add(it);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return chiamateaperte;
    }

    public ArrayList<Articolo> getArticoli(Integer ID) throws NullPointerException{
        ArrayList<Articolo> articoliperintervento = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.ARTICOLI.getNomeTabella() + " WHERE ID = " + ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Articolo art;
                try{art = new Articolo(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getFloat(8), cursor.getInt(9), cursor.getInt(10), cursor.getString(11));}
                catch(Exception e){
                    throw new NullPointerException();}
                articoliperintervento.add(art);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return articoliperintervento;
    }

    public Cliente getCliente(int _id, int ID) throws NullPointerException{

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                DBUtility.BIJECTIONTABNAME.CLIENTI.name(),
                new String[] { DBUtility.TABCLIENTI.ID.name(), DBUtility.TABCLIENTI.CODICE.name(), DBUtility.TABCLIENTI.CODICEHW.name(), DBUtility.TABCLIENTI.CODICESW.name(), DBUtility.TABCLIENTI.RAGIONESOCIALE.name()},
                DBUtility.TABCLIENTI.ID + "=?",
                new String[] { String.valueOf(ID) },
                null, null, null, null
        );

        Cliente contact = null;

        if (cursor != null) {
            cursor.moveToFirst();
            contact  = new Cliente(_id, cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(0));
            cursor.close();
        }
        return contact;

    }

    public ArrayList<Intervento> getIntervento(Integer ID){
        ArrayList<Intervento> interventi = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.INTERVENTI.getNomeTabella() + " WHERE ID = " + ID;SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
        if (cursor.moveToFirst()) {
            Intervento it;
            Date data;
            Date dataprevista;
            try {
                data = sdf.parse(cursor.getString(4));
                dataprevista = sdf.parse(cursor.getString(5));
            } catch (Exception e) {
                return null;
            }
            it = new Intervento(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), data, dataprevista, cursor.getInt(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getInt(15), cursor.getInt(16), cursor.getString(17), cursor.getInt(18));
            interventi.add(it);
        }
        cursor.close();
        return interventi;

    }

    public ArrayList<SottoIt> getSottoIntervento(Integer ID){
        ArrayList<SottoIt> sint = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.SOTTOIT.getNomeTabella() + " WHERE ID=" + ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
        if (cursor.moveToFirst()) {
            SottoIt st;
            do {
                Date datain;
                Date datafin;
                try{
                    datain = sdf.parse(cursor.getString(7));
                    datafin = sdf.parse(cursor.getString(8));
                }catch(Exception e){
                    return null;
                }
                st = new SottoIt(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5),cursor.getString(6),datain, datafin,cursor.getString(9),cursor.getString(10),cursor.getInt(11),cursor.getInt(12),cursor.getInt(13),cursor.getInt(14),cursor.getString(15),cursor.getString(16));

                // Adding contact to list
                sint.add(st);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sint;

    }

    public TipiIntervento getTipoIntervento(int _id){
        return null;
    }
        /*END GET*/

        /*START GETALL*/
    public ArrayList<SottoIt> getAllOperazioni(){
        ArrayList<SottoIt> sottoit = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.SOTTOIT.getNomeTabella() + " WHERE IDIT = 0";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);

        if (cursor.moveToFirst()) {
            do {
                Date datain;
                Date datafin;
                try{
                    datain = sdf.parse(cursor.getString(7));
                    datafin = sdf.parse(cursor.getString(8));
                }catch(Exception e){
                    return null;
                }
                SottoIt st = new SottoIt(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5),cursor.getString(6),datain, datafin,cursor.getString(9),cursor.getString(10),cursor.getInt(11),cursor.getInt(12),cursor.getInt(13),cursor.getInt(14),cursor.getString(15),cursor.getString(16));
                // Adding contact to list
                sottoit.add(st);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sottoit;
    }

    public ArrayList<Articolo> getAllArticolo(){
        ArrayList<Articolo> articoli = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.ARTICOLI.getNomeTabella();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Articolo art = new Articolo(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getFloat(8), cursor.getInt(9), cursor.getInt(10), cursor.getString(11));

                articoli.add(art);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return articoli;
    }

    public List<Cliente> getAllCliente(){

        ArrayList<Cliente> clienti = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.CLIENTI.getNomeTabella();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Cliente contact = new Cliente(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getInt(5));
                // Adding contact to list
                clienti.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return clienti;
    }

    public ArrayList<Intervento> getAllIntervento() {
        ArrayList<Intervento> interventi = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DBUtility.BIJECTIONTABNAME.INTERVENTI.getNomeTabella();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ITALY);
        if (cursor.moveToFirst()) {
            Intervento it;
            Date data;
            Date dataprevista;
            do {

                try {
                    data = sdf.parse(cursor.getString(4));
                    dataprevista = sdf.parse(cursor.getString(5));
                } catch (Exception e) {
                    return null;
                }

                it = new Intervento(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), data, dataprevista, cursor.getInt(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getInt(15), cursor.getInt(16), cursor.getString(17), cursor.getInt(18));

                // Adding intervent to list
                interventi.add(it);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return interventi;
    }

    public List<SottoIt> getAllSottoIntervento(){
        ArrayList<SottoIt> sint = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.SOTTOIT.getNomeTabella() + " ORDER BY " + DBUtility.TABSOTTOIT.ID.getSQLColonnaInfo().getSQLColumnName() + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if (cursor.moveToFirst()) {
            SottoIt st;
            do {
                Date datain;
                Date datafin;
                try{
                    datain = sdf.parse(cursor.getString(7));
                    datafin = sdf.parse(cursor.getString(8));
                }catch(Exception e){
                    return null;
                }
                st = new SottoIt(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5),cursor.getString(6),datain, datafin,cursor.getString(9),cursor.getString(10),cursor.getInt(11),cursor.getInt(12),cursor.getInt(13),cursor.getInt(14),cursor.getString(15),cursor.getString(16));

                // Adding contact to list
                sint.add(st);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sint;

    }

    public List<SottoIt> getDailySottoIntervento(String yesterDay, String toDay){
        ArrayList<SottoIt> sint = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.SOTTOIT.getNomeTabella() + " WHERE " + DBUtility.TABSOTTOIT.DATAINIZIO.getSQLColonnaInfo().getSQLColumnName() + " BETWEEN '" + yesterDay + "' AND '" + toDay  + "' ORDER BY " + DBUtility.TABSOTTOIT.DATAINIZIO.getSQLColonnaInfo().getSQLColumnName() + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if (cursor.moveToFirst()) {
            SottoIt st;
            do {
                Date datain;
                Date datafin;
                try{
                    datain = sdf.parse(cursor.getString(7));
                    datafin = sdf.parse(cursor.getString(8));
                }catch(Exception e){
                    return null;
                }
                st = new SottoIt(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getString(5),cursor.getString(6),datain, datafin,cursor.getString(9),cursor.getString(10),cursor.getInt(11),cursor.getInt(12),cursor.getInt(13),cursor.getInt(14),cursor.getString(15),cursor.getString(16));

                // Adding contact to list
                sint.add(st);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return sint;

    }

    public List<TipiIntervento> getAllTipoIntervento(){
        ArrayList<TipiIntervento> tipi = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBUtility.BIJECTIONTABNAME.TIPIINTERVENTO.getNomeTabella();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TipiIntervento tipo = new TipiIntervento(cursor.getInt(0), cursor.getString(1),cursor.getString(2), cursor.getInt(3));
                // Adding contact to list
                tipi.add(tipo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tipi;
    }
        /*END GETALL*/

        /*START UPDATE */
    public int updateArticolo(Articolo obj){
        String whereClause = "ID = " + obj.getId();
        SQLiteDatabase sqldb= this.getWritableDatabase();
        ContentValues cv= DBUtility.BIJECTIONTABNAME.ARTICOLI.ObjectToRow(obj);
        sqldb.update(DBUtility.BIJECTIONTABNAME.ARTICOLI.getNomeTabella(),cv,whereClause,null);
        sqldb.close();
        return 1;
    }

    public int updateCliente(Cliente obj){return 0;}

    public int updateIntervento(Intervento obj){
        String whereClause = "ID = " + obj.getId();
        SQLiteDatabase sqldb= this.getWritableDatabase();
        ContentValues cv= DBUtility.BIJECTIONTABNAME.INTERVENTI.ObjectToRow(obj);
        sqldb.update(DBUtility.BIJECTIONTABNAME.INTERVENTI.getNomeTabella(),cv,whereClause,null);
        sqldb.close();
        return 1;
    }

    public int updateSottoIntervento(SottoIt obj){
        String whereClause = "ID = " + obj.getId();
        SQLiteDatabase sqldb= this.getWritableDatabase();
        ContentValues cv= DBUtility.BIJECTIONTABNAME.SOTTOIT.ObjectToRow(obj);
        sqldb.update(DBUtility.BIJECTIONTABNAME.SOTTOIT.getNomeTabella(),cv,whereClause,null);
        sqldb.close();
        return 1;
    }

    public int updateTipoIntervento(TipiIntervento obj){return 0;}
        /*END UPDATE */
        /*START DELETE ALL*/
    public void deleteAllClienti(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBUtility.BIJECTIONTABNAME.CLIENTI.getNomeTabella(),null,null);
        db.close();
    }

    public void deleteAllTipiIntervento(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBUtility.BIJECTIONTABNAME.TIPIINTERVENTO.getNomeTabella(),null,null);
        db.close();
    }

    public void deleteAllInterventi(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBUtility.BIJECTIONTABNAME.INTERVENTI.getNomeTabella(),null,null);
        db.close();
    }

    public void deleteAllSottoit(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBUtility.BIJECTIONTABNAME.SOTTOIT.getNomeTabella(),null,null);
        db.close();
    }

    public void deleteAllArticoli(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBUtility.BIJECTIONTABNAME.ARTICOLI.getNomeTabella(),null,null);
        db.close();
    }
        /*END DELETE ALL* */
        /*START DELETE ALL*/
    public boolean deleteArticolo(Integer ID){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(DBUtility.BIJECTIONTABNAME.ARTICOLI.getNomeTabella(),"ID = " + ID,null);
        db.close();
        return res != 0;
    }

    public boolean deleteArticoli(Integer IDIt){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(DBUtility.BIJECTIONTABNAME.ARTICOLI.getNomeTabella(),"IDIT = " + IDIt,null);
        db.close();
        return res != 0;
    }

   public boolean deleteIntervento(Integer ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(DBUtility.BIJECTIONTABNAME.INTERVENTI.getNomeTabella(), "ID = " + ID, null);
        db.close();
        return res != 0;
    }

    public boolean deleteOperazione(Integer ID){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(DBUtility.BIJECTIONTABNAME.SOTTOIT.getNomeTabella(),"ID = " + ID,null);
        db.close();
        return res != 0;
    }

    public boolean deleteOperazioni(Integer IDIt){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(DBUtility.BIJECTIONTABNAME.SOTTOIT.getNomeTabella(),"IDIT = " + IDIt,null);
        db.close();
        return res != 0;
    }

    public void deleteCliente(Cliente obj){}

    public void deleteIntervento(Intervento obj){}

    public void deleteSottoIntervento(SottoIt obj){}

    public boolean deleteSottoIntervento(Integer ID){
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(DBUtility.BIJECTIONTABNAME.SOTTOIT.getNomeTabella(),"ID = " + ID,null);
        db.close();
        return res != 0;
    }

    public void deleteTipoIntervento(TipiIntervento obj){}
        /*END DELETE*/

        /*START ELEMENTS COUNT*/
    public int countArticolo(){return 0;}

    public int countCliente(){return 0;}

    public int countIntervento(){return 0;}

    public int countSottoIntervento(){return 0;}

    public int countTipoInterfvento(){return 0;}
        /*END ELEMENTS COUNT*/

    /*END CRUD*/

}
