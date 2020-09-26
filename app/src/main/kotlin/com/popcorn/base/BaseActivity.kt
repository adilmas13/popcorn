package com.popcorn.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.popcorn.utilities.showToast

abstract class BaseActivity<B : ViewBinding, M : ViewModel> : AppCompatActivity() {

    private var toastInstance: Toast? = null

    lateinit var viewModel: M

    lateinit var binding: B

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun subscribeToObservers()

    abstract fun createViewModel(): Class<M>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        generateViewModel()
        subscribeToObservers()
    }

    private fun generateViewModel() {
        viewModel = ViewModelProvider(this).get(createViewModel())
    }

    fun showMessage(message: String) {
        toastInstance = showToast(message)
    }

    override fun onDestroy() {
        toastInstance?.cancel() // cancel and toast message that is being displayed
        super.onDestroy()
    }
}
