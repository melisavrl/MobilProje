package com.example.melisaodev;

import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView loadingImage;
    private boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);

            loadingImage = findViewById(R.id.loadingImage);
            startRotationAnimation();
            
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkInternetConnection();
                }
            }, 5000);
            
        } catch (Exception e) {
            Log.e("MainActivity", "Hata: " + e.getMessage(), e);
            Toast.makeText(this, "Uygulama başlatılırken hata oluştu: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void startRotationAnimation() {
        try {
            RotateAnimation rotate = new RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            );
            rotate.setDuration(2000);
            rotate.setRepeatCount(Animation.INFINITE);
            loadingImage.startAnimation(rotate);
        } catch (Exception e) {
            Log.e("MainActivity", "Animasyon hatası: " + e.getMessage(), e);
        }
    }

    private void checkInternetConnection() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                android.net.Network network = connectivityManager.getActiveNetwork();
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                isConnected = capabilities != null && (
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                );
            } else {
                @SuppressWarnings("deprecation")
                android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                @SuppressWarnings("deprecation")
                boolean isConnectedDeprecated = networkInfo != null && networkInfo.isConnected();
                isConnected = isConnectedDeprecated;
            }

            if (isConnected) {
                Toast.makeText(this, "İnternet bağlantısı var!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, CategoryListActivity.class));
                finish();
            } else {
                Toast.makeText(this, "İnternet bağlantısı yok!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("MainActivity", "İnternet kontrolü hatası: " + e.getMessage(), e);
        }
    }
} 