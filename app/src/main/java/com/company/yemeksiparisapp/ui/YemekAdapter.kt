package com.company.yemeksiparisapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.yemeksiparisapp.R
import com.company.yemeksiparisapp.data.model.Yemek
import com.company.yemeksiparisapp.databinding.YemekCardBinding

class YemekAdapter(private val yemekList: List<Yemek>) :
    RecyclerView.Adapter<YemekAdapter.YemekViewHolder>() {

    inner class YemekViewHolder(val binding: YemekCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemekViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = YemekCardBinding.inflate(layoutInflater, parent, false)
        return YemekViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YemekViewHolder, position: Int) {
        val yemek = yemekList[position]
        val b = holder.binding

        b.yemekAdi.text = yemek.yemek_adi
        b.yemekFiyat.text = "${yemek.yemek_fiyat} ₺"

        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Glide.with(b.yemekResim.context)
            .load(imageUrl)
            .into(b.yemekResim)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("yemek", yemek)
            holder.itemView.context.startActivity(intent)
        }
        var isFavorite = false

        b.favoriIkon.setOnClickListener {
            isFavorite = !isFavorite
            val iconRes = if (isFavorite) R.drawable.favorite_white else R.drawable.favorite_white
            b.favoriIkon.setImageResource(iconRes)
        }

    }


    override fun getItemCount(): Int = yemekList.size
}
