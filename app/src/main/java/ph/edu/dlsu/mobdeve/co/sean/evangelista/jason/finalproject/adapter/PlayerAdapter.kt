package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.UserProfileActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ItemPlayerListBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player

class PlayerAdapter: RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private var playerArrayList = ArrayList<Player>()
    private lateinit var context: Context

    var onItemClick: ((Player) -> Unit)? = null


    public constructor(context: Context, playerArrayList: ArrayList<Player>){
        this.context = context
        this.playerArrayList = playerArrayList
    }

    override fun getItemCount(): Int{
        return playerArrayList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayerAdapter.PlayerViewHolder {
        val itemBinding = ItemPlayerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PlayerViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PlayerAdapter.PlayerViewHolder,
                                  position: Int) {
        holder.bindPlayer(playerArrayList[position])

    }

    inner class PlayerViewHolder(private val itemBinding: ItemPlayerListBinding)
        : RecyclerView.ViewHolder(itemBinding.root){

            fun bindPlayer(player:Player){
                itemBinding.textUsername.text = player.username
                itemBinding.textRating.text = player.rating.toString()
                itemBinding.textRank.text = player.rank
                itemBinding.ivPlayerImage.setImageResource(R.drawable.ic_profile)
            }

            init {
                itemBinding.cvTeammate.setOnClickListener{
                    onItemClick?.invoke(playerArrayList[adapterPosition])
                }
            }

        }

}