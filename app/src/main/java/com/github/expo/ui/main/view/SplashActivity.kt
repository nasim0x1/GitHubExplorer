package com.github.expo.ui.main.view

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Pair
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.github.expo.R


/* 
 * Created by nasim on 1/16/22  
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    lateinit var logo:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        logo = findViewById(R.id.gitHubLogo)

//        for full screen
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Home::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair<View, String>(logo, "logoTransition")
            )
            startActivity(intent,options.toBundle())
            finish()
        }, 2000)

    }

}