package com.josemaria.proactivibe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuPrincipal extends AppCompatActivity {
    Button CerrarSesion, agregarTarea, listarTareas, logros;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView nombrePrincipal, correoPrincipal;
    ProgressBar progressBarDatos;

    DatabaseReference usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nombrePrincipal = findViewById(R.id.NombrePrincipal);
        correoPrincipal = findViewById(R.id.CorreoPrincipal);
        progressBarDatos = findViewById(R.id.progressBarDatos);

        usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");
        CerrarSesion = findViewById(R.id.CerrarSesion);
        agregarTarea = findViewById(R.id.agregarTarea);
        listarTareas = findViewById(R.id.tusTareas);
        logros = findViewById(R.id.logros);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        agregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarTarea();
            }
        });
        listarTareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarTareas();
            }
        });
        logros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logros();
            }
        });

        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalirAplicacion();
            }
        });
    }

    @Override
    protected void onStart() {
        ComprobarInicioSesion();
        super.onStart();
    }

    private void ComprobarInicioSesion(){
        if(user!=null){
            CargarDatos();
        }else{
            startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
        }
    }

    private void CargarDatos(){
        usuarios.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    progressBarDatos.setVisibility(View.GONE);
                    nombrePrincipal.setVisibility(View.VISIBLE);
                    correoPrincipal.setVisibility(View.VISIBLE);

                    String nombre = ""+snapshot.child("nombres").getValue();
                    String correo = ""+snapshot.child("correo").getValue();

                    nombrePrincipal.setText(nombre);
                    correoPrincipal.setText(correo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SalirAplicacion() {
        firebaseAuth.signOut();
        startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
        Toast.makeText(this, "Cerraste sesion de manera exitosa", Toast.LENGTH_SHORT).show();
    }

    private void AgregarTarea() {
        startActivity(new Intent(MenuPrincipal.this, Agregar_Tarea.class));
    }

    private void listarTareas() {
        startActivity(new Intent(MenuPrincipal.this, Listar_Tareas.class));
    }

    private void Logros() {
        startActivity(new Intent(MenuPrincipal.this, Logros.class));
    }
}