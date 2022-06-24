package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ConnectHistory(
    var player_id: String? = null,
    var date: Date? = null
) : Parcelable {
}