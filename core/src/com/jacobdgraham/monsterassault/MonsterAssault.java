package com.jacobdgraham.monsterassault;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jacobdgraham.monsterassault.screen.GameOverDiedScreen;
import com.jacobdgraham.monsterassault.screen.GameOverSuccessScreen;
import com.jacobdgraham.monsterassault.screen.GameScreen;
import com.jacobdgraham.monsterassault.screen.HowToPlayScreen;
import com.jacobdgraham.monsterassault.screen.MainMenuScreen;
import com.jacobdgraham.monsterassault.screen.PauseGameScreen;
import com.jacobdgraham.monsterassault.utils.Transition;

public class MonsterAssault extends Game {

	private SpriteBatch batch;
	private final GameScreen gameScreen;
	private final PauseGameScreen pauseGameScreen;
	private final GameOverDiedScreen gameOverDiedScreen;
	private final GameOverSuccessScreen gameOverSuccessScreen;
	private final HowToPlayScreen howToPlayScreen;
	private boolean gamePaused = false;

	public MonsterAssault() {
		this.gameScreen = new GameScreen(this);
		this.pauseGameScreen = new PauseGameScreen(this);
		this.howToPlayScreen = new HowToPlayScreen(this);
		this.gameOverDiedScreen = new GameOverDiedScreen(this);
		this.gameOverSuccessScreen = new GameOverSuccessScreen(this);
	}
	@Override
	public void create() {
		showMainMenuScreen();
	}

	public void showMainMenuScreen() {
		setScreen(new MainMenuScreen(this));
	}

	public void showScreen(final Stage stage, final Screen gameScreen) {
		if (stage == null) {
			setScreen(gameScreen);
			return;
		}
		Transition transition = new Transition(stage, this);
		transition.switchToScreen(gameScreen);
	}

	public void showGameScreen(final Stage stage) {
		if (stage == null) {
			setScreen(gameScreen);
			return;
		}
		Transition transition = new Transition(stage, this);
		transition.switchToScreen(gameScreen);
	}

	public void showPauseMenu() {
		setScreen(pauseGameScreen);
	}

	public boolean getGamePaused() {
		return gamePaused;
	}

	public void setGamePaused(boolean paused) {
		gamePaused = paused;
		if (paused) {
			showPauseMenu();
		} else {
			showGameScreen(null);
		}
	}
	@Override
	public void dispose() {
		super.dispose();
	}
}
