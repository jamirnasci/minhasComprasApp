package com.jamir.compras.novacompra

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.jamir.compras.R
import com.jamir.compras.databinding.ActivityNovaCompraBinding
import com.jamir.compras.entities.ItemCompra
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class NovaCompra : AppCompatActivity() {
    private lateinit var binding: ActivityNovaCompraBinding

    companion object{
        var items: MutableList<ItemCompra> = mutableListOf()

        fun somarLista(): Double{
            var total: Double = 0.00
            NovaCompra.items.forEach { item ->
                total += item.preco * item.quantidade
            }
            return total
        }
        fun somarItens(): Int{
            var total: Int = 0
            NovaCompra.items.forEach { item ->
                total += item.quantidade
            }
            return total
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaCompraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formattedDate = currentDate.format(formatter)
        binding.dataView.text = formattedDate
        binding.totalItens.text = somarItens().toString()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.somaTotal.text = somarLista().toString()

        val adapter = ListaCompraAdapter(binding.somaTotal, binding.totalItens,this)
        binding.recyclerView.adapter = adapter

        binding.addItemBtn.setOnClickListener {
            val view = LayoutInflater.from(applicationContext).inflate(R.layout.edit_item_view, null)
            val nome = view.findViewById<EditText>(R.id.dialogNome)
            val preco = view.findViewById<EditText>(R.id.dialogPreco)
            val quantidade = view.findViewById<EditText>(R.id.dialogQuantidade)

            val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton("Adicionar"){ _, _ ->
                    items.add(
                        ItemCompra(
                        nome.text.toString(),
                        preco.text.toString().toDouble(),
                        quantidade.text.toString().toInt()
                    )
                    )
                    adapter.notifyDataSetChanged()
                    adapter.refreshLista()
                }
                .setNegativeButton("Cancelar", null)
            dialog.show()
        }
    }
}