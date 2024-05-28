package com.example.subs_inter.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.subs_inter.R
import com.example.subs_inter.adapter.SectionsPagerAdapter
import com.example.subs_inter.auth.LoginActivity
import com.example.subs_inter.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        setupOnBackPressed()
        setupTabLayout()

        val toolbar = binding.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_logout)
        toolbar.setNavigationOnClickListener {
            logout()
        }

        binding.fab.setOnClickListener {
            val newMode = if (sectionsPagerAdapter.mode == SectionsPagerAdapter.MODE_NORMAL) {
                SectionsPagerAdapter.MODE_ADD
            } else {
                SectionsPagerAdapter.MODE_NORMAL
            }
            sectionsPagerAdapter.switchMode(newMode)
            binding.viewPager.adapter = sectionsPagerAdapter // Re-set the adapter
            binding.viewPager.currentItem = 0 // Ensure it navigates to the correct fragment
            setupTabLayout()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupTabLayout() {
        tabLayoutMediator?.detach()

        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (sectionsPagerAdapter.mode) {
                SectionsPagerAdapter.MODE_ADD -> getString(R.string.add)
                else -> getString(if (position == 0) R.string.tab_text_1 else R.string.tab_text_2)
            }
        }
        tabLayoutMediator?.attach()
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            binding.viewPager.adapter = sectionsPagerAdapter
            if (sectionsPagerAdapter.mode == SectionsPagerAdapter.MODE_ADD) {
                sectionsPagerAdapter.switchMode(SectionsPagerAdapter.MODE_NORMAL)
                setupTabLayout()
            } else {
                isEnabled = false
                activity?.onBackPressed()
            }
        }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        tabLayoutMediator?.detach()
    }
}
