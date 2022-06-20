package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.userprofile

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentUserProfileAboutMeBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player


class UserProfileAboutMeFragment : Fragment(R.layout.fragment_user_profile_about_me) {

    private var _binding : FragmentUserProfileAboutMeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileAboutMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val player = requireArguments().getParcelable<Player>("player")
        setDetails(player)
    }

    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setDetails(player: Player?){
//        Log.d(ContentValues.TAG, "PLAYER ID: ${player!!.id}")
//        Log.d(ContentValues.TAG, "PLAYER BIO: ${player.bio}")
        binding.tvUserBioContent.text = player!!.bio
        binding.tvUserGamingHoursContent.text = player.gaming_hours
        binding.tvUserDiscord.text = "Discord: " + player.discord
        binding.tvUserTwitter.text = "Twitter: " + player.twitter
        binding.tvUserOtherSocials.text = "Other Socials: " + player.other_socials
    }

}