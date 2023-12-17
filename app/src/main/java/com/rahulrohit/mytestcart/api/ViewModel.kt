package com.rahulrohit.mytestcart.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel : ViewModel() {

    private val _womenProducts = MutableLiveData<List<ProductResponse>>()
    val womenProducts: LiveData<List<ProductResponse>> get() = _womenProducts

    private val _menProducts = MutableLiveData<List<ProductResponse>>()
    val menProducts: LiveData<List<ProductResponse>> get() = _menProducts

    private val _allProducts = MutableLiveData<List<ProductResponse>>()
    val allProducts: LiveData<List<ProductResponse>> get() = _allProducts

    private val _products = MutableLiveData<ProductResponse>()
    val products: LiveData<ProductResponse> get() = _products

    fun fetchWomenProducts() {
        val apiService = RetrofitClient.instance
        val call = apiService.getWomenProducts()

        call.enqueue(object : Callback<List<ProductResponse>> {
            override fun onResponse(call: Call<List<ProductResponse>>, response: Response<List<ProductResponse>>) {
                if (response.isSuccessful) {
                    _womenProducts.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
            }
        })
    }

    fun fetchMenProducts() {
        val apiService = RetrofitClient.instance
        val call = apiService.getMenProducts()

        call.enqueue(object : Callback<List<ProductResponse>> {
            override fun onResponse(call: Call<List<ProductResponse>>, response: Response<List<ProductResponse>>) {
                if (response.isSuccessful) {
                    _menProducts.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                // Handle failure if needed
            }
        })
    }

    fun fetchAllProducts() {
        val apiService = RetrofitClient.instance
        val call = apiService.getAllProducts()

        call.enqueue(object : Callback<List<ProductResponse>> {
            override fun onResponse(call: Call<List<ProductResponse>>, response: Response<List<ProductResponse>>) {
                if (response.isSuccessful) {
                    _allProducts.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                // Handle failure if needed
            }
        })
    }

    fun fetchProduct(productId: Int) {
        val apiService = RetrofitClient.instance
        val call = apiService.getProduct(productId)

        call.enqueue(object : Callback<ProductResponse> {

            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    _products.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                t.message
            }
        })
    }
}

