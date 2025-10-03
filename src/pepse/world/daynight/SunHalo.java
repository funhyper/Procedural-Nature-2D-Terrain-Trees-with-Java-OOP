package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {

    private static final float SUN_MULTIPLAYER = 2;

    public static GameObject create(
            GameObjectCollection gameObjects,
            GameObject sun,
            Color color,
            int layer) {
        GameObject sunHalo = new GameObject(
                Vector2.ZERO, sun.getDimensions().mult(SUN_MULTIPLAYER),
                new OvalRenderable(color)
        );
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag("halo");

        sunHalo.addComponent(
                deltaTime -> sunHalo.setCenter(sun.getCenter())
        );

        gameObjects.addGameObject(sunHalo, layer);
        return sunHalo;
    }

}
