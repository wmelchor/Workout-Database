package edu.gmu.cs477.fall2022.project2_wmelchor;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button doWorkout;
    Button editOrUpdateDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intentDoWorkout = new Intent(this, DoWorkout.class);
        doWorkout = (Button) findViewById(R.id.doWorkout);
        doWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(intentDoWorkout);
            }
        });
        Intent intentSetupWorkout = new Intent(this, SetupWorkout.class);
        editOrUpdateDB = (Button) findViewById(R.id.setupEdit);
        editOrUpdateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                someActivityResultLauncher.launch(intentSetupWorkout);
            }
        });
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
                    }
                }
            });
}