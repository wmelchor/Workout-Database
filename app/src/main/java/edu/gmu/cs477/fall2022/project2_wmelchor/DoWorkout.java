package edu.gmu.cs477.fall2022.project2_wmelchor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DoWorkout extends AppCompatActivity {
    private DBHelper databaseHelper = null;
    private SQLiteDatabase db = null;
    ListView listView;
    ListView myList;
    ArrayAdapter arrAdapter;
    SimpleCursorAdapter myAdapter;
    Cursor mCursor;

    final static String[] all_columns = {DBHelper.ID, DBHelper.WORKOUT, DBHelper.REPS, DBHelper.SETS,
                                            DBHelper.WEIGHT, DBHelper.NOTES};
    final static String[] print_columns = {DBHelper.WORKOUT};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_workout);
        myList = (ListView) findViewById(R.id.workoutList);
        arrAdapter = new ArrayAdapter<String>(this, R.layout.line);
        databaseHelper = new DBHelper(this);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get correct name from array adaptor
                String name = arrAdapter.getItem(position).toString();
                // Find first instance of that name in the sql list and gather data from that
                for(int i = 0; i < myAdapter.getCount(); i++) {
                    mCursor.moveToPosition(i);
                    if(mCursor.getString(1).equals(name)) {
                        break;
                    }
                }
                String reps = mCursor.getString(2);
                String sets = mCursor.getString(3);
                String weight = mCursor.getString(4);
                String notes = mCursor.getString(5);
                String message = name + "\n" + "Reps: " + reps + " Sets: " + sets + " Weight: " + weight
                        + " Notes: " + notes;
                Toast.makeText(DoWorkout.this,  message, Toast.LENGTH_SHORT).show();
            }
        });
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertView("Single Item Deletion",position);
                return true;
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
        for(int i = 0; i < myAdapter.getCount(); i++) {
            mCursor.moveToPosition(i);
            arrAdapter.add(myAdapter.getCursor().getString(1));
        }
        myList.setAdapter(arrAdapter);
    }

    public void onPause() {
        super.onPause();
        if (db != null) db.close();
    }

    private void alertView(String message, final int position ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(DoWorkout.this);
        dialog.setTitle( message )
                .setIcon(R.drawable.ic_launcher_background)
                .setMessage("Are you sure you want to do this?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }})
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        arrAdapter.remove(arrAdapter.getItem(position));
                    }
                }).show();
    }
}