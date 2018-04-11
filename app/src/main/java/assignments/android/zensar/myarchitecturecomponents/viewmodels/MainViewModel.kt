package assignments.android.zensar.myarchitecturecomponents.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import assignments.android.zensar.myarchitecturecomponents.networking.Repository
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import assignments.android.zensar.myarchitecturecomponents.interactors.IMainViewModel


/**
 * Created by RK51670 on 05-04-2018.
 */
class MainViewModel @Inject constructor(repository: Repository) : ViewModel(), IMainViewModel {

    val mRepo = repository
    override fun getData(params: Map<String, String>):LiveData<Any> {
        return mRepo.fetchNearbyPlaces(params)
    }

    internal class Factory(repository: Repository) : ViewModelProvider.Factory {
        val repo = repository
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(repo) as T
        }
    }


}