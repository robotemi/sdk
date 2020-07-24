package com.robotemi.sdk.listeners

interface OnConstraintBeWithStatusChangedListener {

    /**
     * Listen for Constraint Follow state.
     *
     * @param isConstraint true if temi is in constraint follow state, false if is in any other state.
     */
    fun onConstraintBeWithStatusChanged(isConstraint: Boolean)
}
