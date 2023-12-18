package com.rahulrohit.mytestcart.database.room.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val productDao: ProductDao = ProductDatabase.getDatabase(application).productDao()

     fun getAllProducts(): LiveData<List<ProductEntity>> {
        return productDao.getAllProducts()
    }

    fun deleteProduct(productId: Int) {
        GlobalScope.launch {
            productDao.deleteProduct(productId)
        }
    }

}