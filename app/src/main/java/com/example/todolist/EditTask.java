package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class EditTask extends AppCompatActivity {
    private Spinner edtspinner_rond;
    private RondAdapter adapter;

    EditText edttitreAjout;
    EditText edtcontenuTache;
    Button modifierbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_task2);
        ImageView filtreIv = findViewById(R.id.filtreIv);

        // Rendre l'ImageView invisible
        filtreIv.setVisibility(View.GONE);
        String taskId = getIntent().getStringExtra("taskId");
        String taskName = getIntent().getStringExtra("taskName");
        String taskstatus = getIntent().getStringExtra("taskstatus");
        String taskContent = getIntent().getStringExtra("taskContent");

        Log.d("EditTask", "ID: " + taskId + " Name: " + taskName + " Status: " + taskstatus + " Content: " + taskContent);

        if (taskId == null || taskName == null || taskstatus == null || taskContent == null) {
            Toast.makeText(this, "Erreur: Une des valeurs de la tâche est null", Toast.LENGTH_SHORT).show();
            finish(); // Fermer l'activité si les valeurs sont incorrectes
            return;
        }

        edttitreAjout = findViewById(R.id.edttitreAjout);
        edtcontenuTache = findViewById(R.id.edtcontenuTache);
        modifierbtn = findViewById(R.id.modifierbtn);

        edttitreAjout.setText(taskName);
        edtcontenuTache.setText(taskContent);

        edtspinner_rond = findViewById(R.id.edtspinner_rond);
        adapter = new RondAdapter(EditTask.this, DataSpinner.getRondSpinnerList());
        edtspinner_rond.setAdapter(adapter);

        try {
            int status = Integer.parseInt(taskstatus);
            selectSpinnerItemByStatus(status);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Statut de tâche invalide", Toast.LENGTH_SHORT).show();
        }

        modifierbtn.setOnClickListener(v -> {
            String newTitle = edttitreAjout.getText().toString();
            String newContent = edtcontenuTache.getText().toString();
            String newStatus = edtspinner_rond.getSelectedItem().toString();

            if (newTitle.isEmpty() || newContent.isEmpty() || newStatus.isEmpty()) {
                Toast.makeText(EditTask.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                boolean updated = new DbHelper(EditTask.this).updateTask(taskId, newTitle, newContent, newStatus);
                if (updated) {
                    Toast.makeText(EditTask.this, "Tâche modifiée avec succès", Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("taskId", taskId);
                    resultIntent.putExtra("taskName", newTitle);
                    resultIntent.putExtra("taskContent", newContent);
                    resultIntent.putExtra("taskStatus", newStatus);
                    resultIntent.putExtra("taskPosition", getIntent().getIntExtra("taskPosition", -1));
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish(); // Retourner à l'activité principale
                } else {
                    Toast.makeText(EditTask.this, "Échec de la modification de la tâche", Toast.LENGTH_SHORT).show();
                }
            }
        });


        FloatingActionButton returnTaskFAB = findViewById(R.id.returnFAB);
        returnTaskFAB.setOnClickListener(v -> {
            Intent intent = new Intent(EditTask.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

    }

    private void selectSpinnerItemByStatus(int status) {
        switch (status) {
            case 0: // Correspond à "In progress"
                edtspinner_rond.setSelection(0); // Sélectionne le premier élément (In progress)
                break;
            case 1: // Correspond à "Done"
                edtspinner_rond.setSelection(1); // Sélectionne le deuxième élément (Done)
                break;
            case 2: // Correspond à
                edtspinner_rond.setSelection(2); // Sélectionne le troisième élément (Todo)
                break;
            case 3: // Correspond à "Bug"
                edtspinner_rond.setSelection(3); // Sélectionne le quatrième élément (Bug)
                break;
            default:
                // Gestion d'une valeur de statut non prévue
                break;
        }
    }





}