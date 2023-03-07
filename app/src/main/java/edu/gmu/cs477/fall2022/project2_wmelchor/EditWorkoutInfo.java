package edu.gmu.cs477.fall2022.project2_wmelchor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditWorkoutInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout_info);
        TextView workoutInput = (TextView) findViewById(R.id.inputWorkout);
        EditText repsInput = (EditText) findViewById(R.id.inputReps);
        EditText setsInput = (EditText) findViewById(R.id.inputSets);
        EditText weightInput = (EditText) findViewById(R.id.inputWeight);
        EditText notesInput = (EditText) findViewById(R.id.inputNotes);
        setTitle("Update Workout");
        Intent intent = getIntent();
        String workoutName = intent.getStringExtra("WorkoutName");
        String workoutReps = intent.getStringExtra("WorkoutReps");
        String workoutSets = intent.getStringExtra("WorkoutSets");
        String workoutWeight = intent.getStringExtra("WorkoutWeight");
        String workoutNotes = intent.getStringExtra("WorkoutNotes");
        workoutInput.setText(workoutName);
        repsInput.setHint(workoutReps);
        setsInput.setHint(workoutSets);
        weightInput.setHint(workoutWeight);
        notesInput.setHint(workoutNotes);
    }
    public void updateInfo(View v) {
        EditText repsInput = (EditText) findViewById(R.id.inputReps);
        EditText setsInput = (EditText) findViewById(R.id.inputSets);
        EditText weightInput = (EditText) findViewById(R.id.inputWeight);
        EditText notesInput = (EditText) findViewById(R.id.inputNotes);
        Intent intent = getIntent();
        String workoutName = intent.getStringExtra("WorkoutName");
        String workoutReps = intent.getStringExtra("WorkoutReps");
        String workoutSets = intent.getStringExtra("WorkoutSets");
        String workoutWeight = intent.getStringExtra("WorkoutWeight");
        String workoutNotes = intent.getStringExtra("WorkoutNotes");
        String newWorkoutName;
        String newRepAmount;
        String newSetAmount;
        String newWeightAmount;
        String newNotes;
        newWorkoutName = workoutName;
        if(!repsInput.getText().toString().equals("")){
            newRepAmount = repsInput.getText().toString();
        } else {
            newRepAmount = workoutReps;
        }
        if(!setsInput.getText().toString().equals("")){
            newSetAmount = setsInput.getText().toString();
        } else {
            newSetAmount = workoutSets;
        }
        if(!weightInput.getText().toString().equals("")){
            newWeightAmount = weightInput.getText().toString();
        } else {
            newWeightAmount = workoutWeight;
        }
        if(!notesInput.getText().toString().equals("")){
            newNotes = notesInput.getText().toString();
        } else {
            newNotes = workoutNotes;
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra("NewWorkoutName", newWorkoutName);
        resultIntent.putExtra("NewRepAmount", newRepAmount);
        resultIntent.putExtra("NewSetAmount", newSetAmount);
        resultIntent.putExtra("NewWeightAmount", newWeightAmount);
        resultIntent.putExtra("NewNotes", newNotes);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    public void cancel(View v) {
        finish();
    }
}