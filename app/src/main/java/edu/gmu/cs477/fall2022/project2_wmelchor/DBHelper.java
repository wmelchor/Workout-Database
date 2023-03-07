package edu.gmu.cs477.fall2022.project2_wmelchor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class DBHelper extends SQLiteOpenHelper {

    final static String DBNAME = "exercise_database";
    final static String ID = "_id";
    final static String WORKOUT = "name";
    final static String REPS = "reps";
    final static String SETS = "sets";
    final static String WEIGHT = "weight";
    final static String NOTES = "notes";
    final private static String CREATE_CMD =
            "CREATE TABLE "+DBNAME+" (" + ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WORKOUT + " TEXT NOT NULL, "+
                    REPS + " Integer, " + SETS +
                    " Integer, " + WEIGHT + " Integer, "+
                    NOTES + " TEXT NOT NULL)";

    final private static Integer VERSION = 1;
    final private Context context;

    public DBHelper(Context context) {
        super(context, "exercise", null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
        ContentValues values = new ContentValues(5);
        values.put(WORKOUT, "Push Ups");
        values.put(REPS, 10);
        values.put(SETS, 5);
        values.put(WEIGHT, 0);
        values.put(NOTES, "Make sure to focus on good form");
        db.insert(DBNAME,null,values);
    }
    private static final String DATABASE_ALTER_TEAM_1 = "ALTER TABLE "
            + WORKOUT + " ADD COLUMN " + "string;";

    private static final String DATABASE_ALTER_TEAM_2 = "ALTER TABLE "
            + WORKOUT + " ADD COLUMN " + "string;";
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        if (oldVersion < 2) {
            db.execSQL(DATABASE_ALTER_TEAM_1);
        }
        if (oldVersion < 3) {
            db.execSQL(DATABASE_ALTER_TEAM_2);
        }
    }

    boolean deleteWorkout(SQLiteDatabase db, String name) {
        return db.delete(DBNAME, ID + "=?", new String[]{name}) > 0;
    }


    void deleteDatabase ( ) {
        context.deleteDatabase(DBNAME);
    }
}