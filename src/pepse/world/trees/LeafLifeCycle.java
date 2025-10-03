package pepse.world.trees;

import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.util.Vector2;
import pepse.world.Block;

import java.util.Random;

public class LeafLifeCycle {

    private static final float WIGGLE_VALUE = 10;
    private static final float TRANSITION_TIME = 1;
    private static final float SHRINKAGE_COEFFICIENT = 0.8f;
    private static final float MAX_TIME_UNTIL_FALLING = 60;
    private static final float WIND_MAX_TIME = 2;
    private static final float FADEOUT_TIME = 4;
    private static final float FALLING_VELOCITY = 35;
    private static final float MAX_LEAF_STALL_TIME = 12;
    private static final float WIGGLE_WHEN_FALLING_MAX_VELOCITY = 120;
    private static final float WIGGLE_WHEN_FALLING_TIME = 1;
    private Leaf leaf;
    private Vector2 originPos;
    private Random random;


    public LeafLifeCycle(Leaf leaf, Vector2 originPos, Random random) {

        this.leaf = leaf;
        this.originPos = originPos;
        this.random = random;
    }
    public void start() {
        startWindTransition();
        startFallingDynamic();
    }
    private void startWindTransition() {
        float waitTime = random.nextFloat(0, WIND_MAX_TIME);
        // Height transition
        new ScheduledTask(
                leaf, waitTime, false,
                () ->  new Transition<Float>(
                        leaf,
                        height -> leaf.setDimensions(new Vector2(height, leaf.getDimensions().y())),
                        leaf.getDimensions().x(),
                        leaf.getDimensions().x() * SHRINKAGE_COEFFICIENT,
                        Transition.LINEAR_INTERPOLATOR_FLOAT,
                        TRANSITION_TIME,
                        Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                        null
                ));

        // Wiggle transition
        new ScheduledTask(
                leaf, waitTime, false,
                () -> new Transition<Float> (
                        leaf,
                        leaf.renderer()::setRenderableAngle,
                        -WIGGLE_VALUE,
                        WIGGLE_VALUE,
                        Transition.LINEAR_INTERPOLATOR_FLOAT,
                        TRANSITION_TIME,
                        Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                        null
                )
        );
    }

    private void startFallingDynamic() {
        float waitTimeToFade = random.nextFloat(0, MAX_TIME_UNTIL_FALLING);
        new ScheduledTask(
                leaf,
                waitTimeToFade,
                false,
                () -> {
                    Transition<Float> t = addWiggleWhenFalling();
                    leaf.renderer().fadeOut(FADEOUT_TIME,
                            () -> afterFade(t));
                    leaf.transform().setVelocityY(FALLING_VELOCITY);

                }
        );
    }

    private Transition<Float> addWiggleWhenFalling() {
        return new Transition<>(
                leaf,
                leaf.transform()::setVelocityX,
                -WIGGLE_WHEN_FALLING_MAX_VELOCITY,
                WIGGLE_WHEN_FALLING_MAX_VELOCITY,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                WIGGLE_WHEN_FALLING_TIME,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null
        );
    }
    private void afterFade(Transition<Float> t) {
        float stallTime = random.nextFloat(0, MAX_LEAF_STALL_TIME);
        new ScheduledTask(
                leaf,
                stallTime,
                false,
                () -> {
                    leaf.removeComponent(t);
                    leaf.renderer().fadeIn(1);
                    leaf.transform().setVelocityX(0);
                    leaf.transform().setVelocityY(0);
                    leaf.setTopLeftCorner(originPos);
                    startFallingDynamic();
                }
        );
    }
}
