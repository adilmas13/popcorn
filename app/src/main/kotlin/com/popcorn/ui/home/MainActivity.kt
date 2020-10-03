package com.popcorn.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.popcorn.R
import com.popcorn.base.BaseActivity
import com.popcorn.databinding.ActivityMainBinding
import com.popcorn.utilities.setTransparentStatusBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTransparentStatusBar()
        super.onCreate(savedInstanceState)
    }

    override fun createViewModel() = MainActivityViewModel::class.java

    override fun getLayoutId() = R.layout.activity_main

    override fun subscribeToObservers() = Unit
}
