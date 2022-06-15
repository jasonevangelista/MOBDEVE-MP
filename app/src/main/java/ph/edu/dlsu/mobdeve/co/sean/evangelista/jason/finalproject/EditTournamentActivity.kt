package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.annotation.SuppressLint
import android.content.ContentValues
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityEditTournamentBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament
import java.text.SimpleDateFormat
import java.util.*


class EditTournamentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditTournamentBinding

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTournamentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set tournament information in UI
        val tournament = intent.getParcelableExtra<Tournament>("tournament")
        setDetails(tournament)

        db = Firebase.firestore
        auth = Firebase.auth
        userID = auth.currentUser!!.uid

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
//            Log.d("TOURNEY", "UPDATING TOURNAMENT ${tournament!!.id} BY USER ${userID}")
            // submit new tournament details to database
            // <CODE HERE>

            // go to previous page
            finish()
        }
    }

    private fun setDetails(tournament: Tournament?){
        binding.etTourneyName.setText(tournament!!.name)
        binding.etCurrCap.setText(tournament.current_capacity.toString())
        binding.etMaxCap.setText(tournament.max_capacity.toString())
        selectSpinnerValue(binding.spinnerGame, tournament.featured_game.toString())
        binding.etDescription.setText(tournament.description)
        binding.etInstruction.setText(tournament.instructions)

        // dates
        val cutoff_date: Date = SimpleDateFormat("yyyy-MM-dd").parse(tournament.cutoff_date)
        binding.dpCutDate.init(cutoff_date.year + 1900, cutoff_date.month, cutoff_date.date, null)
        val start_date: Date = SimpleDateFormat("yyyy-MM-dd").parse(tournament.start_date)
        binding.dpStartDate.init(start_date.year + 1900, start_date.month, start_date.date, null)
    }

    private fun selectSpinnerValue(spinner: Spinner, myString: String) {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == myString) {
                spinner.setSelection(i)
                break
            }
        }
    }

    private fun checkFormInputErrors(tournament: Tournament): Int{ // 1 == success, 0 == fail
        when {
            TextUtils.isEmpty(tournament.name) -> {
                binding.etTourneyName.setError("Name cannot be empty!")
                binding.etTourneyName.requestFocus()
                return 0
            }
            TextUtils.isEmpty(tournament.current_capacity.toString()) -> {
                binding.etCurrCap.setError("Current Capacity cannot be empty!")
                binding.etCurrCap.requestFocus()
                return 0
            }
            TextUtils.isEmpty(tournament.max_capacity.toString()) -> {
                binding.etMaxCap.setError("Max Capacity cannot be empty!")
                binding.etMaxCap.requestFocus()
                return 0
            }
            tournament.current_capacity > tournament.max_capacity -> {
                binding.etMaxCap.setError("Max Capacity must be greater than Current Capacity!")
                binding.etMaxCap.requestFocus()
                return 0
            }
            TextUtils.isEmpty(tournament.description.toString()) -> {
                binding.etDescription.setError("Description cannot be empty!")
                binding.etDescription.requestFocus()
                return 0
            }
            (ParseStringToDate(tournament.start_date.toString()).before(ParseStringToDate(tournament.cutoff_date.toString()))) -> {
                binding.tvStartDate.setError("Start Date must come after Cut-off Date!")
                binding.dpCutDate.requestFocus()
                Toast.makeText(this, "Start Date must come after Cut-off Date!", Toast.LENGTH_SHORT).show()
                return 0
            }
            TextUtils.isEmpty(tournament.instructions.toString()) -> {
                binding.etInstruction.setError("Instructions cannot be empty!")
                binding.etInstruction.requestFocus()
                return 0
            }
        }
        return 1
    }

    @SuppressLint("SimpleDateFormat")
    private fun ParseStringToDate(date: String): Date{
        var dateFormatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        var formattedDate: Date = dateFormatter.parse(date)!!
        return dateFormatter.parse(date)!!
    }
}