package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.MyTournamentAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.TournamentAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.TournamentsDAO
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao.TournamentsDAOArrayImpl
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityMyTournamentsBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament
import java.util.*

class MyTournamentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyTournamentsBinding

    private lateinit var tournamentAdapter: MyTournamentAdapter
    private lateinit var tournamentArrayList: ArrayList<Tournament>
    private lateinit var viewManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTournamentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        tournamentAdapter = MyTournamentAdapter(this@MyTournamentsActivity, tournamentArrayList)
        viewManager = LinearLayoutManager(this@MyTournamentsActivity)

        binding.rvMyTournamentsList.apply {
            layoutManager = viewManager
            adapter = tournamentAdapter
        }

        tournamentAdapter.onItemClick = { tournament ->
            val goToTournamentProfile = Intent(this, TournamentProfileActivity::class.java)
            startActivity(goToTournamentProfile)
        }

        tournamentAdapter.onEditButtonClick = {tournament ->
            val goToEditTournament = Intent(this, EditTournamentActivity::class.java)
            startActivity(goToEditTournament)
        }
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