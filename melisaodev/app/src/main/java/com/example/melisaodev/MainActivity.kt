package com.example.melisaodev

import android.content.Intent
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var loadingImage: ImageView
    private var isConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_main)

            loadingImage = findViewById(R.id.loadingImage)
            startRotationAnimation()
            
            // 5 saniye sonra internet kontrolü yap
            Handler(Looper.getMainLooper()).postDelayed({
                checkInternetConnection()
            }, 5000)
            
        } catch (e: Exception) {
            Log.e("MainActivity", "Hata: ${e.message}", e)
            Toast.makeText(this, "Uygulama başlatılırken hata oluştu: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun startRotationAnimation() {
        try {
            val rotate = RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            )
            rotate.duration = 2000 // 2 saniye
            rotate.repeatCount = Animation.INFINITE
            loadingImage.startAnimation(rotate)
        } catch (e: Exception) {
            Log.e("MainActivity", "Animasyon hatası: ${e.message}", e)
        }
    }

    private fun checkInternetConnection() {
        try {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork
                val capabilities = connectivityManager.getNetworkCapabilities(network)
                isConnected = capabilities != null && (
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                )
            } else {
                @Suppress("DEPRECATION")
                val networkInfo = connectivityManager.activeNetworkInfo
                @Suppress("DEPRECATION")
                isConnected = networkInfo != null && networkInfo.isConnected
            }

            if (isConnected) {
                Toast.makeText(this, "İnternet bağlantısı var!", Toast.LENGTH_LONG).show()
                // Liste ekranına geçiş yap
                startActivity(Intent(this, CategoryListActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "İnternet bağlantısı yok!", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "İnternet kontrolü hatası: ${e.message}", e)
        }
    }
} 