package com.jacobdgraham.monsterassault.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.jacobdgraham.monsterassault.MonsterAssault;
import com.jacobdgraham.monsterassault.entity.Bullet;
import com.jacobdgraham.monsterassault.entity.Enemy;
import com.jacobdgraham.monsterassault.entity.Player;
import com.jacobdgraham.monsterassault.pathfinding.AStarPathFinder;
import com.jacobdgraham.monsterassault.screen.GameScreen;

import java.util.Iterator;

public class GameState {

    private final Enemy enemy;
    private final Player player;
    private final TiledMap tiledMap;
    private final TiledMapTileLayer collisionLayer;

    private final OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

    private final SpriteBatch batch;

    private final OrthographicCamera orthographicCamera;

    private final AStarPathFinder dijkstraPathfinder;

    private final Stage stage;

    private final Texture playerTexture;

    private final Texture bulletTexture;

    private final Array<Bullet> bullets;

    private final Iterator<Bullet> bulletsIterator;

    private final ImageButton pauseMenuButton;

    private final ClickListener pauseMenuClickListener;

    private MonsterAssault monsterAssault;

    private boolean stateCanBeLoaded = false;

    public GameState(GameScreen gameScreen) {
        this.enemy = gameScreen.enemy;
        this.player = gameScreen.player;
        this.tiledMap = gameScreen.tiledMap;
        this.collisionLayer = gameScreen.collisionLayer;
        this.orthogonalTiledMapRenderer = gameScreen.orthogonalTiledMapRenderer;
        this.batch = gameScreen.batch;
        this.orthographicCamera = gameScreen.camera;
        this.dijkstraPathfinder = gameScreen.pathfinder;
        this.stage = gameScreen.stage;
        this.playerTexture = gameScreen.playerTexture;
        this.bulletTexture = gameScreen.bulletTexture;
        this.bullets = gameScreen.bullets;
        this.bulletsIterator = gameScreen.bulletsIterator;
        this.pauseMenuButton = gameScreen.pauseMenuButton;
        this.pauseMenuClickListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                monsterAssault.setGamePaused(true);
            }
        };
        stateCanBeLoaded = true;
    }

    public void loadState(GameScreen gameScreen) {
        gameScreen.enemy = this.enemy;
        gameScreen.player = this.player;
        gameScreen.tiledMap = this.tiledMap;
        gameScreen.collisionLayer = this.collisionLayer;
        gameScreen.orthogonalTiledMapRenderer = this.orthogonalTiledMapRenderer;
        gameScreen.batch = this.batch;
        gameScreen.camera = this.orthographicCamera;
        gameScreen.pathfinder = this.dijkstraPathfinder;
        gameScreen.stage = this.stage;
        gameScreen.playerTexture = this.playerTexture;
        gameScreen.bulletTexture = this.bulletTexture;
        gameScreen.bullets = this.bullets;
        gameScreen.bulletsIterator = this.bulletsIterator;
        gameScreen.pauseMenuButton = pauseMenuButton;
        gameScreen.pauseMenuButton.addListener(pauseMenuClickListener);
    }

    public final boolean getIfValidSavedGameState() {
        return stateCanBeLoaded;
    }
}
