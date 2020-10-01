package com.popcorn.utilities

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatDelegate

private var sW = 0

fun Activity.setTransparentStatusBar() {
    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    } else {
        var style = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            style = style or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.decorView.systemUiVisibility = style
    }
    window.statusBarColor = Color.TRANSPARENT
}

fun Activity.screenWidth(refresh: Boolean = false) = if (sW > 0 && refresh.not()) {
    sW
} else {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    sW = displayMetrics.widthPixels
    sW
}
