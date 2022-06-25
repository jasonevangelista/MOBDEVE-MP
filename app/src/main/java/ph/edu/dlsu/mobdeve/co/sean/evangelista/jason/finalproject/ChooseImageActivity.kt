package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityChooseImageBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player

class ChooseImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val player = intent.getParcelableExtra<Player>("player")

        binding.ivAvatar1.setOnClickListener {
            val goToEditProfile = Intent(this, EditProfileActivity::class.java)
            goToEditProfile.putExtra("player", player)
            goToEditProfile.putExtra("image", "avatar1")
            startActivity(goToEditProfile)
            finish()
        }

        binding.ivAvatar2.setOnClickListener {
            val goToEditProfile = Intent(this, EditProfileActivity::class.java)
            goToEditProfile.putExtra("player", player)
            goToEditProfile.putExtra("image", "avatar2")
            startActivity(goToEditProfile)
            finish()
        }

        binding.ivAvatar3.setOnClickListener {
            val goToEditProfile = Intent(this, EditProfileActivity::class.java)
            goToEditProfile.putExtra("player", player)
            goToEditProfile.putExtra("image", "avatar3")
            startActivity(goToEditProfile)
            finish()
        }

        binding.ivAvatar4.setOnClickListener {
            val goToEditProfile = Intent(this, EditProfileActivity::class.java)
            goToEditProfile.putExtra("player", player)
            goToEditProfile.putExtra("image", "avatar4")
            startActivity(goToEditProfile)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            val goToEditProfile = Intent(this, EditProfileActivity::class.java)
            goToEditProfile.putExtra("player", player)
            startActivity(goToEditProfile)
            finish()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()

        val player = intent.getParcelableExtra<Player>("player")
        val goToEditProfile = Intent(this, EditProfileActivity::class.java)
        goToEditProfile.putExtra("player", player)
        startActivity(goToEditProfile)
        finish()
    }
}