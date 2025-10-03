package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import pepse.world.Block;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class TreeBuilder {

    private static final float TREE_PROBABILITY = 0.11f;
    private static final int MIN_HEIGHT = 3;
    private static final int MAX_HEIGHT = 6;
    private final Random random = new Random();
    private final Set<Vector2> occupiedLeafPoints;
    private final Set<Integer> consideredTrunkPoints;
    private final Function<Float, Float> groundHeightCallBack;
    private final GameObjectCollection gameObjects;
    private final  int layer;

    public TreeBuilder(Function<Float, Float> groundHeightCallBack,
                       GameObjectCollection gameObjects,
                       int layer) {

        this.groundHeightCallBack = groundHeightCallBack;
        this.gameObjects = gameObjects;
        this.layer = layer;
        
        occupiedLeafPoints = new HashSet<>();
        consideredTrunkPoints = new HashSet<>();
    }

    public void createInRange(
            int minX,
            int maxX) {

        int blockMinX = Block.getDivisibleValueByBlockSize(minX);
        int blockMaxX = Block.getDivisibleValueByBlockSize(maxX);

        for (int i=blockMinX; i<blockMaxX; i+= Block.SIZE) {
            if (random.nextFloat() <= TREE_PROBABILITY && !consideredTrunkPoints.contains(i)) {
                int trunkHeight = random.nextInt(MIN_HEIGHT, MAX_HEIGHT);
                float groundHeight = groundHeightCallBack.apply((float) i);
                groundHeight = Block.getDivisibleValueByBlockSize(groundHeight);

                Trunk.create(groundHeight, trunkHeight, i, gameObjects, layer);

                Vector2 treeTop = new Vector2(i, groundHeight - Block.SIZE*trunkHeight);
                createLeafsAroundTrunk(treeTop);
            }
            consideredTrunkPoints.add(i);
        }
    }

    private void createLeafsAroundTrunk(Vector2 treeTop) {
        Set<Vector2> desiredLeafPoints = new HashSet<>();
        addDesiredPoints(desiredLeafPoints, treeTop);
        desiredLeafPoints.removeIf(occupiedLeafPoints::contains);

        for (Vector2 p : desiredLeafPoints) {
            Leaf leaf = new Leaf(p);
            gameObjects.addGameObject(leaf, layer+1);
            occupiedLeafPoints.add(p);
        }

    }
    private static void addDesiredPoints(Set<Vector2> desiredPoints, Vector2 treeTop) {
        desiredPoints.add(new Vector2(treeTop));
        desiredPoints.add(new Vector2(treeTop.x(), treeTop.y() - Block.SIZE));
        desiredPoints.add(new Vector2(treeTop.x(), treeTop.y() - 2 * Block.SIZE));
        desiredPoints.add(new Vector2(treeTop.x()+Block.SIZE, treeTop.y()));
        desiredPoints.add(new Vector2(treeTop.x()-Block.SIZE, treeTop.y()));
        desiredPoints.add(new Vector2(treeTop.x()+Block.SIZE, treeTop.y()- Block.SIZE));
        desiredPoints.add(new Vector2(treeTop.x()+Block.SIZE, treeTop.y()- 2 * Block.SIZE));
        desiredPoints.add(new Vector2(treeTop.x()-Block.SIZE, treeTop.y() - Block.SIZE));
        desiredPoints.add(new Vector2(treeTop.x()-Block.SIZE, treeTop.y() - 2 * Block.SIZE));

    }
}
