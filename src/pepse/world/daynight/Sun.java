package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {
    private static final float SUN_SIZE = 150;
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength,
                                    GameObjectCollection gameObjects,
                                    int layer, ImageReader imageReader) {
        GameObject sun = new GameObject(
                new Vector2(0, 0),
                Vector2.ONES.mult(SUN_SIZE),
                new OvalRenderable(Color.YELLOW)
        );

        new Transition<Float>(
                sun,
                angle -> sun.setCenter(calcSunPosition(windowDimensions, angle)),
                (float) -Math.PI/2, 1.5f * (float) Math.PI,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP, null);
        sun.setTag("sun");

        gameObjects.addGameObject(sun, layer);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        return sun;
    }

    private static Vector2 calcSunPosition(Vector2 windowDimensions, float angleInSky) {
        Vector2 center = new Vector2(windowDimensions.x()/2, windowDimensions.y()/2);
        float radius = center.y();
        return new Vector2(radius* (float) Math.cos(angleInSky) + center.x(),
                 radius * (float) Math.sin(angleInSky) + center.y());
    }

}
