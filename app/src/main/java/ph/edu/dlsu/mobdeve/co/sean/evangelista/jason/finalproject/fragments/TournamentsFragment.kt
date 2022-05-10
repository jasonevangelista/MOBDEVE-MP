package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.TournamentAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.TournamentsDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.TournamentsDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentTournamentsBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament
import java.text.SimpleDateFormat
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
        viewManager = LinearLayoutManager(activity)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        binding.rvTournamentList.apply {
            layoutManager = viewManager
            adapter = TournamentAdapter(requireContext(), tournamentArrayList)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun init(){
        var dao: TournamentsDAO = TournamentsDAOArrayImpl()

        var tournament = Tournament()
        tournament.name = "Iron Tourney"
        tournament.current_capacity = 5
        tournament.max_capacity = 20
        tournament.start_date = Date()

        dao.addTournament(tournament)
        dao.addTournament(tournament)
        dao.addTournament(tournament)
        dao.addTournament(tournament)
        dao.addTournament(tournament)


        tournamentArrayList = dao.getTournaments()
    }
}