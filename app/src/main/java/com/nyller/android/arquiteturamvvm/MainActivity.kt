package com.nyller.android.arquiteturamvvm

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nyller.android.arquiteturamvvm.adapters.MainAdapter
import com.nyller.android.arquiteturamvvm.databinding.ActivityMainBinding
import com.nyller.android.arquiteturamvvm.repositories.MainRepository
import com.nyller.android.arquiteturamvvm.rest.RetrofitService
import com.nyller.android.arquiteturamvvm.viewmodel.main.MainViewModel
import com.nyller.android.arquiteturamvvm.viewmodel.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: MainViewModel

    private val retrofitService = RetrofitService.getInstance()

    private val adapter = MainAdapter{
        openLink(it.link)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Criar a viewModel
        viewModel = ViewModelProvider(this, MainViewModelFactory(
            MainRepository(retrofitService))).get(MainViewModel::class.java)

        binding.recyclerview.adapter = adapter

    }

    override fun onStart() {
        super.onStart()

        // Iniciar os observers
        viewModel.liveList.observe(this, Observer { lives ->
            Log.i("tag", "onStart")
            adapter.setLiveList(lives)
        })

        viewModel.errorMessage.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

    }

    override fun onResume() {
        super.onResume()

        // Toda vez que o usuario retornar para a activity, vai ser executada
        // a requisição da lista novamente
        viewModel.getAllLives()

    }

    private fun openLink(link: String) { // Função de clique no Recycler

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)

    }

}