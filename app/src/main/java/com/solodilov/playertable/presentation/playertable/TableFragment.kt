package com.solodilov.playertable.presentation.playertable

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.solodilov.playertable.App
import com.solodilov.playertable.R
import com.solodilov.playertable.databinding.FragmentPlayerTableBinding
import com.solodilov.playertable.presentation.common.*
import com.solodilov.playertable.presentation.model.PlayerUi
import javax.inject.Inject


class TableFragment : Fragment(R.layout.fragment_player_table) {

    private val binding by viewBinding(FragmentPlayerTableBinding::bind)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: TableViewModel by viewModels { viewModelFactory }

    private var tableAdapter: TableAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListenerToRootView()
        initViews()
        collectFlow(viewModel.uiState, ::handleState)
    }

    private fun initViews() {
        tableAdapter = TableAdapter(::handleInput)
        binding.playerTable.adapter = tableAdapter

        binding.errorLayout.tryButton.setOnClickListener { viewModel.getData() }
    }

    private fun handleState(state: UiState<List<PlayerUi>>) =
        with(binding) {
            progressBar.isVisible = state is UiState.Loading
            errorLayout.root.isVisible = state is UiState.Error
            playerTable.isVisible = state is UiState.Success

            state
                .onSuccess { data -> tableAdapter?.submitList(data) }
                .onError { error -> showToast(error.message.toString()) }
        }

    private fun handleInput(playerId: Int, opponentId: Int, score: Int?) {
        viewModel.changeGame(playerId, opponentId, score)
    }

    var isOpened = false
    private fun setListenerToRootView() {
        val activityRootView: View = requireActivity().findViewById(android.R.id.content)
        activityRootView.viewTreeObserver.addOnGlobalLayoutListener {
            val heightDiff = activityRootView.rootView.height - activityRootView.height
            if (heightDiff > 100) { // 99% of the time the height diff will be due to a keyboard.
                if (!isOpened) {
                    //keyboard opened
                }
                isOpened = true
            } else if (isOpened) {
                //keyboard closed
                viewModel.getData()
                isOpened = false
            }
        }
    }

    override fun onDestroyView() {
        tableAdapter = null
        super.onDestroyView()
    }
}