package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.userprofile

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.taufiqrahman.reviewratings.BarLabels
import com.taufiqrahman.reviewratings.RatingReviews
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.AddReviewActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.ReviewAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.ReviewsDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.ReviewsDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityUserProfileBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentUserProfileReviewsBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Game
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Review
import java.time.LocalDate
import java.util.*


class UserProfileReviewsFragment : Fragment(R.layout.fragment_user_profile_reviews) {

    private var _binding : FragmentUserProfileReviewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: FirebaseFirestore

    private lateinit var reviewArrayList: ArrayList<Review>
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var viewManager : LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileReviewsBinding.inflate(inflater, container, false)

        db = Firebase.firestore
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val player = requireArguments().getParcelable<Player>("player")
//
//        init(player)
        reviewArrayList = ArrayList<Review>()

        val player = requireArguments().getParcelable<Player>("player")

        viewManager = LinearLayoutManager(activity)
        reviewAdapter = ReviewAdapter(requireContext(), reviewArrayList)

        binding.rvPlayerReviews.apply {
            layoutManager = viewManager
            adapter = reviewAdapter
        }

        binding.btnAddReview.setOnClickListener {
            val goToAddReview = Intent(activity, AddReviewActivity::class.java)
            goToAddReview.putExtra("player", player)
            activity?.startActivity(goToAddReview)
        }



    }

    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
//        setRatingVisualization()
        val player = requireArguments().getParcelable<Player>("player")
        init(player)
    }

    private fun init(player: Player?){
        var dao: ReviewsDAO = ReviewsDAOArrayImpl()

//        var review1 = Review()
//        review1.sender = "player2"
//        review1.receiver = "player1"
//        review1.content = "Great teammate!!!"
//        review1.rating = 5.0F
//        review1.date = Timestamp.now().toDate()
//        dao.addReview(review1)
//
//        dao.addReview(review1)
//        dao.addReview(review1)
//        dao.addReview(review1)
//        dao.addReview(review1)
//        dao.addReview(review1)
//        dao.addReview(review1)



        binding.btnAddReview.setOnClickListener {
            val goToAddReview = Intent(activity, AddReviewActivity::class.java)
            goToAddReview.putExtra("player", player)
            activity?.startActivity(goToAddReview)
        }

        if(player != null){
            db.collection("players").document(player!!.id.toString())
                .collection("reviews")
                .get()
                .addOnSuccessListener { result ->

                    for (review in result){
                        val currReview = review.toObject<Review>()
                        dao.addReview(currReview)
                    }
                    reviewArrayList = dao.getReviews()
                    // automatically sort by latest datetime
                    reviewArrayList = reviewArrayList.sortedWith(compareBy { it.date }).reversed().toCollection(ArrayList())

                    reviewAdapter = ReviewAdapter(requireContext(), reviewArrayList)

                    binding.rvPlayerReviews.apply {
                        layoutManager = viewManager
                        adapter = reviewAdapter
                    }

                    val averageRating: Float = player.rating
                    val totalReviews: Int = result.size()
                    // rating arrangement in array -> [5,4,3,2,1]
                    val ratersArray: IntArray = getRatersArray(result)

                    setRatingVisualization(averageRating, totalReviews, ratersArray)
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting game documents: ", exception)
                }
        }


    }

    private fun setRatingVisualization(averageRating: Float, totalReviews: Int, ratersArray: IntArray){
        binding.ratingReviews.clearAll()

        val color = Color.parseColor("#8EA8C3")

        var maxRaterValue = ratersArray.maxOrNull()
        Log.d("REVIEW", "MAX RATER VALUE: ${maxRaterValue}")
        if (maxRaterValue == null){
            maxRaterValue = 0
        }

        binding.tvAverageReviews.text = averageRating.toString()
        binding.tvTotalReviewCount.text = "$totalReviews reviews"
        binding.ratingReviews.createRatingBars(maxRaterValue, BarLabels.STYPE1, color, ratersArray)
        reviewAdapter.notifyDataSetChanged()
    }

    private fun getRatersArray(results: QuerySnapshot): IntArray{
        var ratersMap = hashMapOf<Int, Int>()

        // initialize count
        ratersMap[1] = 0
        ratersMap[2] = 0
        ratersMap[3] = 0
        ratersMap[4] = 0
        ratersMap[5] = 0

        for (result in results){
            val currReview = result.toObject<Review>()
            val currRating = currReview.rating.toInt()
            ratersMap[currRating] = ratersMap[currRating]!!.plus(1)
        }

        // rating arrangement in array -> [5,4,3,2,1]
        val ratersArray: IntArray = intArrayOf(ratersMap[5]!!, ratersMap[4]!!, ratersMap[3]!!, ratersMap[2]!!, ratersMap[1]!!)

        return ratersArray
    }

}