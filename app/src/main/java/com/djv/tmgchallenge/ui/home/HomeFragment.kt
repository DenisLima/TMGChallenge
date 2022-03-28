package com.djv.tmgchallenge.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.djv.tmgchallenge.databinding.FragmentHomeBinding
import com.djv.tmgchallenge.ui.adapter.GameAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchList()
        prepareObserver()
    }

    private fun prepareObserver() {
        viewModel.getList().observe(viewLifecycleOwner){
            binding.homeLoading.visibility = View.GONE
            binding.homeRecycler.apply {
                this.layoutManager = LinearLayoutManager(requireContext())
                val adapter = GameAdapter()
                adapter.setList(it)
                this.addItemDecoration(
                    DividerItemDecoration(requireContext(), LinearLayoutManager.HORIZONTAL)
                )
                this.adapter = adapter
            }
            binding.homeRecycler.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}