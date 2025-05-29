package com.company.yemeksiparisapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.company.yemeksiparisapp.R
import com.company.yemeksiparisapp.data.model.Yemek
import com.company.yemeksiparisapp.databinding.ActivityDetailBinding

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

        binding.yemekAdi.text = secilenYemek.yemek_adi
        binding.yemekFiyat.text = "${secilenYemek.yemek_fiyat} ₺"
        Glide.with(this).load(imageUrl).into(binding.yemekResim)

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
            // Buraya sepete ekleme POST fonksiyonu gelecek
        }
    }
}
