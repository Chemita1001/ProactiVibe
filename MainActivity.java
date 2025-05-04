package com.josemaria.proactivibe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button Inicio_Sesion, Registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Inicio_Sesion = findViewById(R.id.Btn_Login);
        Registro = findViewById(R.id.Btn_Registro);
        Inicio_Sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iniciar_Sesion(v);
            }
        });
        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registro(v);
            }
        });
    }

    //Metodos
    public void Iniciar_Sesion(View v){
        startActivity(new Intent(MainActivity.this, Inicio_De_Sesion.class));
    }

    public void Registro(View v){
        startActivity(new Intent(MainActivity.this, Registro.class));
    }
}