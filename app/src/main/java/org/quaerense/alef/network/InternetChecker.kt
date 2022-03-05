package org.quaerense.alef.network

import android.content.Context
import android.net.ConnectivityManager

class InternetChecker(private val context: Context) {

    fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}