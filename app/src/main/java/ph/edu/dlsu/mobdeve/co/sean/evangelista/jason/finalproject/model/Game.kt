package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game (
    var id: String? = null,
    var name: String? = null,
    var creator_id: String? = null,
    var rank: String? = null,
    var server: String? = null,
    var role: String? = null,
) : Parcelable {}