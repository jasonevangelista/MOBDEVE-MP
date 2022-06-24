package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.cketti.mailto.EmailIntentBuilder
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityConnectBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.ConnectHistory
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player
import java.util.*

class ConnectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConnectBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        db = Firebase.firestore


        val player = intent.getParcelableExtra<Player>("player")

        binding.btnBack.setOnClickListener { view: View? ->
            // go back to previous page
            finish()
        }

        binding.btnCancel.setOnClickListener { view: View? ->
            // go back to previous page
            finish()
        }

        binding.btnSendEmail.setOnClickListener { view: View? ->
            // send email to player to connect with
            val body = binding.etConnectMessage.text.toString()

            if(player != null){
                // redirect user to email app
                // (this method assumes that the logged in user also has the email logged in their email app)
                EmailIntentBuilder.from(this@ConnectActivity)
                    .to(player.email.toString())
                    .subject("GameBuddy Connect")
                    .body(body)
                    .start()

                // append player id and datetime to current user's history log list
                var historyLog = ConnectHistory(player_id = player.id, date = Timestamp.now().toDate())
                updateHistoryLog(auth.currentUser!!.uid, historyLog)

            }
        }
    }

    private fun updateHistoryLog(userID: String, connectHistory: ConnectHistory){
        db.collection("players")
            .document(userID)
            .collection("history")
            .add(connectHistory)
            .addOnSuccessListener { result ->
                Log.d("HISTORY_LOG", "Successfully added a history log!")
                finish()
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error adding history document: ", exception)
            }
    }
}