package edu.gmu.cs477.fall2022.project2_wmelchor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class EnterWorkoutInfo extends AppCompatActivity {

    private DBHelper databaseHelper = null;
    private SQLiteDatabase db = null;
    SimpleCursorAdapter myAdapter;
    Cursor mCursor;
    final static String[] all_columns = {DBHelper.ID, DBHelper.WORKOUT, DBHelper.REPS, DBHelper.SETS,
            DBHelper.WEIGHT, DBHelper.NOTES};
    final static String[] print_columns = {DBHelper.WORKOUT};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_workout_info);
    }
    public void addToWorkoutList(View v) {
        boolean allGood = true;
        databaseHelper = new DBHelper(this);
        EditText wName = (EditText)findViewById(R.id.inputWorkout);
        EditText wReps = (EditText)findViewById(R.id.inputReps);
        EditText wSets = (EditText)findViewById(R.id.inputSets);
        EditText wWeight = (EditText)findViewById(R.id.inputWeight);
        EditText wNotes = (EditText)findViewById(R.id.inputNotes);
        String workoutName = wName.getText().toString();
        String workoutReps = wReps.getText().toString();
        String workoutSets = wSets.getText().toString();
        String workoutWeight = wWeight.getText().toString();
        String workoutNotes = wNotes.getText().toString();
        if (workoutName.equals("")) {
            Toast.makeText(this, "Enter a workout", Toast.LENGTH_SHORT).show();
            allGood = false;
        }
        if (workoutReps.equals("")) {
            Toast.makeText(this, "Enter amount of reps", Toast.LENGTH_SHORT).show();
            allGood = false;
        }
        if (workoutSets.equals("")) {
            Toast.makeText(this, "Enter amount of sets", Toast.LENGTH_SHORT).show();
            allGood = false;
        }
        if (workoutWeight.equals("")) {
            workoutWeight = "0";
        }
        if (workoutNotes.equals("")) {
            workoutNotes = "";
        }
        db = databaseHelper.getWritableDatabase();
        /* This little section is to check if the workout already exists */
        mCursor = db.query(databaseHelper.DBNAME, all_columns, null, null, null, null,
                null);
        myAdapter = new SimpleCursorAdapter(this,
                R.layout.workout_info,
                mCursor,
                print_columns,
                new int[]{R.id.name}, 0);
        for(int i = 0; i < myAdapter.getCount(); i++) {
            mCursor.moveToPosition(i);
            if(mCursor.getString(1).equals(workoutName)) {
                Toast.makeText(this, "Workout already exists", Toast.LENGTH_SHORT).show();
                allGood = false;
            }
        }
        // If workout doesn't exist, continue inserting
        if(allGood) {
            ContentValues cv = new ContentValues(5);
            cv.put(databaseHelper.WORKOUT, workoutName);
            cv.put(databaseHelper.REPS, workoutReps);
            cv.put(databaseHelper.SETS, workoutSets);
            cv.put(databaseHelper.WEIGHT, workoutWeight);
            cv.put(databaseHelper.NOTES, workoutNotes);
            db.insert(databaseHelper.DBNAME, null, cv);
            finish();
        }
    }

    public void cancel(View v) {
        finish();
    }
}