package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;

class Trunk {

    private static final Color TRUNK_COLOR = new Color(100, 50, 20);


    public static void create(float groundHeight,
                                    int trunkHeight,
                                    int col,
                              GameObjectCollection gameObjects,
                              int layer) {
        for (int i=0; i<trunkHeight; i++) {
            Block block = new Block(new Vector2(col, groundHeight - Block.SIZE*(i+1)),
                    new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR)));
            block.setTag("trunk");
            gameObjects.addGameObject(block, layer);
        }
    }
}
