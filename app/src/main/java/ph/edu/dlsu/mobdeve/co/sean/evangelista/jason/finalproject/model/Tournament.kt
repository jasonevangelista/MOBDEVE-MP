package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model

import java.time.LocalDate
import java.util.Date

//class Tournament {
//
//    lateinit var name:String
//    var current_capacity: Int = 0
//    var max_capacity: Int = 0
//    lateinit var start_date: LocalDate
//
//}

data class Tournament(
    var id: String? = null,
    var name: String? = null,
    var creator_id: String? = null,
    var current_capacity: Int = 0,
    var max_capacity: Int = 0,
    var featured_game: String? = null,
    var description: String? = null,
    var cutoff_date: LocalDate? = null,
    var start_date: LocalDate? = null,
    var instructions: String? = null
)