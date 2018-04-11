package assignments.android.zensar.myarchitecturecomponents.interactors

import android.arch.lifecycle.LiveData
import assignments.android.zensar.myarchitecturecomponents.models.nearbyplaces.MyPlaces

/**
 * Created by RK51670 on 05-04-2018.
 */
interface IRepoView {
    fun fetchNearbyPlaces(params:Map<String, String>):LiveData<Any>
}