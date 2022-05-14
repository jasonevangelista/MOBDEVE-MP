package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.myprofile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.AddGameActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentMyProfileGamesBinding

class MyProfileGamesFragment : Fragment(R.layout.fragment_my_profile_games) {

    private var _binding : FragmentMyProfileGamesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddGame.setOnClickListener {
            val goToAddGame = Intent(activity, AddGameActivity::class.java)
            activity?.startActivity(goToAddGame)
        }

    }


    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}