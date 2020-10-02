package com.popcorn.ui.splash

import android.animation.AnimatorSet
import android.view.View
import android.view.animation.OvershootInterpolator
import com.popcorn.utilities.animate
import com.popcorn.utilities.onRendered

class SplashActivityAnimator(
    private val popText: View,
    private val cornText: View,
    private val logo: View
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

    fun startAnimation() {
        logo.onRendered {
            val animationSet = AnimatorSet()
            val logoBuilder = logo.animate {
                scale {
                    scaleX = 1f
                    scaleY = 1f
                }
                alpha {
                    alpha = 1f
                }
            }.build()
            val popTextBuilder = popText.animate {
                translate {
                    translateXFrom = (-popText.width).toFloat()
                }
                alpha {
                    alpha = 1f
                }
            }.build()
            val cornTextBuilder = cornText.animate {
                startDelay = 100
                translate {
                    translateXFrom = (-cornText.width).toFloat()
                }
                alpha {
                    alpha = 1f
                }
            }.build()
            animationSet.playTogether(
                logoBuilder.animator,
                popTextBuilder.animator,
                cornTextBuilder.animator
            )
            animationSet.interpolator = OvershootInterpolator()
            animationSet.duration = 500L
            animationSet.startDelay = 1000
            animationSet.start()
        }
    }
}
