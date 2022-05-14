package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityAddTournamentBinding

class AddTournamentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTournamentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTournamentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnCancelTournament.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnAddTournament.setOnClickListener {
            // submit tournament details to database
            // <CODE HERE>

            // go to previous page
            finish()
        }
    }
}