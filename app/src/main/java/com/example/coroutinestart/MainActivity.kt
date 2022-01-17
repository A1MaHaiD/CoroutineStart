package com.example.coroutinestart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.coroutinestart.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnDownload.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        binding.progress.isVisible = true
        binding.btnDownload.isEnabled = false
        loadCity {
            binding.tvLocation.text = it
            loadTemperature(it) {
                binding.tvTemperature.text = it.toString()
                binding.progress.isVisible = false
                binding.progress.isEnabled = true
            }
        }

    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
            callback.invoke("Kyiv")
        }
    }

    private fun loadTemperature(city: String, callback: (Int) -> Unit) {
        thread {
            Toast.makeText(
                this,
                getString(R.string.loading, city),
                Toast.LENGTH_SHORT
            ).show()
            Thread.sleep(5000)
            callback.invoke(-4)
        }
    }
}