package com.app.grofiesta.utils

import android.app.*
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.BindingAdapter
import com.app.grofiesta.BuildConfig
import com.app.grofiesta.R
import com.app.grofiesta.data.model.ApiResponseModels
import com.app.grofiesta.ui.main.view.product.ServiceActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONTokener
import java.io.File
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class Utility {
    companion object {
        var selectedDate:String=""

        var isLadger: Boolean = true
        //        private var mediaFactory: MediaFactory? = null
        var dateM: Date? = null
//    fun setConnectivity(context: Context) {
//        try {
//            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val networkInfo = connMgr.activeNetworkInfo
//            val isConnectedToInternet = networkInfo != null && networkInfo.isConnected
//            ApplicationClass.setIsConnected(isConnectedToInternet)
//        } catch (e: Exception) {
//        }
//    }

        fun showToastForLogin(ctx:Context) {
            Toast.makeText(ctx, "Please Login first.", Toast.LENGTH_SHORT).show()
        }

        fun MyCustomDialog(context: Context, layout: Int): Dialog {
            val dialog = Dialog(context)
            dialog.setContentView(layout)
            dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation_UpDown_dialog
            dialog.setCanceledOnTouchOutside(true)
            dialog.setCancelable(true)
            dialog.window!!.setGravity(Gravity.CENTER)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            return dialog
        }

        fun getRealPathFromURI(contentUri: Uri, context: Context): String? {
            val result: String?
            val cursor =
                context.contentResolver.query(contentUri, null, null, null, null)
            if (cursor == null) {
                result = contentUri.path
            } else {
                cursor.moveToFirst()
                val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                result = cursor.getString(idx)
                cursor.close()
            }
            return result
        }

        fun convertModel(mData: List<ApiResponseModels.DymainHomeProductResponse.Data.Productsdata>): ArrayList<ApiResponseModels.GroProductResponse.Success> {

            var  mList= ArrayList<ApiResponseModels.GroProductResponse.Success>()

            mData.forEach {
                mList.add(
                    ApiResponseModels.GroProductResponse.Success(
                    ""+it.product_id,""+it.product_name,""+it.weight_size,
                    ""+it.main_price,""+it.discount_percent,
                        ""+it.display_price,""+it.image,it.hasCart
                ))
            }

            return mList
        }


        fun showProgressDialog1(context: Context): ProgressDialog {
            val progressDialog = ProgressDialog(context)
            try {
                progressDialog.show()
                if (progressDialog.window != null) {
                    progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
                progressDialog.setContentView(R.layout.progress_layout)
                progressDialog.isIndeterminate = true
                progressDialog.setCancelable(true)
                progressDialog.setCanceledOnTouchOutside(false)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return progressDialog
        }


        fun isConnected(context: Context): Boolean {
            val cm =
                context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }


        fun getAppVersion(): String {
            return BuildConfig.VERSION_NAME
        }

        fun closeKeyboard(view: View, context: Context) {
            if (view != null) {
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }


        fun startActivity(context: Context, intent: Intent) {
            context.startActivity(intent)
        }

        fun startActivityWithLeftToRightAnimation(ctx: Activity?, `in`: Intent?) {
            if (ctx != null && `in` != null) {
                ctx.startActivity(`in`)
                ctx.overridePendingTransition(R.anim.slide_in_right, R.anim.scale_down)
            }
        }

        fun startActivityWithLeftToRightAnimationContext(ctx: Context?, `in`: Intent?) {
            if (ctx != null && `in` != null) {
                ctx.startActivity(`in`)
            }
        }


        fun Context.dismissKeyboard(view: View?) {
            view?.let {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }

        fun View.visible(isVisible: Boolean) {
            visibility = if (isVisible) View.VISIBLE else View.GONE
        }

        fun View.snackbar(message: String, action: (() -> Unit)? = null) {
            val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
            action?.let {
                snackbar.setAction("Retry") {
                    it()
                }
            }
            snackbar.show()
        }


        @BindingAdapter("imageUrl")
        fun setImageUrl(imageView: ShapeableImageView, url: String?) {
            val context = imageView.context
            if (url != null)
                Glide.with(context).load(url).into(imageView)
        }
//        fun startActivityBottomToUpAnimation(ctx: Activity?, `in`: Intent?) {
//            if (ctx != null && `in` != null) {
//                ctx.startActivity(`in`)
//                ctx.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
//            }
//        }

//        fun startActivityUpToBottomAnimation(ctx: Activity?, `in`: Intent?) {
//            if (ctx != null && `in` != null) {
//                ctx.startActivity(`in`)
//                ctx.overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_top)
//            }
//        }


        fun showToast(mContext: Context, message: String) {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
        }

        fun showToast(mContext: Context) {
            Toast.makeText(mContext, "Unknown Error!", Toast.LENGTH_SHORT).show()
        }

        fun isNetworkAvailable(context: Context): Boolean {
            try {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                return activeNetworkInfo != null && activeNetworkInfo.isConnected
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false
        }


        fun setImageViaGlide(
            placeholderId: Int,
            imageUrl: String,
            imgVw: ImageView,
            mContext: Context
        ) {
            if (!TextUtils.isEmpty(imageUrl) && imgVw != null) {
                val requestOptions: RequestOptions =
                    RequestOptions().placeholder(placeholderId).error(placeholderId)
                        .fallback(placeholderId)
                        .diskCacheStrategy(
                            DiskCacheStrategy.ALL
                        )
                Glide.with(mContext).load(imageUrl).apply(requestOptions).into(imgVw)
            }
        }


        fun showSnackBar(parentLayout: View?, msg: String) {
            if (parentLayout != null) {
                val snackBar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_SHORT)
                snackBar.setActionTextColor(Color.WHITE)

                val view = snackBar.view
                val tv = view.findViewById(R.id.snackbar_text) as TextView
                tv.setTextColor(Color.WHITE)
                //view.setBackgroundColor(parentLayout.context.resources.getColor(R.color.blue_03A9F4))
                snackBar.show()
            }
        }


        fun toTitleCase(str: String?): String? {

            if (str == null) {
                return null
            }

            var space = true
            val builder = StringBuilder(str)
            val len = builder.length

            for (i in 0 until len) {
                val c = builder[i]
                if (space) {
                    if (!Character.isWhitespace(c)) {
                        // Convert to title case and switch out of whitespace mode.
                        builder.setCharAt(i, Character.toTitleCase(c))
                        space = false
                    }
                } else if (Character.isWhitespace(c)) {
                    space = true
                } else {
                    builder.setCharAt(i, Character.toLowerCase(c))
                }
            }

            return builder.toString()
        }


//        fun getLayoutManagerAsPerDevice(mContext: Context): GridLayoutManager {
//            var layoutManager: RecyclerView.LayoutManager
//            //check if we are running app on tablet or on mobile phone
//            val isTablet = mContext.resources.getBoolean(R.bool.isTablet)
//            if (isTablet)
//                layoutManager = GridLayoutManager(mContext, 4)
//            else
//                layoutManager = GridLayoutManager(mContext, 2)
//            return layoutManager
//        }


        fun openCalender(context: Context, txtDate: TextView){
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            var mt:String=""
            var dy:String=""

            val dpd = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var months=monthOfYear+1
                if(months<10)
                    mt="0"+months; //if month less than 10 then ad 0 before month
                else mt= months.toString()


                if(dayOfMonth<10)
                    dy = "0"+dayOfMonth;
                else dy = dayOfMonth.toString()

                txtDate.setText(""+"" + year + "-" +mt + "-" + dy)


            }, year, month, day)

            dpd.getDatePicker().setMaxDate(c.getTimeInMillis());
            dpd!!.show()

        }


        fun getCurrentdate():String{
            val c: Date = Calendar.getInstance().getTime()
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate: String = df.format(c)
            return currentDate
        }

        fun getPrevous10Date():String{

            val c: Date = Calendar.getInstance().getTime()
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate: String = df.format(c)
            val myDate: Date = df.parse(currentDate)
            val calendar = Calendar.getInstance()
            calendar.time = myDate
            calendar.add(Calendar.DAY_OF_YEAR, -10)
            val newDate = calendar.time
            val df2 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date: String = df2.format(newDate)

            return date

        }

        fun getFromDefaultTime():String{
            val defautFromTime="00:00:00"
            return defautFromTime
        }

        fun getToDefaultTime():String{
            val defautFromTime="23:30:00"
            return defautFromTime
        }




        fun openWatch(context: Context, txtTime: TextView){
            val mHour: Int
            val mMinute: Int

            val c = Calendar.getInstance()
            mHour = c[Calendar.HOUR_OF_DAY]
            mMinute = c[Calendar.MINUTE]


            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(
                context,
                OnTimeSetListener { view, hourOfDay, minute ->
                    txtTime.setText("$hourOfDay:$minute")
                }, mHour, mMinute, false
            )
            timePickerDialog.show()
        }


        //method to convert server utc to local time
        fun getNewDate(millis: String): String {

            val time = java.lang.Long.valueOf(millis)
            val calendar = Calendar.getInstance()
            val tz = TimeZone.getDefault()
            calendar.timeInMillis = time * 1000
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.timeInMillis))
            val currenTimeZone = calendar.time as Date
            val month = currenTimeZone.month + 1
            val year = currenTimeZone.year + 1900
            return currenTimeZone.date.toString() + " " + convertMonthNumaricToAlpha(month) + " " + year

//            return  DateUtils.formatDateTime(ctx, millis.toLong() * 1000, DateUtils.FORMAT_SHOW_YEAR)
        }


        private fun convertMonthNumaricToAlpha(month: Int): Any? {
            var months: String? = null
            when (month) {
                1 -> months = "Jan"
                2 -> months = "Feb"
                3 -> months = "Mar"
                4 -> months = "Apr"
                5 -> months = "May"
                6 -> months = "Jun"
                7 -> months = "Jul"
                8 -> months = "Aug"
                9 -> months = "Sep"
                10 -> months = "Oct"
                11 -> months = "Nov"
                12 -> months = "Dec"
            }
            return months
        }


        private fun convertMonthNumaricToAlphaFull(month: Int): Any? {
            var months: String? = null
            when (month) {
                1 -> months = "January"
                2 -> months = "February"
                3 -> months = "March"
                4 -> months = "April"
                5 -> months = "May"
                6 -> months = "June"
                7 -> months = "July"
                8 -> months = "August"
                9 -> months = "September"
                10 -> months = "October"
                11 -> months = "November"
                12 -> months = "December"
            }
            return months
        }


        //method to convert server utc to local time
        fun getMongoDate(millis: String): String {

            val date: Date?
            var strDate = ""
            try {
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val dateString = millis
                date = format.parse("" + dateString)
                val day = date.date
                val months = date.month + 1
                val year = date.year + 1900
                strDate = "" + day + "-" + months + "-" + year
            } catch (e: Exception) {
            }
            return strDate
        }

        fun getMongoDateMonth(millis: String): String {

            val date: Date?
            var strDate = ""
            try {
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val dateString = millis
                date = format.parse("" + dateString)
                val day = date.date
                val months = date.month + 1
                val year = date.year + 1900
                strDate = "" + convertMonthNumaricToAlpha(months) + " " + day + ", " + year
            } catch (e: Exception) {
            }
            return strDate
        }


//        @JvmStatic
//        fun showPgDialog(ctx: Context?): Dialog? {
//            if (ctx != null) {
//                val dialog = Dialog(ctx)
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                //            dialog.setCancelable(false);
//
//                dialog.setContentView(R.layout.dialogprogress)
//                dialog.setCanceledOnTouchOutside(false)
//
//                dialog.show()
//
//                return dialog
//            }
//            return null
//        }


        fun getRootDirPath(context: Context): String {
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                val file = ContextCompat.getExternalFilesDirs(
                    context.applicationContext,
                    null
                )[0]
                return file.getAbsolutePath()
            } else {
                return context.applicationContext.filesDir.absolutePath
            }
        }

        fun getProgressDisplayLine(currentBytes: Long, totalBytes: Long): String {
            return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes)
        }

        private fun getBytesToMBString(bytes: Long): String {
            return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00))
        }


        fun openPdfWithIntent(filePath: String, context: Context) {
            val file = File(filePath)
            val context = context
            val pdfViewIntent = Intent(Intent.ACTION_VIEW)

            val apkURI = FileProvider.getUriForFile(
                context,
                context.getApplicationContext()
                    .getPackageName() + ".provider", file
            )

            pdfViewIntent.setDataAndType(apkURI, "application/pdf")
            pdfViewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val intent = Intent.createChooser(pdfViewIntent, "Open Account Pdf")
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // Instruct the user to install a PDF reader here, or something
                Toast.makeText(context, "No suitable App Found to view pdf", Toast.LENGTH_LONG)
                    .show()
            }

        }

//        fun setViewCallback(mClickListener: AlertCallBack) {
//            this.mClickListener = mClickListener
//        }


//        fun showAlertDialog(ctx: Context?, msg: String): Dialog? {
//            if (ctx != null) {
//                val dialog = Dialog(ctx)
//                dialog.setContentView(R.layout.dialog_alert)
//                val btn1 = dialog.findViewById<Button>(R.id.buttonOk)
//                val txtMessage = dialog.findViewById<TextView>(R.id.txtMessage)
//                txtMessage.setText(msg)
//                btn1.setOnClickListener { v: View? ->
//                    mClickListener.onAlertCallBack(msg)
//                    dialog.dismiss()
//                }
//                dialog.show()
//                return dialog
//            }
//            return null
//        }


//        fun html2text(html: String): String? {
//
//           return Jsoup.parse(html).text()
//
//
//        }

        fun getIndianRupee(value: String): String {

            val format = NumberFormat.getCurrencyInstance(Locale("en", "in"))
            return format.format(BigDecimal(value))
        }

        fun parseJson(data: String): String? {
            return try {
                JSONTokener(data).nextValue().toString()
            } catch (e: JSONException) {
                "{}"
            }
        }

    }

}