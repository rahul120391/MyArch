package assignments.android.zensar.myarchitecturecomponents.utility

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ImageView
import android.widget.Toast
import assignments.android.zensar.myarchitecturecomponents.R
import assignments.android.zensar.myarchitecturecomponents.interactors.IPermissionResultTransfer

import com.bumptech.glide.Glide



/**
 * Created by RK51670 on 31-01-2018.
 */
object Utils {


    internal var mDialog: AlertDialog? = null

    /**
     * Show AlertDialog on denying location repoRequest
     */
    fun showAlertDialog(context: Context, iPermissionResultTransfer: IPermissionResultTransfer) {
        dismissDialog()
        if (mDialog == null) {
            mDialog = AlertDialog.Builder(context).create()
            mDialog!!.setTitle(context.getString(R.string.permission))
            mDialog!!.setMessage(context.getString(R.string.permission_message))
            mDialog!!.setCancelable(false)
            mDialog!!.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.retry)) { dialog, which -> iPermissionResultTransfer.setResultCode(Constants.SUCCESS_RESULT, context) }
            mDialog!!.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel)) { dialog, which -> iPermissionResultTransfer.setResultCode(Constants.FAILURE_RESULT, context) }
        }
        mDialog!!.show()
    }

    /**
     * Dismiss Dialog
     */
    fun dismissDialog() {
        if (mDialog != null) {
            mDialog!!.dismiss()
            mDialog = null
        }
    }
    /**
     * Show play services error
     */
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    /**
     * Load Images
     */
    fun loadImages(context: Context, imageView: ImageView, url: String) {
        Glide.with(context).load(url).into(imageView)
    }

    /**
     * Start activity intent function
     */
    fun startAcivity(context: Context, classname: Class<*>) {
        val startActivity = Intent(context, classname)
        startActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(startActivity)
    }

    /**
     * Start activity for result intent function
     */
    fun startAcivityForResult(context: Context, classname: Class<*>, requestCode: Int) {
        val startActivity = Intent(context, classname)
        startActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        (context as Activity).startActivityForResult(startActivity, requestCode)
    }

    /**
     * Start activity intent function
     */
    fun startAcivityForResultWithData(context: Context, classname: Class<*>, bundle: Bundle, requestCode: Int) {
        val startActivity = Intent(context, classname)
        startActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity.putExtra(Constants.DATA, bundle)
        (context as Activity).startActivityForResult(startActivity, requestCode)
    }

    /**
     * Start activity intent function
     */
    fun startAcivityWithData(context: Context, classname: Class<*>, bundle: Bundle) {
        val startActivity = Intent(context, classname)
        startActivity.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity.putExtra(Constants.DATA, bundle)
        (context as Activity).startActivity(startActivity)
    }

    /**
     * Check network connectivity
     */
    fun checkConnectivity(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }


}