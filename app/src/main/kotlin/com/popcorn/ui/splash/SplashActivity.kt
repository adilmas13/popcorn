package com.popcorn.ui.splash

import android.os.Bundle
import com.popcorn.R
import com.popcorn.base.BaseActivity
import com.popcorn.databinding.ActivitySplashBinding
import com.popcorn.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashActivityViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getConfig()
    }

    override fun getLayoutId() = R.layout.activity_splash

    override fun subscribeToObservers() {
        val ctx = this
        viewModel.apply {
            onSuccess.observe(ctx, { redirectToHome() })
            onError.observe(ctx, { showMessage("Something went wrong") })
        }
    }

    private fun redirectToHome() {
        startActivity(MainActivity.getIntent(this))
        finish()
    }

    override fun createViewModel() = SplashActivityViewModel::class.java
}
