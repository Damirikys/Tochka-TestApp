package pro.jeminay.tochka.di

import android.app.Activity
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pro.jeminay.tochka.App
import pro.jeminay.tochka.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    AuthModule::class,
    AppModule::class,
    ViewModelModule::class
])
interface AppComponent {

    fun inject(app: App)

    fun activityInjector(): DispatchingAndroidInjector<Activity>

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}