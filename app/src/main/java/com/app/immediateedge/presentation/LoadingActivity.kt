package com.app.immediateedge.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.immediateedge.databinding.LoadingActivityBinding
import com.app.immediateedge.presenter.MainPresenter
import com.app.immediateedge.presenter.LoadingPresenter


class LoadingActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = LoadingActivityBinding.inflate(layoutInflater)
        val view = binding.root
        installSplashScreen()
        setContentView(view)

        val presenter = LoadingPresenter(this)

        presenter.createBroadcastManager()
    }

    companion object {
        const val BROADCAST_RECEIVER_KEY = "BROADCAST_RECEIVER_KEY"
        const val BROADCAST_RECEIVER_ACTION = "BROADCAST_RECEIVER_ACTION"
    }

}