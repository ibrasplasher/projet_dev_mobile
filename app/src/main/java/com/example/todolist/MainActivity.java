package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.model.TaskModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView taskRv;
    ArrayList<TaskModel> dataList = new ArrayList<>();
    TaskListAdapter taskListAdapter;
    private Toolbar toolbar;

    DbHelper DB;
    ArrayList<String> titreAjout, ContenuTache;
    private static final int EDIT_TASK_REQUEST_CODE = 1;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);

        DB = new DbHelper(this);
        titreAjout = new ArrayList<>();
        ContenuTache = new ArrayList<>();


        taskRv = findViewById(R.id.taskListRv);

        taskListAdapter = new TaskListAdapter(this,dataList,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        taskRv.setLayoutManager(layoutManager);
        taskRv.setAdapter(taskListAdapter);
        displaydata();


        findViewById(R.id.addTaskFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddTaskActivity.class));
            }
        });


    }
    private void displaydata() {
        dataList.clear();
        ContenuTache.clear();
        Cursor cursor = DB.getAllData();
    if (cursor.getCount() == 0) {
        Toast.makeText(this, "aucune donnée disponible", Toast.LENGTH_SHORT).show();
        return;
    }
    else {
        while (cursor.moveToNext()) {
            String taskId = cursor.getString(cursor.getColumnIndex("id"));
            String taskName = cursor.getString(cursor.getColumnIndex("titre"));
            String taskStatus = cursor.getString(cursor.getColumnIndex("status"));
            String taskContent = cursor.getString(cursor.getColumnIndex("contenu"));

            // Crée un nouvel objet TaskModel avec les données récupérées
            TaskModel task = new TaskModel(taskId, taskStatus, taskName, taskContent);

            // Ajoute l'objet TaskModel à la liste dataList
            dataList.add(task);
        }
    }
        taskListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, EditTask.class);
        intent.putExtra("taskId", dataList.get(position).getTaskId());
        intent.putExtra("taskName", dataList.get(position).getTaskName());
        intent.putExtra("taskstatus", dataList.get(position).getTaskstatus());
        intent.putExtra("taskContent", dataList.get(position).getTaskContent());
        startActivityForResult(intent, EDIT_TASK_REQUEST_CODE);



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TASK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Mise à jour de la liste dataList
            displaydata();
        }
    }

}