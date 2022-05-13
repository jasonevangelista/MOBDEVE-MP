package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { view: View? ->
            // home page
            var goToHome = Intent(this, PlayerListActivity::class.java)
            startActivity(goToHome)

            // user profile
//            var goToUserProfile = Intent(this, UserProfileActivity::class.java)
//            startActivity(goToUserProfile)
            finish()
        }

        binding.btnToRegister.setOnClickListener { view: View? ->
            var goToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(goToRegister)
            finish()
        }


    }
}