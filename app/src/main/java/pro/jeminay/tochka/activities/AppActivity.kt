package pro.jeminay.tochka.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import pro.jeminay.tochka.annotations.BindLayout
import pro.jeminay.tochka.annotations.BindViewModel
import pro.jeminay.tochka.interfaces.ActivityResultReceiver
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
abstract class AppActivity<T : ViewDataBinding, S : ViewModel> : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var binding: T

    protected lateinit var viewModel: S

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val viewModelClass = javaClass.declaredAnnotations.find { it is BindViewModel } as BindViewModel
        viewModel = ViewModelProviders.of(this, viewModelFactory)[viewModelClass.value.java] as S

        val layout = javaClass.declaredAnnotations.find { it is BindLayout } as BindLayout
        binding = DataBindingUtil.setContentView(this, layout.resId)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val resultReceiver = if (viewModel is ActivityResultReceiver)
            viewModel as ActivityResultReceiver
        else return super.onActivityResult(requestCode, resultCode, data)

        if (resultReceiver.onActivityResult(requestCode, resultCode, data).not()) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.setLifecycleOwner(this)
    }

    override fun onStop() {
        super.onStop()
        binding.setLifecycleOwner(null)
    }
}
