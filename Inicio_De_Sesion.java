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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inicio_De_Sesion extends AppCompatActivity {

    EditText correoLogin, contraseñaLogin;
    Button btnLogin;
    TextView usuarioNuevoTxt;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    String correo = "", contraseña = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_de_sesion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Morvese a la actividad anterior
        Toolbar toolbar = findViewById(R.id.tblAgregarTarea);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Iniciar Sesión");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        correoLogin = findViewById(R.id.correoInicio);
        contraseñaLogin = findViewById(R.id.passLogin);
        btnLogin = findViewById(R.id.btnLogin);
        usuarioNuevoTxt = findViewById(R.id.usuarioNuevoTXT);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(Inicio_De_Sesion.this);
        progressDialog.setTitle("Espere");
        progressDialog.setCanceledOnTouchOutside(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidarDatos();
            }
        });

        usuarioNuevoTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inicio_De_Sesion.this, Registro.class));
            }
        });
    }

    private void ValidarDatos() {
        correo = correoLogin.getText().toString();
        contraseña = contraseñaLogin.getText().toString();

        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(contraseña)) {
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
        } else {
          InicioDeUsuario();
        }
    }

    private void InicioDeUsuario() {
        progressDialog.setMessage("Iniciando sesión");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(correo,contraseña)
                .addOnCompleteListener(Inicio_De_Sesion.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser usuario = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(Inicio_De_Sesion.this, MenuPrincipal.class));
                            Toast.makeText(Inicio_De_Sesion.this, "Bienvenido: " + usuario.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Inicio_De_Sesion.this, "Verifica si el usuario o la constraseña son correctos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Inicio_De_Sesion.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}