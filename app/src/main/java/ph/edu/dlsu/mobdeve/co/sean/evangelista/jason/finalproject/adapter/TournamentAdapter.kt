package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ItemMyTournamentsListBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ItemTournamentListBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Tournament

class TournamentAdapter: RecyclerView.Adapter<TournamentAdapter.TournamentViewHolder> {

    private var tournamentArrayList = ArrayList<Tournament>()
    private lateinit var context: Context

    var onItemClick: ((Tournament) -> Unit)? = null

    public constructor(context: Context, tournamentArrayList: ArrayList<Tournament>){
        this.context = context
        this.tournamentArrayList = tournamentArrayList
    }

    override fun getItemCount(): Int {
        return tournamentArrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentAdapter.TournamentViewHolder {
        val itemBinding = ItemTournamentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TournamentViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TournamentAdapter.TournamentViewHolder, position: Int) {
        holder.bindTournament(tournamentArrayList[position])
    }


    inner class TournamentViewHolder(private val itemBinding: ItemTournamentListBinding)
        : RecyclerView.ViewHolder(itemBinding.root){

            fun bindTournament(tournament: Tournament){
                val slots: String = tournament.current_capacity.toString() + " / " + tournament.max_capacity.toString()

                itemBinding.textTournamentName.text = tournament.name
                itemBinding.textTournamentSlots.text = slots
                itemBinding.textTournamentDate.text = tournament.start_date.toString()
            }

            init {
                itemBinding.cvTournament.setOnClickListener {
                    onItemClick?.invoke(tournamentArrayList[adapterPosition])
                }
            }
        }
}

class MyTournamentAdapter: RecyclerView.Adapter<MyTournamentAdapter.TournamentViewHolder> {

    private var tournamentArrayList = ArrayList<Tournament>()
    private lateinit var context: Context

    var onItemClick: ((Tournament) -> Unit)? = null
    var onEditButtonClick: ((Tournament) -> Unit)? = null

    public constructor(context: Context, tournamentArrayList: ArrayList<Tournament>){
        this.context = context
        this.tournamentArrayList = tournamentArrayList
    }

    override fun getItemCount(): Int {
        return tournamentArrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTournamentAdapter.TournamentViewHolder {
        val itemBinding = ItemMyTournamentsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TournamentViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyTournamentAdapter.TournamentViewHolder, position: Int) {
        holder.bindTournament(tournamentArrayList[position])
    }


    inner class TournamentViewHolder(private val itemBinding: ItemMyTournamentsListBinding)
        : RecyclerView.ViewHolder(itemBinding.root){

        fun bindTournament(tournament: Tournament){
            val slots: String = tournament.current_capacity.toString() + " / " + tournament.max_capacity.toString()

            itemBinding.textTournamentName.text = tournament.name
            itemBinding.textTournamentSlots.text = slots
            itemBinding.textTournamentDate.text = tournament.start_date.toString()
        }

        init {
            itemBinding.cvMyTournament.setOnClickListener {
                onItemClick?.invoke(tournamentArrayList[adapterPosition])
            }
            itemBinding.btnEditTournament.setOnClickListener {
                onEditButtonClick?.invoke(tournamentArrayList[adapterPosition])
            }
        }
    }
}