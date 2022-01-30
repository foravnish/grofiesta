package com.app.grofiesta.data.apiClient

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.app.grofiesta.R
import com.app.grofiesta.ui.base.BaseActivity
import com.app.grofiesta.utils.RetryDialog
import com.app.grofiesta.utils.Utility
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object NetworkHandling {

    fun showNetworkError(context: Context, exception: Throwable) {
        if (exception is HttpException) {
            val error: HttpException = exception
            when (exception.code()) {
                503 -> {
                    getRetryDialog(context, RetryDialog.SERVER_ERROR)
                }
                else -> {
                    (context as BaseActivity).showToast(context.getString(R.string.something_went_wrong))
                }
            }
        } else if (exception is SocketTimeoutException || exception is IOException) {
            (context as BaseActivity).showToast(context.getString(R.string.internet_is_slow))
        }
    }

    fun getRetryDialog(context: Context, type: Int) {
        val retryDialog = RetryDialog(context, type)
        retryDialog.setOnRetryButtonClickListener(View.OnClickListener {
            retryDialog.dismiss()
        })
        retryDialog.show()
    }

    fun isConnected(context: Context): Boolean {
        val cm =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun isNetworkConnected(context: Context): Boolean {
        if (Utility.isConnected(context))
            return true
        else
            (context as BaseActivity).showToast(context.getString(R.string.internet_not_connect))
        return false
    }

}