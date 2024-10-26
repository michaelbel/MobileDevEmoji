package org.michaelbel.mobiledevemoji.data

sealed interface ActionMode {

    data object None: ActionMode

    data object Filters: ActionMode

    data object Search: ActionMode
}