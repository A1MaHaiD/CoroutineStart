package com.example.coroutinestart

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.webkit.ValueCallback
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestart.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnDownload.setOnClickListener {
            lifecycleScope.launch {
                loadWithoutCoroutine()
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

    private fun loadWithoutCoroutine(step: Int = 0, obj: Any? = null) {
        when (step) {
            0 -> {
                Log.d("MainActivity", "LoadData started: $this")
                binding.progress.isVisible = true
                binding.btnDownload.isEnabled = false
                loadCityWithoutCoroutine {
                    loadWithoutCoroutine(1, it)
                }
            }
            1 -> {
                val city = obj as String
                binding.tvLocation.text = city
                loadTemperatureWithoutCoroutine(city) {
                    loadWithoutCoroutine(2, it)
                }
            }

            2 -> {
                val temperature = obj as Int
                binding.tvTemperature.text = temperature.toString()
                binding.progress.isVisible = false
                binding.btnDownload.isEnabled = true
                Log.d("MainActivity", "LoadData finished: $this")
            }
        }
    }

    private fun loadCityWithoutCoroutine(callback: (String) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            callback.invoke("Kyiv")
        }, 5000)
    }

    private fun loadTemperatureWithoutCoroutine(city: String, callback: (Int) -> Unit) {
            Toast.makeText(
                this,
                getString(R.string.loading, city),
                Toast.LENGTH_SHORT
            ).show()
        Handler(Looper.getMainLooper()).postDelayed({
            callback.invoke(-4)
        }, 5000)
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