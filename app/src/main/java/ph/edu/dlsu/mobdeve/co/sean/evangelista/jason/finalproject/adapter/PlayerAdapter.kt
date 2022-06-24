package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.UserProfileActivity
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ItemPlayerListBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

    fun getItems(): ArrayList<Player> {
        return playerArrayList
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
                val rating = "★ " + player.rating.toString()

                itemBinding.textUsername.text = player.username
                itemBinding.textRating.text = rating
                itemBinding.ivPlayerImage.setImageResource(R.drawable.ic_profile)

                if(player.connect_date != null){
                    val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")


                    // show connect date in UI
                    itemBinding.textDateContact.visibility = View.VISIBLE
                    // hide unneeded card text views
                    itemBinding.textRank.visibility = View.GONE
                    itemBinding.textMessage.visibility = View.GONE

//                    var dateFormatted = player.connect_date.toString()
                    var dateFormatted = format.format(player.connect_date)
                    itemBinding.textDateContact.text = dateFormatted
                }
                else{
                    // hide connect date
                    itemBinding.textDateContact.visibility = View.GONE
                    // show needed card text views
                    itemBinding.textRank.visibility = View.VISIBLE
                    itemBinding.textMessage.visibility = View.VISIBLE

                    val rank = "${player.featured_game} - ${player.rank}"
                    itemBinding.textRank.text = rank
                    itemBinding.textMessage.text = player.message

                }
            }

            init {
                itemBinding.cvTeammate.setOnClickListener{
                    onItemClick?.invoke(playerArrayList[adapterPosition])
                }
            }

        }

}