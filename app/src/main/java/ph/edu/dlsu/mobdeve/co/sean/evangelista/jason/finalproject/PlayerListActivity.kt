package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityPlayerListBinding
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.ProfileFragment
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.TeammatesFragment
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.TournamentsFragment

class PlayerListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_player_list)
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val profileFragment = ProfileFragment()
        val teammatesFragment = TeammatesFragment()
        val tournamentsFragment = TournamentsFragment()

        // ViewPlayerList (Teammates) set as home screen
        makeCurrentFragment(teammatesFragment)

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId){
                R.id.ic_teammates -> makeCurrentFragment(teammatesFragment)
                R.id.ic_profile -> makeCurrentFragment(profileFragment)
                R.id.ic_tournaments -> makeCurrentFragment(tournamentsFragment)
            }
            true
        }


    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            addToBackStack(null)
            commit()
        }


}