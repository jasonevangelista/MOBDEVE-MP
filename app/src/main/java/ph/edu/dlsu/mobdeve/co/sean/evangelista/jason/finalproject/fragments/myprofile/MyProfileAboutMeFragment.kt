package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.myprofile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.AddReviewActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.EditProfileActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.MainActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentMyProfileAboutMeBinding

class MyProfileAboutMeFragment : Fragment(R.layout.fragment_my_profile_about_me) {

    private var _binding : FragmentMyProfileAboutMeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileAboutMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(auth.currentUser != null){
            var email = auth.currentUser!!.email
            binding.tvMyUsername.text = email
        }

        binding.btnEditProfile.setOnClickListener {
            val goToEditProfile = Intent(activity, EditProfileActivity::class.java)
            activity?.startActivity(goToEditProfile)
        }

        binding.btnLogout.setOnClickListener {
            Firebase.auth.signOut()

            val goToLogin = Intent(activity, MainActivity::class.java)
            activity?.startActivity(goToLogin)
            activity?.finish()
        }

    }

    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}