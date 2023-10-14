package dev.panwar.a7minutesworkout.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import dev.panwar.a7minutesworkout.R


class SplashScreen : AppCompatActivity() {
        private val splashTimeOut: Long = 3000 // 3 seconds

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_sp_screen)

            val sharedPref = getSharedPreferences("MODE", Context.MODE_PRIVATE)
            val nightMode = sharedPref.getBoolean("nightMode", false)

            if (nightMode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

            // Add any animation code here
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, splashTimeOut)

            val splashImageView = findViewById<ImageView>(R.id.splashImageView)
            splashImageView.alpha = 0f
            splashImageView.animate().setDuration(1500).alpha(1f).withEndAction {
                // Animation complete
            }

        }
    }
