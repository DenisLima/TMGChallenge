package com.djv.tmgchallenge.ui.matches

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.djv.tmgchallenge.App
import com.djv.tmgchallenge.R
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.data.registerdata.MatchRegisterData
import com.djv.tmgchallenge.databinding.FragmentMatchInsertBinding
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class MatchInsertFragment: Fragment() {

    private var _binding: FragmentMatchInsertBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArrayAdapter<Player>
    @Inject lateinit var viewModel: MatchViewModel
    @Inject lateinit var matchRegisterData: MatchRegisterData

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            checkFieldsForEmptyValues()
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.instance.libraryComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchInsertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPlayerList()
        prepareObservers()
        initComponents()
    }

    private fun initComponents() {
        binding.cancelButton.setOnClickListener { requireActivity().onBackPressed() }
        binding.mainScoreEd.addTextChangedListener(textWatcher)
        binding.secondScoreEd.addTextChangedListener(textWatcher)
        binding.updateMatchButton.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, 1)
            val format1 = SimpleDateFormat("dd/MM/yyyy")
            val formatted = format1.format(cal.time)
            val selectedMainObject = binding.mainPlayerSpinner.selectedItem as Player
            val selectedSecondObject = binding.secondPlayerSpinner.selectedItem as Player

            viewModel.insertGame(
                Game(
                    id = 0,
                    mainPlayer = selectedMainObject.id,
                    mainScore = binding.mainScoreEd.text.toString().toInt(),
                    secondPlayer = selectedSecondObject.id,
                    secondScore = binding.secondScoreEd.text.toString().toInt(),
                    dateRegister = formatted
                )
            )

        }
    }

    private fun prepareObservers() {
        viewModel.getPlayers().observe(viewLifecycleOwner) {
            binding.mainPlayerSpinner.visibility = View.VISIBLE
            binding.secondPlayerSpinner.visibility = View.VISIBLE
            binding.mainProgress.visibility = View.GONE
            binding.secondProgress.visibility = View.GONE

            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it)
            binding.mainPlayerSpinner.adapter = adapter
            binding.secondPlayerSpinner.adapter = adapter
        }

        viewModel.getSamePlayer().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(R.string.warning_same_player), Toast.LENGTH_LONG).show()
        }

        viewModel.isInsertMatch().observe(viewLifecycleOwner) {
            matchRegisterData.isRefresh = true
            requireActivity().onBackPressed()
        }
    }

    private fun checkFieldsForEmptyValues() {
        binding.updateMatchButton.isEnabled =
            binding.mainScoreEd.text!!.isNotEmpty() &&
                    binding.secondScoreEd.text!!.isNotEmpty()
    }
}