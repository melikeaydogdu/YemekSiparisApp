package com.company.yemeksiparisapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.yemeksiparisapp.data.model.SepetCevap
import com.company.yemeksiparisapp.data.model.SepetYemek
import com.company.yemeksiparisapp.data.repo.ApiUtils
import com.company.yemeksiparisapp.databinding.ActivitySepetBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SepetActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySepetBinding
    private var toplamTutar = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySepetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sepetOnaylaButton.setOnClickListener {
            Toast.makeText(this, "Siparişiniz alındı", Toast.LENGTH_SHORT).show()
        }

        sepetiYukle()
    }

    private fun sepetiYukle() {
        ApiUtils.getYemeklerDao().sepettekileriGetir("melike_aydogdu")
            .enqueue(object : Callback<SepetCevap> {
                override fun onResponse(call: Call<SepetCevap>, response: Response<SepetCevap>) {
                    if (response.isSuccessful) {
                        val liste = response.body()?.sepet_yemekler ?: emptyList()
                        hesaplaToplam(liste)

                        val adapter = SepetAdapter(liste.toMutableList()) {
                            hesaplaToplam(liste)
                        }
                        binding.sepetRecyclerView.layoutManager = LinearLayoutManager(this@SepetActivity)
                        binding.sepetRecyclerView.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<SepetCevap>, t: Throwable) {
                    Log.e("Sepet", "HATA: ${t.message}")
                }
            })
    }


    private fun hesaplaToplam(liste: List<SepetYemek>) {
        toplamTutar = 0
        for (item in liste) {
            val fiyat = item.yemek_fiyat.toIntOrNull() ?: 0
            val adet = item.yemek_siparis_adet.toIntOrNull() ?: 0
            toplamTutar += fiyat * adet
        }
        binding.toplamTutarText.text = "Toplam: ₺$toplamTutar"
    }


}
