package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.userprofile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.AddReviewActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.UserProfileActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.PlayerAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.ReviewAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.ReviewsDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.ReviewsDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentUserProfileReviewsBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Review
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

    private fun init(){
        var dao: ReviewsDAO = ReviewsDAOArrayImpl()

        var review1 = Review()
        review1.sender = "player2"
        review1.receiver = "player1"
        review1.content = "Great teammate!!!"
        review1.rating = 5.0F
        review1.date = Date()
        dao.addReview(review1)

        dao.addReview(review1)
        dao.addReview(review1)
        dao.addReview(review1)
        dao.addReview(review1)
        dao.addReview(review1)
        dao.addReview(review1)

        reviewArrayList = dao.getReviews()

    }

}