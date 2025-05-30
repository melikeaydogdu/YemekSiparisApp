package com.company.yemeksiparisapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.company.yemeksiparisapp.data.model.Yemek
import com.company.yemeksiparisapp.databinding.ActivityDetailBinding
import com.company.yemeksiparisapp.util.FavoriteManager
import com.company.yemeksiparisapp.R
import com.company.yemeksiparisapp.data.model.SepetCevap
import com.company.yemeksiparisapp.data.repo.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var yemek: Yemek

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        yemek = intent.getSerializableExtra("yemek") as Yemek

        binding.yemekAdi.text = yemek.yemek_adi
        binding.yemekFiyat.text = "${yemek.yemek_fiyat} ₺"
        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Glide.with(this).load(imageUrl).into(binding.yemekResim)

        updateFavoriteIcon()

        binding.favoriteButton.setOnClickListener {
            FavoriteManager.toggleFavorite(yemek.yemek_adi)
            updateFavoriteIcon()
        }

        binding.backButton.setOnClickListener {
            finish()
        }
        setupAdetSecimi()
    }

    private fun updateFavoriteIcon() {
        val isFav = FavoriteManager.isFavorite(yemek.yemek_adi)
        val tintColor = if (isFav) getColor(R.color.purple_700) else getColor(android.R.color.darker_gray)
        binding.favoriteButton.setColorFilter(tintColor)
    }
    private var adet = 1

    private fun setupAdetSecimi() {
        binding.adetText.text = adet.toString()

        binding.azaltButton.setOnClickListener {
            if (adet > 1) {
                adet--
                binding.adetText.text = adet.toString()
            }
        }

        binding.arttirButton.setOnClickListener {
            adet++
            binding.adetText.text = adet.toString()
        }

        binding.sepeteEkleButton.setOnClickListener {
            val yemekAdi = yemek.yemek_adi
            val yemekResimAdi = yemek.yemek_resim_adi
            val yemekFiyat = yemek.yemek_fiyat.toIntOrNull() ?: 0

            ApiUtils.getYemeklerDao().sepeteYemekEkle(
                yemekAdi,
                yemekResimAdi,
                yemekFiyat,
                adet,
                "melike_aydogdu"
            ).enqueue(object : Callback<SepetCevap> {
                override fun onResponse(call: Call<SepetCevap>, response: Response<SepetCevap>) {
                    Toast.makeText(this@DetailActivity, "Sepete eklendi", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<SepetCevap>, t: Throwable) {
                    Toast.makeText(this@DetailActivity, "HATA: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}
