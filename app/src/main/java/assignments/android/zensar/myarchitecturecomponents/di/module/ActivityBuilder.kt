package assignments.android.zensar.myarchitecturecomponents.di.module

import assignments.android.zensar.myarchitecturecomponents.activities.main.MainActivity
import dagger.Module
import assignments.android.zensar.myarchitecturecomponents.activities.main.MainActivityModule
import dagger.android.ContributesAndroidInjector





/**
 * Created by RK51670 on 05-04-2018.
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    internal abstract fun bindMainActivity(): MainActivity

}