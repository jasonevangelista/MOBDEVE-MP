package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityEditGameBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Game
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament
import java.text.SimpleDateFormat
import java.util.*

class EditGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditGameBinding

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set tournament information in UI
        val game = intent.getParcelableExtra<Game>("game")
        setDetails(game)

        db = Firebase.firestore
        auth = Firebase.auth
        userID = auth.currentUser!!.uid

        binding.btnBack.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnDelete.setOnClickListener {
            // delete game details for my profile from database
            // <CODE HERE>

            // go to previous page
            finish()
        }

        binding.btnCancelGame.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnEditGame.setOnClickListener {
            Log.d("GAME", "EDITING GAME")
            // submit new game details for my profile to database
            // <CODE HERE>

            // go to previous page
            //finish()
        }

    }

    private fun setDetails(game: Game?){
        selectSpinnerValue(binding.spinnerGame, game!!.name.toString())
        binding.etPlayerRank.setText(game.rank)
        binding.etPlayerServer.setText(game.server)
        binding.etPlayerRole.setText(game.role)
    }

    private fun selectSpinnerValue(spinner: Spinner, myString: String) {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == myString) {
                spinner.setSelection(i)
                break
            }
        }
    }

    private fun checkFormInputErrors(game: Game): Int{ // 1 == success, 0 == fail
        when {
            TextUtils.isEmpty(game.rank) -> {
                binding.etPlayerRank.setError("Rank cannot be empty!")
                binding.etPlayerRank.requestFocus()
                return 0
            }
            TextUtils.isEmpty(game.server) -> {
                binding.etPlayerServer.setError("Server(s) cannot be empty!")
                binding.etPlayerServer.requestFocus()
                return 0
            }
            TextUtils.isEmpty(game.role) -> {
                binding.etPlayerRole.setError("Preferred Role(s) cannot be empty!")
                binding.etPlayerRole.requestFocus()
                return 0
            }
        }
        return 1
    }
}