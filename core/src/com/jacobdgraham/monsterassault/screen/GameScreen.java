package com.jacobdgraham.monsterassault.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jacobdgraham.monsterassault.MonsterAssault;
import com.jacobdgraham.monsterassault.entity.Bullet;
import com.jacobdgraham.monsterassault.entity.Enemy;
import com.jacobdgraham.monsterassault.entity.Player;
import com.jacobdgraham.monsterassault.event.RoundData;
import com.jacobdgraham.monsterassault.event.RoundManager;
import com.jacobdgraham.monsterassault.interfaces.IBulletHitListener;
import com.jacobdgraham.monsterassault.pathfinding.AStarNode;
import com.jacobdgraham.monsterassault.pathfinding.AStarPathFinder;
import com.jacobdgraham.monsterassault.utils.MusicAndSoundManager;

import java.util.Iterator;
import java.util.LinkedList;

public class GameScreen extends ScreenAdapter implements Screen {
    public OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    public SpriteBatch batch;
    public Player player;
    public OrthographicCamera camera;
    public TiledMapTileLayer collisionLayer;
    public Enemy enemy;
    public AStarPathFinder pathfinder;
    public RoundManager roundManager;
    public RoundData roundData;
    public MonsterAssault monsterAssault;
    public Stage stage;
    public PauseGameScreen pauseGameScreen;
    public TiledMap tiledMap;
    public Texture playerTexture;
    public Texture bulletTexture;
    public Array<Bullet> bullets;
    public Queue<Enemy> enemies;
    public Queue<Enemy> aliveEnemies;
    public Iterator<Bullet> bulletsIterator;
    public ImageButton pauseMenuButton;
    private Label enemiesLeftLabel;
    private Label currentRoundLabel;
    private Label playerHealthLabel;
    private BitmapFont enemiesLeftLabelFont;
    private BitmapFont currentRoundLabelFont;
    private float loop_duration = 1.0f;
    private final float enemy_spawn_interval = 2.0f;
    private boolean shouldPlayerMove = false;
    private float touchStartTime = 0;
    private MusicAndSoundManager musicAndSoundManager;
    private BitmapFont playerHealthLabelBitmapFont;

    private Texture playerMovementJoystickBaseTexture;
    private Texture playerMovementJoystickKnobTexture;
    private Image playerMovementJoystickBaseImage;
    private Image playerMovementJoystickKnobImage;
    private float playerJoystickRadius;
    private boolean playerMovementKnobActive;
    private float joystickStartX;
    private float joystickStartY;

    private Texture playerShootButtonTexture;
    private Image playerShootButtonImage;

    public GameScreen(MonsterAssault monsterAssault) {
        this.monsterAssault = monsterAssault;
        this.pauseGameScreen = new PauseGameScreen(monsterAssault);
        this.bullets = new Array<>();
        this.enemies = new Queue<>();
        this.aliveEnemies = new Queue<>();
    }

    public void show() {
        stage = new Stage(new ScreenViewport());
        playerTexture = new Texture("PlayerLookingNorth.png");
        bulletTexture = new Texture("Bullet.png");
        tiledMap = new TmxMapLoader().load("MapBuilding.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        collisionLayer = (TiledMapTileLayer) tiledMap.getLayers().get("CollisionLayer");
        enemiesLeftLabelFont = new BitmapFont();
        currentRoundLabelFont = new BitmapFont();
        playerHealthLabelBitmapFont = new BitmapFont();
        Label.LabelStyle enemiesLeftLabelStyle = new Label.LabelStyle(enemiesLeftLabelFont, Color.RED);
        Label.LabelStyle currentRoundLabelStyle = new Label.LabelStyle(currentRoundLabelFont, Color.RED);
        Label.LabelStyle playerHealthLabelStyle = new Label.LabelStyle(playerHealthLabelBitmapFont, Color.WHITE);
        final String initialEnemiesLeftButtonText = "Enemies left: 0";
        final String initialCurrentRoundButtonText = "Round: 0";
        final String initialPlayerHealthLabelText = "Health: 0";
        roundManager = new RoundManager();
        enemiesLeftLabel = new Label(initialEnemiesLeftButtonText + enemies.size, enemiesLeftLabelStyle);
        currentRoundLabel = new Label(initialCurrentRoundButtonText, currentRoundLabelStyle);
        playerHealthLabel = new Label(initialPlayerHealthLabelText, playerHealthLabelStyle);

        musicAndSoundManager = MusicAndSoundManager.getInstance();
        musicAndSoundManager.play();

        pathfinder = new AStarPathFinder(tiledMap);
        camera = new OrthographicCamera();
        batch = new SpriteBatch();

        final float initialScreenWidth = Gdx.graphics.getWidth();
        final float initialScreenHeight = Gdx.graphics.getHeight();
        final float middleOfScreenX = initialScreenWidth / 2 - 16;
        final float middleOfScreenY = initialScreenHeight / 2 - 16;
        enemiesLeftLabelFont.getData().setScale(4.0f);
        currentRoundLabelFont.getData().setScale(4.0f);
        playerHealthLabelBitmapFont.getData().setScale(4.0f);
        roundData = roundManager.getRoundData();

        player = new Player(playerTexture,middleOfScreenX/3,middleOfScreenY/2,32, 32);

        Table topRowLabelTable = new Table();
        topRowLabelTable.setWidth(Gdx.graphics.getWidth());
        topRowLabelTable.top().left();
        topRowLabelTable.setFillParent(true);
        topRowLabelTable.add(currentRoundLabel).left().padLeft(currentRoundLabel.getWidth());
        topRowLabelTable.add(enemiesLeftLabel).left().padLeft(enemiesLeftLabel.getWidth());
        topRowLabelTable.add(playerHealthLabel).left().padLeft(playerHealthLabel.getWidth());
        topRowLabelTable.row();
        topRowLabelTable.add(pauseMenuButton);

        playerMovementJoystickBaseTexture = new Texture("PlayerControls/MonsterAssaultPlayerControlBase.png");
        playerMovementJoystickKnobTexture = new Texture("PlayerControls/MonsterAssaultPlayerControlKnob.png");
        playerShootButtonTexture = new Texture("PlayerControls/MonsterAssaultPlayerShootBullet.png");

        playerMovementJoystickBaseImage = new Image(playerMovementJoystickBaseTexture);
        playerMovementJoystickKnobImage = new Image(playerMovementJoystickKnobTexture);
        playerShootButtonImage = new Image(playerShootButtonTexture);

        playerJoystickRadius = playerMovementJoystickBaseImage.getWidth() / 2;
        playerMovementJoystickBaseImage.setPosition(50, 50);
        playerMovementJoystickKnobImage.setPosition(50 +
                        (playerJoystickRadius - playerMovementJoystickKnobImage.getWidth() / 2),
                        50 + (playerJoystickRadius - playerMovementJoystickKnobImage.getHeight() / 2));

        playerMovementJoystickKnobImage.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                playerMovementKnobActive = true;
                joystickStartX = playerMovementJoystickBaseImage.getX() + playerJoystickRadius;
                joystickStartY = playerMovementJoystickBaseImage.getY() + playerJoystickRadius;
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (playerMovementKnobActive) {
                    float joystickCenterX = joystickStartX;
                    float joystickCenterY = joystickStartY;
                    float deltaX = x - joystickCenterX;
                    float deltaY = y - joystickCenterY;
                    float distance = (float) Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));

                    if (distance > playerJoystickRadius) {
                        deltaX = (deltaX / distance) * playerJoystickRadius;
                        deltaY = (deltaY / distance) * playerJoystickRadius;
                    }
                    playerMovementJoystickKnobImage.setPosition(
                            (joystickCenterX + deltaX) - (playerMovementJoystickKnobImage.getWidth() / 2),
                            (joystickCenterY + deltaY) - (playerMovementJoystickKnobImage.getHeight() / 2));
                    calculatePlayerMovement(deltaX, deltaY);
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (playerMovementKnobActive) {
                    playerMovementJoystickKnobImage.setPosition(
                            playerMovementJoystickBaseImage.getX() + (playerJoystickRadius - playerMovementJoystickKnobImage.getWidth() / 2),
                            playerMovementJoystickBaseImage.getY() + (playerJoystickRadius - playerMovementJoystickKnobImage.getHeight() / 2));
                    playerMovementKnobActive = false;
                }
            }
        });

        playerShootButtonImage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                displayPlayerShotBullet();
                musicAndSoundManager.playBulletShootSound();
                return true;
            }
        });

        playerShootButtonImage.setPosition(Gdx.graphics.getWidth() - playerShootButtonImage.getWidth() - 50, 50);

        stage.addActor(playerMovementJoystickBaseImage);
        stage.addActor(playerMovementJoystickKnobImage);
        stage.addActor(playerShootButtonImage);

        camera.setToOrtho(false, (float) (initialScreenWidth/3.0), (float) (initialScreenHeight/3.0));
        camera.update();

        musicAndSoundManager.loadZombieGroans();

        stage.addActor(topRowLabelTable);
        Gdx.input.setInputProcessor(stage);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void render(float delta) {
        if (monsterAssault.getGamePaused()) {
            return;
        }
		/*
		 Use the OpenGL ES 2.0 API to clear the screen. Internally, the colour buffer (which stores all of the screen pixels), is cleared.
		*/
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		/*
		Each time the player sprite is moved, update the camera to set the position on top of the player and redraw the map textures
		to dynamically update the game as the player is moving around the map
		 */
        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();
        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.render(batch);

        updateEnemiesLabel();
        Label.LabelStyle playerHealthLabelStyle = new Label.LabelStyle();
        playerHealthLabelStyle.font = playerHealthLabelBitmapFont;
        playerHealthLabelStyle.fontColor = player.changeHealthLabelColour();
        playerHealthLabel.setStyle(playerHealthLabelStyle);
        playerHealthLabel.setText("Health: " + player.getHealth());

        if (player.isHit()) {
            player.updateHitTimer(delta); // Update the hit timer
            float hitAlpha = 1.0f - (player.getHitTimer() / player.getHitDuration());
            player.setColor(Color.RED.r, Color.RED.g, Color.RED.b, hitAlpha);

            player.render(batch);

            /*
             Check if the hit effect duration has elapsed
             */
            if (player.getHitTimer() >= player.getHitDuration()) {
                player.setHit(false);
                player.setColor(Color.WHITE);
            }
        }

        if (aliveEnemies.isEmpty()) {
            beginNextRound();
            loop_duration = enemies.size*2;
        }

        if (enemy != null && enemy.getHealth() <= 0) {
            enemy = null;
        }

        if (Gdx.input.justTouched()) {
            touchStartTime = TimeUtils.nanoTime() / 1000000000.0f; // Convert to seconds
            shouldPlayerMove = false;
        }

//        if (Gdx.input.isTouched()) {
//            float touchDuration = (TimeUtils.nanoTime() / 1000000000.0f) - touchStartTime;
//            float tapThreshold = 0.1f;
//            if (touchDuration >= tapThreshold) {
//                shouldPlayerMove = true;
//                calculatePlayerMovement();
//            }
//        }

        if (!shouldPlayerMove && Gdx.input.justTouched()) {
            displayPlayerShotBullet();
            musicAndSoundManager.playBulletShootSound();
        }

        if (player.getHealth() <= 0) {
            musicAndSoundManager.playCharacterDeathSound();
            monsterAssault.showGameOverDiedScreen();
        }
        if (roundManager.getIsGameOver()) {
            monsterAssault.showGameOverSuccessScreen();
        }

        updateBulletPositions();
        updateEnemies(delta);

        for (Enemy enemy : aliveEnemies) {
            calculateEnemyPathfindingMovements(enemy);
            processBulletForEnemy(enemy);

            if (enemy.isHittingPlayer()) {
                player.takeDamage(enemy.dealDamageToPlayerOncePerSecond());
                player.setHit(true);
            }
        }

        batch.end();
        stage.draw();
    }

    private void updateBulletPositions() {
        bulletsIterator = bullets.iterator();
        while (bulletsIterator.hasNext()) {
            Bullet bullet = bulletsIterator.next();
            bullet.move(); // Move the bullet at a constant speed
            bullet.render(batch);

            final float screenWidth = Gdx.graphics.getWidth();
            final float screenHeight = Gdx.graphics.getHeight();

            if (bullet.getX() < 0 || bullet.getX() > screenWidth || bullet.getY() < 0 || bullet.getY() > screenHeight) {
                bulletsIterator.remove();
                continue;
            }

            bullet.setBulletHitListener(new IBulletHitListener() {
                @Override
                public void onBulletHit(Enemy enemy, Bullet bullet) {
                    handleBulletHit(enemy, bullet);
                }
            });
        }
    }

    private void handleBulletHit(Enemy enemy, Bullet bullet) {
        enemy.takeDamage(bullet.getDamage());
        musicAndSoundManager.playBulletHitSound();
    }

    private void updateEnemies(float delta) {
        for (Enemy enemy : aliveEnemies) {
            calculateEnemyPathfindingMovements(enemy);
            if (enemy.isHittingPlayer()) {
                player.takeDamage(enemy.dealDamageToPlayerOncePerSecond());
                player.setHit(true);
            }
        }
    }

    private void beginNextRound() {
        initializeRoundChangeEvent();
        initializeRoundEnemies();
        scheduleEnemySpawning();

//        if (Gdx.input.isTouched()) {
//            calculatePlayerMovement();
//        }
    }

    private void initializeRoundChangeEvent() {
        roundData = roundManager.changeRound();
        final int currentRound = roundData.getCurrentRound();
        currentRoundLabel.setText("Round " + currentRound);
        musicAndSoundManager.playRoundChangeRound();
        player.fillPlayerHealth();
    }

    private void updateEnemiesLabel() {
        enemiesLeftLabel.setText("Enemies left: " + aliveEnemies.size);
    }

    private void displayPlayerShotBullet() {
        Bullet bullet = new Bullet(bulletTexture, player.getX(), player.getY(), (float) bulletTexture.getWidth(), (float) bulletTexture.getHeight(), 25, 5, (player.getRotation() + 90.0f));
        bullets.add(bullet);
    }

    private void scheduleEnemySpawning() {
        final Timer.Task repeatedTask = new Timer.Task() {
            float elapsedTime = 0.0f;
            @Override
            public void run() {
                elapsedTime += enemy_spawn_interval;
                aliveEnemies.addFirst(enemies.removeFirst());

                if (elapsedTime >= loop_duration) {
                    this.cancel();
                }
            }
        };
        Timer.schedule(repeatedTask, 0, enemy_spawn_interval);
    }

    private void calculateEnemyPathfindingMovements(Enemy enemy) {
        if (enemy != null && enemy.getHealth() <= 0) {
            aliveEnemies.removeValue(enemy, false);
            enemy = null;
        }
        if (enemy != null) {
            LinkedList<AStarNode> pathToPlayer = pathfinder.findShortestPath(
                    (int) enemy.getX() / 32,
                    (int) enemy.getY() / 32,
                    (int) player.getX() / 32,
                    (int) player.getY() / 32
            );
            enemy.setPath(pathToPlayer);
            enemy.move();
        }
        if (enemy != null) {
            enemy.render(batch);
        }
    }

    private void initializeRoundEnemies() {
        enemies = roundData.getRoundEnemies();
    }

    private void processBulletForEnemy(Enemy enemy) {
        if (enemy == null) {
            return;
        }

        Rectangle enemyBounds = new Rectangle(enemy.getX(), enemy.getY(), enemy.getBoundingBoxWidth(), enemy.getBoundingBoxHeight());

        bulletsIterator = bullets.iterator();
        while (bulletsIterator.hasNext()) {
            Bullet bullet = bulletsIterator.next();

            Rectangle bulletBounds = bullet.getBoundingBox();
            if (bulletBounds.overlaps(enemyBounds)) {
                enemy.takeDamage(bullet.getDamage());
                bulletsIterator.remove();
            }
        }
    }

//    private void calculatePlayerMovement() {
//        final float touchX = Gdx.input.getX();
//        final float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
//            /*
//             With libgdx, the origin (0,0) is at the bottom left of the screen, with Y increasing upwards. Because the y coordinate increases upwards from
//             the bottom of the screen, instead of the top, we must invert the y-coordinate of the touch position, otherwise the sprite will move in the
//             opposite Y direction of where our finger is.
//             */
//        float directionX = touchX - player.getX();
//        float directionY = touchY - player.getY();
//
//        float angle_in_degrees = (float) Math.toDegrees(Math.atan2(directionY, directionX));
//        player.setRotation(angle_in_degrees - 90);
//
//            /*
//            In game development, a vector is used to determine the position of an object, the speed of the entity moving, the length of the object,
//            and the distance between two specific positions.
//            In this instance, a vector is created to determine where the sprite must travel.
//             */
//        float length_of_movement_vector = (float) Math.sqrt(directionX * directionX + directionY * directionY);
//
//            /*
//            To avoid division by zero errors when forming a vector
//             */
//        if (length_of_movement_vector != 0) {
//            directionX /= length_of_movement_vector;
//            directionY /= length_of_movement_vector;
//        }
//
//        final float newXPosition = player.getX() + directionX;
//        final float newYPosition = player.getY() + directionY;
//
//            /*
//            Calculate the tile coordinates of the player's new position in the collision layer.
//            */
//        int tileX = (int) (newXPosition / collisionLayer.getTileWidth());
//        int tileY = (int) (newYPosition / collisionLayer.getTileHeight());
//
//        // Check if the target tile is blocked (e.g., has a property like "blocked" set)
//        TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);
//        if (cell != null) {
//            // Get the tile associated with the cell
//            TiledMapTile tile = cell.getTile();
//
//            // Check if the tile has a property named "blocked" set to true
//            if (tile.getProperties().containsKey("collision")) {
//                if (tile.getProperties().get("collision").equals("false")) {
//                    player.setX(newXPosition);
//                    player.setY(newYPosition);
//                }
//            }
//        }
//    }

    private void calculatePlayerMovement(float deltaX, float deltaY) {
        float timeStep = Gdx.graphics.getDeltaTime();
        float newX = player.getX() + deltaX * timeStep;
        float newY = player.getY() + deltaY * timeStep;

        // Calculate the direction angle for player rotation
        float angleInDegrees = (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
        player.setRotation(angleInDegrees - 90);

        // Calculate the potential new position in tile coordinates
        int tileX = (int) (newX / collisionLayer.getTileWidth());
        int tileY = (int) (newY / collisionLayer.getTileHeight());
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);

        // Check for collision
        if (cell != null) {
            TiledMapTile tile = cell.getTile();
            if (tile != null && tile.getProperties().containsKey("collision") && tile.getProperties().get("collision", String.class).equals("false")) {
                // Move the player if no collision
                player.setX(newX);
                player.setY(newY);
            }
        } else {
            // Default to allowing movement if no cell is found at the location
            player.setX(newX);
            player.setY(newY);
        }
    }


//    private void calculatePlayerMovement(float deltaX, float deltaY) {
//        float newX = player.getX() + deltaX * Gdx.graphics.getDeltaTime();
//        float newY = player.getY() + deltaY * Gdx.graphics.getDeltaTime();
//
//        float directionX = newX - player.getX();
//        float directionY = newY - player.getY();
//
//        float angle_in_degrees = (float) Math.toDegrees(Math.atan2(directionY, directionX));
//        player.setRotation(angle_in_degrees - 90);
//
//            /*
//            In game development, a vector is used to determine the position of an object, the speed of the entity moving, the length of the object,
//            and the distance between two specific positions.
//            In this instance, a vector is created to determine where the sprite must travel.
//             */
//        float length_of_movement_vector = (float) Math.sqrt(directionX * directionX + directionY * directionY);
//
//            /*
//            To avoid division by zero errors when forming a vector
//             */
//        if (length_of_movement_vector != 0) {
//            directionX /= length_of_movement_vector;
//            directionY /= length_of_movement_vector;
//        }
//
//        int tileX = (int) (newX / collisionLayer.getTileWidth());
//        int tileY = (int) (newY / collisionLayer.getTileHeight());
//        TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, tileY);
//
//        if (cell != null) {
//            TiledMapTile tile = cell.getTile();
//            if (tile.getProperties().containsKey("collision")) {
//                if (tile.getProperties().get("collision").equals("false")) {
//                    player.setX(newX);
//                    player.setY(newY);
//                }
//            }
//        }
//    }

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
        musicAndSoundManager.pause();
    }

    @Override
    public void dispose() {
        batch.dispose();
        enemiesLeftLabelFont.dispose();
        super.dispose();
        currentRoundLabelFont.dispose();
        orthogonalTiledMapRenderer.dispose();
        Gdx.input.setInputProcessor(null);
        bulletTexture.dispose();
        stage.dispose();
        tiledMap.dispose();
        playerTexture.dispose();
    }
}
