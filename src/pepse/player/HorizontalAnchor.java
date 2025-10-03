package pepse.player;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class HorizontalAnchor extends GameObject {

    private final Player player;
    private final Vector2 windowDimensions;

    public HorizontalAnchor(Player player, Vector2 windowDimensions) {
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.player = player;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float height = windowDimensions.y()*0.7f;
        setTopLeftCorner(new Vector2(player.getTopLeftCorner().x(), height));
    }
}
