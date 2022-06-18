package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//class Player {
//    lateinit var username:String
//    var rating: Float = 0.0F
//    lateinit var rank: String
//
//}

@Parcelize
data class Player(
    var id: String? = null,
    var username: String? = null,
    var email: String? = null,
    var bio: String = "This is my bio.",
    var message: String = "this is my message.",
    var gaming_hours: String = "Anytime",
    var socials: ArrayList<String>? = null,
    var games: ArrayList<String>? = null,
    var history: ArrayList<String>? = null,
    var img_url: String? = null,
    var rating: Float = 0.0F,
    var featured_game: String? = null,
    var rank: String? = null
): Parcelable{

}