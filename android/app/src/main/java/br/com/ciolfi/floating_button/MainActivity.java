package br.com.felipecastrosales.floating_button;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.Screen;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

    private static final String CHANNEL = "floating_button";
    MethodChannel channel;

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        channel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //GeneratedPluginRegistrant.registerWith(this);
        //MethodChannel channel = new MethodChannel(getFlutterView(), CHANNEL);

        channel.setMethodCallHandler(
            (call, result) -> {
                switch (call.method){
                    case "show":
                        FloatWindow.get().show();
                        break;
                    case "hide":
                        FloatWindow.get().hide();
                        break;
                    case "create":
                        ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setImageResource(R.drawable.plus);

                        FloatWindow
                                .with(getApplicationContext())
                                .setView(imageView)
                                .setWidth(Screen.width, 0.15f)
                                .setHeight(Screen.width,0.15f)
                                .setX(Screen.width, 0.8f)
                                .setY(Screen.height, 0.3f)
                                .setDesktopShow(true)
                                .build();

                        imageView.setOnClickListener(v -> {
                            channel.invokeMethod("touch", null);
                            Toast.makeText(MainActivity.this, "+1", Toast.LENGTH_SHORT).show();
                        });
                        break;
                    case "isShowing":
                        result.success(FloatWindow.get().isShowing());
                        break;
                    default:
                        result.notImplemented();
                }
            }
        );
    }

    @Override
    protected void onDestroy() {
        FloatWindow.destroy();
        super.onDestroy();
    }
}