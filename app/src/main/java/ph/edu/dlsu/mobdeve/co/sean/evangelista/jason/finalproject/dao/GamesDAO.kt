package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao

import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Game

interface GamesDAO {
    fun addGame(game: Game)
    fun getGames(): ArrayList<Game>
}

class GamesDAOArrayImpl: GamesDAO{

    private var arrayListGames = ArrayList<Game>()

    override fun addGame(game: Game){
        arrayListGames.add(0, game)
    }

    override fun getGames(): ArrayList<Game> {
        return arrayListGames
    }
}