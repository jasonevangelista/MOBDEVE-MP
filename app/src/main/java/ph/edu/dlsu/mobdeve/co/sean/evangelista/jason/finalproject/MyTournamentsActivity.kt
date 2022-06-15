package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.MyTournamentAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.TournamentAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.TournamentsDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.TournamentsDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityMyTournamentsBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament
import java.time.LocalDate
import java.util.*

class MyTournamentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyTournamentsBinding

    private lateinit var tournamentAdapter: MyTournamentAdapter
    private lateinit var tournamentArrayList: ArrayList<Tournament>
    private lateinit var viewManager : LinearLayoutManager

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var userID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTournamentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore
        auth = Firebase.auth

        userID = auth.currentUser!!.uid

        init()


        tournamentAdapter = MyTournamentAdapter(this@MyTournamentsActivity, tournamentArrayList)
        viewManager = LinearLayoutManager(this@MyTournamentsActivity)

        binding.rvMyTournamentsList.apply {
            layoutManager = viewManager
            adapter = tournamentAdapter
        }

        tournamentAdapter.onItemClick = { tournament ->
            val goToTournamentProfile = Intent(this, TournamentProfileActivity::class.java)
            goToTournamentProfile.putExtra("tournament", tournament)
            startActivity(goToTournamentProfile)
        }

        tournamentAdapter.onEditButtonClick = {tournament ->
            val goToEditTournament = Intent(this, EditTournamentActivity::class.java)
            goToEditTournament.putExtra("tournament", tournament)
            startActivity(goToEditTournament)
        }
    }

    private fun init(){
        var dao: TournamentsDAO = TournamentsDAOArrayImpl()

        // document.toObject<Tournament>()
        db.collection("tournaments")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    // convert document to tournament object
                    val currTournament = document.toObject<Tournament>()
                    currTournament.id = document.id

                    // retrieve all tournaments created by user
                    if(currTournament.creator_id == userID){
                        // add tournament to tournament array
                        dao.addTournament(currTournament)

//                    binding.rvTournamentList.apply {
//                        layoutManager = viewManager
//                        adapter = tournamentAdapter
//                    }
                        tournamentAdapter.notifyDataSetChanged()
                        Log.d(ContentValues.TAG, "CURR DOC: ${currTournament}")
                    }


                }
                tournamentArrayList = dao.getTournaments()
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting tournament documents: ", exception)
            }


        tournamentArrayList = dao.getTournaments()
    }
}