package com.popcorn.ui.splash

import android.animation.AnimatorSet
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.popcorn.utilities.animate
import com.popcorn.utilities.onRendered

class SplashActivityAnimator(
    private val popText: View,
    private val cornText: View,
    private val logo: View,
    private val screenWidth: Int
) {

    fun init() {
        logo.apply {
            alpha = 0f
            scaleX = 0f
            scaleY = 0f
        }
        popText.apply {
            alpha = 0f
        }
        cornText.apply {
            alpha = 0f
        }
    }

    fun startAnimation(): LiveData<Boolean> {
        val test = MutableLiveData<Boolean>()
        logo.onRendered {
            val animationSet = AnimatorSet()
            animationSet.playTogether(
                logoEnterAnimation().animator,
                popTextEnterAnimation().animator,
                cornTextEnterAnimation().animator
            )
            animationSet.interpolator = OvershootInterpolator()
            animationSet.duration = 500L
            animationSet.startDelay = 1000
            animationSet.doOnEnd {
                test.value = true
            }
            animationSet.start()
        }
        return test
    }

    private fun logoEnterAnimation() = logo.animate {
        scale {
            scaleX = 1f
            scaleY = 1f
        }
        alpha {
            alpha = 1f
        }
    }.build()

    private fun cornTextEnterAnimation() = cornText.animate {
        startDelay = 100
        translate {
            translateXFrom = (-cornText.width).toFloat()
        }
        alpha {
            alpha = 1f
        }
    }.build()

    private fun popTextEnterAnimation() = popText.animate {
        translate {
            translateXFrom = (-popText.width).toFloat()
        }
        alpha {
            alpha = 1f
        }
    }.build()

    fun endAnimation(onCompleted: () -> Unit) {
        val animationSet = AnimatorSet()
        val logoBuilder = logo.animate {
            scale {
                scaleX = 0f
                scaleY = 0f
            }
            alpha {
                alpha = 0f
            }
        }.build()
        val popTextBuilder = popText.animate {
            translate {
                translateXTo = screenWidth.toFloat()
            }
            alpha {
                alpha = 0f
            }
        }.build()
        val cornTextBuilder = cornText.animate {
            startDelay = 100
            translate {
                translateXTo = (-cornText.width).toFloat()
            }
            alpha {
                alpha = 0f
            }
        }.build()
        animationSet.playTogether(
            logoBuilder.animator,
            popTextBuilder.animator,
            cornTextBuilder.animator
        )
        animationSet.interpolator = OvershootInterpolator()
        animationSet.duration = 1000L
        animationSet.startDelay = 3000
        animationSet.doOnEnd { onCompleted() }
        animationSet.start()
    }
}
