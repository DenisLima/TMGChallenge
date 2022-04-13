package com.djv.tmgchallenge.ui.players

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.djv.tmgchallenge.App
import com.djv.tmgchallenge.R
import com.djv.tmgchallenge.data.model.Player
import com.djv.tmgchallenge.databinding.FragmentPlayersBinding
import com.djv.tmgchallenge.data.registerdata.PlayerAddData
import com.djv.tmgchallenge.ui.adapter.PlayerAdapter
import javax.inject.Inject

class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: PlayerViewModel

    @Inject
    lateinit var playerAdd: PlayerAddData

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.instance.libraryComponent.inject(this)
    }

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

    override fun onResume() {
        super.onResume()
        isNeedRefreshList()
    }

    private fun isNeedRefreshList() {
        if (playerAdd.isRefreshList) {
            binding.progressPlayer.visibility = View.VISIBLE
            binding.playerRecycler.visibility = View.GONE
            viewModel.getPlayerList()
        }
    }

    private fun initComponents() {
        binding.floatPlayerButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_players_to_playerInsert)
        }
    }

    private fun prepareObserver() {

//        uiDisposable.add(viewModel.publishSub.subscribeBy(
//            onNext = {
//                if (it) {
//                    binding.progressPlayer.visibility = View.VISIBLE
//                    binding.playerRecycler.visibility = View.GONE
//                    viewModel.getPlayerList()
//                }
//            },
//            onError = {
//                println(it.message.toString())
//            }
//        ))
//
//        viewModel.testFinal.observe(viewLifecycleOwner) {
//            println(it)
//        }

        viewModel.getPlayerLv().observe(viewLifecycleOwner) {
            binding.playerRecycler.apply {
                this.layoutManager = LinearLayoutManager(requireContext())
                val adapter = PlayerAdapter(object : PlayerAdapter.HandleClick {
                    override fun onDeleteCLick(player: Player) {
                        showDeleteAlertDialog(player)
                    }

                    override fun onItemClick(player: Player) {
                       val action = PlayerFragmentDirections.actionNavigationPlayersToDialogPlayer(player)
                        findNavController().navigate(action)
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

        viewModel.getButtonDialogClicked().observe(viewLifecycleOwner) {
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