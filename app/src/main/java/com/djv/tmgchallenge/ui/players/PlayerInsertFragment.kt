package com.djv.tmgchallenge.ui.players

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.djv.tmgchallenge.App
import com.djv.tmgchallenge.R
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.databinding.FragmentPlayerInsertBinding
import javax.inject.Inject

class PlayerInsertFragment: Fragment() {

    private var _binding: FragmentPlayerInsertBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: PlayerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerInsertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.instance.libraryComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        prepareObservers()
    }

    private fun initComponents() {

        binding.updateButton.setOnClickListener {
            viewModel.checkPlayerExist(
                player = Player(name = binding.playerName.text.toString()),
                isUpdate = false
            )
            //requireActivity().onBackPressed()
        }

        binding.cancelButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.playerName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isNotEmpty()) {
                    binding.updateButton.isEnabled = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun prepareObservers() {
        viewModel.getPlayerExist().observe(viewLifecycleOwner) {
            if (it.first) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.warning_text),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                viewModel.updatePlayer(it.second)
            }
        }
        viewModel.isCloseDialog.observe(viewLifecycleOwner) {
            requireActivity().onBackPressed()
        }
    }
}