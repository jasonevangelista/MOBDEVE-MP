package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityEditProfileBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var userID: String
    private lateinit var currUsername: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set game information in UI
        val player = intent.getParcelableExtra<Player>("player")
        setDetails(player)

        db = Firebase.firestore
        auth = Firebase.auth
        userID = auth.currentUser!!.uid
        currUsername = player!!.username.toString()

        binding.btnBack.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnChangeImage.setOnClickListener {
            // allow user to select profile image
            // <CODE HERE>

            // go to previous page
            finish()
        }

        binding.btnCancelProfile.setOnClickListener {
            // go to previous page
            finish()
        }

        binding.btnEditProfile.setOnClickListener {
            Log.d("PLAYER", "UPDATING GAME ${player!!.id} BY USER ${userID}")
            // submit new profile details for my profile to database
            // <CODE HERE>
            var email = player.email
            var username = binding.etUsername.text.toString()
            var discord =  binding.etDiscord.text.toString()
            var twitter = binding.etTwitter.text.toString()
            var other_socials = binding.etOtherSocials.text.toString()
//            var img_url = binding.userImage
            var bio = binding.etBio.text.toString()
            var message = binding.etMessage.text.toString()
            var gaming_hours = binding.etGamingHours.text.toString()

            var updatedProfile = Player (
                email = email,
                username = username,
                discord = discord,
                twitter = twitter,
                other_socials = other_socials,
//                img_url = img_url,
                bio = bio,
                message = message,
                gaming_hours = gaming_hours
            )

            // FORM INPUT HANDLING
//            if(checkInputFormErrors(updatedProfile) == 1){
//                // UPDATE DB
//                Log.d("PLAYER", "UPDATING PLAYER IN DB")
//                db.collection("players")
//                    .document(userID)
//                    .set(updatedProfile)
//                    .addOnSuccessListener { documentReference ->
//                        Log.d("TAG", "onSuccess: existing profile is updated with id ${player.id} by user ${userID}")
//                        Toast.makeText(this, "Successfully updated user profile!", Toast.LENGTH_SHORT).show()
//
//                        // redirect to home
//                        redirectToHome()
//                        finish()
//                    }
//                    .addOnFailureListener { e ->
//                        Toast.makeText(this, "Error updating player document!", Toast.LENGTH_SHORT).show()
//                        Log.w("TAG", "Error updating player document", e)
//
//                        // redirect to home
//                        redirectToHome()
//                        finish()
//                    }
//            }
            // go to previous page
            // finish()

            checkUniqueUsername(updatedProfile)
        }

    }

    private fun checkInputFormErrors(player: Player?): Int{ // 1 == success, 0 == fail
        when {
            TextUtils.isEmpty(player!!.username) -> {
                binding.etUsername.setError("Username cannot be empty!")
                binding.etUsername.requestFocus()
                return 0
            }
        }
        return 1
    }

    private fun setDetails(player: Player?){
        binding.etUsername.setText(player!!.username)
        binding.etDiscord.setText(player.discord)
        binding.etTwitter.setText(player.twitter)
        binding.etOtherSocials.setText(player.other_socials)
        binding.etBio.setText(player.bio)
        binding.etMessage.setText(player.message)
        binding.etGamingHours.setText(player.gaming_hours)
        binding.userImage.setImageResource(R.drawable.ic_profile) // temp placeholder
    }

    private fun redirectToHome(){
        val goToHome = Intent(this, PlayerListActivity::class.java)
        startActivity(goToHome)
    }

    private fun checkUniqueUsername(player: Player?) {
        var found = 0 // 1 == not unique, 0 == unique

        db.collection("players")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val playerDoc = document.toObject<Player>()
                    if (playerDoc.username == player!!.username) {
//                        Log.d("TAG", "docID ${document.id} - currUserID ${userID}")
                        if (document.id == userID) {
                            found = 0
                            break
                        } else {
                            binding.etUsername.setError("Username already taken!")
                            binding.etUsername.requestFocus()
                            Log.d("TAG", "USERNAME IS NOT UNIQUE")
                            found = 1
                            break
                        }
                    }
                }

                if(found == 0) {
                    if(checkInputFormErrors(player) == 1){
                        // UPDATE DB
                        Log.d("PLAYER", "UPDATING PLAYER IN DB")
                        db.collection("players")
                            .document(userID)
                            .set(player!!)
                            .addOnSuccessListener { documentReference ->
                                Log.d("TAG", "onSuccess: existing profile is updated with id ${player.id} by user ${userID}")
                                Toast.makeText(this, "Successfully updated user profile!", Toast.LENGTH_SHORT).show()

                                // redirect to home
                                redirectToHome()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error updating player document!", Toast.LENGTH_SHORT).show()
                                Log.w("TAG", "Error updating player document", e)

                                // redirect to home
                                redirectToHome()
                                finish()
                            }
                    }
                }
            }
            .addOnFailureListener{ exception ->
                Log.d("TAG", "Error getting documents: ", exception)
                found = 1
            }
    }
}