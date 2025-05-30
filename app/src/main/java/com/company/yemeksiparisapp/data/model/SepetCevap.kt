package com.company.yemeksiparisapp.data.model

data class SepetCevap(
    val success: Int,
    val sepet_yemekler: List<SepetYemek>,
    val message: String
)
