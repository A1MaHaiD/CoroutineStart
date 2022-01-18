package com.example.coroutinestart

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestart.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnDownload.setOnClickListener {
            lifecycleScope.launch {
                loadData()
            }
        }
    }

    private suspend fun loadData() {
        Log.d("MainActivity", "LoadData started: $this")
        binding.progress.isVisible = true
        binding.btnDownload.isEnabled = false
        val city = loadCity()
        binding.tvLocation.text = city
        val temperature = loadTemperature(city)
        binding.tvTemperature.text = temperature.toString()
        binding.progress.isVisible = false
        binding.btnDownload.isEnabled = true
        Log.d("MainActivity", "LoadData finished: $this")
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Kyiv"
    }

    private suspend fun loadTemperature(city: String): Int {
        Toast.makeText(
            this,
            getString(R.string.loading, city),
            Toast.LENGTH_SHORT
        ).show()
        delay(5000)
        return -4
    }
}