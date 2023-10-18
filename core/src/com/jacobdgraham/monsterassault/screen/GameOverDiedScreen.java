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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jacobdgraham.monsterassault.MonsterAssault;

public class GameOverDiedScreen extends ScreenAdapter implements Screen {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private final MonsterAssault monsterAssault;
    private Stage stage;
    public GameOverDiedScreen(MonsterAssault monsterAssaultScreen) {
        monsterAssault = monsterAssaultScreen;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        final Texture backgroundTexture = new Texture(Gdx.files.internal("EndingScreen.png"));
        final Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        final Texture mainMenuButton = new Texture(Gdx.files.internal("MainMenuPlayButton.png"));
        final Drawable mainMenuButtonDrawable = new TextureRegionDrawable(new TextureRegion(mainMenuButton));
        final ImageButton mainMenuImageButton = new ImageButton(mainMenuButtonDrawable);

        mainMenuImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                monsterAssault.showMainMenuScreen();
            }
        });

        final float buttonX = Gdx.graphics.getWidth() / 5f - mainMenuImageButton.getWidth() / 5f;
        final float buttonY = Gdx.graphics.getHeight() / 2f - mainMenuImageButton.getHeight() / 2f;

        final Table table = new Table();
        table.add(mainMenuImageButton).pad(10f).row();
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
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
