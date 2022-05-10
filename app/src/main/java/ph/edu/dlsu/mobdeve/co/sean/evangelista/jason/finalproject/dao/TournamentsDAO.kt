package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao

import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament


interface TournamentsDAO {
    fun addTournament(tournament: Tournament)
    fun getTournaments(): ArrayList<Tournament>
}

class TournamentsDAOArrayImpl: TournamentsDAO{

    private var arrayListTournaments = ArrayList<Tournament>()

    override fun addTournament(tournament: Tournament){
        arrayListTournaments.add(0, tournament)
    }

    override fun getTournaments(): ArrayList<Tournament> {
        return arrayListTournaments
    }
}