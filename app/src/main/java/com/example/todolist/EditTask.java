package com.example.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
       String name = getIntent().getStringExtra("taskId");
       String taskName = getIntent().getStringExtra("taskName");
       String taskstatus = getIntent().getStringExtra("taskstatus");
       String taskContent = getIntent().getStringExtra("taskContent");

       edttitreAjout = findViewById(R.id.edttitreAjout);
       edtcontenuTache = findViewById(R.id.edtcontenuTache);
       modifierbtn = findViewById(R.id.modifierbtn);

       edttitreAjout.setText(taskName);
       edtcontenuTache.setText(taskContent);

        edtspinner_rond = findViewById(R.id.edtspinner_rond);
        adapter = new RondAdapter(EditTask.this, DataSpinner.getRondSpinnerList());
        edtspinner_rond.setAdapter(adapter);

        //if status
        // Select the correct spinner item based on task status
        int status = Integer.parseInt(taskstatus);
        selectSpinnerItemByStatus(status);

        modifierbtn.setOnClickListener(v -> {
            // Handle the modify button click
            String taskId = getIntent().getStringExtra("taskId");
            String newTitle = edttitreAjout.getText().toString();
            String newContent = edtcontenuTache.getText().toString();
            String newStatus = edtspinner_rond.getSelectedItem().toString();

            if (newTitle.isEmpty() || newContent.isEmpty() || newStatus.isEmpty()) {
                Toast.makeText(EditTask.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                boolean updated = new DbHelper(EditTask.this).updateTask(taskId, newTitle, newContent, newStatus);
                if (updated) {
                    Toast.makeText(EditTask.this, "Tâche modifiée avec succès", Toast.LENGTH_SHORT).show();
                    // Ajoutez ici toute logique supplémentaire après la mise à jour de la tâche, comme retourner à l'activité principale

                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_OK, resultIntent);

                } else {
                    Toast.makeText(EditTask.this, "Échec de la modification de la tâche", Toast.LENGTH_SHORT).show();
                }
            }


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