package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.PlayerAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.PlayersDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.PlayersDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentTeammatesBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player

class TeammatesFragment : Fragment(R.layout.fragment_teammates){

    private var _binding : FragmentTeammatesBinding? = null
    private val binding get() = _binding!!

    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var playerArrayList: ArrayList<Player>
    private lateinit var viewManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTeammatesBinding.inflate(inflater, container, false)

        viewManager = LinearLayoutManager(activity)
//        playerAdapter = PlayerAdapter(requireContext(), playerArrayList)
//        viewAdapter = PlayerAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
//        binding.rvPlayerList.layoutManager(LinearLayoutManager(requireContext()))
        binding.rvPlayerList.apply {
            layoutManager = viewManager
            adapter = PlayerAdapter(requireContext(), playerArrayList, this@TeammatesFragment::onItemClickHandler)
        }

//        binding.rvPlayerList.layoutManager = LinearLayoutManager(requireContext())
//        playerAdapter = PlayerAdapter(requireContext(), playerArrayList)
//        binding.rvPlayerList.setAdapter(playerAdapter)
//        binding.btnTest.setOnClickListener{
//            val goToLogin = Intent(activity, MainActivity::class.java)
//            activity?.startActivity(goToLogin)
////            activity?.finish()
//        }

//        binding.rvPlayerList.setonclick

    }

    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun init(){
        var dao: PlayersDAO = PlayersDAOArrayImpl()

        var player = Player()
        player.username = "player1"
        player.rating = 5.0F
        player.rank = "Iron"

        dao.addPlayer(player)
        dao.addPlayer(player)
        dao.addPlayer(player)

        playerArrayList = dao.getPlayers()
    }

    private fun onItemClickHandler(position:Int){
        Log.d("***","${position}")
    }
}