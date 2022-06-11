package lee.module.kotlin.pattern.view

import lee.module.kotlin.pattern.view.model.UiState

interface IUiStateController {

    /**
     * Handle All Screen navigator, includes error states, empty states, success state, navigate...
     */
    val uiStateController: ((UiState) -> Unit)
}
