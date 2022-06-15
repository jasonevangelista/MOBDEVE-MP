package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ItemGameListBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Game

class GameAdapter: RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private var gameArrayList = ArrayList<Game>()
    private lateinit var context: Context

    var onEditButtonClick: ((Game) -> Unit)? = null

    public constructor(context: Context, gameArrayList: ArrayList<Game>){
        this.context = context
        this.gameArrayList = gameArrayList
    }

    override fun getItemCount(): Int {
        return gameArrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.GameViewHolder {
        val itemBinding = ItemGameListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: GameAdapter.GameViewHolder, position: Int) {
        holder.bindGame(gameArrayList[position])
    }

    inner class GameViewHolder(private val itemBinding: ItemGameListBinding)
        : RecyclerView.ViewHolder(itemBinding.root){

            @RequiresApi(Build.VERSION_CODES.O)
            fun bindGame(game: Game){
                val label: String = "Game: " + game.name.toString()
                val rank: String = "Rank\t\t\t\t\t" + game.rank.toString()
                val server: String = "Server(s)\t\t" + game.server.toString()
                val role: String = "Role(s)\t\t\t" + game.role.toString()

                itemBinding.tvMyGameLabel.text = label
                itemBinding.textGameRank.text = rank
                itemBinding.textGameServer.text = server
                itemBinding.textGameRole.text = role
            }

            init {
                itemBinding.btnMyGameEdit.setOnClickListener{
                    onEditButtonClick?.invoke(gameArrayList[adapterPosition])
                }
            }

        }
}