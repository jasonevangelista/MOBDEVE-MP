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

    }
}