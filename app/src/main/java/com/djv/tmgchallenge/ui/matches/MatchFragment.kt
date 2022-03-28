package com.djv.tmgchallenge.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.djv.tmgchallenge.R
import com.djv.tmgchallenge.data.model.Game
import com.djv.tmgchallenge.data.model.PlayerAndGame
import com.djv.tmgchallenge.databinding.FragmentMatchesBinding
import com.djv.tmgchallenge.ui.adapter.MatchAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MatchFragment: Fragment() {

    private var binding: FragmentMatchesBinding? = null
    private val bind get() = binding!!

    private val viewModel by viewModel<MatchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchesBinding.inflate(inflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchGames()
        prepareObservers()
        initComponents()
    }

    private fun initComponents() {
        bind.floatMatchButton.setOnClickListener {
            DialogMatch.newInstance(::handleSaveMatches).show(childFragmentManager, null)
        }
    }

    private fun handleSaveMatches(game: Game) {
        viewModel.insertGame(game)
    }

    private fun prepareObservers() {
        viewModel.getFetchGames().observe(viewLifecycleOwner) {
            bind.recylerMatches.apply {
                this.layoutManager = LinearLayoutManager(requireContext())
                val adapter = MatchAdapter(object : MatchAdapter.HandleDeleteMatch {
                    override fun onDeleteClicked(game: PlayerAndGame) {
                        showDeleteAlertDialog(game)
                    }
                })
                adapter.setList(it)
                this.adapter = adapter
            }
            bind.recylerMatches.visibility = View.VISIBLE
            bind.loadingTextMatch.visibility = View.GONE
            bind.progressMatch.visibility = View.GONE
        }

        viewModel.getFetchData().observe(viewLifecycleOwner){
            bind.recylerMatches.visibility = View.GONE
            bind.progressMatch.visibility = View.VISIBLE
            bind.loadingTextMatch.visibility = View.VISIBLE
            viewModel.fetchGames()
        }
        viewModel.getNotifyAdapter().observe(viewLifecycleOwner) {
            bind.progressMatch.visibility = View.VISIBLE
            bind.recylerMatches.visibility = View.GONE
            viewModel.fetchGames()
        }

        viewModel.getSamePlayer().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(R.string.warning_same_player), Toast.LENGTH_LONG).show()
        }
    }

    private fun showDeleteAlertDialog(playerAndGame: PlayerAndGame) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.title_dialog))
        builder.setMessage(getString(R.string.warning_delete_game))
        builder.setPositiveButton(android.R.string.yes) { _, _ ->
            viewModel.deleteGame(playerAndGame)
        }
        builder.setNegativeButton(android.R.string.no) { _, _ ->
        }
        builder.show()
    }
}