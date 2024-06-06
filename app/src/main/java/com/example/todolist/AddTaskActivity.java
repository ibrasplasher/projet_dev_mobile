package com.example.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;



public class AddTaskActivity extends AppCompatActivity {


  public  EditText titreAjout, contenuTache;
    Button add_task;
    DbHelper DB;




    private Spinner spinner_rond;
    private RondAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_task);

        titreAjout = findViewById(R.id.titreAjout);
        contenuTache = findViewById(R.id.contenuTache);
        add_task = findViewById(R.id.ajouterbtn);

        DB = new DbHelper(this);

        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titre = titreAjout.getText().toString();
                String contenu = contenuTache.getText().toString();
                String status = spinner_rond.getSelectedItem().toString(); // Récupère le statut depuis le Spinner
                if (titre.isEmpty()){Toast.makeText(AddTaskActivity.this, "veuillez entrer le titre de la tâche", Toast.LENGTH_SHORT).show();}
                else{
                Boolean checkinsertdata = DB.insertData(titre, contenu, status);
                if (checkinsertdata == true) {
                    Toast.makeText(AddTaskActivity.this, "Ajout Réussi", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddTaskActivity.this, "Erreur lors de l'Ajout ", Toast.LENGTH_SHORT).show();
                }
            }}
        });









        FloatingActionButton returnTaskFAB = findViewById(R.id.returnFAB);
        returnTaskFAB.setOnClickListener(v -> {
            Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        EdgeToEdge.enable(this);

        spinner_rond = findViewById(R.id.spinner_rond);
        adapter = new RondAdapter(AddTaskActivity.this, DataSpinner.getRondSpinnerList());
        spinner_rond.setAdapter(adapter);
    }
}