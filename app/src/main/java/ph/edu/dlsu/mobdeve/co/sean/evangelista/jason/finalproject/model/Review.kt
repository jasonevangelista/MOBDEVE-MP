package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model

import android.os.Build
import java.time.LocalDate
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.parcelize.Parcelize
import java.util.*

//class Review {
//    lateinit var sender : String
//    lateinit var receiver : String
//    lateinit var content : String
//    var rating : Float = 0F
//    lateinit var date : LocalDate
//}

@RequiresApi(Build.VERSION_CODES.O)
@Parcelize
data class Review (
    var id: String? = null,
    var sender: String? = null,
    var creator_id: String? = null,
    var receiver: String? = null,
    var content: String? = null,
    var rating: Float = 0.0F,
    var date: Date? = null
) : Parcelable {}

