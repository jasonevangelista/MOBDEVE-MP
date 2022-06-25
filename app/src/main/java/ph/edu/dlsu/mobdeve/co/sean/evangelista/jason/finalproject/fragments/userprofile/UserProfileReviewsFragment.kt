package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.userprofile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufiqrahman.reviewratings.BarLabels
import com.taufiqrahman.reviewratings.RatingReviews
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.AddReviewActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.ReviewAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.ReviewsDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.ReviewsDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentUserProfileReviewsBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Review
import java.time.LocalDate
import java.util.*


class UserProfileReviewsFragment : Fragment(R.layout.fragment_user_profile_reviews) {

    private var _binding : FragmentUserProfileReviewsBinding? = null
    private val binding get() = _binding!!

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        viewManager = LinearLayoutManager(activity)
        reviewAdapter = ReviewAdapter(requireContext(), reviewArrayList)

        binding.rvPlayerReviews.apply {
            layoutManager = viewManager
            adapter = reviewAdapter
        }

        binding.btnAddReview.setOnClickListener {
            val goToAddReview = Intent(activity, AddReviewActivity::class.java)
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
        setRatingVisualization()
    }

    private fun init(){
        var dao: ReviewsDAO = ReviewsDAOArrayImpl()

        var review1 = Review()
        review1.sender = "player2"
        review1.receiver = "player1"
        review1.content = "Great teammate!!!"
        review1.rating = 5.0F
        review1.date = LocalDate.now()
        dao.addReview(review1)

        dao.addReview(review1)
        dao.addReview(review1)
        dao.addReview(review1)
        dao.addReview(review1)
        dao.addReview(review1)
        dao.addReview(review1)

        reviewArrayList = dao.getReviews()

    }

    private fun setRatingVisualization(){

        val color = Color.parseColor("#8EA8C3")
        // rating arrangement in array -> [5,4,3,2,1]
        val raters = intArrayOf(
            10,
            15,
            5,
            30,
            100
        )
        val averageRating: Float = 4.7F
        val totalReviews: Int = 20

        var maxRaterValue = raters.maxOrNull()
        if (maxRaterValue == null){
            maxRaterValue = 0
        }

        binding.tvAverageReviews.text = averageRating.toString()
        binding.tvTotalReviewCount.text = "$totalReviews reviews"
        binding.ratingReviews.createRatingBars(maxRaterValue, BarLabels.STYPE1, color, raters)
        reviewAdapter.notifyDataSetChanged()
    }

}