package com.josemaria.proactivibe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Detalles_Actividad extends AppCompatActivity {

    TextView txtTitulo, txtDescripcion, txtFecha, txtEstado;
    Button btnTerminar, btnCancelar;
    String idTarea, uidUsuario;
    DatabaseReference tareasRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalles_actividad);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Morvese a la actividad anterior
        Toolbar toolbar = findViewById(R.id.tblDetallesActividad);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.txtDescripcionDa);
        txtFecha = findViewById(R.id.txtFechaDa);
        txtEstado = findViewById(R.id.txtEstadoDa);
        btnTerminar = findViewById(R.id.btnTerminar);
        btnCancelar = findViewById(R.id.btnCancelar);
        idTarea = getIntent().getStringExtra("idTarea");
        uidUsuario = getIntent().getStringExtra("uidUsuario");
        tareasRef = FirebaseDatabase.getInstance().getReference("Tareas").child(idTarea);

        // Recoger los datos enviados desde la lista
        String titulo = getIntent().getStringExtra("titulo");
        String descripcion = getIntent().getStringExtra("descripcion");
        String fecha = getIntent().getStringExtra("fecha");
        String estado = getIntent().getStringExtra("estado");

        txtTitulo.setText(titulo);
        txtDescripcion.setText(descripcion);
        txtFecha.setText(fecha);
        txtEstado.setText(estado);

        btnTerminar.setOnClickListener(view -> actualizarEstado("Terminado"));

        btnCancelar.setOnClickListener(view -> actualizarEstado("Cancelado"));
    }

    private void actualizarEstado(String nuevoEstado) {
        tareasRef.child("Estado").setValue(nuevoEstado).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (nuevoEstado.equals("Terminado")) {
                    sumarTareaCompletada(uidUsuario);
                }
                Toast.makeText(this, "Estado actualizado a " + nuevoEstado, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar estado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sumarTareaCompletada(String uidUsuario) {
        DatabaseReference usuarioRef = FirebaseDatabase.getInstance()
                .getReference("Usuarios")
                .child(uidUsuario)
                .child("tareascompletadas");

        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long tareasCompletadas = 0;
                if (snapshot.exists()) {
                    try {
                        tareasCompletadas = Long.parseLong(snapshot.getValue().toString());
                    } catch (NumberFormatException e) {
                        tareasCompletadas = 0;
                    }
                }

                // Sumar 1 y actualizar
                usuarioRef.setValue(tareasCompletadas + 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Detalles_Actividad.this, "No se pudo actualizar tareas completadas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}