package com.example.alpe;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificacionReceiver extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Aquí manejas el mensaje recibido
        if (remoteMessage.getNotification() != null) {
            // Mensaje de notificación
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            mostrarNotificacion(title, body);
        }

        // Aquí puedes manejar los datos adicionales del mensaje (si los hay)
        if (remoteMessage.getData().size() > 0) {
            // Datos adicionales
            String datosAdicionales = remoteMessage.getData().toString();
            // Manejar los datos según sea necesario
        }
    }
    private void mostrarNotificacion(String title, String body) {
        String channelId = "mi_canal_id";
        String channelName = "Mi Canal";

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher) // Icono de la notificación
                .setContentTitle(title) // Título de la notificación
                .setContentText(body) // Contenido de la notificación
                .setPriority(NotificationCompat.PRIORITY_HIGH); // Prioridad de la notificación

        notificationManager.notify(1, builder.build());
    }
}
