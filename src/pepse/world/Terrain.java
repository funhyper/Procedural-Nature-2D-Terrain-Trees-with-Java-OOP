package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Terrain {
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final double NOISE_STATIC_VALUE = 0;
    private static final float GROUND_MAX_LIMIT_COEFFICIENT = 0.9f;
    private static final float GROUND_MIN_LIMIT_COEFFICIENT = 0.5f;
    private final NoiseGenerator n;
    private final GameObjectCollection gameObjects;
    private final int groundLayer;
    private final Vector2 windowDimensions;
    private final Set<Integer> occupiedCols;
    private final float depthFactor;
    public Terrain(GameObjectCollection gameObjects,
                   int groundLayer,
                   Vector2 windowDimensions) {

        this.gameObjects = gameObjects;
        this.groundLayer = groundLayer;
        this.windowDimensions = windowDimensions;
        occupiedCols = new HashSet<>();
        depthFactor = 1.5f;
        n = new NoiseGenerator();
    }

    public float groundHeightAt(float x) {
        float alpha = 0.01f;
        float noise = (float) n.noise(x*alpha, NOISE_STATIC_VALUE);
        noise = (noise + 1) / 2;
        float high_limit = windowDimensions.y() * GROUND_MAX_LIMIT_COEFFICIENT;
        float low_limit = windowDimensions.y() * GROUND_MIN_LIMIT_COEFFICIENT;

        noise = (high_limit - low_limit) * noise + low_limit;
        return noise;
    }

    public void createInRange(int minX, int maxX) {
        int comfortableMinX = Block.getDivisibleValueByBlockSize(minX);
        int comfortableMinY = Block.getDivisibleValueByBlockSize(maxX);
        for (int i=comfortableMinX; i <=comfortableMinY; i+= Block.SIZE) {
            if (occupiedCols.contains(i))
                continue;
            occupiedCols.add(i);
            float height = groundHeightAt(i);
            int depth = Block.getDivisibleValueByBlockSize(height);
            for (int j=depth; j < windowDimensions.y() * depthFactor; j += Block.SIZE) {
                Block block = new Block(new Vector2(i, j),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR)));
                block.setTag("ground");
                gameObjects.addGameObject(block, groundLayer);
            }
        }
    }

}
