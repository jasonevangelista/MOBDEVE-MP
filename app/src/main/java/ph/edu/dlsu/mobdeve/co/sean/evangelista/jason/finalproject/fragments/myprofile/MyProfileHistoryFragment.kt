package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.myprofile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.UserProfileActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.PlayerAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.PlayersDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.PlayersDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentMyProfileHistoryBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament
import java.util.*

class MyProfileHistoryFragment : Fragment(R.layout.fragment_my_profile_history) {

    private var _binding : FragmentMyProfileHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var playerArrayList: ArrayList<Player>
    private lateinit var viewManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        viewManager = LinearLayoutManager(activity)
        playerAdapter = PlayerAdapter(requireContext(), playerArrayList)

        binding.rvMyPlayerHistory.apply {
            layoutManager = viewManager
            adapter = playerAdapter
        }

        playerAdapter.onItemClick = { player ->
            val goToUserProfile = Intent(activity, UserProfileActivity::class.java)

            val bundle = Bundle()
            bundle.putString("username", player.username)
            goToUserProfile.putExtras(bundle)
            
            activity?.startActivity(goToUserProfile)
        }

    }

    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun init(){
        var dao: PlayersDAO = PlayersDAOArrayImpl()

        var player1 = Player()
        player1.username = "player1"
        player1.rating = 5.0F
        player1.rank = "Iron"
        dao.addPlayer(player1)

        var player2 = Player()
        player2.username = "player2"
        player2.rating = 4.0F
        player2.rank = "Gold"
        dao.addPlayer(player2)

        var player3 = Player()
        player3.username = "player3"
        player3.rating = 2.0F
        player3.rank = "Bronze"
        dao.addPlayer(player3)

        dao.addPlayer(player3)
        dao.addPlayer(player3)
        dao.addPlayer(player3)

        playerArrayList = dao.getPlayers()
    }

}