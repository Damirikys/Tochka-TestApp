package pro.jeminay.tochka

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import pro.jeminay.tochka.di.AppComponent
import pro.jeminay.tochka.di.DaggerAppComponent

class App : Application(), HasActivityInjector {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }

    private fun setupDagger() {
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return appComponent.activityInjector()
    }
}