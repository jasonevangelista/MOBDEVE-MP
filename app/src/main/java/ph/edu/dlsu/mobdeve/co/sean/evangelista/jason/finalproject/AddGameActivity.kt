package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityAddGameBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Game
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament

class AddGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddGameBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        db = Firebase.firestore

        binding.btnBack.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnCancelGame.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnAddGame.setOnClickListener {
            Log.d("GAME", "ADDING NEW GAME")
            // submit game details for my profile to database
            var userID = auth.currentUser!!.uid
            var name = binding.etGameTitle.text.toString()
            var rank = binding.etPlayerRank.text.toString()
            var server = binding.etPlayerServer.text.toString()
            var role = binding.etPlayerRole.text.toString()

            var newGame = Game(
                name = name,
                creator_id = userID,
                rank = rank,
                server = server,
                role = role
            )

            // FORM INPUT HANDLING
            if(checkFormInputErrors(newGame) == 1){
                // ADD TO DB
                Log.d("GAME", "ADDING GAME TO DB")
                db.collection("games")
                    .add(newGame)
                    .addOnSuccessListener{documentReference ->
                        Log.d("TAG", "onSuccess: new game is added with id ${documentReference.id} by user ${userID}")
                        Toast.makeText(this, "Successfully added a game!", Toast.LENGTH_SHORT).show()


                        val goToHome = Intent(this, PlayerListActivity::class.java)

                        val bundle = Bundle()
                        bundle.putInt("currFragment", 1)
                        goToHome.putExtras(bundle)

                        startActivity(goToHome)
                        finish()

                    }
                    .addOnFailureListener { e ->
                        Log.w("TAG", "Error adding game document", e)

                        val goToHome = Intent(this, PlayerListActivity::class.java)
                        startActivity(goToHome)
                        finish()
                    }
            }
            // go to previous page
            finish()
        }

    }

    private fun checkFormInputErrors(game: Game): Int{ // 1 == success, 0 == fail
        when {
            TextUtils.isEmpty(game.name) -> {
                binding.etGameTitle.setError("Game name cannot be empty!")
                binding.etGameTitle.requestFocus()
                return 0
            }
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