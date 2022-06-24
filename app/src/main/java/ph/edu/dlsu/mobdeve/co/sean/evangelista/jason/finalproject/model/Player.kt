package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

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
    var message: String = "This is my message.",
    var gaming_hours: String = "This is my gaming hours.",
//    var socials: ArrayList<String>? = null,
//    var games: ArrayList<String>? = null,
//    var history: ArrayList<String>? = null,
    var discord: String = "None",
    var twitter: String = "None",
    var other_socials: String = "None",
    var img_url: String? = null,
    var featured_game: String? = null,
    var rank: String? = null,
    var rating: Float = 5.0F,

    var connect_date: Date? = null
) : Parcelable {
}