package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnChangeImage.setOnClickListener {
            // allow user to select profile image
            // <CODE HERE>

            // go to previous page
            finish()
        }

        binding.btnCancelProfile.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnEditProfile.setOnClickListener {
            // submit new profile details for my profile to database
            // <CODE HERE>

            // go to previous page
            finish()
        }

    }
}