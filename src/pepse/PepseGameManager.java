package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.player.HorizontalAnchor;
import pepse.player.Player;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.WorldManager;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.TreeBuilder;

import java.awt.*;

public class PepseGameManager extends GameManager {

    private static final float SUN_CYCLE_LENGTH = 60;

    public static void main(String[] args) {
        new PepseGameManager().run();
    }


    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();

        Sky.create(gameObjects(), windowDimensions, Layer.BACKGROUND);

        Terrain terrain = new Terrain(gameObjects(), Layer.STATIC_OBJECTS, windowDimensions);

        Night.create(gameObjects(),
                windowDimensions,
                SUN_CYCLE_LENGTH, Layer.FOREGROUND);

        GameObject sun = Sun.create(windowDimensions, SUN_CYCLE_LENGTH, gameObjects(),
                Layer.BACKGROUND+1, imageReader);

        SunHalo.create(gameObjects(), sun,
                new Color(255,255,0, 20),
                Layer.BACKGROUND+10);
        TreeBuilder treeBuilder = new TreeBuilder(terrain::groundHeightAt, gameObjects(), Layer.BACKGROUND+2);

        Player player = new Player(
                imageReader.readImage("assets/player.png", true),
                inputListener, windowDimensions);
        gameObjects().addGameObject(player, Layer.DEFAULT);

        HorizontalAnchor horizontalAnchor = new HorizontalAnchor(player, windowDimensions);
        gameObjects().addGameObject(horizontalAnchor);

        setCamera(new Camera(Vector2.ZERO, windowDimensions, windowDimensions));
        camera().setToFollow(horizontalAnchor, Vector2.UP.mult(50));

        WorldManager worldManager = new WorldManager(player, terrain, treeBuilder, windowDimensions);
        gameObjects().addGameObject(worldManager);
    }

}
