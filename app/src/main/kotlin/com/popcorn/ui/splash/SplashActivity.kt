package com.popcorn.ui.splash

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.popcorn.R
import com.popcorn.base.BaseActivity
import com.popcorn.databinding.ActivitySplashBinding
import com.popcorn.ui.home.MainActivity
import com.popcorn.utilities.screenWidth
import com.popcorn.utilities.setTransparentStatusBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashActivityViewModel>() {

    private lateinit var animator: SplashActivityAnimator
    override fun onCreate(savedInstanceState: Bundle?) {
        setTransparentStatusBar()
        super.onCreate(savedInstanceState)
        animator = SplashActivityAnimator(tvPop, tvCorn, ivLogo, screenWidth()).apply { init() }
        zip2(viewModel.onSuccess, animator.startAnimation(), timer()).observe(
            this,
            {
                animator.endAnimation {
                    redirectToHome()
                }
            }
        )
        viewModel.getConfig()
    }

    override fun getLayoutId() = R.layout.activity_splash

    override fun subscribeToObservers() {
        val ctx = this
        viewModel.apply {
            onError.observe(ctx, { showMessage("Something went wrong") })
        }
    }

    private fun timer(): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        lifecycleScope.launch {
            delay(1000)
            liveData.postValue(true)
        }
        return liveData
    }

    private fun redirectToHome() {
        startActivity(MainActivity.getIntent(this))
        finish()
    }

    override fun createViewModel() = SplashActivityViewModel::class.java
}

fun <T1, T2, T3> zip2(
    src1: LiveData<T1>,
    src2: LiveData<T2>,
    src3: LiveData<T3>
): MediatorLiveData<Boolean> {
    val mediatorLiveData = MediatorLiveData<kotlin.Boolean>()
    var check = 0

    fun update() {
        if (++check == 3) {
            mediatorLiveData.postValue(true)
        }
    }

    mediatorLiveData.addSource(src1) {
        Log.d("THE_SOURCE", "SOURCE_1")
        update()
    }
    mediatorLiveData.addSource(src2) {
        Log.d("THE_SOURCE", "SOURCE_2")
        update()
    }
    mediatorLiveData.addSource(src3) {
        Log.d("THE_SOURCE", "SOURCE_3")
        update()
    }
    return mediatorLiveData
}
