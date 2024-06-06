package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.model.TaskModel;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_status.db";
    private static final int DATABASE_VERSION = 1;
    protected static final String TABLE_STATUS = "t_status";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_STATUS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titre TEXT, " +
                "contenu TEXT, " +
                "status TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int ii) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
        onCreate(sqLiteDatabase);
    }
    public Boolean insertData(String titreAjout, String contenuTache, String status ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("titre", titreAjout.trim());
        contentValues.put("contenu", contenuTache);
        contentValues.put("status", status);

        if (titreAjout == null || titreAjout.trim().isEmpty()) {
            return false;  // Titre invalide ajout échoué
        }



        long result = db.insert(TABLE_STATUS, null, contentValues);

        if (result == -1){
            return false;
        }else{
            return true;
        }
    }
    public TaskModel getTaskByID(String taskID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STATUS, null, "id=?", new String[]{taskID}, null, null, null);
        TaskModel task = null;
        if (cursor != null && cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex("titre"));
            String content = cursor.getString(cursor.getColumnIndex("contenu"));
            String status = cursor.getString(cursor.getColumnIndex("status"));
            task = new TaskModel(taskID, title, content, status);
            cursor.close();
        }
        db.close();
        return task;
    }

    public boolean updateTask(String taskID, String newTitle, String newContent, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titre", newTitle);
        values.put("contenu", newContent);
        values.put("status", newStatus);
        int rowsAffected = db.update(TABLE_STATUS, values, "id=?", new String[]{taskID});
        db.close();
        return rowsAffected > 0;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STATUS, null);
        return cursor;
    }

}
