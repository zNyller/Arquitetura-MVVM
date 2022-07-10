package com.nyller.android.arquiteturamvvm.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nyller.android.arquiteturamvvm.models.Live
import com.nyller.android.arquiteturamvvm.repositories.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val liveList = MutableLiveData<List<Live>>()
    val errorMessage = MutableLiveData<String>()
    /* Como o LiveData respeita o LifeCycle do Android, ele não invocará seu retorno
    de chamada do observador, a menos que a activity ou fragment tenha passado pelo
    onStart() e não tenha passado pelo onStop();
    Além disso, o LiveData também removerá automaticamente o observador quando seu
    host receber o onDestroy();
     */

    fun getAllLives(){

        val request = repository.getAllLives()
        // Executar o request e passar o callback(executa algo quando termina)
        request.enqueue(object : Callback<List<Live>>{
            override fun onResponse(call: Call<List<Live>>, response: Response<List<Live>>) {
                // Quando houver uma resposta da chamada
                liveList.postValue(response.body())

            }

            override fun onFailure(call: Call<List<Live>>, t: Throwable) {
                // Quando houver uma falha na chamada
                errorMessage.postValue(t.message)
            }

        })
    }

}