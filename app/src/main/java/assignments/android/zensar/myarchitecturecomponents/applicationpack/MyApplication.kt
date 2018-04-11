package assignments.android.zensar.myarchitecturecomponents.applicationpack

import android.app.Activity
import android.app.Application
import assignments.android.zensar.myarchitecturecomponents.di.component.DaggerMyAppComponent
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject


/**
 * Created by RK51670 on 04-04-2018.
 */
class MyApplication() : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    override fun onCreate() {
        super.onCreate()
        DaggerMyAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }


    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

}