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
import androidx.recyclerview.widget.LinearLayoutManager
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

//        var player1 = Player()
//        player1.username = "player1"
//        player1.rating = 5.0F
//        player1.rank = "Iron"
        var player1 = Player(username = "player1", rating = 5.0F)
        dao.addPlayer(player1)

//        var player2 = Player()
//        player2.username = "player2"
//        player2.rating = 4.0F
//        player2.rank = "Gold"
        var player2 = Player(username = "player2", rating = 4.0F)
        dao.addPlayer(player2)

//        var player3 = Player()
//        player3.username = "player3"
//        player3.rating = 2.0F
//        player3.rank = "Bronze"
        var player3 = Player(username = "player3", rating = 2.0F)
        dao.addPlayer(player3)

        dao.addPlayer(player3)
        dao.addPlayer(player3)
        dao.addPlayer(player3)

        playerArrayList = dao.getPlayers()


//        var dao: TournamentsDAO = TournamentsDAOArrayImpl()

        // document.toObject<Tournament>()

        // query all players in players collection
        db.collection("players")
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "PLAYER DOCUMENTS:")
                for (docPlayer in result) {
                    Log.d(TAG, "${docPlayer.id} => ${docPlayer.data}")

                    // query all game information for each player
                    db.collection("players")
                        .document(docPlayer.id)
                        .collection("games")
                        .get()
                        .addOnSuccessListener { game_results ->
                            for(docGame in game_results){
//                                Log.d(TAG, "${docPlayer.id} => GAME ${docGame.data}")
                                Log.d(TAG,
                                    "\nID: ${docPlayer.id}\n" +
                                        "USERNAME: ${docPlayer.data.get("username")}\n" +
                                        "RATING: ${docPlayer.data.get("rating")}\n" +
                                        "GAME: ${docGame.data.get("name")}\n" +
                                        "RANK: ${docGame.data.get("rank")}\n" +
                                        "MESSAGE: ${docPlayer.data.get("message")}\n")
                            }
                        }.addOnFailureListener { exception ->
                            Log.d(ContentValues.TAG, "Error getting tournament documents: ", exception)
                        }

                    // convert document to tournament object
//                    val currPlayer = document.toObject<Tournament>()
//                    currTournament.id = document.id

                    // add tournament to tournament array
//                    dao.addTournament(currTournament)

//                    binding.rvTournamentList.apply {
//                        layoutManager = viewManager
//                        adapter = tournamentAdapter
//                    }
//                    tournamentAdapter.notifyDataSetChanged()

//                    Log.d(TAG, "${document.id} => ${document.data}")
//                    Log.d(ContentValues.TAG, "CURR DOC: ${currTournament}")
                }

//                tournamentArrayList = dao.getTournaments()

                // set initial filter and sort options
//                filterTournaments()
//                sortTournaments()
                // set listeners for filter and sort options
//                addFilterListener()
//                addSortListener()

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting tournament documents: ", exception)
            }

//        tournamentArrayList = dao.getTournaments()
    }

}