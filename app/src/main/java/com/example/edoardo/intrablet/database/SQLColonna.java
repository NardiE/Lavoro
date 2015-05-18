package com.example.edoardo.intrablet.database;


@SuppressWarnings("unused")
public class SQLColonna implements SQLColonnaTabellabile {

    private final String _internalRep;
    private final int _declorder;
    private int tempOrder;
    private final DBUtility.SQLTYPE slqType;
    private final boolean ispk;
    private final int colLength;

    SQLColonna(String _internalRep, DBUtility.SQLTYPE sqlType,boolean isPk, int _declorder) {
        this._internalRep = _internalRep;
        this.slqType = sqlType;
        this._declorder = _declorder;
        this.ispk = isPk;
        this.tempOrder = _declorder;
        this.colLength = -1;
    }

    SQLColonna(String _internalRep, DBUtility.SQLTYPE sqlType,int colLength,boolean isPk ,int _declorder) {
        this._internalRep = _internalRep;
        this.slqType = sqlType;
        this._declorder = _declorder;
        this.ispk = isPk;
        this.tempOrder = _declorder;
        this.colLength = colLength;
    }

    @Override
    public void setProjectedPosition(int tempOrder) {this.tempOrder=tempOrder;}

    @Override
    public int getProjectedPosition() {{ return tempOrder;} }

    @Override
    public int getColumnPosition() {return _declorder;}

    @Override
    public String getSQLColumnName() {return _internalRep;}

    @Override
    public String getFieldType() {return this.slqType.getDeclType();}

    @Override
    public int getFieldLength() {return colLength;}

    @Override
    public boolean isPrimaryKey() {return this.ispk;}
}
