package com.example.pokemonapp.view.pokemondetail

import com.google.android.material.appbar.AppBarLayout

abstract class AppBarStateChangeListener : AppBarLayout.OnOffsetChangedListener {

    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private var currentState =
        State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, p1: Int) {

        if (p1 == 0) {
            if (currentState != State.EXPANDED) {
                onStateChanged(appBarLayout,
                    State.EXPANDED
                )
            }
            currentState =
                State.EXPANDED
        } else if (Math.abs(p1) >= appBarLayout?.totalScrollRange!!) {
            if (currentState != State.COLLAPSED) {
                onStateChanged(appBarLayout,
                    State.COLLAPSED
                )
            }
            currentState =
                State.COLLAPSED
        } else {
            if (currentState != State.IDLE) {
                onStateChanged(appBarLayout,
                    State.IDLE
                )
            }
            currentState =
                State.IDLE;
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout?, state: State)
}