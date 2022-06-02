package lee.module.kotlin.pattern.view

import lee.module.kotlin.pattern.view.model.UiState

interface IState {

    /**
     * Handle All Screen UIState, includes error states, empty states, success state, navigate...
     */
    val uiNavigator: ((UiState) -> Unit)
}
