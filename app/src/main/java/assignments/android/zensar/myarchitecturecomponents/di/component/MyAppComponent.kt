package assignments.android.zensar.myarchitecturecomponents.di.component

import assignments.android.zensar.myarchitecturecomponents.applicationpack.MyApplication
import dagger.Component
import android.app.Application
import assignments.android.zensar.myarchitecturecomponents.di.module.ActivityBuilder
import assignments.android.zensar.myarchitecturecomponents.di.module.MyAppModule
import dagger.BindsInstance
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created by RK51670 on 04-04-2018.
 */

@Singleton
@Component(modules = arrayOf(AndroidSupportInjectionModule::class,MyAppModule::class,ActivityBuilder::class))
public interface MyAppComponent{
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): MyAppComponent
    }
    fun inject(app: MyApplication)
}