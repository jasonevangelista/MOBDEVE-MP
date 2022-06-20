package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.myprofile

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
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.AddGameActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.EditGameActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.GameAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.TournamentAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.GamesDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.GamesDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentMyProfileGamesBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Game
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament

class MyProfileGamesFragment : Fragment(R.layout.fragment_my_profile_games) {

    private var _binding : FragmentMyProfileGamesBinding? = null
    private val binding get() = _binding!!

    private lateinit var gameAdapter: GameAdapter
    private lateinit var gameArrayList: ArrayList<Game>
    private lateinit var viewManager : LinearLayoutManager

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyProfileGamesBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        db = Firebase.firestore

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        viewManager = LinearLayoutManager(activity)
        gameAdapter = GameAdapter(requireContext(), gameArrayList)

        binding.rvGameList.apply{
            layoutManager = viewManager
            adapter = gameAdapter
        }

        binding.btnAddGame.setOnClickListener {
            val goToAddGame = Intent(activity, AddGameActivity::class.java)
            activity?.startActivity(goToAddGame)
        }

        gameAdapter.onEditButtonClick = { game ->
            val goToEditGame = Intent(activity, EditGameActivity::class.java)
            goToEditGame.putExtra("game", game)
            activity?.startActivity(goToEditGame)
        }

    }

    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun init() {
        var dao: GamesDAO = GamesDAOArrayImpl()

        db.collection("players").document(auth.currentUser!!.uid)
            .collection("games")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    // convert document to game object
                    val currGame = document.toObject<Game>()
                    currGame.id = document.id

                    // add game to game array
                    dao.addGame(currGame)

                    gameAdapter.notifyDataSetChanged()

                    Log.d(TAG, "CURR DOC: ${currGame}")
                }
                gameArrayList = dao.getGames()
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting game documents: ", exception)
            }

        gameArrayList = dao.getGames()
    }
}
