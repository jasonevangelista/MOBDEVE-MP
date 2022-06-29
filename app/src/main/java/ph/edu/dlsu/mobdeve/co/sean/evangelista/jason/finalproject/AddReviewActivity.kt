package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityAddReviewBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Review
import java.time.LocalDate
import java.util.*
import kotlin.math.roundToInt


class AddReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddReviewBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        db = Firebase.firestore

        val playerReceiver = intent.getParcelableExtra<Player>("player")

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Back"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

//        binding.btnBack.setOnClickListener {
//            // go back to previous page
//            finish()
//        }

        binding.btnCancelReview.setOnClickListener {
            // go back to previous page
            finish()
        }

        binding.btnAddReview.setOnClickListener {
            Log.d("REVIEW", "ADDING NEW REVIEW")

            var senderUsername: String? = null
            db.collection("players")
                .document(auth.currentUser!!.uid)
                .get()
                .addOnSuccessListener { playerDoc ->
                    if(playerDoc != null){
                        val playerSender = playerDoc.toObject<Player>()
                        senderUsername = playerSender!!.username.toString()

                        // submit review to database
                        var userID = auth.currentUser!!.uid
                        var sender = senderUsername
                        var receiver = playerReceiver!!.username
                        var content = binding.etAddReview.text.toString()
                        var rating = binding.addRating.rating
                        var date: Date = Timestamp.now().toDate()

                        var newReview = Review(
                            sender = sender,
                            creator_id = userID,
                            receiver = receiver,
                            content = content,
                            rating = rating,
                            date = date
                        )

                        // FORM INPUT HANDLING
                        if(checkFormInputErrors(newReview) == 1) {
                            // ADD REVIEW TO DB
                            Log.d("REVIEW", "ADDING REVIEW TO DB")
                            db.collection("players")
                                .document(playerReceiver.id.toString()).collection("reviews")
                                .add(newReview)
                                .addOnSuccessListener { documentReference ->
                                    Log.d("TAG","onSuccess: new review is added with id ${documentReference.id}")
                                    Toast.makeText(this, "Successfully added a review!", Toast.LENGTH_SHORT).show()

                                    updateRating(playerReceiver.id.toString())
                                }
                                .addOnFailureListener { e ->
                                    Log.w("TAG", "Error adding review document", e)

                                    val goToHome = Intent(this, PlayerListActivity::class.java)
                                    startActivity(goToHome)
                                    finish()
                                }
                        }
                    } else {
                        Log.d("TAG", "PLAYER NOT FOUND")
                    }
                }
                .addOnFailureListener{ e ->
                    Log.d("TAG", "Error finding document", e)
                }


            // go back to previous page
            // finish()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun checkFormInputErrors(review: Review): Int{  // 1 == success, 0 == fail
        when {
            TextUtils.isEmpty(review.content) -> {
                binding.etAddReview.setError("Review cannot be empty!")
                binding.etAddReview.requestFocus()
                return 0
            }
            review.rating == 0F -> {
                Toast.makeText(this, "Rating must have at least 1 star!", Toast.LENGTH_SHORT).show()
                return 0
            }
        }
        return 1
    }

    private fun updateRating(playerID: String){
        var aveRating = 0.0F
        db.collection("players")
            .document(playerID).collection("reviews")
            .get()
            .addOnSuccessListener { reviews ->
                for(reviewDoc in reviews){
                    aveRating += reviewDoc.toObject<Review>().rating
                }
                aveRating /= reviews.size()
                // set average rating to 1 decimal place
                aveRating = ((aveRating * 10.0).roundToInt() / 10.0).toFloat()
                Log.d("AVE RATING", "AVE RATING: ${aveRating}")

                db.collection("players")
                    .document(playerID)
                    .update("rating", aveRating)
                    .addOnSuccessListener {
                        Log.d("TAG", "Player rating successfully updated!")
                        val goToHome = Intent(this, PlayerListActivity::class.java)
                        startActivity(goToHome)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Log.w("TAG", "Error updating document", e)
                    }
            }
            .addOnFailureListener{ e ->
                Log.d("TAG", "Error finding document", e)
            }
    }
}