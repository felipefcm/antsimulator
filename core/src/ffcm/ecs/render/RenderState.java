
package ffcm.ecs.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RenderState
{
    public SpriteBatch spriteBatch;
    public ShapeRenderer shapeRenderer;

    public OrthographicCamera mainCamera;
    public OrthographicCamera guiCamera;
}
