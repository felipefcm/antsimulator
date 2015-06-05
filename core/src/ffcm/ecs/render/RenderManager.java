
package ffcm.ecs.render;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import ffcm.ecs.ECSManager;

public class RenderManager
{
    public static RenderManager instance = new RenderManager();

    public enum RenderPass
    {
        OpaqueUnlit,
        TransparentUnlit,
        Light,
        OpaqueLit,
        TransparentLit
    }

    private ObjectMap<RenderPass, Array<IRenderable>> renderables;

    public RenderState state;

    public RenderManager()
    {
    }

    public void Init()
    {
        renderables = new ObjectMap<>();

        RenderPass[] passes = RenderPass.values();

        for(int i = 0; i < passes.length; ++i)
            renderables.put(passes[i], new Array<IRenderable>());

        state = new RenderState();
        state.spriteBatch = ECSManager.instance.ecsConfig.spriteBatch;
        state.shapeRenderer = ECSManager.instance.ecsConfig.shapeRenderer;
        state.mainCamera = ECSManager.instance.ecsConfig.mainCamera;
        state.guiCamera = ECSManager.instance.ecsConfig.guiCamera;
    }

    public void RegisterRenderable(RenderPass pass, final IRenderable renderable)
    {
        Array<IRenderable> array = renderables.get(pass);

        if(!array.contains(renderable, true))
            array.add(renderable);
    }

    public void UnregisterRenderable(RenderPass pass, final IRenderable renderable)
    {

    }

    public void ProcessRenderables()
    {
        RenderPass[] passes = RenderPass.values();

        for(int i = 0; i < passes.length; ++i)
        {
            Array<IRenderable> array = renderables.get(passes[i]);

            for(IRenderable renderable : array)
                renderable.Render(state);
        }
    }

    public void Dispose()
    {
        RenderPass[] passes = RenderPass.values();

        for(int i = 0; i < passes.length; ++i)
            renderables.get(passes[i]).clear();

        renderables.clear();
    }
}
