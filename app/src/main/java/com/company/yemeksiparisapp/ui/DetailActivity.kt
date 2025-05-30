package com.company.yemeksiparisapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.company.yemeksiparisapp.data.model.SepetCevap
import com.company.yemeksiparisapp.data.model.Yemek
import com.company.yemeksiparisapp.data.repo.ApiUtils
import com.company.yemeksiparisapp.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var adet = 1
    private lateinit var secilenYemek: Yemek

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        secilenYemek = intent.getSerializableExtra("yemek") as Yemek

        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${secilenYemek.yemek_resim_adi}"
        Glide.with(this).load(imageUrl).into(binding.yemekResim)

        binding.yemekAdi.text = secilenYemek.yemek_adi
        binding.yemekFiyat.text = "${secilenYemek.yemek_fiyat} ₺"
        binding.adetText.text = adet.toString()

        binding.arttirButton.setOnClickListener {
            adet++
            binding.adetText.text = adet.toString()
        }

        binding.azaltButton.setOnClickListener {
            if (adet > 1) {
                adet--
                binding.adetText.text = adet.toString()
            }
        }

        binding.sepeteEkleButton.setOnClickListener {
            val yemekAdi = secilenYemek.yemek_adi
            val resimAdi = secilenYemek.yemek_resim_adi
            val fiyat = secilenYemek.yemek_fiyat.toIntOrNull() ?: 0
            val kullaniciAdi = "melike_aydogdu"

            ApiUtils.getYemeklerDao().sepeteYemekEkle(
                yemekAdi,
                resimAdi,
                fiyat,
                adet,
                kullaniciAdi
            ).enqueue(object : Callback<SepetCevap> {
                override fun onResponse(call: Call<SepetCevap>, response: Response<SepetCevap>) {
                    if (response.isSuccessful) {
                        val cevap = response.body()
                        Log.d("Sepet", "Başarılı: ${cevap?.message}")
                        Toast.makeText(this@DetailActivity, "Sepete eklendi", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<SepetCevap>, t: Throwable) {
                    Log.e("Sepet", "HATA: ${t.message}")
                    Toast.makeText(this@DetailActivity, "Sepete eklenemedi", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
