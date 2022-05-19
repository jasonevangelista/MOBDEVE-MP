package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityTournamentProfileBinding

class TournamentProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTournamentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTournamentProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}