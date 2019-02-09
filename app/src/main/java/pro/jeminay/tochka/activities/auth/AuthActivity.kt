package pro.jeminay.tochka.activities.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import pro.jeminay.tochka.R
import pro.jeminay.tochka.activities.AppActivity
import pro.jeminay.tochka.activities.main.MainActivity
import pro.jeminay.tochka.annotations.BindLayout
import pro.jeminay.tochka.annotations.BindViewModel
import pro.jeminay.tochka.databinding.ActivityAuthBinding

@BindLayout(R.layout.activity_auth)
@BindViewModel(AuthViewModel::class)
class AuthActivity : AppActivity<ActivityAuthBinding, AuthViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.authSuccessEvent.observe(this) {
            Toast.makeText(applicationContext, "Welcome aboard, ${it.firstName}!", Toast.LENGTH_SHORT).show()

            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        viewModel.authFailedEvent.observe(this) {
            Toast.makeText(applicationContext, "Auth failed!", Toast.LENGTH_SHORT).show()
        }

        binding.vm = viewModel
    }
}