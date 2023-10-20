package com.jacobdgraham.monsterassault.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jacobdgraham.monsterassault.MonsterAssault;
import com.jacobdgraham.monsterassault.utils.MusicAndSoundManager;

public class GameOverDiedScreen implements Screen {
    private final MonsterAssault monsterAssault;
    private Stage gameOverDiedStage;
    private MusicAndSoundManager musicAndSoundManager;
    private BitmapFont youDiedLabelBitmapFont;

    private Label youDiedLabel;

    public GameOverDiedScreen(MonsterAssault monsterAssaultScreen) {
        monsterAssault = monsterAssaultScreen;
    }

    @Override
    public void show() {
        gameOverDiedStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(gameOverDiedStage);
        musicAndSoundManager = MusicAndSoundManager.getInstance();
        musicAndSoundManager.playGameOverSound();

        youDiedLabelBitmapFont = new BitmapFont();
        youDiedLabelBitmapFont.getData().setScale(5.0f);
        Label.LabelStyle gameOverDiedLabelStyle = new Label.LabelStyle(youDiedLabelBitmapFont, Color.RED);

        final String gameOverDiedScreenLabelText =
                "You died!";

        youDiedLabel = new Label(gameOverDiedScreenLabelText, gameOverDiedLabelStyle);

        final Texture backgroundTexture = new Texture(Gdx.files.internal("EndingScreen.png"));
        final Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        final Texture mainMenuButton = new Texture(Gdx.files.internal("BackButton.png"));
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
        table.add(youDiedLabel).pad(10f).row();
        table.add(mainMenuImageButton).pad(10f).row();
        table.setPosition(buttonX, buttonY);

        gameOverDiedStage.addActor(backgroundImage);
        gameOverDiedStage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameOverDiedStage.act(delta);
        gameOverDiedStage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        gameOverDiedStage.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
