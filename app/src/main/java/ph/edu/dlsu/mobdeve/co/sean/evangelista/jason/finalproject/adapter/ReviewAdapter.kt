package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ItemPlayerReviewBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Review

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private var reviewArrayList = ArrayList<Review>()
    private lateinit var context: Context

    public constructor(context: Context, reviewArrayList: ArrayList<Review>){
        this.context = context
        this.reviewArrayList = reviewArrayList
    }

    override fun getItemCount(): Int {
        return reviewArrayList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemBinding = ItemPlayerReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bindReview(reviewArrayList[position])
    }


    inner class ReviewViewHolder(private val itemBinding : ItemPlayerReviewBinding)
        : RecyclerView.ViewHolder(itemBinding.root){

            fun bindReview(review:Review){
                var rating: String = "★ " + review.rating.toString()

                itemBinding.tvReviewerName.text =  review.sender
                itemBinding.tvReviewContent.text = review.content
                itemBinding.tvReviewRating.text = rating
                itemBinding.tvReviewDate.text = review.date.toString()
            }

        }
}