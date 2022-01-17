package com.github.expo.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.expo.R
import com.github.expo.data.api.RetrofitInstance
import com.github.expo.data.repository.UserRepoRepository
import com.github.expo.databinding.ActivityUserRepositoryBinding
import com.github.expo.ui.main.adapter.RepositoryAdapter
import com.github.expo.ui.main.viewmodel.UserRepositoryViewModel
import com.github.expo.utils.Resource
import es.dmoral.toasty.Toasty

class UserRepository : AppCompatActivity() {
    lateinit var binding: ActivityUserRepositoryBinding
    private var username:String?=null
    private lateinit var vm: UserRepositoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserRepositoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = intent.getStringExtra("username").toString()
        binding.toolbarText.text = username + " Repository"

        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val instance = RetrofitInstance
        val repository = UserRepoRepository(this,instance)
        val factory = ViewModelFactory(repository)
        vm = ViewModelProvider(this,factory).get(UserRepositoryViewModel::class.java)
        vm.request(username!!)
        vm.response.observe(this, Observer {
            when(it){
                is Resource.loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.success ->{
                    binding.progressBar.visibility = View.INVISIBLE
                    it.data.let {
                       val adepter = RepositoryAdapter(this,it!!)
                        binding.recyclerView.adapter = adepter
                    }
                }
                is  Resource.error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toasty.error(this,it.message.toString(), Toasty.LENGTH_LONG).show()

                }
            }
        })



    }
    class ViewModelFactory(val repository:UserRepoRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserRepositoryViewModel::class.java)){
                return UserRepositoryViewModel(repository) as T
            }
            throw IllegalAccessException("Unknown class!")
        }
    }

    override fun onResume() {
        super.onResume()
        binding.progressBar.visibility = View.INVISIBLE

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }
}