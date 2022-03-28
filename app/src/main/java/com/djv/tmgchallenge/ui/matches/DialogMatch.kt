package com.djv.tmgchallenge.ui.matches

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.databinding.PartialUpdateMatchBinding
import com.djv.tmgchallenge.ui.model.RegisterPlayer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.*


class DialogMatch: DialogFragment() {

    private lateinit var binding: PartialUpdateMatchBinding
    private lateinit var adapter: ArrayAdapter<Player>
    private val viewModel by sharedViewModel<MatchViewModel>()
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            checkFieldsForEmptyValues()
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = PartialUpdateMatchBinding.inflate(layoutInflater)
        val alert = AlertDialog.Builder(activity)
        alert.setView(binding.root)
        binding.cancelButton.setOnClickListener { dismiss() }
        binding.mainScoreEd.addTextChangedListener(textWatcher)
        binding.secondScoreEd.addTextChangedListener(textWatcher)
        viewModel.getPlayerList()
        prepareObservers()
        binding.updateMatchButton.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, 1)
            val format1 = SimpleDateFormat("dd/MM/yyyy")
            val formatted = format1.format(cal.time)
            val selectedMainObject = binding.mainPlayerSpinner.selectedItem as Player
            val selectedSecondObject = binding.secondPlayerSpinner.selectedItem as Player
            onClick.invoke(
                Game(
                    id = 0,
                    mainPlayer = selectedMainObject.id,
                    mainScore = binding.mainScoreEd.text.toString().toInt(),
                    secondPlayer = selectedSecondObject.id,
                    secondScore = binding.secondScoreEd.text.toString().toInt(),
                    dateRegister = formatted
                )
            )
            dismiss()
        }
        return alert.create()
    }

    private fun prepareObservers() {
        viewModel.getPlayers().observe(this) {
            binding.mainPlayerSpinner.visibility = View.VISIBLE
            binding.secondPlayerSpinner.visibility = View.VISIBLE
            binding.mainProgress.visibility = View.GONE
            binding.secondProgress.visibility = View.GONE

            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it)
            binding.mainPlayerSpinner.adapter = adapter
            binding.secondPlayerSpinner.adapter = adapter
        }
    }

    private fun checkFieldsForEmptyValues() {
        binding.updateMatchButton.isEnabled =
            binding.mainScoreEd.text!!.isNotEmpty() &&
                    binding.secondScoreEd.text!!.isNotEmpty()
    }

    companion object {
        private var onClick: (game: Game) -> Unit = {}

        fun newInstance(listener: (game: Game) -> Unit): DialogMatch {
            val fragment = DialogMatch()
            onClick = listener
            return fragment
        }
    }
}