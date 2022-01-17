package com.github.expo.ui.main.view

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.util.Pair
import com.github.expo.R
import com.github.expo.data.api.RetrofitInstance
import com.github.expo.data.model.UserProfileModel
import com.github.expo.data.repository.UserProfileRepository
import com.github.expo.databinding.ActivityHomeBinding
import com.github.expo.ui.main.viewmodel.HomeViewModel
import com.github.expo.utils.Resource
import com.google.gson.Gson
import es.dmoral.toasty.Toasty

class Home : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var vm: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = UserProfileRepository(this, RetrofitInstance)
        val factory = ViewModelFactory(repository)
        vm = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        vm.response.observe(this, Observer { res ->

            when (res) {

                is Resource.loading -> {
                    binding.search.text = null
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.error -> {
                    binding.search.text = "Search"
                    binding.progressBar.visibility = View.INVISIBLE
                    Toasty.error(this@Home,res.message.toString(),Toasty.LENGTH_LONG).show()
                }
                is Resource.success -> {
                    binding.search.text = "Search"
                    binding.progressBar.visibility = View.INVISIBLE
                    res.data.let {
                        sharedAnimation(it!!)
                    }
                }

            }
        })


        binding.search.setOnClickListener {
            binding.inputUsername.clearFocus();

            vm.request(binding.inputUsername.text.toString())
        }


    }

    fun sharedAnimation(data: UserProfileModel) {
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this,
            Pair<View, String>(binding.buttonSection, "buttonTransition")
        )
        val intent = Intent(this, Profile::class.java)
        val json = Gson().toJson(data)
        intent.putExtra("data", json)
        startActivity(intent,options.toBundle())

    }


    class ViewModelFactory(private val repository: UserProfileRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(repository) as T
            }
            throw IllegalAccessException("Unknowns class!")
        }
    }
}