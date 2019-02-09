package pro.jeminay.tochka.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pro.jeminay.tochka.api.FacebookApiService
import pro.jeminay.tochka.api.GithubApiService
import pro.jeminay.tochka.api.VKApiService
import pro.jeminay.tochka.data.Database
import pro.jeminay.tochka.data.UserDao
import pro.jeminay.tochka.auth.AuthManager
import pro.jeminay.tochka.data.UserRepo
import pro.jeminay.tochka.data.UserRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(gson: Gson, okhttp: OkHttpClient): Retrofit.Builder = Retrofit.Builder()
        .client(okhttp)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))

    @Provides
    @Singleton
    fun provideVKApiService(builder: Retrofit.Builder): VKApiService = builder
        .baseUrl("https://api.vk.com")
        .build()
        .create(VKApiService::class.java)

    @Provides
    @Singleton
    fun provideFacebookApiService(builder: Retrofit.Builder): FacebookApiService = builder
        .baseUrl("https://graph.facebook.com")
        .build()
        .create(FacebookApiService::class.java)

    @Provides
    @Singleton
    fun provideGithubApiService(builder: Retrofit.Builder): GithubApiService = builder
        .baseUrl("https://api.github.com")
        .build()
        .create(GithubApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database = Room.databaseBuilder(context, Database::class.java, "database")
        .build()

    @Provides
    @Singleton
    fun provideUserDao(database: Database): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepo = UserRepository(userDao)

    @Provides
    @Singleton
    fun provideUserManager(userDao: UserDao): AuthManager =
        AuthManager(userDao)
}