package pro.jeminay.tochka.activities.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import pro.jeminay.tochka.R
import pro.jeminay.tochka.activities.AppActivity
import pro.jeminay.tochka.activities.auth.AuthActivity
import pro.jeminay.tochka.annotations.BindLayout
import pro.jeminay.tochka.annotations.BindViewModel
import pro.jeminay.tochka.databinding.ActivityMainBinding

@BindLayout(R.layout.activity_main)
@BindViewModel(MainViewModel::class)
class MainActivity : AppActivity<ActivityMainBinding, MainViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toggle = ActionBarDrawerToggle(this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        viewModel.requestFailedEvent.observe(this) {
            Toast.makeText(this, "Hmm... request failed! Rate limit? Connection?", Toast.LENGTH_LONG)
                .show()
        }

        viewModel.logoutEvent.observe(this) {
            finish()
            startActivity(Intent(this, AuthActivity::class.java))
        }

        binding.vm = viewModel
        binding.navigationView.setNavigationItemSelectedListener(viewModel)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}