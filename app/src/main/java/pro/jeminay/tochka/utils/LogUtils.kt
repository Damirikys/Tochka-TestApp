package pro.jeminay.tochka.utils

import android.util.Log

fun <T : Any> T.logD(name: String? = "", callback: () -> String) {
    Log.d(this.javaClass.simpleName, "$name: ${callback()}")
}

fun <T : Any> T.logE(name: String? = "", callback: () -> String) {
    Log.e(this.javaClass.simpleName, "$name: ${callback()}")
}

fun <T : Any> T.logI(name: String? = "", callback: () -> String) {
    Log.i(this.javaClass.simpleName, "$name: ${callback()}")
}

fun <T : Any> T.logWTF(name: String? = "", callback: () -> String) {
    Log.wtf(this.javaClass.simpleName, "$name: ${callback()}")
}