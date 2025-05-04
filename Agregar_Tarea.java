package com.josemaria.proactivibe;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Agregar_Tarea extends AppCompatActivity {

    TextView uidUsuario, correoUsuario, fechaHoraActual, Fecha, Estado;
    EditText Titulo, Descripcion;
    Button btnCalendario, btnGuardar;
    int dia, mes, año;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference usuarios;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_tarea);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        btnCalendario = findViewById(R.id.Btn_Calendario);
        Fecha = findViewById(R.id.Fecha);
        Estado = findViewById(R.id.Estado);
        btnGuardar = findViewById(R.id.btnSave);
        uidUsuario = findViewById(R.id.Uid_Usuario);
        correoUsuario = findViewById(R.id.Correo_usuario);
        fechaHoraActual = findViewById(R.id.Fecha_hora_actual);
        Titulo = findViewById(R.id.Titulo);
        Descripcion = findViewById(R.id.Descripcion);
        //Morvese a la actividad anterior
        Toolbar toolbar = findViewById(R.id.tblAgregarTarea);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ObtenerDatos();

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario = Calendar.getInstance();

                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                año = calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Agregar_Tarea.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String diaFormateado, mesFormateado;

                        if (dayOfMonth < 10) {
                            diaFormateado = "0" + dayOfMonth;
                        } else {
                            diaFormateado = String.valueOf(dayOfMonth);
                        }

                        int Mes = month + 1;

                        if (Mes < 10) {
                            mesFormateado = "0" + Mes;
                        } else {
                            mesFormateado = String.valueOf(Mes);
                        }
                        int horaActual = calendario.get(Calendar.HOUR_OF_DAY);
                        int minutoActual = calendario.get(Calendar.MINUTE);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(Agregar_Tarea.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String horaFormateada = (hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay));
                                String minutoFormateado = (minute < 10 ? "0" + minute : String.valueOf(minute));

                                // Mostrar fecha y hora en el TextView
                                Fecha.setText(diaFormateado + "/" + mesFormateado + "/" + year + " " + horaFormateada + ":" + minutoFormateado);
                            }
                        }, horaActual, minutoActual, true);
                        timePickerDialog.show();
                    }
                }, año, mes, dia);
                datePickerDialog.show();
                
                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GuardarDatos();
                    }
                });
            }
            
        });        
    }

    private void GuardarDatos() {
        //Obtener identificación
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference newPostRef = database.child("posts").push();
        String postId = newPostRef.getKey();
        String uid = firebaseAuth.getUid();

        HashMap<String, String> Datos = new HashMap<>();
        Datos.put("idTarea", postId);
        Datos.put("uidUsuario", uid);
        Datos.put("Descripcion", String.valueOf(Descripcion.getText()));
        Datos.put("Titulo", String.valueOf(Titulo.getText()));
        Datos.put("Fecha", String.valueOf(Fecha.getText()));
        Datos.put("Estado", String.valueOf(Estado.getText()));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tareas");
        databaseReference.child(postId).setValue(Datos).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Agregar_Tarea.this, "Tarea creada con exito", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Agregar_Tarea.this, MenuPrincipal.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Agregar_Tarea.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ObtenerDatos() {
        usuarios.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Date actual = Calendar.getInstance().getTime();
                    String uidUsuarioS = ""+snapshot.child("uid").getValue();
                    String correoS = ""+snapshot.child("correo").getValue();
                    uidUsuario.setText(uidUsuarioS);
                    correoUsuario.setText(correoS);
                    fechaHoraActual.setText(actual.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}