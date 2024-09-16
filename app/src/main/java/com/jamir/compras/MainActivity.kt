package com.jamir.compras

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jamir.compras.databinding.ActivityMainBinding
import com.jamir.compras.novacompra.NovaCompra

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.novasComprasBtn.setOnClickListener {
            val novaCompraIntent = Intent(applicationContext, NovaCompra::class.java)
            startActivity(novaCompraIntent)
        }
    }
}