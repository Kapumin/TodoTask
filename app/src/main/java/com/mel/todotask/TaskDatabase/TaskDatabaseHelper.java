package com.mel.todotask.TaskDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TaskDatabaseHelper extends SQLiteOpenHelper {




    public TaskDatabaseHelper(@Nullable Context context) {
        super(context,TaskDatabase.db_name,null,TaskDatabase.db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE "+ TaskDatabase.TaskTable.table_name+"("+ TaskDatabase.TaskTable._ID+" INTEGER PRIMARY KEY " +
                " AUTOINCREMENT," + TaskDatabase.TaskTable.COl + " TEXT not null )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" drop table if exists "+ TaskDatabase.TaskTable.table_name);
        onCreate(sqLiteDatabase);
    }

    public void insert(String task){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskDatabase.TaskTable.COl,task);
        sqLiteDatabase.insert(TaskDatabase.TaskTable.table_name,null,contentValues);
        sqLiteDatabase.close();

    }

    public Cursor displayTask(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery(" select * from "+ TaskDatabase.TaskTable.table_name,null);
        return result;
    }


    public void delete(String task){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TaskDatabase.TaskTable.table_name, TaskDatabase.TaskTable.COl+ " = ?",new String[] {task});
        sqLiteDatabase.close();

    }

}




