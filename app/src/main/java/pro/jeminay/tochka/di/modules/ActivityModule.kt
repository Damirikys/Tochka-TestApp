package pro.jeminay.tochka.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pro.jeminay.tochka.activities.auth.AuthActivity
import pro.jeminay.tochka.activities.main.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindAuthActivity(): AuthActivity

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}