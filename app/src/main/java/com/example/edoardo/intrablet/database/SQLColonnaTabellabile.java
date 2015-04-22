package com.example.edoardo.intrablet.database;

/**
 * Created by Giacomo on 13/11/2014.
 * If you need some explanation write down a mail to
 * giacomo.maestrelli@gmail.com
 */
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
