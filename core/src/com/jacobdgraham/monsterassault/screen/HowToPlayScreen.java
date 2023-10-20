package com.jacobdgraham.monsterassault.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jacobdgraham.monsterassault.MonsterAssault;
import com.jacobdgraham.monsterassault.utils.MusicAndSoundManager;

public class HowToPlayScreen implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private final MonsterAssault monsterAssault;
    private Stage stage;
    private BitmapFont howToPlayBitmapFont;
    private MusicAndSoundManager musicAndSoundManager;
    public HowToPlayScreen(MonsterAssault monsterAssaultScreen) {
        monsterAssault = monsterAssaultScreen;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        ScrollPane scrollPane = new ScrollPane(null);
        Gdx.input.setInputProcessor(stage);
        musicAndSoundManager = MusicAndSoundManager.getInstance();
        scrollPane.setSize(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight() / 2.0f);
        scrollPane.setPosition(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/4.0f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        musicAndSoundManager = MusicAndSoundManager.getInstance();
        musicAndSoundManager.play();

        final Texture backgroundTexture = new Texture(Gdx.files.internal("SASZombieAssault.png"));
        final Image backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        howToPlayBitmapFont = new BitmapFont();
        Label.LabelStyle gameInstructionsLabelStyle = new Label.LabelStyle(howToPlayBitmapFont, Color.WHITE);

        final String gameInstructionsIntroductionLabelText =
                "Welcome to the first version of the Monster Assault game! You will find below how to play the game";
        final String gameInstructionsHowToMoveLabelText =
                "You can move your character on the screen by using your finger. Just press and hold anywhere on the screen to move him";
        final String gameInstructionsHowToShootLabelText =
                "You can make your character shoot a bullet by tapping on the screen with your finger. The bullet will shoot in the direction of where your " +
                        "character is facing";
        final String gameInstructionsGameLabelText =
                "In the top right hand corner of the screen, there is a round" +
                        "enemy counter which shows you how many enemies are still alive and trying to kill you. Each enemy takes 4 bullets to kill";
        final String gameInstructionsRoundProgressionSystemText =
                "In the top left hand corner, there is a counter which indicates the current round you are playing on. For every round, more enemies spawn and " +
                        "they progressively get faster as the rounds progress. There are a total of 10 rounds. Every round, your player regenerates back up" +
                        "to 100 health points.";
        final String gameInstructionsFutureUpdates =
                "This is only the first iteration of this game. Within the next few months, I intend to add an item shop, repairable barriers, and boss enemies " +
                        "which will spawn every 3-5 rounds";

        Label introductionHowToPlayScreenLabel = new Label(gameInstructionsIntroductionLabelText, gameInstructionsLabelStyle);
        Label movingYourCharacterHowToPlayLabel = new Label(gameInstructionsHowToMoveLabelText, gameInstructionsLabelStyle);
        Label shootingBulletsHowToPlayLabel = new Label(gameInstructionsHowToShootLabelText, gameInstructionsLabelStyle);
        Label gameScreenUILabel = new Label(gameInstructionsGameLabelText, gameInstructionsLabelStyle);
        Label roundProgressionSystemLabel = new Label(gameInstructionsRoundProgressionSystemText, gameInstructionsLabelStyle);
        Label futureUpdatesLabel = new Label(gameInstructionsFutureUpdates, gameInstructionsLabelStyle);

        introductionHowToPlayScreenLabel.setWrap(true);
        movingYourCharacterHowToPlayLabel.setWrap(true);
        shootingBulletsHowToPlayLabel.setWrap(true);
        gameScreenUILabel.setWrap(true);
        roundProgressionSystemLabel.setWrap(true);
        futureUpdatesLabel.setWrap(true);

        howToPlayBitmapFont.getData().setScale(3.0f);

        final Texture backButton = new Texture(Gdx.files.internal("MainMenuPlayButton.png"));
        final Drawable backButtonDrawable = new TextureRegionDrawable(new TextureRegion(backButton));
        final ImageButton backImageButton = new ImageButton(backButtonDrawable);
        backImageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                monsterAssault.showMainMenuScreen();
            }
        });

        final float buttonTableX = Gdx.graphics.getWidth() / 12f;
        final float buttonTableY = Gdx.graphics.getHeight() / 1.4f;
        final Table buttonTable = new Table();
        buttonTable.add(backImageButton).width(200f).height(200f).pad(10f);
        buttonTable.setPosition(buttonTableX, buttonTableY);

        final float textFieldTableX = Gdx.graphics.getWidth() / 2f;
        final float textFieldTableY = Gdx.graphics.getHeight() / 1.5f;
        final Table labelTable = new Table();
        labelTable.add(introductionHowToPlayScreenLabel).width(800f).pad(10f);
        labelTable.row();
        labelTable.add(movingYourCharacterHowToPlayLabel).width(800f).pad(10f);
        labelTable.row();
        labelTable.add(shootingBulletsHowToPlayLabel).width(800f).pad(10f);
        labelTable.row();
        labelTable.add(gameScreenUILabel).width(800f).pad(10f);
        labelTable.row();
        labelTable.add(roundProgressionSystemLabel).width(800f).pad(10f);
        labelTable.row();
        labelTable.add(futureUpdatesLabel).width(800f).pad(10f);
        labelTable.setPosition(textFieldTableX, textFieldTableY);

        scrollPane.setActor(labelTable);
        stage.addActor(backgroundImage);
        stage.addActor(buttonTable);
        stage.addActor(scrollPane);
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
        howToPlayBitmapFont.dispose();
        batch.dispose();
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
