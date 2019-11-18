package com.robotemi.sdk.listeners;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface OnConstraintBeWithStatusChangedListener {

    /**
     * Listen for Constraint Follow state.
     *
     * @param isConstraint true if temi is in constraint follow state, false if is in any other state.
     */
    void onConstraintBeWithStatusChanged(boolean isConstraint);
}
