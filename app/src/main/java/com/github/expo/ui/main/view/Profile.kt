package com.github.expo.ui.main.view

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.expo.R
import com.github.expo.data.model.UserProfileModel
import com.github.expo.databinding.ActivityProfileBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Profile : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val json = Gson().fromJson<UserProfileModel>(
            intent.getStringExtra("data").toString(),
            UserProfileModel::class.java
        )

        binding.fulName.text = json.name
        binding.publicRepos.text = json.public_repos.toString()
        binding.gists.text = json.public_gists.toString()
        binding.followers.text = json.followers.toString() + " People"
        binding.following.text = json.following.toString() + " People"
        binding.bio.text = json.bio
        Glide.with(this).load(json.avatar_url).into(binding.profileImage)
        binding.location.text = if (json.location != null) json.location else "Not found"
        binding.email.text = if (json.email != null) json.email.toString() else "Email is Private"
        binding.company.text = if (json.company != null) json.company.toString() else "Not found"
        binding.hireable.text =
            if (json.hireable != null) if (json.hireable == true) "Yes" else "No" else "No information"

        if (json.public_repos == 0) {
            binding.repository.text = "No repository found"
            binding.repository.isEnabled = false
            val sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                binding.repository.setBackgroundDrawable(resources.getDrawable(R.drawable.bg_gray));
            } else {
                binding.repository.setBackground(getResources().getDrawable(R.drawable.bg_gray));
            }
        }

        binding.repository.setOnClickListener {
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair<View, String>(binding.relativeLayout, "buttonTransition")
            )

            val intent = Intent(this@Profile, UserRepository::class.java)
            intent.putExtra("username", json.login)
            startActivity(intent, options.toBundle())
        }

    }
}