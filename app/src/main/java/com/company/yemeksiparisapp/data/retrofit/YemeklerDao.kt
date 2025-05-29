package com.company.yemeksiparisapp.data.retrofit

import com.company.yemeksiparisapp.data.model.YemeklerCevap
import retrofit2.Call
import retrofit2.http.GET

interface YemeklerDao {
    @GET("yemekler/tumYemekleriGetir.php")
    fun tumYemekleriGetir(): Call<YemeklerCevap>
}
