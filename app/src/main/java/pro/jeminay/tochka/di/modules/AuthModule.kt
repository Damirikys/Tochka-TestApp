package pro.jeminay.tochka.di.modules

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import pro.jeminay.tochka.auth.AuthStrategy
import pro.jeminay.tochka.auth.FacebookAuthStrategy
import pro.jeminay.tochka.auth.GoogleAuthStrategy
import pro.jeminay.tochka.auth.VKAuthStrategy
import pro.jeminay.tochka.consts.AuthTypes
import pro.jeminay.tochka.annotations.BindAuthStrategy

@Module
abstract class AuthModule {

    @Binds
    @IntoMap
    @BindAuthStrategy(AuthTypes.VK)
    internal abstract fun bindVKAuthStrategy(authStrategy: VKAuthStrategy): AuthStrategy

    @Binds
    @IntoMap
    @BindAuthStrategy(AuthTypes.FACEBOOK)
    internal abstract fun bindFacebookAuthStrategy(authStrategy: FacebookAuthStrategy): AuthStrategy

    @Binds
    @IntoMap
    @BindAuthStrategy(AuthTypes.GOOGLE)
    internal abstract fun bindGoogleAuthStrategy(authStrategy: GoogleAuthStrategy): AuthStrategy
}