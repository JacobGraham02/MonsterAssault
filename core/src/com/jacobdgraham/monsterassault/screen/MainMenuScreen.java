package com.jacobdgraham.monsterassault.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jacobdgraham.monsterassault.MonsterAssault;
import com.jacobdgraham.monsterassault.utils.MusicAndSoundManager;
import com.jacobdgraham.monsterassault.utils.Transition;

public class MainMenuScreen extends ScreenAdapter implements Screen {
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final MonsterAssault monsterAssault;
    private Stage stage;
    private MusicAndSoundManager musicAndSoundManager;

    public MainMenuScreen(MonsterAssault monsterAssaultScreen) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        monsterAssault = monsterAssaultScreen;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        musicAndSoundManager = MusicAndSoundManager.getInstance();
        musicAndSoundManager.play();

        final Texture backgroundTexture = new Texture(Gdx.files.internal("SASZombieAssault.png"));
        final Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        final Texture playButton = new Texture(Gdx.files.internal("MainMenuPlayButton.png"));
        final Drawable playDrawable = new TextureRegionDrawable(new TextureRegion(playButton));
        final ImageButton playImageButton = new ImageButton(playDrawable);

        final Texture howToPlayButton = new Texture(Gdx.files.internal("MainMenuHowToPlayButton.png"));
        final Drawable howToPlayDrawable = new TextureRegionDrawable(new TextureRegion(howToPlayButton));
        final ImageButton howToPlayImageButton = new ImageButton(howToPlayDrawable);

        howToPlayImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                monsterAssault.showScreen(stage, new HowToPlayScreen(monsterAssault));
            }
        });

        final float buttonX = Gdx.graphics.getWidth() / 5f - playImageButton.getWidth() / 5f;
        final float buttonY = Gdx.graphics.getHeight() / 2f - playImageButton.getHeight() / 2f;
        playImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                monsterAssault.showScreen(stage, new GameScreen(monsterAssault));
            }
        });
        final Table table = new Table();
        table.add(playImageButton).pad(10f);
        table.row();
        table.add(howToPlayImageButton).pad(10f);
        table.setPosition(buttonX, buttonY);

        stage.addActor(backgroundImage);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        musicAndSoundManager.pause();
    }

    @Override
    public void dispose() {
        musicAndSoundManager.dispose();
        batch.dispose();
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
