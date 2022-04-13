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
import androidx.navigation.fragment.navArgs
import com.djv.tmgchallenge.App
import com.djv.tmgchallenge.R
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.databinding.FragmentPlayerUpdateBinding
import javax.inject.Inject


class PlayerUpdateFragment : Fragment() {

    private var _binding: FragmentPlayerUpdateBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: PlayerViewModel
    val player: PlayerUpdateFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.instance.libraryComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerUpdateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareObservers()
        initComponents()
    }

    private fun initComponents() {

        player.let { player ->
            binding.playerName.setText(player.playerArg.name)
        }

        if (binding.playerName.text.toString().isNotEmpty()) {
            binding.updateButton.isEnabled = true
            binding.updateButton.text = getString(R.string.button_update)
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

        binding.updateButton.setOnClickListener {
            viewModel.checkPlayerExist(
                player = Player(player.playerArg.id, name = binding.playerName.text.toString()),
                isUpdate = true
            )
        }

        binding.cancelButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
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