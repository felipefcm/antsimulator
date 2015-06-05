
package ffcm.ecs;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;

import ffcm.ecs.render.RenderManager;
import ffcm.ecs.resources.EntityTemplateManager;

public class ECSManager
{
    public static ECSManager instance = new ECSManager();

    public ECSConfig ecsConfig;

	public Engine ecsEngine;
	public EntityTemplateManager entityTemplateManager;

    public ECSManager()
    {
    }

    public void Init(final ECSConfig config)
    {
		this.ecsConfig = config;

		ecsEngine = new Engine();
		entityTemplateManager = new EntityTemplateManager();

        RenderManager.instance.Init();
    }

    public void Update()
    {
        ecsEngine.update(Gdx.graphics.getDeltaTime());

        RenderManager.instance.ProcessRenderables();
    }
}
