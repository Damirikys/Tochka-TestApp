package pro.jeminay.tochka.annotations

import dagger.MapKey
import pro.jeminay.tochka.consts.AuthTypes

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class BindAuthStrategy(val value: AuthTypes)