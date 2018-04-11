package assignments.android.zensar.myarchitecturecomponents.utility

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

object LocationUtils {

    fun checkPlayServicesAvailbility(context: Activity): Int {
        val googleApiAvail = GoogleApiAvailability.getInstance()
        val checkApiAvailability = googleApiAvail.isGooglePlayServicesAvailable(context)
        if (checkApiAvailability == ConnectionResult.SUCCESS) {
            return Constants.TRUE
        } else {
            if (googleApiAvail.isUserResolvableError(checkApiAvailability)) {
                googleApiAvail.getErrorDialog(context, checkApiAvailability, Constants.PLAY_SERVICES_RESOLUTION_REQUEST)
                return Constants.ERROR
            }
        }
        return Constants.FALSE
    }


    /**
     * Runtime permission for Api Level 23 and above
     */
    fun checkRuntimePermission(context: Activity): Boolean {
        val checkPermission = checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(context, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), Constants.REQUEST_ACCESS_LOCATION)
            }
        } else if (checkPermission == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    /**
     * check the api level
     */
    fun checkApiLevelForRuntimePermission(context: Activity): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkRuntimePermission(context)
        } else true
    }


}