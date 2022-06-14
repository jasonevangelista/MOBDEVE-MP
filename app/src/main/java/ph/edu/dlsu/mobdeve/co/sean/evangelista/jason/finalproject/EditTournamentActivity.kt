package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityEditTournamentBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament

class EditTournamentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTournamentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTournamentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set tournament information in UI
        val tournament = intent.getParcelableExtra<Tournament>("tournament")
        setDetails(tournament)

        binding.btnBack.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnDelete.setOnClickListener {
            // delete tournament details from database
            // <CODE HERE>

            // go to previous page
            finish()
        }

        binding.btnCancelTournament.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnEditTournament.setOnClickListener {
            // submit new tournament details to database
            // <CODE HERE>

            // go to previous page
            finish()
        }
    }

    private fun setDetails(tournament: Tournament?){
        binding.etTourneyName.setText(tournament!!.name)
        binding.etCurrCap.setText(tournament.current_capacity)
        binding.etMaxCap.setText(tournament.max_capacity)
//        binding.spinnerGame.setSelection()
//        val selectionPosition: Int = adapter.getPosition(tournament.)
//        spinner.setSelection(selectionPosition)
    }
}