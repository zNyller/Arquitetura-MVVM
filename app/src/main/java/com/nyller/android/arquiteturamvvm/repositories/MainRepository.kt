package com.nyller.android.arquiteturamvvm.repositories

import com.nyller.android.arquiteturamvvm.rest.RetrofitService

// Recebe uma instância de um RetrofitService
class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllLives() = retrofitService.getAllLives()

}