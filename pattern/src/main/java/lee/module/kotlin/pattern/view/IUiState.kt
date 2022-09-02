package lee.module.kotlin.pattern.view

import lee.module.kotlin.pattern.view.model.UiState

interface IUiState {

    /**
     * Handle All Screen navigator, includes error states, empty states, success state, navigate...
     */
    fun handleUiState(uiState: UiState)
}
