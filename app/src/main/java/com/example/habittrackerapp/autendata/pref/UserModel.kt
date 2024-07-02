package com.example.habittrackerapp.autendata.pref

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)
