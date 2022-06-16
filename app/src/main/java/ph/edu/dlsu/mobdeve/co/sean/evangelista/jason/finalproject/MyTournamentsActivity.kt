package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
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
import kotlin.collections.ArrayList

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

                filterTournaments()
                sortTournaments()

                addFilterListener()
                addSortListener()

            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting tournament documents: ", exception)
            }


        tournamentArrayList = dao.getTournaments()
    }

    private fun addFilterListener(){
        binding.spFilterTournament.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@MyTournamentsActivity, "Changed filter option to ${binding.spFilterTournament.selectedItem}", Toast.LENGTH_SHORT).show()
                filterTournaments()
                sortTournaments()

            }


        }
    }

    private fun addSortListener(){
        binding.spSortTournament.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@MyTournamentsActivity, "Changed sort option to ${binding.spSortTournament.selectedItem}", Toast.LENGTH_SHORT).show()
                sortTournaments()
            }
        }
    }

    private fun filterTournaments(){
        if(::tournamentArrayList.isInitialized){
            var filteredTournaments = ArrayList<Tournament>()

            // include tournaments with any featured game
            if(binding.spFilterTournament.selectedItem == "All Games"){
                filteredTournaments = tournamentArrayList
            }

            // include only tournaments with the featured game selected
            else{
                for (tournament in tournamentArrayList) {
                    if(tournament.featured_game == binding.spFilterTournament.selectedItem){
                        filteredTournaments.add(tournament)
                    }
                }
            }

            tournamentAdapter = MyTournamentAdapter(this@MyTournamentsActivity, filteredTournaments)

            binding.rvMyTournamentsList.apply {
                layoutManager = viewManager
                adapter = tournamentAdapter
            }

            tournamentAdapter.onItemClick = { tournament ->
                val goToTournamentProfile = Intent(this@MyTournamentsActivity, TournamentProfileActivity::class.java)
                goToTournamentProfile.putExtra("tournament", tournament)
                startActivity(goToTournamentProfile)
            }

            tournamentAdapter.onEditButtonClick = {tournament ->
                val goToEditTournament = Intent(this@MyTournamentsActivity, EditTournamentActivity::class.java)
                goToEditTournament.putExtra("tournament", tournament)
                startActivity(goToEditTournament)
            }

        }
    }

    private fun sortTournaments() {
        var currTournaments: ArrayList<Tournament> = tournamentAdapter.getItems()
        var sortedTournaments = ArrayList<Tournament>()

        if (currTournaments.size > 0) {
            if (binding.spSortTournament.selectedItem == "Nearest Start Date") {
                sortedTournaments = currTournaments.sortedWith(compareBy { it.start_date })
                    .toCollection(ArrayList())
            } else if (binding.spSortTournament.selectedItem == "Farthest Start Date") {
                sortedTournaments =
                    currTournaments.sortedWith(compareBy { it.start_date }).reversed()
                        .toCollection(ArrayList())
            }

            tournamentAdapter = MyTournamentAdapter(this@MyTournamentsActivity, sortedTournaments)

            binding.rvMyTournamentsList.apply {
                layoutManager = viewManager
                adapter = tournamentAdapter
            }

            tournamentAdapter.onItemClick = { tournament ->
                val goToTournamentProfile =
                    Intent(this@MyTournamentsActivity, TournamentProfileActivity::class.java)
                goToTournamentProfile.putExtra("tournament", tournament)
                startActivity(goToTournamentProfile)
            }

            tournamentAdapter.onEditButtonClick = { tournament ->
                val goToEditTournament =
                    Intent(this@MyTournamentsActivity, EditTournamentActivity::class.java)
                goToEditTournament.putExtra("tournament", tournament)
                startActivity(goToEditTournament)
            }

        }

    }
}