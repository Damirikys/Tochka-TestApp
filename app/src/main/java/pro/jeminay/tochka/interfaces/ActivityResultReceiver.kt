package pro.jeminay.tochka.interfaces

import android.content.Intent

interface ActivityResultReceiver {

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean
}