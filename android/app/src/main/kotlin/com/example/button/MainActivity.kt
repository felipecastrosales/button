package com.felipecastrosales.button

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import com.yhao.floatwindow.FloatWindow
import com.yhao.floatwindow.Screen
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {

    companion object {
        private const val CHANNEL = "floating_button"
    }

    private lateinit var channel: MethodChannel

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        channel.setMethodCallHandler { call, result ->
            when (call.method) {
                "show" -> FloatWindow.get().show()
                "hide" -> FloatWindow.get().hide()
                "create" -> {
                    val imageView = ImageView(applicationContext)
                    imageView.setImageResource(R.drawable.plus)

                    FloatWindow
                        .with(applicationContext)
                        .setView(imageView)
                        .setWidth(Screen.width, 0.15f)
                        .setHeight(Screen.width, 0.15f)
                        .setX(Screen.width, 0.8f)
                        .setY(Screen.height, 0.3f)
                        .setDesktopShow(true)
                        .build()

                    imageView.setOnClickListener {
                        channel.invokeMethod("touch", null)
                        Toast.makeText(this@MainActivity, "+1", Toast.LENGTH_SHORT).show()
                    }
                }
                "isShowing" -> result.success(FloatWindow.get().isShowing())
                else -> result.notImplemented()
            }
        }
    }

    override fun onDestroy() {
        FloatWindow.destroy()
        super.onDestroy()
    }
}
