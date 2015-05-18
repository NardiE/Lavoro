package com.example.edoardo.intrablet.database;


@SuppressWarnings("unused")

public interface SQLColonnaTabellabile {

    /**if projection in select use this before query to set data. MUST REFLECT ORDER IN QUERY AND SETTED EveryTIME */
    public void setProjectedPosition(int tempOrder);

    /**if projection select use this to retrive data*/
    public int getProjectedPosition();

    public int getColumnPosition();

    public String getSQLColumnName();

    public String getFieldType();

    public int getFieldLength();

    public boolean isPrimaryKey();
}
