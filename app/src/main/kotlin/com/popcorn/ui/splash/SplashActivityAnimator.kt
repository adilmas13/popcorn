package com.popcorn.ui.splash

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.OvershootInterpolator

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
    }

    fun startAnimation() {
        logo.animate {
            startDelay = 2000
            duration = 500
            scale {
                scaleX = 1f
                scaleY = 1f
            }
            alpha {
                alpha = 1f
            }
        }.build()
    }
}

fun View.animate(block: AnimationBuilder.() -> Unit): AnimationBuilder {
    val builder = AnimationBuilder(this)
    builder.block()
    return builder
}

class AnimationBuilder(private val view: View) {

    private var scaleAnimation: ScaleAnimation? = null
    private var alphaAnimation: AlphaAnimation? = null

    var startDelay = 0L
    var duration = 0L

    fun build() {
        val animation = ObjectAnimator.ofPropertyValuesHolder(view)
        val properties = mutableListOf<PropertyValuesHolder>()
        scaleAnimation?.let {
            properties.add(PropertyValuesHolder.ofFloat(View.SCALE_X, it.scaleX))
            properties.add(PropertyValuesHolder.ofFloat(View.SCALE_Y, it.scaleY))
        }
        alphaAnimation?.let {
            properties.add(PropertyValuesHolder.ofFloat(View.ALPHA, it.alpha))
        }
        animation.setValues(*properties.toTypedArray())
        animation.interpolator = OvershootInterpolator()
        animation.duration = duration
        animation.startDelay = startDelay
        animation.start()
    }

    fun scale(block: ScaleAnimation.() -> Unit) {
        scaleAnimation = ScaleAnimation(view)
        scaleAnimation?.block()
    }

    fun alpha(block: AlphaAnimation.() -> Unit) {
        alphaAnimation = AlphaAnimation(view)
        alphaAnimation?.block()
    }
}

class ScaleAnimation(private val view: View) {
    var scaleX = 1f
    var scaleY = 1f
}

class AlphaAnimation(private val view: View) {
    var alpha = 1f
}
