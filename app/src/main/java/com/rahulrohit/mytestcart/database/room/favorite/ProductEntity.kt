package com.rahulrohit.mytestcart.database.room.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val aid:Long=0,
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val image: String,
    val rate: Double,
    var isFavorite: Boolean = false
)