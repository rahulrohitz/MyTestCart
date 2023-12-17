package com.rahulrohit.mytestcart.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahulrohit.mytestcart.api.ProductResponse
import com.rahulrohit.mytestcart.databinding.ItemProductBinding


class HomeProductAdapter : RecyclerView.Adapter<HomeProductAdapter.ViewHolder>() {

    private var products: List<ProductResponse> = emptyList()
    private var onItemClick: ((ProductResponse) -> Unit)? = null

    fun setProducts(products: List<ProductResponse>) {
        this.products = products
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (ProductResponse) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(products[position])
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(product: ProductResponse) {
            binding.txtTitle.text = product.title
            binding.txtPrice.text = "$ ${product.price}"
            Glide.with(itemView.context).load(product.image).into(binding.imgProduct)
        }
    }
}