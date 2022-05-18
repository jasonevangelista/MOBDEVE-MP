package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.dao

import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Review


interface ReviewsDAO {
    fun addReview(review: Review)
    fun getReviews(): ArrayList<Review>
}

class ReviewsDAOArrayImpl: ReviewsDAO{
    private var arrayListReviews = ArrayList<Review>()

    override fun addReview(review: Review){
        arrayListReviews.add(0, review)
    }

    override fun getReviews(): ArrayList<Review>{
        return arrayListReviews
    }
}