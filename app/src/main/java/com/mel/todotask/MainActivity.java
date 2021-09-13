package com.mel.todotask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mel.todotask.TaskDatabase.TaskDatabase;
import com.mel.todotask.TaskDatabase.TaskDatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView taskListView;
    private FloatingActionButton floatingActionButton;
    private TaskDatabaseHelper taskDatabaseHelper;
    private ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDatabaseHelper = new TaskDatabaseHelper(this);
        taskListView = findViewById(R.id.taskList);
        floatingActionButton = findViewById(R.id.addFB);

        displayTask();
        addTask();
    }

    public void addTask(){

        floatingActionButton.setOnClickListener(view -> {

            EditText taskEdit = new EditText(MainActivity.this);
            AlertDialog alertDialogBuilder = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("New Task")
                    .setMessage("Add Your New Task")
                    .setView(taskEdit)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String task = taskEdit.getText().toString();
                            taskDatabaseHelper.insert(task);
                            displayTask();
                        }
                    })
                    .setNegativeButton("Cancel", null).show();
            alertDialogBuilder.show();
        });
    }

    public void displayTask() {
        ArrayList<String> taskList = new ArrayList<>();
        Cursor cursor = taskDatabaseHelper.displayTask();
        if (cursor.getCount() == 0){
            Toast.makeText(MainActivity.this, "Empty Database", Toast.LENGTH_SHORT).show();
        }
            while (cursor.moveToNext()) {
                int i = cursor.getColumnIndex(TaskDatabase.TaskTable.COl);
                taskList.add(cursor.getString(i));
            }
            if(arrayAdapter == null)
            {
                arrayAdapter = new ArrayAdapter<>(this, R.layout.task_check, R.id.taskView, taskList);
                taskListView.setAdapter(arrayAdapter);
            }
            else {
                arrayAdapter.clear();
                arrayAdapter.addAll(taskList);
                arrayAdapter.notifyDataSetChanged();
            }
            cursor.close();
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskView = (TextView) parent.findViewById(R.id.taskView);
        String task = taskView.getText().toString();
        taskDatabaseHelper.delete(task);
        displayTask();
    }
}

