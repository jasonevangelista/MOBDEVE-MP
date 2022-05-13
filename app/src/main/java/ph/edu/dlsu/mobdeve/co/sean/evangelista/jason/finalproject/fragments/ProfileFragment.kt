package ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.R
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.MyProfileViewPagerAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.adapter.PlayerAdapter
import ph.edu.dlsu.mobdeve.co.sean.evangelista.jason.finalproject.databinding.FragmentProfileBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = binding.tlMyProfile
        val viewPager = binding.vpMyProfile

        val adapter = MyProfileViewPagerAdapter(childFragmentManager, lifecycle)

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
                    tab.text = "History"
                }
            }
        }.attach()
    }

    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}