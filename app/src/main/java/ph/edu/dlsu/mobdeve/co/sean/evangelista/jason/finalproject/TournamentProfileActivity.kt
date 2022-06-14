package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityTournamentProfileBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament

class TournamentProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTournamentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTournamentProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set tournament information in UI
        val tournament = intent.getParcelableExtra<Tournament>("tournament")
        setDetails(tournament)

    }

    private fun setDetails(tournament: Tournament?){
        val formattedCapacity: String = "${tournament!!.current_capacity.toString()} / ${tournament.max_capacity.toString()}"

        binding.tvTournamentName.text = tournament.name
        binding.tvTournamentGame.text = tournament.featured_game
        binding.tvTournamentCapacity.text = formattedCapacity
        binding.tvTournamentDescriptionContent.text = tournament.description
        binding.tvTournamentCutoffDateContent.text = tournament.cutoff_date
        binding.tvTournamentStartDateContent.text = tournament.start_date
        binding.tvTournamentInstructionsContent.text = tournament.instructions
    }
}