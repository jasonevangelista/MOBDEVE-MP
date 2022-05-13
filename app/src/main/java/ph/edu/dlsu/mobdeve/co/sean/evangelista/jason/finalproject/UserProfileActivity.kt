package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.UserProfileViewPagerAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabLayout = binding.tlUserProfile
        val viewPager = binding.vpUserProfile

        val adapter = UserProfileViewPagerAdapter(supportFragmentManager, lifecycle)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){tab, position->
            when(position){
                0 -> {
                    tab.text = "About Me"
                }
                1 -> {
                    tab.text = "Games"
                }
                2 -> {
                    tab.text = "Reviews"
                }
            }
        }.attach()

    }

}