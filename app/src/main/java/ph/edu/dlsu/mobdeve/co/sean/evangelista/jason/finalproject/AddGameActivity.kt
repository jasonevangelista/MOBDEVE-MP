package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityAddGameBinding

class AddGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnCancelGame.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnAddGame.setOnClickListener {
            // submit game details for my profile to database
            // <CODE HERE>

            // go to previous page
            finish()
        }

    }
}