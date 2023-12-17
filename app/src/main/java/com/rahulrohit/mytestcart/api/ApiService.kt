package com.rahulrohit.mytestcart.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("products/{id}")
    fun getProduct(@Path("id") id:Int) : Call<ProductResponse>

    @GET("products/category/women's clothing")
    fun getWomenProducts(): Call<List<ProductResponse>>

    @GET("products/category/men's clothing")
    fun getMenProducts(): Call<List<ProductResponse>>

    @GET("products")
    fun getAllProducts(): Call<List<ProductResponse>>
}

