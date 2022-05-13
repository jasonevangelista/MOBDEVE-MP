package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.myprofile.MyProfileAboutMeFragment
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.myprofile.MyProfileGamesFragment
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.myprofile.MyProfileHistoryFragment

class MyProfileViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle)  {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                MyProfileAboutMeFragment()
            }
            1 -> {
                MyProfileGamesFragment()
            }
            2 -> {
                MyProfileHistoryFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}