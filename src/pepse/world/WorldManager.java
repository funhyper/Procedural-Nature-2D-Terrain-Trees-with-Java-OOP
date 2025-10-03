package pepse.world;

import danogl.GameObject;
import danogl.util.Vector2;
import pepse.player.Player;
import pepse.world.Terrain;
import pepse.world.trees.TreeBuilder;

public class WorldManager extends GameObject {
    private final Player player;
    private final Terrain terrain;
    private final TreeBuilder treeBuilder;
    private final Vector2 windowDimensions;

    public WorldManager(Player player, Terrain terrain, TreeBuilder treeBuilder, Vector2 windowDimensions) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.player = player;
        this.terrain = terrain;
        this.treeBuilder = treeBuilder;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        int playerCenter = (int) player.getCenter().x();
        int terrainDelta = (int) windowDimensions.x();

        terrain.createInRange(playerCenter-terrainDelta, playerCenter+terrainDelta);
        treeBuilder.createInRange(playerCenter-terrainDelta, playerCenter+terrainDelta);
    }
}
