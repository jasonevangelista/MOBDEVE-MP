package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.*
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.TournamentAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.TournamentsDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.TournamentsDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentTournamentsBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class TournamentsFragment : Fragment(R.layout.fragment_tournaments) {

    private var _binding : FragmentTournamentsBinding? = null
    private val binding get() = _binding!!

    private lateinit var tournamentAdapter: TournamentAdapter
    private lateinit var tournamentArrayList: ArrayList<Tournament>
    private lateinit var viewManager : LinearLayoutManager

    private lateinit var db: FirebaseFirestore

//    private var dataRetrieved: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTournamentsBinding.inflate(inflater, container, false)

        db = Firebase.firestore

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        viewManager = LinearLayoutManager(activity)
        tournamentAdapter = TournamentAdapter(requireContext(), tournamentArrayList)

        binding.rvTournamentList.apply {
            layoutManager = viewManager
            adapter = tournamentAdapter
        }

        binding.btnAddTournament.setOnClickListener {
            val goToAddTournament = Intent(activity, AddTournamentActivity::class.java)
            activity?.startActivity(goToAddTournament)
            activity?.finish()
        }

        binding.btnMyTournaments.setOnClickListener {
            val goToMyTournaments = Intent(activity, MyTournamentsActivity::class.java)
            activity?.startActivity(goToMyTournaments)
        }

        tournamentAdapter.onItemClick = { tournament ->
            Log.d(ContentValues.TAG, "ITEM CLICKED: ${tournament}")
            val goToTournamentProfile = Intent(activity, TournamentProfileActivity::class.java)
            goToTournamentProfile.putExtra("tournament", tournament)
            activity?.startActivity(goToTournamentProfile)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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

                    // add tournament to tournament array
                    dao.addTournament(currTournament)

//                    binding.rvTournamentList.apply {
//                        layoutManager = viewManager
//                        adapter = tournamentAdapter
//                    }
                    tournamentAdapter.notifyDataSetChanged()

//                    Log.d(TAG, "${document.id} => ${document.data}")
                    Log.d(TAG, "CURR DOC: ${currTournament}")
                }

                tournamentArrayList = dao.getTournaments()

                // set initial filter and sort options
                filterTournaments()
                sortTournaments()
                // set listeners for filter and sort options
                addFilterListener()
                addSortListener()

            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting tournament documents: ", exception)
            }

        tournamentArrayList = dao.getTournaments()
    }

    private fun addFilterListener(){
        binding.spFilterTournament.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(activity, "Changed filter option to ${binding.spFilterTournament.selectedItem}", Toast.LENGTH_SHORT).show()
                filterTournaments()
                sortTournaments() // after getting filtered tournaments, sort them according to sort option
            }

        }
    }

    private fun addSortListener(){
        binding.spSortTournament.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(activity, "Changed sort option to ${binding.spSortTournament.selectedItem}", Toast.LENGTH_SHORT).show()
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

            tournamentAdapter = TournamentAdapter(requireContext(), filteredTournaments)

            binding.rvTournamentList.apply {
                layoutManager = viewManager
                adapter = tournamentAdapter
            }

            tournamentAdapter.onItemClick = { tournament ->
                Log.d(ContentValues.TAG, "ITEM CLICKED: ${tournament}")
                val goToTournamentProfile = Intent(activity, TournamentProfileActivity::class.java)
                goToTournamentProfile.putExtra("tournament", tournament)
                activity?.startActivity(goToTournamentProfile)
            }

        }
    }

    private fun sortTournaments(){
        var currTournaments: ArrayList<Tournament> = tournamentAdapter.getItems()
        var sortedTournaments = ArrayList<Tournament>()

        if (currTournaments.size > 0){
            if(binding.spSortTournament.selectedItem == "Nearest Start Date"){
                sortedTournaments = currTournaments.sortedWith(compareBy { it.start_date }).toCollection(ArrayList())
            }
            else if(binding.spSortTournament.selectedItem == "Farthest Start Date"){
                sortedTournaments = currTournaments.sortedWith(compareBy { it.start_date }).reversed().toCollection(ArrayList())
            }

            tournamentAdapter = TournamentAdapter(requireContext(), sortedTournaments)

            binding.rvTournamentList.apply {
                layoutManager = viewManager
                adapter = tournamentAdapter
            }

            tournamentAdapter.onItemClick = { tournament ->
                Log.d(ContentValues.TAG, "ITEM CLICKED: ${tournament}")
                val goToTournamentProfile = Intent(activity, TournamentProfileActivity::class.java)
                goToTournamentProfile.putExtra("tournament", tournament)
                activity?.startActivity(goToTournamentProfile)
            }


        }
    }
}