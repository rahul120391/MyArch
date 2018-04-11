package assignments.android.zensar.myarchitecturecomponents.activities.main

import assignments.android.zensar.myarchitecturecomponents.networking.Repository
import assignments.android.zensar.myarchitecturecomponents.viewmodels.MainViewModel
import dagger.Module
import dagger.Provides
import android.arch.lifecycle.ViewModelProvider
import assignments.android.zensar.myarchitecturecomponents.lifecycleobservers.MainActivityObserver
import assignments.android.zensar.myarchitecturecomponents.utility.MyLocationManagerClass


/**
 * Created by RK51670 on 04-04-2018.
 */

@Module
public class MainActivityModule {


    @Provides
    fun providesViewModelFactory(repository: Repository): ViewModelProvider.Factory {
        return MainViewModel.Factory(repository)
    }

    @Provides
    fun providesMainObserver(myLocationManagerClass: MyLocationManagerClass,
                             context: MainActivity):MainActivityObserver{
        return MainActivityObserver(myLocationManagerClass,context)
    }

    @Provides
    fun provideLocationManagerClass(context: MainActivity):MyLocationManagerClass{
        return MyLocationManagerClass(context)
    }

}