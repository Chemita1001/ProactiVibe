package com.josemaria.proactivibe;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class Notificaciones extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String titulo = intent.getStringExtra("titulo");
        String descripcion = intent.getStringExtra("descripcion");

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String canalId = "canal_recordatorio";
        CharSequence nombre = "RecordatorioTarea";
        String descripcionCanal = "Notificaciones emergentes para recordatorios de tareas";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(
                    canalId, nombre, NotificationManager.IMPORTANCE_HIGH);
            canal.setDescription(descripcionCanal);
            canal.enableLights(true);
            canal.enableVibration(true);
            canal.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(canal);
        }

        NotificationCompat.Builder constructor = new NotificationCompat.Builder(context, canalId)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Cambia por tu ícono real
                .setContentTitle(titulo)
                .setContentText(descripcion)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Heads-up
                .setDefaults(NotificationCompat.DEFAULT_ALL)   // Luz, vibración, sonido
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setAutoCancel(true);

        notificationManager.notify((int) System.currentTimeMillis(), constructor.build());
    }
}

