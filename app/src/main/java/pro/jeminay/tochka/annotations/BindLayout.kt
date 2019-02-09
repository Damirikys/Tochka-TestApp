package pro.jeminay.tochka.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindLayout(val resId: Int)