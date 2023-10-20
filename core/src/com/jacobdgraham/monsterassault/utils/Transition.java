package com.jacobdgraham.monsterassault.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class Transition {

    private final Stage stage;
    private final Game game;

    public Transition(Stage stage, Game game) {
        this.stage = stage;
        this.game = game;
    }

    public void switchToScreen(final Screen newScreen) {
        if (stage != null) {
            stage.addAction(Actions.sequence(
                Actions.fadeOut(0.5f),
                Actions.run(() -> game.setScreen(newScreen)),
                Actions.fadeIn(0.5f)
            ));
        }
    }
}
