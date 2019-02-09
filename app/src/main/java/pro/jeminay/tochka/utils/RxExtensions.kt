package pro.jeminay.tochka.utils

import androidx.databinding.*
import io.reactivex.Observable

fun <T> ObservableField<T>.toObservable(): Observable<T> = Observable.create { emitter ->
    if (!emitter.isDisposed) {
        val value = get()
        if (value != null) {
            emitter.onNext(value)
        }
    }

    val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(dataBindingObservable: androidx.databinding.Observable, propertyId: Int) {
            val value = get()
            if (value != null) {
                emitter.onNext(value)
            }
        }
    }

    addOnPropertyChangedCallback(callback)

    emitter.setDisposable(io.reactivex.disposables.Disposables.fromAction { removeOnPropertyChangedCallback(callback) })
}

fun ObservableInt.toObservable(): Observable<Int> = Observable.create { emitter ->
    if (!emitter.isDisposed) {
        emitter.onNext(get())
    }

    val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(dataBindingObservable: androidx.databinding.Observable, propertyId: Int) {
            emitter.onNext(get())
        }
    }

    addOnPropertyChangedCallback(callback)

    emitter.setDisposable(io.reactivex.disposables.Disposables.fromAction { removeOnPropertyChangedCallback(callback) })
}

fun ObservableBoolean.toObservable(): Observable<Boolean> = io.reactivex.Observable.create { emitter ->
    if (!emitter.isDisposed) {
        emitter.onNext(get())
    }

    val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(dataBindingObservable: androidx.databinding.Observable, propertyId: Int) {
            emitter.onNext(get())
        }
    }

    addOnPropertyChangedCallback(callback)

    emitter.setDisposable(io.reactivex.disposables.Disposables.fromAction { removeOnPropertyChangedCallback(callback) })
}

fun ObservableLong.toObservable(): Observable<Long> = io.reactivex.Observable.create { emitter ->
    if (!emitter.isDisposed) {
        emitter.onNext(get())
    }

    val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(dataBindingObservable: androidx.databinding.Observable, propertyId: Int) {
            emitter.onNext(get())
        }
    }

    addOnPropertyChangedCallback(callback)

    emitter.setDisposable(io.reactivex.disposables.Disposables.fromAction { removeOnPropertyChangedCallback(callback) })
}

fun <T> ObservableList<T>.toObservable(): Observable<List<T>> = io.reactivex.Observable.create { emitter ->
    if (!emitter.isDisposed) {
        emitter.onNext(toList())
    }

    val callback = object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
        override fun onItemRangeRemoved(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
        }

        override fun onItemRangeMoved(sender: ObservableList<T>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
        }

        override fun onItemRangeInserted(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
        }

        override fun onItemRangeChanged(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
        }

        override fun onChanged(sender: ObservableList<T>?) {
            emitter.onNext(sender?.toList() ?: kotlin.collections.listOf())
        }
    }

    addOnListChangedCallback(callback)

    emitter.setDisposable(io.reactivex.disposables.Disposables.fromAction { removeOnListChangedCallback(callback) })
}