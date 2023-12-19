package com.rahulrohit.mytestcart.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahulrohit.mytestcart.database.room.favorite.CartProductEntity
import com.rahulrohit.mytestcart.database.room.favorite.ProductEntity
import com.rahulrohit.mytestcart.databinding.ItemFavoriteBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var products: MutableList<CartProductEntity> = mutableListOf()
    private var onItemClick: ((CartProductEntity) -> Unit)? = null
    private var onDeleteClick: ((CartProductEntity) -> Unit)? = null

    fun setProducts(products: List<CartProductEntity>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(products[position])

        }
        holder.binding.deleteButton.setOnClickListener {
            onDeleteClick?.invoke(products[position])
            Toast.makeText(holder.itemView.context, "Item is Deleted", Toast.LENGTH_SHORT).show()
        }
    }
    fun setOnDeleteClickListener(listener: (CartProductEntity) -> Unit) {
        onDeleteClick = listener


    }
    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: CartProductEntity) {
            binding.productRate.text = product.rate.toString()
            binding.productCategory.text = product.category
            binding.productTitle.text = product.title
            binding.txtPrice.text = "$ ${product.price}"
            Glide.with(itemView.context).load(product.image).into(binding.imgProduct)
        }
    }
}
