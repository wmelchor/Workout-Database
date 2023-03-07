package edu.gmu.cs477.fall2022.project2_wmelchor;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SetupWorkout extends AppCompatActivity {
    Button addToWorkout;
    private DBHelper databaseHelper = null;
    private SQLiteDatabase db = null;
    ListView listView;
    SimpleCursorAdapter myAdapter;
    Cursor mCursor;
    String newWorkoutName;
    String newRepAmount;
    String newSetAmount;
    String newWeightAmount;
    String newNotes;
    String rowId;
    final static String[] all_columns = {DBHelper.ID, DBHelper.WORKOUT, DBHelper.REPS, DBHelper.SETS,
            DBHelper.WEIGHT, DBHelper.NOTES};
    final static String[] print_columns = {DBHelper.WORKOUT};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_workout);
        listView = (ListView) findViewById(R.id.workoutList);
        databaseHelper = new DBHelper(this);
        Intent intentEditWorkout = new Intent(this, EditWorkoutInfo.class);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rowId = mCursor.getString(0);
                String wName = mCursor.getString(1);
                String wReps = mCursor.getString(2);
                String wSets = mCursor.getString(3);
                String wWeight = mCursor.getString(4);
                String wNotes = mCursor.getString(5);
                intentEditWorkout.putExtra("WorkoutName", wName);
                intentEditWorkout.putExtra("WorkoutReps", wReps);
                intentEditWorkout.putExtra("WorkoutSets", wSets);
                intentEditWorkout.putExtra("WorkoutWeight", wWeight);
                intentEditWorkout.putExtra("WorkoutNotes", wNotes);
                someActivityResultLauncher.launch(intentEditWorkout);
            }
        });
        Intent intentAddToWorkout = new Intent(this, EnterWorkoutInfo.class);
        addToWorkout = (Button) findViewById(R.id.add);
        addToWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(intentAddToWorkout);
                finish();
            }
        });
    }

    public void onResume() {
        super.onResume();
        db = databaseHelper.getWritableDatabase();

        mCursor = db.query(databaseHelper.DBNAME, all_columns, null, null, null, null,
                null);
        myAdapter = new SimpleCursorAdapter(this,
                R.layout.workout_info,
                mCursor,
                print_columns,
                new int[]{R.id.name}, 0);
        listView.setAdapter(myAdapter);
    }

    public void onPause() {
        super.onPause();
        if (db != null) db.close();
    }

    ActivityResultLauncher<Intent>
            someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() ==
                            Activity.RESULT_OK) {
                        Intent data = result.getData();
                        newWorkoutName = data.getStringExtra("NewWorkoutName");
                        newRepAmount = data.getStringExtra("NewRepAmount");;
                        newSetAmount = data.getStringExtra("NewSetAmount");;
                        newWeightAmount = data.getStringExtra("NewWeightAmount");;
                        newNotes = data.getStringExtra("NewNotes");
                        db = databaseHelper.getWritableDatabase();
                        ContentValues cv = new ContentValues(5);
                        cv.put(databaseHelper.WORKOUT,newWorkoutName);
                        cv.put(databaseHelper.REPS, newRepAmount);
                        cv.put(databaseHelper.SETS, newSetAmount);
                        cv.put(databaseHelper.WEIGHT, newWeightAmount);
                        cv.put(databaseHelper.NOTES, newNotes);
                        db.update(databaseHelper.DBNAME, cv, databaseHelper.WORKOUT + "=?",
                                    new String[] {newWorkoutName});
                    }
                }
            });
}