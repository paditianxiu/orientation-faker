package me.padi.orientationfaker.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import me.padi.orientationfaker.control.Orientation
import me.padi.orientationfaker.control.OrientationController

class OrientationService : Service() {
    private lateinit var controller: OrientationController
    override fun onCreate() {
        super.onCreate()
        controller = OrientationController(this)
        startForegroundNotification()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val orientationValue = intent?.getIntExtra(EXTRA_ORIENTATION, Orientation.UNSPECIFIED.value)
            ?: Orientation.UNSPECIFIED.value
        val orientation = Orientation.of(orientationValue)

        controller.setOrientation(orientation)

        return START_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()
        controller.stop()
    }
    override fun onBind(intent: Intent?): IBinder? = null
    private fun startForegroundNotification() {
        val channelId = "orientation_service_channel"
        val channelName = "Orientation Control Service"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("屏幕方向控制中")
            .setContentText("服务正在运行...")
            .setSmallIcon(android.R.drawable.ic_menu_rotate) // 替换为你的图标
            .build()
        startForeground(1, notification)
    }
    companion object {
        private const val EXTRA_ORIENTATION = "extra_orientation"
        fun start(context: Context, orientation: Orientation) {
            val intent = Intent(context, OrientationService::class.java).apply {
                putExtra(EXTRA_ORIENTATION, orientation.value)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
        fun stop(context: Context) {
            val intent = Intent(context, OrientationService::class.java)
            context.stopService(intent)
        }
    }
}