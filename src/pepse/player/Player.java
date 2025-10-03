package pepse.player;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Terrain;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
    private static final Vector2 DIMENSIONS = new Vector2(Block.SIZE,Block.SIZE*3);
    private static final float ACCELERATION = 500f;
    private static final float VELOCITY = 350f;
    private boolean isLanded = false;
    private final UserInputListener userInputListener;


    public Player(Renderable renderable, UserInputListener userInputListener,
                  Vector2 windowDimensions) {

        super(new Vector2(
                windowDimensions.x()/2,
                 DIMENSIONS.y())
                ,DIMENSIONS, renderable);
        this.userInputListener = userInputListener;
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(ACCELERATION);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float totalVelocityX = 0;
        if (userInputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            totalVelocityX -= VELOCITY;
            renderer().setIsFlippedHorizontally(true);
        }
        if (userInputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            totalVelocityX += VELOCITY;
            renderer().setIsFlippedHorizontally(false);
        }
        transform().setVelocityX(totalVelocityX);



        if (userInputListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            if (getVelocity().y() == 0) {
                transform().setVelocityY(-VELOCITY);
            }
        }
    }
}
