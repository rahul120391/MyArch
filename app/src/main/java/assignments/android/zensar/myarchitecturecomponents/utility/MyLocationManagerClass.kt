package assignments.android.zensar.myarchitecturecomponents.utility

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.util.Log
import assignments.android.zensar.myarchitecturecomponents.interactors.IMainLocation
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener


class MyLocationManagerClass(var mActivityContext: Activity) : LocationCallback(), OnSuccessListener<LocationSettingsResponse>, OnFailureListener,IMainLocation {

    internal var mLocationRequest: LocationRequest? = null
    internal var mBuilder: LocationSettingsRequest.Builder? = null
    internal var mFusedLocationApiClient: FusedLocationProviderClient? = null
    internal var mCurrentLocation: Location? = null

    var mLocationData:MutableLiveData<Location> = MutableLiveData<Location>()

    fun checkGooglePlayService() {
        if (LocationUtils.checkPlayServicesAvailbility(mActivityContext) == Constants.TRUE) {
            if (LocationUtils.checkApiLevelForRuntimePermission(mActivityContext)) {
                createLocationRequest()
            }
        } else if (LocationUtils.checkPlayServicesAvailbility(mActivityContext) == Constants.FALSE) {
            mActivityContext.finish()
        }
    }

    fun getLastKnownLocation() {
        try {
            if (mFusedLocationApiClient != null) {
                val checkPermission = ContextCompat.checkSelfPermission(mActivityContext, Manifest.permission.ACCESS_FINE_LOCATION )
                if (checkPermission == PackageManager.PERMISSION_GRANTED) {
                    val lastKnownLocation = this.mFusedLocationApiClient!!.getLastLocation()
                    mCurrentLocation = lastKnownLocation.getResult()
                }
            }
            else{
                startLocationUpdates()
            }
        } catch (e: Throwable) {
            Log.i(Constants.ERROR_MESSAGE, e.message)
        }

    }

    override fun onLocationResult(locationReq: LocationResult) {
        if (locationReq != null) {
            for (location in locationReq!!.getLocations()) {
                mCurrentLocation = location
            }
        } else {
            getLastKnownLocation()
        }
        if (mCurrentLocation != null) {
            stopLocationUpdates()
        }
        mLocationData.postValue(mCurrentLocation)
    }



    fun checkLocationSettings() {
        if (mBuilder == null) {
            mBuilder = LocationSettingsRequest.Builder().
                    addLocationRequest(mLocationRequest!!).setAlwaysShow(true)
        }
        val settingClient = LocationServices.getSettingsClient(mActivityContext)
        val task = settingClient.checkLocationSettings(mBuilder!!.build())
        task.addOnSuccessListener(this)
        task.addOnFailureListener(this)
    }

    fun createLocationRequest() {
        if (mLocationRequest == null) {
            mLocationRequest = LocationRequest()
        }
        mLocationRequest!!.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        checkLocationSettings()
    }

    override fun onFailure(e: Exception) {
        if (e is ResolvableApiException) {
            try {
                e.startResolutionForResult(mActivityContext, Constants.REQUEST_CHECK_SETTINGS)
            } catch (exception: Exception) {
                Log.i(Constants.ERROR_MESSAGE, exception.message)
            }

        }
    }

    override fun onSuccess(p0: LocationSettingsResponse) {
        startLocationUpdates()
    }

    fun startLocationUpdates() {
        try {
            setUpFusedApiClient()
            mFusedLocationApiClient!!.requestLocationUpdates(mLocationRequest, this, Looper.myLooper())
        } catch (e: SecurityException) {
            Log.i(Constants.ERROR_MESSAGE, e.message)
        }

    }

    fun stopLocationUpdates() {
        if (mFusedLocationApiClient != null) {
            mFusedLocationApiClient!!.removeLocationUpdates(this)

        }
    }

    fun setUpFusedApiClient() {
        if (mFusedLocationApiClient == null) {
            mFusedLocationApiClient = LocationServices.getFusedLocationProviderClient(mActivityContext)
        }
    }

    fun openAppSettings() {
        val intent = Intent()
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", mActivityContext.getPackageName(), null)
        intent.setData(uri)
        mActivityContext.startActivityForResult(intent, Constants.OPEN_MOBILE_APP_SETTINGS)
    }

}