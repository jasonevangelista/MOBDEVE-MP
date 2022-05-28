package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTournamentsBinding.inflate(inflater, container, false)
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
        }

        binding.btnMyTournaments.setOnClickListener {
            val goToMyTournaments = Intent(activity, MyTournamentsActivity::class.java)
            activity?.startActivity(goToMyTournaments)
        }

        tournamentAdapter.onItemClick = { tournament ->
            val goToTournamentProfile = Intent(activity, TournamentProfileActivity::class.java)

//            val bundle = Bundle()
//            bundle.putString("username", player.username)
//            goToUserProfile.putExtras(bundle)
            activity?.startActivity(goToTournamentProfile)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(){
        var dao: TournamentsDAO = TournamentsDAOArrayImpl()

        var tournament = Tournament()
        tournament.name = "Iron Tourney"
        tournament.current_capacity = 5
        tournament.max_capacity = 20
        tournament.start_date = LocalDate.now()

        dao.addTournament(tournament)
        dao.addTournament(tournament)
        dao.addTournament(tournament)
        dao.addTournament(tournament)
        dao.addTournament(tournament)
        dao.addTournament(tournament)
        dao.addTournament(tournament)


        tournamentArrayList = dao.getTournaments()
    }
}