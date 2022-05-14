package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.userprofile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.AddReviewActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.UserProfileActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.PlayerAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentUserProfileReviewsBinding

class UserProfileReviewsFragment : Fragment(R.layout.fragment_user_profile_reviews) {

    private var _binding : FragmentUserProfileReviewsBinding? = null
    private val binding get() = _binding!!

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

}