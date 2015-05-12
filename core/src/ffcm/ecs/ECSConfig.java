
package ffcm.ecs;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ECSConfig
{
    public SpriteBatch spriteBatch;
    public ShapeRenderer shapeRenderer;

    public OrthographicCamera mainCamera;
    public OrthographicCamera guiCamera;

    public AssetManager assetManager;

    public ECSConfig()
    {
    }
}
