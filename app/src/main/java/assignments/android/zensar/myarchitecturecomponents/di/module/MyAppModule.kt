package assignments.android.zensar.myarchitecturecomponents.di.module

import assignments.android.zensar.myarchitecturecomponents.networking.RetrofitAdapter
import assignments.android.zensar.myarchitecturecomponents.networking.RetrofitService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.app.Application
import android.content.Context
import assignments.android.zensar.myarchitecturecomponents.networking.Repository
import assignments.android.zensar.myarchitecturecomponents.utility.MyLocationManagerClass


/**
 * Created by RK51670 on 04-04-2018.
 */

@Module
public class MyAppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideRetrofitService(): RetrofitService {
        return RetrofitAdapter.create()
    }

    @Singleton
    @Provides
    fun provideRepo(retrofitService: RetrofitService):Repository{
        return Repository(retrofitService)
    }

}