package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener { view: View? ->
            var goToLogin = Intent(this, MainActivity::class.java)
            startActivity(goToLogin)
            finish()
        }

        binding.btnToLogin.setOnClickListener { view: View? ->
            var goToLogin = Intent(this, MainActivity::class.java)
            startActivity(goToLogin)
            finish()
        }
    }
}