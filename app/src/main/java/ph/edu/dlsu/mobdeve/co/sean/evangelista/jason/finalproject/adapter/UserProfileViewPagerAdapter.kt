package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.userprofile.UserProfileAboutMeFragment
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.userprofile.UserProfileGamesFragment
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments.userprofile.UserProfileReviewsFragment
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.model.Player

class UserProfileViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, playerData: Player?) : FragmentStateAdapter(fragmentManager, lifecycle)  {

    var playerData = playerData
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle().apply {
                    putParcelable("player", playerData)
                }

        return when(position){
            0 -> {
                val fragment = UserProfileAboutMeFragment()
                fragment.arguments = bundle
                return fragment
            }
            1 -> {
                val fragment = UserProfileGamesFragment()
                fragment.arguments = bundle
                return fragment
            }
            2 -> {
                val fragment = UserProfileReviewsFragment()
                fragment.arguments = bundle
                return fragment
            }
            else -> {
                val fragment = Fragment()
                fragment.arguments = bundle
                return fragment
            }
        }
    }
}