package com.jacobdgraham.monsterassault.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jacobdgraham.monsterassault.MonsterAssault;

public class PauseGameScreen implements Screen {
    private final MonsterAssault monsterAssault;
    private Stage pauseStage;

    private boolean gamePaused = false;
    private Texture backgroundTexture;
    private Drawable buttonDrawable;
    private ImageButton resumeButton;
    private Image pauseMenuBackground;

    public PauseGameScreen(MonsterAssault monsterAssault) {
        this.monsterAssault = monsterAssault;
    }
    @Override
    public void show() {
        pauseStage = new Stage(new ScreenViewport());

        pauseMenuBackground = new Image(new Texture("Barricades/MetalBarricade.png"));
        pauseMenuBackground.setFillParent(true);
        pauseMenuBackground.setColor(1,1,1,0.7f);

        backgroundTexture = new Texture(Gdx.files.internal("BackButton.png"));
        buttonDrawable = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        resumeButton = new ImageButton(buttonDrawable);
        resumeButton.setPosition(pauseStage.getWidth()/2,pauseStage.getHeight()/2);

        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                monsterAssault.setGamePaused(false);
            }
        });

        pauseStage.addActor(pauseMenuBackground);
        pauseStage.addActor(resumeButton);
        Gdx.input.setInputProcessor(pauseStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        pauseStage.act(delta);
        pauseStage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        monsterAssault.setGamePaused(true);
    }

    @Override
    public void resume() {
        monsterAssault.setGamePaused(false);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        pauseStage.dispose();
        Gdx.input.setInputProcessor(null);
        pauseMenuBackground = null;
        backgroundTexture.dispose();
        resumeButton = null;
    }
}
