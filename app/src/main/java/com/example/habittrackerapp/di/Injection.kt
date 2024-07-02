package com.example.habittrackerapp.di

import android.content.Context
import com.example.habittrackerapp.autendata.UserRepository
import com.example.habittrackerapp.autendata.pref.UserPreference
import com.example.habittrackerapp.autendata.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}