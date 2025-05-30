package com.company.yemeksiparisapp.util

object FavoriteManager {
    private val favoriteSet = mutableSetOf<String>()

    fun toggleFavorite(yemekAdi: String): Boolean {
        return if (favoriteSet.contains(yemekAdi)) {
            favoriteSet.remove(yemekAdi)
            false
        } else {
            favoriteSet.add(yemekAdi)
            true
        }
    }

    fun isFavorite(yemekAdi: String): Boolean {
        return favoriteSet.contains(yemekAdi)
    }

    fun getAllFavorites(): Set<String> {
        return favoriteSet
    }
}
