package com.company.yemeksiparisapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.yemeksiparisapp.data.model.SepetCevap
import com.company.yemeksiparisapp.data.model.SepetYemek
import com.company.yemeksiparisapp.data.repo.ApiUtils
import com.company.yemeksiparisapp.databinding.SepetCardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SepetAdapter(
    private var sepetList: MutableList<SepetYemek>,
    private val onSepetDegisti: () -> Unit
) : RecyclerView.Adapter<SepetAdapter.SepetViewHolder>() {

    inner class SepetViewHolder(val binding: SepetCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SepetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SepetCardBinding.inflate(inflater, parent, false)
        return SepetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SepetViewHolder, position: Int) {
        val item = sepetList[position]
        val b = holder.binding

        b.sepetYemekAdi.text = item.yemek_adi
        b.sepetYemekAdet.text = "${item.yemek_siparis_adet} adet"
        b.sepetYemekFiyat.text = "${item.yemek_fiyat} ₺"

        val imageUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${item.yemek_resim_adi}"
        Glide.with(b.sepetYemekResim.context).load(imageUrl).into(b.sepetYemekResim)

        b.sepetSilButton.setOnClickListener {
            val context = b.root.context

            ApiUtils.getYemeklerDao().sepettenSil(item.sepet_yemek_id.toInt(), "melike_aydogdu")
                .enqueue(object : Callback<SepetCevap> {
                    override fun onResponse(call: Call<SepetCevap>, response: Response<SepetCevap>) {
                        val yemekAdi = item.yemek_adi
                        Toast.makeText(context, "$yemekAdi silindi", Toast.LENGTH_SHORT).show()

                        sepetList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, sepetList.size)

                        onSepetDegisti()
                    }

                    override fun onFailure(call: Call<SepetCevap>, t: Throwable) {
                        Toast.makeText(context, "Silinemedi", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    override fun getItemCount(): Int = sepetList.size
}
