package pepse.world.trees;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Block;

import java.awt.*;
import java.util.Random;


class Leaf extends Block {
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private final Random random = new Random();
    private final LeafLifeCycle lifeCycle;

    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR)));
        lifeCycle = new LeafLifeCycle(this, topLeftCorner, random);
        lifeCycle.start();
    }
}
