package com.josemaria.proactivibe;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Logros extends AppCompatActivity {
    ArrayList<String> estados= new ArrayList<>();
    ArrayList<String> idsTareas = new ArrayList<>();
    DatabaseReference myDatabase;
    ImageView primeraTarea, tareaCompletada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logros);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Morvese a la actividad anterior
        Toolbar toolbar = findViewById(R.id.tblLogros);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        myDatabase = FirebaseDatabase.getInstance().getReference("Tareas");
        String uidActual = FirebaseAuth.getInstance().getCurrentUser().getUid();
        tareaCompletada = findViewById(R.id.imgPrimeraTareaComp);
        primeraTarea = findViewById(R.id.imgPrimeraTareaCreada);
        listarTareasPorUsuario(uidActual);
        tareaCompletada.setOnClickListener(v -> {
            if (estados.isEmpty()) {
                new AlertDialog.Builder(Logros.this)
                        .setTitle("Logro bloqueado")
                        .setMessage("Completa al menos una tarea para desbloquear este logro.")
                        .setPositiveButton("Entendido", null)
                        .show();
            }
        });

        primeraTarea.setOnClickListener(v -> {
            if (idsTareas.isEmpty()) {
                new AlertDialog.Builder(Logros.this)
                        .setTitle("Logro bloqueado")
                        .setMessage("Crea tu primera tarea para desbloquear este logro.")
                        .setPositiveButton("Entendido", null)
                        .show();
            }
        });
    }
    public void listarTareasPorUsuario(String uidActual) {
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                estados.clear();
                idsTareas.clear();

                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String uidUsuario = "" + snapshot1.child("uidUsuario").getValue();
                        String estadoF = "" + snapshot1.child("Estado").getValue();

                        if (uidUsuario.equals(uidActual)) {
                            String idTarea = "" + snapshot1.child("idTarea").getValue();
                            idsTareas.add(idTarea);

                            if (estadoF.equalsIgnoreCase("Terminado")) {
                                estados.add(estadoF);
                            }
                        }
                    }
                    if (!estados.isEmpty()) {
                        tareaCompletada.setImageResource(R.drawable.emblemaprimeratareacomprgb);
                    }

                    if (!idsTareas.isEmpty()) {
                        primeraTarea.setImageResource(R.drawable.emblematarea1rgb);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Log or handle the error
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}