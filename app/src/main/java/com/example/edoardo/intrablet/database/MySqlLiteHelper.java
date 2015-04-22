package com.example.edoardo.intrablet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.edoardo.intrablet.status.Stato;

import java.util.List;

/**
 * Helper per interagire con Database Palmari.
 */
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
        ContentValues cv= DBUtility.BIJECTIONTABNAME.ARTICOLI.ObjectToRow(obj);
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

    public void addTipoIntervento(TipoIntervento obj){

        ContentValues cv= DBUtility.BIJECTIONTABNAME.TIPIINTERVENTO.ObjectToRow(obj);
        SQLiteDatabase sqldb= this.getWritableDatabase();
        sqldb.insert(DBUtility.BIJECTIONTABNAME.TIPIINTERVENTO.getNomeTabella(),null,cv);
        sqldb.close();
    }
        /*END ADD*/

        /*START GET*/
    public Articolo getArticolo(int _id){
        /*forse più interessante modificare il tipo di richiesta in hw/sw*/
        /**/
        return null;
    }

    public Cliente getCliente(int _id, int ID){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                DBUtility.BIJECTIONTABNAME.CLIENTI.name(),
                new String[] { DBUtility.TABCLIENTI.ID.name(), DBUtility.TABCLIENTI.CODICE.name(), DBUtility.TABCLIENTI.CODICEHW.name(), DBUtility.TABCLIENTI.CODICESW.name(), DBUtility.TABCLIENTI.RAGIONESOCIALE.name()},
                DBUtility.TABCLIENTI.ID + "=?",
                new String[] { String.valueOf(ID) },
                null, null, null, null
        );

        if (cursor != null)
            cursor.moveToFirst();

        Cliente contact = new Cliente(_id, cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getInt(0));

        return contact;

    }

    public Intervento getIntervento(int _id){
        return null;
    }

    public SottoIt getSottoIntervento(int _id){
        return null;
    }

    public TipoIntervento getTipoIntervento(int _id){
        return null;
    }
        /*END GET*/

        /*START GETALL*/
    public List<Articolo> getAllArticolo(){return null;}

    public List<Cliente> getAllCliente(){return null;}

    public Cursor getAllIntervento(Stato st){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crs=  db.query(DBUtility.BIJECTIONTABNAME.INTERVENTI.getNomeTabella(), null,null,null,null,null,null);
        return crs;
    }

    public List<SottoIt> getAllSottoIntervento(){return null;}

    public List<TipoIntervento> getAllTipoIntervento(){return null;}
        /*END GETALL*/

        /*START UPDATE */
    public int updateArticolo(Articolo obj){return 0;}

    public int updateCliente(Cliente obj){return 0;}

    public int updateIntervento(Intervento obj){return 0;}

    public int updateSottoIntervento(SottoIt obj){return 0;}

    public int updateTipoIntervento(TipoIntervento obj){return 0;}
        /*END UPDATE */

        /*START DELETE*/
    public void deleteArticolo(Articolo obj){}

    public void deleteCliente(Cliente obj){}

    public void deleteIntervento(Intervento obj){}

    public void deleteSottoIntervento(SottoIt obj){}

    public void deleteTipoIntervento(TipoIntervento obj){}
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
