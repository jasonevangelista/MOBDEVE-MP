package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.ContentValues
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

        // set game information in UI
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

            db.collection("players")
                .document(auth.currentUser!!.uid).collection("games")
                .document(game!!.id.toString())
                .delete()
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "Game with id:${game!!.id.toString()} successfully deleted!")
                    Toast.makeText(this, "Successfully deleted a game!", Toast.LENGTH_SHORT).show()
                    // redirect to home
                    redirectToHome()
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error deleting document", e)
                    Toast.makeText(this, "Error deleting game!", Toast.LENGTH_SHORT).show()
                    // redirect to home
                    redirectToHome()
                    finish()
                }
            // go to previous page
            // finish()
        }

        binding.btnCancelGame.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnEditGame.setOnClickListener {
            Log.d("GAME", "UPDATING GAME ${game!!.id} BY USER ${userID}")
            // submit new game details for my profile to database
            // <CODE HERE>
            var name = binding.spinnerGame.selectedItem.toString()
            var rank = binding.etPlayerRank.text.toString()
            var server = binding.etPlayerServer.text.toString()
            var role = binding.etPlayerRole.text.toString()

            var updatedGame = Game(
                name = name,
                creator_id = userID,
                rank = rank,
                server = server,
                role = role
            )

            // FORM INPUT HANDLING
            if(checkFormInputErrors(updatedGame) == 1){
                // UPDATE DB
                Log.d("GAME", "UPDATING GAME IN DB")
//                db.collection("games")
//                    .add(newGame)
                db.collection("players")
                    .document(auth.currentUser!!.uid).collection("games")
                    .document(game!!.id.toString())
                    .set(updatedGame)
                    .addOnSuccessListener{documentReference ->
                        Log.d("TAG", "onSuccess: existing game is updated with id ${game.id} by user ${userID}")
                        Toast.makeText(this, "Successfully added a game!", Toast.LENGTH_SHORT).show()

//                        val goToHome = Intent(this, PlayerListActivity::class.java)
                        // redirect to player fragment
//                        val bundle = Bundle()
//                        bundle.putInt("currFragment", 1)
//                        goToHome.putExtras(bundle)
//                        startActivity(goToHome)

                        // redirect to home
                        redirectToHome()
                        finish()

                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error updating game document!", Toast.LENGTH_SHORT).show()
                        Log.w("TAG", "Error updating game document", e)

//                        val goToHome = Intent(this, PlayerListActivity::class.java)
//                        startActivity(goToHome)

                        // redirect to home
                        redirectToHome()
                        finish()
                    }
            }
            // go to previous page
            finish()
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

    private fun redirectToHome(){
        val goToHome = Intent(this, PlayerListActivity::class.java)
        startActivity(goToHome)
    }
}