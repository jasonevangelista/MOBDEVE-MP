package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.TournamentProfileActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.UserProfileActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.PlayerAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.TournamentAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.PlayersDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.PlayersDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.TournamentsDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.TournamentsDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentTeammatesBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament

class TeammatesFragment : Fragment(R.layout.fragment_teammates){

    private var _binding : FragmentTeammatesBinding? = null
    private val binding get() = _binding!!

    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var playerArrayList: ArrayList<Player>
    private lateinit var viewManager: LinearLayoutManager

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTeammatesBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        db = Firebase.firestore

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        viewManager = LinearLayoutManager(activity)
        playerAdapter = PlayerAdapter(requireContext(), playerArrayList)

        binding.rvPlayerList.apply {
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

        // query all players in players collection
        db.collection("players")
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "PLAYER DOCUMENTS:")
                for (docPlayer in result) {
                    Log.d(TAG, "${docPlayer.id} => ${docPlayer.data}")

                    // exclude logged-in user's profile in listing the players
                    if(docPlayer.id != auth.currentUser!!.uid){

                        // query all game information for each player
                        db.collection("players")
                            .document(docPlayer.id)
                            .collection("games")
                            .get()
                            .addOnSuccessListener { game_results ->
                                for(docGame in game_results){
                                    Log.d(TAG,
                                        "\nID: ${docPlayer.id}\n" +
                                            "USERNAME: ${docPlayer.data.get("username")}\n" +
                                            "RATING: ${docPlayer.data.get("rating")}\n" +
                                            "GAME: ${docGame.data.get("name")}\n" +
                                            "RANK: ${docGame.data.get("rank")}\n" +
                                            "MESSAGE: ${docPlayer.data.get("message")}\n")

                                    val currPlayer = docPlayer.toObject<Player>()
                                    currPlayer.id = docPlayer.id
                                    // add specific game of player to attributes (mainly for player card UI)
                                    currPlayer.rank = docGame.data.get("rank").toString()
                                    currPlayer.featured_game = docGame.data.get("name").toString()

                                    dao.addPlayer(currPlayer)
                                }
                                playerArrayList = dao.getPlayers()
                                playerAdapter.notifyDataSetChanged()

                                // set initial filter and sort options
                                filterPlayers()
                                sortPlayers()

                                // set listeners for filter and sort options
                                addFilterListener()
                                addSortListener()

                            }.addOnFailureListener { exception ->
                                Log.d(ContentValues.TAG, "Error getting tournament documents: ", exception)
                            }
                    }
                }

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting tournament documents: ", exception)
            }

        playerArrayList = dao.getPlayers()
    }

    private fun addFilterListener(){
        binding.spFilterPlayer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(activity, "Changed filter option to ${binding.spFilterPlayer.selectedItem}", Toast.LENGTH_SHORT).show()
                filterPlayers()
                sortPlayers() // after getting filtered players, sort them according to sort option
            }

        }
    }

    private fun addSortListener(){
        binding.spSortPlayer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(activity, "Changed sort option to ${binding.spSortPlayer.selectedItem}", Toast.LENGTH_SHORT).show()
                sortPlayers()
            }
        }
    }

    private fun filterPlayers(){
        if(::playerArrayList.isInitialized){
            var filteredPlayers = ArrayList<Player>()

//            if(binding.spFilterPlayer.selectedItem == "All Games"){
//                filteredPlayers = playerArrayList
//            }

            for (player in playerArrayList) {
                if(player.featured_game == binding.spFilterPlayer.selectedItem){
                    filteredPlayers.add(player)
                }
            }

            playerAdapter = PlayerAdapter(requireContext(), filteredPlayers)

            binding.rvPlayerList.apply {
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
    }

    private fun sortPlayers(){
        var currPlayers: ArrayList<Player> = playerAdapter.getItems()
        var sortedPlayers = ArrayList<Player>()

        if (currPlayers.size > 0){
            if(binding.spSortPlayer.selectedItem == "Lowest Ratings"){
                sortedPlayers = currPlayers.sortedWith(compareBy { it.rating }).toCollection(ArrayList())
            }
            else if(binding.spSortPlayer.selectedItem == "Highest Ratings"){
                sortedPlayers = currPlayers.sortedWith(compareBy { it.rating }).reversed().toCollection(ArrayList())
            }

            playerAdapter = PlayerAdapter(requireContext(), sortedPlayers)

            binding.rvPlayerList.apply {
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
    }



}