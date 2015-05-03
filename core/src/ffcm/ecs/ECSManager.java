
package ffcm.ecs;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ffcm.ecs.render.RenderManager;

public class ECSManager
{
    public static ECSManager instance = new ECSManager();

    public OrthographicCamera mainCamera;
	public OrthographicCamera guiCamera;

	public SpriteBatch spriteBatch;
	public ShapeRenderer shapeRenderer;

	public Engine ecsEngine;

    public ECSManager()
    {
    }

    public void Init(final ECSConfig config)
    {
		spriteBatch = config.spriteBatch;
		shapeRenderer = config.shapeRenderer;

        mainCamera = config.mainCamera;
		guiCamera = config.guiCamera;

		ecsEngine = new Engine();

        RenderManager.instance.Init();
    }

    public void Update()
    {
        ecsEngine.update(Gdx.graphics.getDeltaTime());

        RenderManager.instance.ProcessRenderables();
    }
}
