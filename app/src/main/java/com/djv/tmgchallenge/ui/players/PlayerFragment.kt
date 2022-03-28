package com.djv.tmgchallenge.ui.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.djv.tmgchallenge.R
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.databinding.FragmentPlayersBinding
import com.djv.tmgchallenge.ui.adapter.PlayerAdapter
import com.djv.tmgchallenge.ui.model.RegisterPlayer
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<PlayerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPlayerList()
        prepareObserver()
        initComponents()
    }

    private fun initComponents() {
        binding.floatPlayerButton.setOnClickListener {
            DialogPlayer.newInstance(null, ::handleClickAction).show(childFragmentManager, null)
        }
    }

    private fun handleClickAction(registerPlayer: RegisterPlayer) {
        registerPlayer.player?.let {
            viewModel.checkPlayerExist(Player(it.id, registerPlayer.newName), registerPlayer.isRegister)
        }
    }

    private fun prepareObserver() {
        viewModel.getPlayerLv().observe(viewLifecycleOwner) {
            binding.playerRecycler.apply {
                this.layoutManager = LinearLayoutManager(requireContext())
                val adapter = PlayerAdapter(object : PlayerAdapter.HandleClick {
                    override fun onDeleteCLick(player: Player) {
                        showDeleteAlertDialog(player)
                    }
                    override fun onItemClick(player: Player) {
                        DialogPlayer.newInstance(player, ::handleClickAction).show(childFragmentManager, null)
                    }
                })
                adapter.setList(it)
                this.adapter = adapter
                val divider =
                    DividerItemDecoration(requireContext(), LinearLayoutManager.HORIZONTAL)
                this.addItemDecoration(divider)
                this.visibility = View.VISIBLE
            }
            binding.progressPlayer.visibility = View.GONE
            binding.loadingText.visibility = View.GONE
        }

        viewModel.getFetch().observe(viewLifecycleOwner) {
            binding.loadingText.visibility = View.VISIBLE
            viewModel.fetchPlayers()
        }

        viewModel.getNotifyAdapter().observe(viewLifecycleOwner) {
            binding.progressPlayer.visibility = View.VISIBLE
            binding.playerRecycler.visibility = View.GONE
            viewModel.getPlayerList()
        }

        viewModel.getPlayerExist().observe(viewLifecycleOwner){
            if (it.first) {
                Toast.makeText(requireContext(), getString(R.string.warning_text), Toast.LENGTH_LONG).show()
            } else {
                viewModel.updatePlayer(it.second)
            }
        }

        viewModel.getButtonDialogClicked().observe(viewLifecycleOwner){
            viewModel.checkPlayerExist(Player(it.player!!.id, it.newName), it.isRegister)
        }
    }

    private fun showDeleteAlertDialog(player: Player) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.title_dialog))
        builder.setMessage(getString(R.string.message_dialog))
        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            viewModel.deletePlayer(player)
        }
        builder.setNegativeButton(android.R.string.no) { _, _ ->
        }
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}