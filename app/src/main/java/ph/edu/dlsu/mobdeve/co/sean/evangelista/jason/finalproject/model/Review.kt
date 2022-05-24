package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model

import java.time.LocalDate
import java.util.Date

class Review {
    lateinit var sender : String
    lateinit var receiver : String
    lateinit var content : String
    var rating : Float = 0F
    lateinit var date : LocalDate
}