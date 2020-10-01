package com.popcorn.ui.splash

import android.os.Bundle
import com.popcorn.R
import com.popcorn.base.BaseActivity
import com.popcorn.databinding.ActivitySplashBinding
import com.popcorn.utilities.setTransparentStatusBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash.*

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashActivityViewModel>() {

    private lateinit var animator: SplashActivityAnimator
    override fun onCreate(savedInstanceState: Bundle?) {
        setTransparentStatusBar()
        super.onCreate(savedInstanceState)
        animator = SplashActivityAnimator(tvPop, tvCorn, ivLogo).apply { init() }
        animator.startAnimation()
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
//        startActivity(MainActivity.getIntent(this))
//        finish()
    }

    override fun createViewModel() = SplashActivityViewModel::class.java
}
