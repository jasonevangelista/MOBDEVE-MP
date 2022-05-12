package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ItemPlayerListBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player

class PlayerAdapter: RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private var playerArrayList = ArrayList<Player>()
    private lateinit var context: Context
    private lateinit var itemClickHandler: (Int) -> Unit

    public constructor(context: Context, playerArrayList: ArrayList<Player>, itemClickHandler: (Int) -> Unit){
        this.context = context
        this.playerArrayList = playerArrayList
        this.itemClickHandler = itemClickHandler
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

    class PlayerViewHolder(private val itemBinding: ItemPlayerListBinding)
        : RecyclerView.ViewHolder(itemBinding.root){

            fun bindPlayer(player:Player){
                itemBinding.textUsername.text = player.username
                itemBinding.textRating.text = player.rating.toString()
                itemBinding.textRank.text = player.rank
                itemBinding.ivPlayerImage.setImageResource(R.drawable.ic_profile)
            }




        }
}