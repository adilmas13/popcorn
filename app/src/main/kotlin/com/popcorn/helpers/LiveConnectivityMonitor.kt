package com.popcorn.helpers

import android.content.Context
import android.net.ConnectivityManager
import com.popcorn.domain.NetworkMonitor

class LiveConnectivityMonitor(
    private val context: Context
) : NetworkMonitor {

    override fun isConnected(): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}
