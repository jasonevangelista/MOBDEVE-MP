package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.myprofile

import android.content.ContentValues
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
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
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

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var playerArrayList: ArrayList<Player>
    private lateinit var viewManager : LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileHistoryBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        db = Firebase.firestore

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun init(){

        var dao: PlayersDAO = PlayersDAOArrayImpl()

        // query history documents
        db.collection("players")
            .document(auth.currentUser!!.uid)
            .collection("history")
            .get()
            .addOnSuccessListener { result ->
                Log.d("HISTORY_LOG", "Successfully added a history log!")
                for (docHistory in result) {
                    Log.d("HISTORY_LOG", "${docHistory.id} => ${docHistory.data}")

                    // query player document per id in history documents
                    db.collection("players")
                        .document(docHistory.data.get("player_id").toString())
                        .get()
                        .addOnSuccessListener { player_document ->

                            var date_timestamp: Timestamp = docHistory.data.get("date") as Timestamp
                            var date: Date = date_timestamp.toDate()


                            if(player_document != null){
                                // set profile details from in UI
                                val player = player_document.toObject<Player>()
                                player!!.connect_date = date
                                player.id = player_document.id

                                Log.d(
                                "HISTORY_LOG",
                                "\nID: ${player.id}\n" +
                                        "DATE CONNECTED: ${player.connect_date}\n" +
                                        "USERNAME: ${player.username}\n" +
                                        "RATING: ${player.rating}\n" +
                                        "DATE: ${date}"
                                )

                                // add player to playerlist
                                dao.addPlayer(player)
                                playerArrayList = dao.getPlayers()

                                viewManager = LinearLayoutManager(activity)
                                playerAdapter = PlayerAdapter(requireContext(), playerArrayList)

                                binding.rvMyPlayerHistory.apply {
                                    layoutManager = viewManager
                                    adapter = playerAdapter
                                }

                                playerAdapter.onItemClick = { player ->
                                    val goToUserProfile = Intent(activity, UserProfileActivity::class.java)

                                    goToUserProfile.putExtra("player", player)

                                    activity?.startActivity(goToUserProfile)
                                }

                            sortPlayers()
                            addSortListener()
                            }

                        }.addOnFailureListener { exception ->
                            Log.d(ContentValues.TAG, "Error retrieving player id ${docHistory.data.get("player_id").toString()} : ", exception)
                        }

                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error retrieving history document: ", exception)
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

    private fun sortPlayers(){
        var currPlayers: ArrayList<Player> = playerAdapter.getItems()
        var sortedPlayers = ArrayList<Player>()

        if (currPlayers.size > 0){
            if(binding.spSortPlayer.selectedItem == "Latest Connect Date"){
                sortedPlayers = currPlayers.sortedWith(compareBy { it.connect_date }).reversed().toCollection(ArrayList())
            }
            else if(binding.spSortPlayer.selectedItem == "Highest Ratings"){
                sortedPlayers = currPlayers.sortedWith(compareBy { it.rating }).reversed().toCollection(ArrayList())
            }

            viewManager = LinearLayoutManager(activity)
            playerAdapter = PlayerAdapter(requireContext(), sortedPlayers)

            binding.rvMyPlayerHistory.apply {
                layoutManager = viewManager
                adapter = playerAdapter
            }

            playerAdapter.onItemClick = { player ->
                val goToUserProfile = Intent(activity, UserProfileActivity::class.java)

                goToUserProfile.putExtra("player", player)

                activity?.startActivity(goToUserProfile)
            }


        }
    }

}