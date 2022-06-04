package lee.module.kotlin.pattern.view

import lee.module.kotlin.pattern.view.model.NavigationEvent

interface INavigator {

    /**
     * Handle All Screen navigator, includes error states, empty states, success state, navigate...
     */
    val navigator: ((NavigationEvent) -> Unit)
}
