package com.josemaria.proactivibe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Registro extends AppCompatActivity {
    //Declaración de variables
    EditText nombreEt, correoEt, contraseñaEt, confirmarContraseñaEt;
    Button registro;
    TextView cuentaYaCreada;

    FirebaseAuth  firebaseAuth;
    ProgressDialog progressDialog;

    String nombre = " ", correo = " ", contraseña = "", confirmarContraseña = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Morvese a la actividad anterior
        Toolbar toolbar = findViewById(R.id.tblAgregarTarea);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registrar");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Relacionando variables con la interfaz
        nombreEt = findViewById(R.id.Nombre);
        correoEt = findViewById(R.id.correo);
        contraseñaEt = findViewById(R.id.contrasena);
        confirmarContraseñaEt = findViewById(R.id.confirmar_contrasena);
        registro = findViewById(R.id.btnRegistrar);
        cuentaYaCreada = findViewById(R.id.txtCuentaYaCreada);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Registro.this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });

        cuentaYaCreada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registro.this, Inicio_De_Sesion.class));
            }
        });
    }

    private void validarDatos(){
        nombre = nombreEt.getText().toString();
        correo = correoEt.getText().toString();
        contraseña = contraseñaEt.getText().toString();
        confirmarContraseña = confirmarContraseñaEt.getText().toString();
        
        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Ingrese correo", Toast.LENGTH_SHORT).show();            
        } else if (TextUtils.isEmpty(contraseña)) {
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();            
        } else if (TextUtils.isEmpty(confirmarContraseña)) {
            Toast.makeText(this, "Confirmar contraseña", Toast.LENGTH_SHORT).show();
        } else if (!contraseña.equals(confirmarContraseña)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        } else {
            crearCuenta();
        }
    }

    private void crearCuenta() {
        progressDialog.setMessage("Creando su cuenta...");
        progressDialog.show();

        //Creacion de usuario
        firebaseAuth.createUserWithEmailAndPassword(correo, contraseña).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                guardarDatos();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Registro.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarDatos() {
        progressDialog.setMessage("Guardando su información");
        progressDialog.dismiss();

        //Obtener identificación
        String uid = firebaseAuth.getUid();

        HashMap<String, String> Datos = new HashMap<>();
        Datos.put("uid", uid);
        Datos.put("nombres", nombre);
        Datos.put("correo", correo);
        Datos.put("contraseña", contraseña);
        Datos.put("tareascompletadas", "0");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(uid).setValue(Datos).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(Registro.this, "Cuenta creada con exito", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Registro.this, MenuPrincipal.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Registro.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}