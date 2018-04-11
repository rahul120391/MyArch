package assignments.android.zensar.myarchitecturecomponents.lifecycleobservers

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import assignments.android.zensar.myarchitecturecomponents.R
import assignments.android.zensar.myarchitecturecomponents.interactors.IMainObserver
import assignments.android.zensar.myarchitecturecomponents.interactors.IPermissionResultTransfer
import assignments.android.zensar.myarchitecturecomponents.utility.Constants
import assignments.android.zensar.myarchitecturecomponents.utility.MyLocationManagerClass
import assignments.android.zensar.myarchitecturecomponents.utility.Utils
import javax.inject.Inject

/**
 * Created by RK51670 on 06-04-2018.
 */
class MainActivityObserver @Inject constructor(myLocationManagerClass: MyLocationManagerClass, context: Context) : LifecycleObserver, IMainObserver, IPermissionResultTransfer {

    val mLocationManager = myLocationManagerClass
    val mContext = context
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
        mLocationManager.checkGooglePlayService()

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
        if (mLocationManager.mCurrentLocation == null) {
            startLoactionUpdates()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {

    }

    override fun startLoactionUpdates() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && mLocationManager != null) {
            mLocationManager.createLocationRequest()
        }
    }

    override fun stopLocationUpdates() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && mLocationManager != null) {
            mLocationManager.stopLocationUpdates()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {
        stopLocationUpdates()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == Constants.REQUEST_ACCESS_LOCATION && grantResults != null) {
            if (grantResults.size <= 0) {
                (mContext as Activity).finish()
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(mContext as Activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Utils.showAlertDialog(mContext, this)
                } else {
                    if (mLocationManager != null) {
                        Utils.showToast(mContext, mContext.getString(R.string.enable_loctaion))
                        mLocationManager.openAppSettings()
                    }
                }
            }
        } else {
            (mContext as Activity).finish()
        }
    }

    override fun setResultCode(code: Int, context: Context) {
        if (code == Constants.SUCCESS_RESULT) {
            ActivityCompat.requestPermissions(mContext as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), Constants.REQUEST_ACCESS_LOCATION)
        } else if (code == Constants.FAILURE_RESULT) {
            (mContext as Activity).finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == Constants.REQUEST_CHECK_SETTINGS && resultCode == Activity.RESULT_OK) {
            mLocationManager.startLocationUpdates()
        } else if (requestCode == Constants.OPEN_MOBILE_APP_SETTINGS && resultCode == Activity.RESULT_CANCELED && ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Utils.showAlertDialog(mContext, this)
        }
    }


    override fun getLiveLocationData(): LiveData<Location> {
        return mLocationManager.mLocationData
    }

}