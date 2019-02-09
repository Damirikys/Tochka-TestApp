package pro.jeminay.tochka.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(activity: AppCompatActivity, function: (T) -> Unit) {
    this.observe(activity, Observer { function(it) })
}