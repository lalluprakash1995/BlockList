package com.example.neo.blocklist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BlackListDAO {
    SQLiteDatabase database;
    DatabaseHelper dbHelper;
    public BlackListDAO(Context context){
        dbHelper=new DatabaseHelper(context);
        open();
    }
    private void open() throws SQLException {
        database=dbHelper.getWritableDatabase();

    }
    private void close(){
        dbHelper.close();
    }
    public BlackList create(final BlackList blackList){
        final ContentValues values=new ContentValues();
        values.put("phone_number",blackList.phoneNumber);
        final long id=database.insert(DatabaseHelper.TABLE_BLACKLIST,null,values);
        blackList.id=id;
        return blackList;
    }
    public void delete(final BlackList blackList){
        database.delete(DatabaseHelper.TABLE_BLACKLIST, "phone_number = '" + blackList.phoneNumber + "'", null);

    }
    public List<BlackList> getAllBlackList(){
        final List<BlackList> blackListNumbers=new ArrayList<BlackList>();
        final Cursor cursor=database.query(DatabaseHelper.TABLE_BLACKLIST,new String[]{"id","phone_number"}, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            final BlackList number=new BlackList();
            number.id=cursor.getLong(0);
            number.phoneNumber=cursor.getString(1);
            blackListNumbers.add(number);
            cursor.moveToNext();
        }
        return blackListNumbers;
    }
}

