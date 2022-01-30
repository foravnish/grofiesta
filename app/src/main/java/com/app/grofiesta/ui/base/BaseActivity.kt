package com.app.grofiesta.ui.base

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.airbnb.lottie.utils.Utils
import com.app.grofiesta.R
import com.app.grofiesta.utils.Utility
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity() : AppCompatActivity() {

    val REQUEST_CODE_LOCATION_PERMISSIONS = 111
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }

    fun initLeftRightTransaction() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.scale_down)
    }


    fun isValidTimeZone(str: String): Boolean {
        if (!str.equals("") && str!!.length > 0) {
            var defaultTimeZoneList: List<String> = str.split(" ")
            if (defaultTimeZoneList.size == 2) {
                return true
            } else {
                return false
            }
        }
        return false
    }

    fun showToast(message: String) {
        if (message != null) {
            hideKeyboard()
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showErrorCustomerToast() {
        hideKeyboard()
        Toast.makeText(this, "Some Error occur. Please contact customer care", Toast.LENGTH_SHORT).show()
    }

    fun dismissDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog!!.isShowing) {
                mProgressDialog!!.cancel()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun showDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog!!.isShowing) {
                mProgressDialog!!.cancel()
            }
            mProgressDialog = Utility.showProgressDialog1(this)
        } catch (e: Exception) {
        }
    }


    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }


//    fun showToast(message: String) {
//        if (message != null) {
//            hideKeyboard()
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
//        }
//    }

    fun showToastWithoutHideKB(message: String) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun showSuccessPopup(message: String) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(getString(R.string.app_name))
        alertDialog.setMessage(message)

        alertDialog.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }

        alertDialog.show()
    }


    fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WAKE_LOCK
            ), REQUEST_CODE_LOCATION_PERMISSIONS
        )
    }

    open fun showGPSDisabledAlertToUserWithResult() {
        val alertDialogBuilder =
            AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
            .setCancelable(false)
            .setPositiveButton(
                "Goto Settings Page To Enable GPS"
            ) { dialog, id ->
                val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(callGPSSettingIntent, 105)
            }
        alertDialogBuilder.setNegativeButton(
            "Cancel"
        ) { dialog, id -> dialog.cancel() }
        val alert = alertDialogBuilder.create()
        alert.show()
    }

    fun showSnackBar(layout: View, msg: String) {
        Snackbar.make(layout, msg, Snackbar.LENGTH_LONG).show()
    }

    /*
    Hide KeyBoard
   */
    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    open fun showGPSDisabledAlertToUser() {
        val alertDialogBuilder =
            AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
            .setCancelable(false)
            .setPositiveButton(
                "Goto Settings Page To Enable GPS"
            ) { dialog, id ->
                val callGPSSettingIntent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                startActivity(callGPSSettingIntent)
            }
        alertDialogBuilder.setNegativeButton(
            "Cancel"
        ) { dialog, id -> dialog.cancel() }
        val alert = alertDialogBuilder.create()
        alert.show()
    }

    open fun showAlert(message: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(message).setCancelable(false)
            .setPositiveButton("Ok") { dialog, id -> dialog.cancel() }
        val alert = alertDialogBuilder.create()
        alert.show()
    }


}