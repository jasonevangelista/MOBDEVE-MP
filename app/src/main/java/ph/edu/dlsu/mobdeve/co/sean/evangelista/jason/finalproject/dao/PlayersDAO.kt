package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao

import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player

interface PlayersDAO {
    fun addPlayer(player: Player)
    fun getPlayers(): ArrayList<Player>
}

class PlayersDAOArrayImpl: PlayersDAO{
    private var arrayListPlayers = ArrayList<Player>()

    override fun addPlayer(player: Player){
        arrayListPlayers.add(0, player)
    }

    override fun getPlayers(): ArrayList<Player>{
        return arrayListPlayers
    }
}