package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityConnectBinding

class ConnectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConnectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { view: View? ->
            // go back to previous page
            finish()
        }

        binding.btnCancel.setOnClickListener { view: View? ->
            // go back to previous page
            finish()
        }

        binding.btnSendEmail.setOnClickListener { view: View? ->
//            var goToPlayerProfile = Intent(this, MainActivity::class.java)
//            startActivity(goToPlayerProfile)
            // send email to player to connect with
            // <CODE HERE>

            // go back to previous page
            finish()
        }
    }
}