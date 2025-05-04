package com.josemaria.proactivibe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

public class Listar_Tareas extends AppCompatActivity {
    ArrayList<String> titulos = new ArrayList<>();
    ArrayList<String> descripciones = new ArrayList<>();
    ArrayList<String> fechas= new ArrayList<>();
    ArrayList<String> estados= new ArrayList<>();
    ArrayList<String> idsTareas = new ArrayList<>();
    ArrayList<String> uidsTareas = new ArrayList<>();
    ListView lstTareas;
    DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_tareas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Morvese a la actividad anterior
        Toolbar toolbar = findViewById(R.id.tlbListarTareas);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        myDatabase = FirebaseDatabase.getInstance().getReference("Tareas");
        String uidActual = FirebaseAuth.getInstance().getCurrentUser().getUid();
        lstTareas = findViewById(R.id.lstTareas);
        listarTareasPorUsuario(uidActual);
        lstTareas.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(Listar_Tareas.this, Detalles_Actividad.class);
            intent.putExtra("titulo", titulos.get(position));
            intent.putExtra("descripcion", descripciones.get(position));
            intent.putExtra("fecha", fechas.get(position));
            intent.putExtra("estado", estados.get(position));
            intent.putExtra("idTarea", idsTareas.get(position));
            intent.putExtra("uidUsuario", uidsTareas.get(position));
            titulos.clear();
            descripciones.clear();
            fechas.clear();
            estados.clear();
            idsTareas.clear();
            startActivity(intent);
        });
    }

    public View crearFilaPersonalizada(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = getLayoutInflater();
        View miFila = inflater.inflate(R.layout.fila_lista, parent, false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView titulo = miFila.findViewById(R.id.txtTitulo);
        titulo.setText(titulos.get(position));
        TextView descripcion = miFila.findViewById(R.id.txtDescripcion);
        descripcion.setText(descripciones.get(position));
        TextView fecha = miFila.findViewById(R.id.txtFecha);
        fecha.setText(fechas.get(position));
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView estado = miFila.findViewById(R.id.txtEstado);
        estado.setText(estados.get(position));
        return miFila;
    }

    public class AdaptadorPersonalizado extends ArrayAdapter<String> {
        public AdaptadorPersonalizado(Context ctx, int txtViewResourceId, ArrayList<String> objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override
        public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return crearFilaPersonalizada(pos, cnvtView, prnt);
        }

    }
    public void listarTareasPorUsuario(String uidActual) {
        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String uidUsuario = "" + snapshot1.child("uidUsuario").getValue();
                        String estadoF = "" + snapshot1.child("Estado").getValue();
                        if (uidUsuario.equals(uidActual) && !estadoF.equalsIgnoreCase("Terminado") &&
                                !estadoF.equalsIgnoreCase("Cancelado")) {
                            String titulo = (""+snapshot1.child("Titulo").getValue());
                            String descripcion = (""+snapshot1.child("Descripcion").getValue());
                            String fecha = (""+snapshot1.child("Fecha").getValue());
                            String estado = (""+snapshot1.child("Estado").getValue());
                            String idTarea = (""+snapshot1.child("idTarea").getValue());
                            String uidTareas = ("" + snapshot1.child("uidUsuario").getValue());
                            titulos.add(titulo);
                            descripciones.add(descripcion);
                            fechas.add(fecha);
                            estados.add(estado);
                            idsTareas.add(idTarea);
                            uidsTareas.add(uidTareas);
                        }
                    }
                    AdaptadorPersonalizado a=new AdaptadorPersonalizado(Listar_Tareas.this, R.layout.fila_lista, titulos);
                    lstTareas.setAdapter(a);
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
