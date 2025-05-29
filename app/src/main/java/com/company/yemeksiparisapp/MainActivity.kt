package com.company.yemeksiparisapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.yemeksiparisapp.data.repo.ApiUtils
import com.company.yemeksiparisapp.data.model.YemeklerCevap
import com.company.yemeksiparisapp.data.retrofit.YemeklerDao
import com.company.yemeksiparisapp.databinding.ActivityMainBinding
import com.company.yemeksiparisapp.ui.YemekAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var yemeklerDao: YemeklerDao
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: YemekAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // 🔥 Eksik olan bu satır!

        yemeklerDao = ApiUtils.getYemeklerDao()

        yemeklerDao.tumYemekleriGetir().enqueue(object : Callback<YemeklerCevap> {
            override fun onResponse(
                call: Call<YemeklerCevap>,
                response: Response<YemeklerCevap>
            ) {
                if (response.isSuccessful) {
                    val yemekListesi = response.body()?.yemekler ?: emptyList()

                    yemekListesi.forEach {
                        Log.d("Yemek", "${it.yemek_adi} - ${it.yemek_fiyat}₺")
                    }

                    adapter = YemekAdapter(yemekListesi)
                    binding.yemeklerRecyclerView.adapter = adapter
                    binding.yemeklerRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }

            override fun onFailure(call: Call<YemeklerCevap>, t: Throwable) {
                Log.e("Retrofit", "HATA: ${t.message}")
            }
        })
    }
}
