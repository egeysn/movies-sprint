package com.egeysn.movies_sprint.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.egeysn.movies_sprint.R

@SuppressLint("InflateParams")
class LoadingHelper(private var context: Context) {
    private var dialog: Dialog = Dialog(context, R.style.MyAlertDialogStyle)

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(LayoutInflater.from(context).inflate(R.layout.progress_dialog, null))
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
    }

    fun showDialog() {
        try {
            if (!dialog.isShowing) {
                dialog.show()
            }
        } catch (e: Exception) {
        }
    }

    fun hideDialog() {
        try {
            dialog.dismiss()
        } catch (e: java.lang.Exception) {
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: LoadingHelper? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: LoadingHelper(context).also { instance = it }
        }
    }
}
