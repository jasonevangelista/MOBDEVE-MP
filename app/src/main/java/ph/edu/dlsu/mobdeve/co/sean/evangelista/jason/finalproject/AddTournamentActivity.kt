package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityAddTournamentBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
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

//            var name = binding.etTourneyName.text.toString()

            // catch empty number values
            if (TextUtils.isEmpty(binding.etCurrCap.text.toString() )) {
                binding.etCurrCap.setError("Current Capacity cannot be empty!")
                binding.etCurrCap.requestFocus()
            }
            else if(TextUtils.isEmpty( binding.etMaxCap.text.toString() )) {
                binding.etMaxCap.setError("Max Capacity cannot be empty!")
                binding.etMaxCap.requestFocus()
            }
            else{
                var name = binding.etTourneyName.text.toString()
                var current_capacity = binding.etCurrCap.text.toString().toInt()
                var max_capacity = binding.etMaxCap.text.toString().toInt()
                var featured_game = binding.spinnerGame.selectedItem.toString()
                var description = binding.etDescription.text.toString()

                var cutoff_date_day = binding.dpCutDate.dayOfMonth.toString()
                var cutoff_date_month = (binding.dpCutDate.month + 1).toString()
                val cutoff_date_year = binding.dpCutDate.year.toString()
                val cutoff_date = formatDateToString(cutoff_date_year, cutoff_date_month, cutoff_date_day)

                var start_date_day = binding.dpStartDate.dayOfMonth.toString()
                var start_date_month = (binding.dpStartDate.month + 1).toString()
                val start_date_year = binding.dpStartDate.year.toString()
                val start_date = formatDateToString(start_date_year, start_date_month, start_date_day)

                val instructions = binding.etInstruction.text.toString()

                var newTournament = Tournament(
                    name = name,
                    creator_id = userID,
                    current_capacity = current_capacity,
                    max_capacity = max_capacity,
                    featured_game = featured_game,
                    description = description,
                    cutoff_date = cutoff_date,
                    start_date = start_date,
                    instructions = instructions
                )

                // FORM INPUT HANDLING
                if(checkFormInputErrors(newTournament) == 1){
                    // ADD TO DB
                    Log.d("TOURNEY", "ADDING TOURNAMENT TO DB")
                    db.collection("tournaments")
                        .add(newTournament)
                        .addOnSuccessListener{documentReference ->
                            Log.d("TAG", "onSuccess: new tournament is created with id ${documentReference.id} by user ${userID}")
                            Toast.makeText(this, "Successfully added a tournament!", Toast.LENGTH_SHORT).show()


                            val goToHome = Intent(this, PlayerListActivity::class.java)

                            val bundle = Bundle()
                            bundle.putInt("currFragment", 1)
                            goToHome.putExtras(bundle)

                            startActivity(goToHome)
                            finish()

                        }
                        .addOnFailureListener { e ->
                            Log.w("TAG", "Error adding tournament document", e)

                            val goToHome = Intent(this, PlayerListActivity::class.java)
                            startActivity(goToHome)
                            finish()
                        }

                }
            }



        }


    }

    private fun formatDateToString(year: String, month: String,day: String) : String{
        var formatted_day = day
        var formatted_month = month

        if(day.toInt() < 10){
            formatted_day = "0$day"
        }
        if(month.toInt() < 10){
            formatted_month = "0$month"
        }
        return "$year-$formatted_month-$formatted_day" // following yyyy-MM-dd format

//        var formatter:DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//            var cutoff_date_formatted: LocalDate = LocalDate.parse(cutoff_date, formatter)
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
            return dateFormatter.parse(date)!!
    }

}