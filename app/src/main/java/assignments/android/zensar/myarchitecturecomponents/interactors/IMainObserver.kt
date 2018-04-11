package assignments.android.zensar.myarchitecturecomponents.interactors

import android.arch.lifecycle.LiveData
import android.content.Intent
import android.location.Location

/**
 * Created by RK51670 on 06-04-2018.
 */
interface IMainObserver {

    fun onCreate()
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent)
    fun getLiveLocationData():LiveData<Location>
    fun startLoactionUpdates()
    fun stopLocationUpdates()
}