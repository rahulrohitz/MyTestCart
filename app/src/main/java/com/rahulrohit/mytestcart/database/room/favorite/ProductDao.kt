package com.rahulrohit.mytestcart.database.room.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun cartinsertProduct(product: CartProductEntity)

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM cart_product_table")
    fun getAllCartProducts(): LiveData<List<CartProductEntity>>

    @Query("DELETE FROM product_table WHERE id = :productId")
    suspend fun deleteProduct(productId: Int)

    @Query("DELETE FROM cart_product_table WHERE id = :productId")
    suspend fun deleteCartProduct(productId: Int)
}
