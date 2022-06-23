package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.userprofile

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.GameAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.GamesDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.GamesDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentUserProfileAboutMeBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentUserProfileGamesBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Game
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player


class UserProfileGamesFragment : Fragment(R.layout.fragment_user_profile_games) {

    private var _binding : FragmentUserProfileGamesBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: FirebaseFirestore

    private lateinit var gameAdapter: GameAdapter
    private lateinit var gameArrayList: ArrayList<Game>
    private lateinit var viewManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserProfileGamesBinding.inflate(inflater, container, false)

        db = Firebase.firestore
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val player = requireArguments().getParcelable<Player>("player")
//        setDetails(player)
        init(player!!.id)
    }

    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun init(playerID: String?){

        if (playerID != null){
            var dao: GamesDAO = GamesDAOArrayImpl()

            Log.d("GAMES", "player ID: ${playerID}")

            db.collection("players").document(playerID)
                .collection("games")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        // convert document to game object
                        val currGame = document.toObject<Game>()
                        currGame.id = document.id

                        // add game to game array
                        dao.addGame(currGame)

//                        gameAdapter.notifyDataSetChanged()

                        Log.d(ContentValues.TAG, "CURR DOC: ${currGame}")
                    }
                    gameArrayList = dao.getGames()

                    viewManager = LinearLayoutManager(activity)
                    gameAdapter = GameAdapter(requireContext(), gameArrayList, showEditBtn = false)

                    binding.rvGameList.apply{
                        layoutManager = viewManager
                        adapter = gameAdapter
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "Error getting game documents: ", exception)
                }

        }

    }

}