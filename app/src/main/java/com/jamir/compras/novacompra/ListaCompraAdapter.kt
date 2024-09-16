package com.jamir.compras.novacompra

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.jamir.compras.R

class ListaCompraAdapter(
    val somaTotal: TextView,
    val totalItens: TextView,
    val context: Context
) : RecyclerView.Adapter<ListaCompraAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nome: TextView = itemView.findViewById(R.id.nomeProduto)
        val precoTotalProduto: TextView = itemView.findViewById(R.id.precoTotalProduto)
        val quantidade: TextView = itemView.findViewById(R.id.quantidadeProduto)
        val editarItemBtn: ImageButton = itemView.findViewById(R.id.editarItemBtn)
        val precoUnidade: TextView = itemView.findViewById(R.id.precoUnidade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.lista_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return NovaCompra.items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = NovaCompra.items[position]

        holder.nome.text = item.nome
        holder.quantidade.text = item.quantidade.toString()
        holder.precoTotalProduto.text = (item.preco * item.quantidade).toString()
        holder.precoUnidade.text = item.preco.toString()

        holder.editarItemBtn.setOnClickListener {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.edit_item_view, null)

            val nome: EditText = dialogView.findViewById(R.id.dialogNome)
            val preco: EditText = dialogView.findViewById(R.id.dialogPreco)
            val quantidade: EditText = dialogView.findViewById(R.id.dialogQuantidade)

            nome.setText(item.nome)
            preco.setText(item.preco.toString())
            quantidade.setText(item.quantidade.toString())

            val dialog: AlertDialog = AlertDialog.Builder(context)
                .setView(dialogView)
                .setPositiveButton("Modificar") { _, _ ->
                    NovaCompra.items[position].nome = nome.text.toString()
                    NovaCompra.items[position].quantidade = quantidade.text.toString().toInt()
                    NovaCompra.items[position].preco = preco.text.toString().toDouble()
                    notifyDataSetChanged()
                    refreshLista()
                }
                .setNeutralButton("Remover"){_,_ ->
                    NovaCompra.items.removeAt(position)
                    notifyDataSetChanged()
                    refreshLista()
                }
                .setNegativeButton("Cancelar", null)
                .create()
            dialog.show()
        }
    }

    fun refreshLista(){
        somaTotal.text = String.format("%.2f", NovaCompra.somarLista())
        totalItens.text = NovaCompra.somarItens().toString()
    }
}