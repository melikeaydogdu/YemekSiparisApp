package com.company.yemeksiparisapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.yemeksiparisapp.databinding.FragmentFavorilerBinding
import com.company.yemeksiparisapp.util.FavoriteManager
import com.company.yemeksiparisapp.data.model.Yemek

class FavorilerFragment(private val fullList: List<Yemek>) : Fragment() {

    private lateinit var binding: FragmentFavorilerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavorilerBinding.inflate(inflater, container, false)

        val favList = fullList.filter {
            FavoriteManager.isFavorite(it.yemek_adi)
        }

        val adapter = YemekAdapter(favList)
        binding.favoriRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favoriRecyclerView.adapter = adapter

        return binding.root
    }
}
