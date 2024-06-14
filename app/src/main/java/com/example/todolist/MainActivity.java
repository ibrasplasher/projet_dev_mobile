package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.model.TaskModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView taskRv;
    ArrayList<TaskModel> dataList = new ArrayList<>();
    ArrayList<TaskModel> completeDataList = new ArrayList<>();

    TaskListAdapter taskListAdapter;
    private Toolbar toolbar;
    private PopupWindow popupWindow;
    private ImageView filtreIv;

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
        taskListAdapter = new TaskListAdapter(this, dataList,completeDataList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        taskRv.setLayoutManager(layoutManager);
        taskRv.setAdapter(taskListAdapter);
        displaydata();

        findViewById(R.id.addTaskFAB).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddTaskActivity.class)));

        filtreIv = findViewById(R.id.filtreIv);
        filtreIv.setOnClickListener(v -> showPopupWindow(v));

        //taskListAdapter.resetList();
    }

    private void showPopupWindow(View anchorView) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.fragment_menu, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupView.setBackgroundResource(R.drawable.popup_border);

        CheckBox checkboxItem1 = popupView.findViewById(R.id.filtreTodo);
        CheckBox checkboxItem2 = popupView.findViewById(R.id.filtreInProgress);
        CheckBox checkboxItem3 = popupView.findViewById(R.id.filtreDone);
        CheckBox checkboxItem4 = popupView.findViewById(R.id.filtreBug);
        Button buttonApply = popupView.findViewById(R.id.button_apply);

        buttonApply.setOnClickListener(v -> {
            ArrayList<String> selectedStatuses = new ArrayList<>();
            if (checkboxItem1.isChecked()) {
                selectedStatuses.add("2"); // Ajouter le statut Todo
            }
            if (checkboxItem2.isChecked()) {
                selectedStatuses.add("0"); // Ajouter le statut In progress
            }
            if (checkboxItem3.isChecked()) {
                selectedStatuses.add("1"); // Ajouter le statut Done
            }
            if (checkboxItem4.isChecked()) {
                selectedStatuses.add("3"); // Ajouter le statut Bug
            }

            filtrerTaches(selectedStatuses);

            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        });


        // Afficher la PopupWindow en dessous de l'anchorView avec le décalage spécifié
        popupWindow.showAsDropDown(anchorView, 0, 0, Gravity.BOTTOM);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void displaydata() {
        dataList.clear();
        completeDataList.clear();
        ContenuTache.clear();
        Cursor cursor = DB.getAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "aucune donnée disponible", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                String taskId = cursor.getString(cursor.getColumnIndex("id"));
                String taskName = cursor.getString(cursor.getColumnIndex("titre"));
                String taskStatus = cursor.getString(cursor.getColumnIndex("status"));
                String taskContent = cursor.getString(cursor.getColumnIndex("contenu"));

                // Log les valeurs pour débogage
                Log.d("DisplayData", "ID: " + taskId + " Name: " + taskName + " Status: " + taskStatus + " Content: " + taskContent);

                // Vérifiez que les valeurs ne sont pas nulles
                if (taskId == null || taskName == null || taskStatus == null || taskContent == null) {
                    Log.e("DisplayData", "Une des valeurs de la tâche est null");
                    continue; // on va ignorer si la valeur est nulle
                }

                TaskModel task = new TaskModel(taskId, taskStatus, taskName, taskContent);
                dataList.add(task);
                completeDataList.add(task);
            }
        }
        taskListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int position) {
        if (position < 0 || position >= dataList.size()) {
            showToast("Index de tâche invalide");
            return;
        }

        TaskModel task = taskListAdapter.getTaskAt(position);

        // Vérifier les valeurs avant de les passer à l'intent
        if (task.getTaskId() == null || task.getTaskName() == null || task.getTaskstatus() == null || task.getTaskContent() == null) {
            showToast("Une des valeurs de la tâche est null");
            return;
        }

        Log.d("OnItemClick", "ID: " + task.getTaskId() + " Name: " + task.getTaskName() + " Status: " + task.getTaskstatus() + " Content: " + task.getTaskContent());

        Intent intent = new Intent(MainActivity.this, EditTask.class);
        intent.putExtra("taskId", task.getTaskId());
        intent.putExtra("taskName", task.getTaskName());
        intent.putExtra("taskstatus", task.getTaskstatus());
        intent.putExtra("taskContent", task.getTaskContent());
        startActivityForResult(intent, EDIT_TASK_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TASK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String taskId = data.getStringExtra("taskId");
                String newTitle = data.getStringExtra("taskName");
                String newContent = data.getStringExtra("taskContent");
                String newStatus = data.getStringExtra("taskStatus");
                int taskPosition = data.getIntExtra("taskPosition", -1);

                updateTaskInCompleteDataList(taskId, newTitle, newContent, newStatus);


                if (taskPosition != -1) {
                    TaskModel updatedTask = new TaskModel(taskId, newStatus, newTitle, newContent);
                    taskListAdapter.updateTask(taskPosition, updatedTask);
                    filtrerTaches(getCurrentFilters());
                }
            }
        }
    }
    private ArrayList<String> getCurrentFilters() {
        // Retourner la liste des statuts actuellement filtrés

        return new ArrayList<>();
    }


    private void filtrerTaches(ArrayList<String> statuts) {
        if (statuts.isEmpty()) {
            // Si aucun filtre n'est sélectionné, restaurer la liste complète
            taskListAdapter.setDataList(completeDataList);
        } else {
            ArrayList<TaskModel> filteredList = new ArrayList<>();
            for (TaskModel task : completeDataList) {
                if (statuts.contains(task.getTaskstatus())) {
                    filteredList.add(task);
                }
            }
            taskListAdapter.setDataList(filteredList);
        }
    }

    public void updateTaskInCompleteDataList(String taskId, String newTitle, String newContent, String newStatus) {
        for (TaskModel task : completeDataList) {
            if (task.getTaskId().equals(taskId)) {
                task.setTaskName(newTitle);
                task.setTaskContent(newContent);
                task.setTaskstatus(newStatus);
                taskListAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

}
