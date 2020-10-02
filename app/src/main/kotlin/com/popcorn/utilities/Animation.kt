package com.popcorn.utilities

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.animation.Interpolator

open class BaseAnimation {
    var startDelay = 0L
    var duration = 0L
    var interpolator: Interpolator? = null
}

class Animation(val animator: ObjectAnimator) {

    fun play() {
        animator.start()
    }
}

fun View.animate(block: AnimationBuilder.() -> Unit): AnimationBuilder {
    val builder = AnimationBuilder(this)
    builder.block()
    return builder
}

class AnimationBuilder(private val view: View) : BaseAnimation() {

    private var scaleAnimation: ScaleAnimation? = null
    private var alphaAnimation: AlphaAnimation? = null
    private var translateAnimation: TranslateAnimation? = null

    fun build(): Animation {
        val animation = ObjectAnimator.ofPropertyValuesHolder(view)
        val properties = mutableListOf<PropertyValuesHolder>()
        scaleAnimation?.let {
            properties.add(PropertyValuesHolder.ofFloat(View.SCALE_X, it.scaleX))
            properties.add(PropertyValuesHolder.ofFloat(View.SCALE_Y, it.scaleY))
        }
        alphaAnimation?.let {
            properties.add(PropertyValuesHolder.ofFloat(View.ALPHA, it.alpha))
        }
        translateAnimation?.let {
            properties.add(
                PropertyValuesHolder.ofFloat(
                    View.TRANSLATION_X,
                    it.translateXFrom,
                    it.translateXTo
                )
            )
            properties.add(
                PropertyValuesHolder.ofFloat(
                    View.TRANSLATION_Y,
                    it.translateYFrom,
                    it.translateYTo
                )
            )
        }
        animation.setValues(*properties.toTypedArray())
        animation.duration = duration
        animation.startDelay = startDelay
        return Animation(animation)
    }

    fun scale(block: ScaleAnimation.() -> Unit) {
        scaleAnimation = ScaleAnimation(view)
        scaleAnimation?.block()
    }

    fun alpha(block: AlphaAnimation.() -> Unit) {
        alphaAnimation = AlphaAnimation(view)
        alphaAnimation?.block()
    }

    fun translate(block: TranslateAnimation.() -> Unit) {
        translateAnimation = TranslateAnimation(view)
        translateAnimation?.block()
    }
}

class ScaleAnimation(private val view: View) : BaseAnimation() {
    var scaleX = 1f
    var scaleY = 1f
}

class AlphaAnimation(private val view: View) : BaseAnimation() {
    var alpha = 1f
}

class TranslateAnimation(private val view: View) : BaseAnimation() {
    var translateXFrom = 0f
    var translateYFrom = 0f
    var translateXTo = 0f
    var translateYTo = 0f
}
