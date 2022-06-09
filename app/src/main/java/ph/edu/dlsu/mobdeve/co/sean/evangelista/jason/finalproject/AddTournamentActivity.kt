package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityAddTournamentBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.max

class AddTournamentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTournamentBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTournamentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        db = Firebase.firestore

        binding.btnBack.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnCancelTournament.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnAddTournament.setOnClickListener {
            Log.d("TOURNEY", "ADDING NEW TOURNAMENT")
            // submit tournament details to database
            var userID = auth.currentUser!!.uid

            var name = binding.etTourneyName.text.toString()
            var current_capacity = binding.etCurrCap.text.toString().toInt()
            var max_capacity = binding.etMaxCap.text.toString().toInt()
            var featured_game = binding.spinnerGame.selectedItem.toString()
            var description = binding.etDescription.text.toString()

//            val cutoff_date_day = binding.etCutDate.dayOfMonth.toString()
//            val cutoff_date_month = binding.etCutDate.month.toString()
//            val cutoff_date_year = binding.etCutDate.year.toString()
//            val cutoff_date = "$cutoff_date_year-$cutoff_date_month-$cutoff_date_day"
//            val cutoff_date_formatted = LocalDate.parse(cutoff_date)
//
//            val start_date_day = binding.etStartDate.dayOfMonth
//            val start_date_month = binding.etStartDate.month
//            val start_date_year = binding.etStartDate.year
//            val start_date = "$start_date_year-$start_date_month-$start_date_day"
//            val start_date_formatted = LocalDate.parse(start_date)

            val instructions = binding.etInstruction.text.toString()

            // FORM INPUT HANDLING

            // ADD TO DB
            var newTournament = Tournament(
                name = name,
                creator_id = userID,
                current_capacity = current_capacity,
                max_capacity = max_capacity,
                featured_game = featured_game,
                description = description,
//                cutoff_date = cutoff_date_formatted,
//                start_date = start_date_formatted,
                instructions = instructions
            )
            Log.d("TOURNEY", "ADDING TOURNAMENT TO DB")
            db.collection("tournaments")
                .add(newTournament)
                .addOnSuccessListener{documentReference ->
                    Log.d("TAG", "onSuccess: new tournament is created with id ${documentReference.id} by user ${userID}")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding tournament document", e)
                }

            // go to previous page
            finish()
        }
    }
}