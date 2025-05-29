package com.company.yemeksiparisapp.data.repo

import com.company.yemeksiparisapp.data.retrofit.YemeklerDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiUtils {
    companion object {
        private const val BASE_URL = "http://kasimadalan.pe.hu/"

        fun getYemeklerDao(): YemeklerDao {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(YemeklerDao::class.java)
        }
    }
}
