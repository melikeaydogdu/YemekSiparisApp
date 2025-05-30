package com.company.yemeksiparisapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.yemeksiparisapp.data.model.Yemek
import com.company.yemeksiparisapp.data.model.YemeklerCevap
import com.company.yemeksiparisapp.data.repo.ApiUtils
import com.company.yemeksiparisapp.data.retrofit.YemeklerDao
import com.company.yemeksiparisapp.databinding.ActivityMainBinding
import com.company.yemeksiparisapp.ui.FavorilerFragment
import com.company.yemeksiparisapp.ui.SepetActivity
import com.company.yemeksiparisapp.ui.YemekAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var yemeklerDao: YemeklerDao
    private lateinit var adapter: YemekAdapter
    private lateinit var fullList: List<Yemek>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        yemeklerDao = ApiUtils.getYemeklerDao()

        yemeklerDao.tumYemekleriGetir().enqueue(object : Callback<YemeklerCevap> {
            override fun onResponse(call: Call<YemeklerCevap>, response: Response<YemeklerCevap>) {
                if (response.isSuccessful) {
                    fullList = response.body()?.yemekler ?: emptyList()
                    adapter = YemekAdapter(fullList)
                    binding.yemeklerRecyclerView.layoutManager =
                        LinearLayoutManager(this@MainActivity)
                    binding.yemeklerRecyclerView.adapter = adapter


                    binding.searchView.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean = true

                        override fun onQueryTextChange(newText: String?): Boolean {
                            val filteredList = fullList.filter {
                                it.yemek_adi.contains(newText.orEmpty(), ignoreCase = true)
                            }
                            adapter = YemekAdapter(filteredList)
                            binding.yemeklerRecyclerView.adapter = adapter
                            return true
                        }
                    })
                }
            }

            override fun onFailure(call: Call<YemeklerCevap>, t: Throwable) {
                Log.e("Retrofit", "HATA: ${t.message}")
            }
        })
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    binding.fragmentContainerView.visibility = View.GONE
                    binding.yemeklerRecyclerView.visibility = View.VISIBLE
                    binding.searchView.visibility = View.VISIBLE
                    true
                }

                R.id.nav_fav -> {
                    binding.fragmentContainerView.visibility = View.VISIBLE
                    binding.yemeklerRecyclerView.visibility = View.GONE
                    binding.searchView.visibility = View.GONE

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, FavorilerFragment(fullList))
                        .addToBackStack(null)
                        .commit()
                    true
                }

                R.id.nav_cart -> {
                    startActivity(Intent(this, SepetActivity::class.java))
                    true
                }

                else -> false
            }
        }



        fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.main_menu, menu)
            return true
        }

        fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_sepet -> {
                    startActivity(Intent(this, SepetActivity::class.java))
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
        }


    }}
