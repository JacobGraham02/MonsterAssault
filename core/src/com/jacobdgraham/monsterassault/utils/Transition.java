package com.jacobdgraham.monsterassault.utils;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class Transition {

    private final Stage stage;
    private float alpha;
    private final Game game;

    public Transition(Stage stage, Game game) {
        this.stage = stage;
        this.alpha = 1.0f;
        this.game = game;
    }

    public void switchToScreen(final Screen newScreen) {
        if (stage != null) {
            stage.addAction(Actions.sequence(
                Actions.fadeOut(0.5f),
                Actions.run(() -> {
                    game.setScreen(newScreen);
                }),
                Actions.fadeIn(0.5f)
            ));
        }
    }
}
