package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.userprofile.UserProfileAboutMeFragment
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.userprofile.UserProfileGamesFragment
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.userprofile.UserProfileReviewsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle)  {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                UserProfileAboutMeFragment()
            }
            1 -> {
                UserProfileGamesFragment()
            }
            2 -> {
                UserProfileReviewsFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}