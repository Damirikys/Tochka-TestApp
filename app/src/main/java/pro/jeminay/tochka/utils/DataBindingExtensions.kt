@file:Suppress("UNCHECKED_CAST")

package pro.jeminay.tochka.utils

import androidx.databinding.*
import io.reactivex.disposables.Disposable

fun ObservableInt.subscribe(callback: (Int) -> Unit): Disposable {
    apply { callback(get()) }

    val listener = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable?, i: Int) = callback((observable as ObservableInt).get())
    }

    addOnPropertyChangedCallback(listener)

    return io.reactivex.disposables.Disposables.fromAction { removeOnPropertyChangedCallback(listener) }
}

fun ObservableDouble.subscribe(callback: (Double) -> Unit): Disposable {
    apply { callback(get()) }

    val listener = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable?, i: Int) = callback((observable as ObservableDouble).get())
    }

    addOnPropertyChangedCallback(listener)

    return io.reactivex.disposables.Disposables.fromAction { removeOnPropertyChangedCallback(listener) }
}

fun ObservableLong.subscribe(callback: (Long) -> Unit): Disposable {
    apply { callback(get()) }

    val listener = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable?, i: Int) = callback((observable as ObservableLong).get())
    }

    addOnPropertyChangedCallback(listener)

    return io.reactivex.disposables.Disposables.fromAction { removeOnPropertyChangedCallback(listener) }
}

fun ObservableBoolean.subscribe(callback: (Boolean) -> Unit): Disposable {
    apply { callback(get()) }

    val listener = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable?, i: Int) = callback((observable as ObservableBoolean).get())
    }

    addOnPropertyChangedCallback(listener)

    return io.reactivex.disposables.Disposables.fromAction { removeOnPropertyChangedCallback(listener) }
}

fun <T : Any> ObservableField<T>.subscribe(callback: (T?) -> Unit): Disposable {
    apply { callback(get()) }

    val listener = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable?, i: Int) = callback((observable as ObservableField<T>).get())
    }

    addOnPropertyChangedCallback(listener)

    return io.reactivex.disposables.Disposables.fromAction { removeOnPropertyChangedCallback(listener) }
}


