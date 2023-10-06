package dev.panwar.a7minutesworkout.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import dev.panwar.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        handleThemeSwitch()

        binding?.flStart?.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        binding?.flBMI?.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

        binding?.flHistory?.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun handleThemeSwitch() {
        val sharedPref = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        val nightMode = sharedPref.getBoolean("nightMode", false)
        var editor: SharedPreferences.Editor

        if (nightMode) {
            binding?.themeSwitch?.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        binding?.themeSwitch?.setOnClickListener {
            if (nightMode) {
                binding?.themeSwitch?.isChecked = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPref.edit()
                editor.putBoolean("nightMode", false)
            } else {
                binding?.themeSwitch?.isChecked = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPref.edit()
                editor.putBoolean("nightMode", true)
            }
            editor.apply()
        }
    }


    // must Create this method ...this is important to prevent memory leak after binding is used
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
