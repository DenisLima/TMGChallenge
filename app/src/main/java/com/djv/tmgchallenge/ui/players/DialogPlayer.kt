package com.djv.tmgchallenge.ui.players

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.DialogFragment
import com.djv.tmgchallenge.R
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.databinding.PartialUpdatePlayerBinding
import com.djv.tmgchallenge.ui.model.RegisterPlayer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class DialogPlayer: DialogFragment() {

    private lateinit var binding: PartialUpdatePlayerBinding

    private val player by lazy {
        arguments?.getParcelable<Player>(ARG_PLAYER)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = PartialUpdatePlayerBinding.inflate(layoutInflater)
        val alert = AlertDialog.Builder(activity)
        alert.setView(binding.root)

        player?.let { player ->
            if (player.name.isNotEmpty()) {
                binding.playerName.setText(player.name)
            }
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
            if (player != null) {
                onClick.invoke(RegisterPlayer(player!!, binding.playerName.text.toString(), true))
                //listener!!.makeAction(RegisterPlayer(player!!, binding.playerName.text.toString(), true))
                //sharedViewModel.getDialogButtonListener(RegisterPlayer(player!!, binding.playerName.text.toString(), true))
                //handleUpdatePlayer.updatePlayer(player, binding.playerName.text.toString(), true)
            } else {
                onClick.invoke(RegisterPlayer(Player(), binding.playerName.text.toString(), false))
                //listener!!.makeAction(RegisterPlayer(null, binding.playerName.text.toString(), false))
                //sharedViewModel.getDialogButtonListener(RegisterPlayer(null, binding.playerName.text.toString(), false))
                //handleUpdatePlayer.updatePlayer(null, binding.playerName.text.toString(), false)
            }
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        return alert.create()
    }

    companion object {
        private const val ARG_PLAYER = "argPlayer"
        private var onClick: (registerPlayer: RegisterPlayer) -> Unit = {}

        fun newInstance(player: Player?, listener: (registerPlayer: RegisterPlayer) -> Unit): DialogPlayer {
            val fragment = DialogPlayer()
            onClick = listener
            val bundle = Bundle().apply {
                putParcelable(ARG_PLAYER, player)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}