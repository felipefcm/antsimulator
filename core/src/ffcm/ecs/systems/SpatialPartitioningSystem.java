
package ffcm.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import ffcm.antsim.resource.quadtree.QuadTree;
import ffcm.ecs.comps.CSpatial;
import ffcm.ecs.comps.CSprite;
import ffcm.ecs.comps.CTransform;
import ffcm.ecs.comps.Mapper;
import ffcm.ecs.render.IRenderable;
import ffcm.ecs.render.RenderManager;
import ffcm.ecs.render.RenderState;

public class SpatialPartitioningSystem extends EntitySystem implements EntityListener, IRenderable
{
    private ImmutableArray<Entity> entities;

    private QuadTree<Entity> quadTree;

    private float timer = 0;

    public SpatialPartitioningSystem(int priority)
    {
        super(priority);

        quadTree = new QuadTree<>(null);
    }

    public void SetArea(Rectangle area)
    {
        quadTree.area = area;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        Family family = Family.all(CTransform.class, CSpatial.class).get();

        entities = engine.getEntitiesFor(family);
        engine.addEntityListener(family, this);

        RenderManager.instance.RegisterRenderable(RenderManager.RenderPass.OpaqueUnlit, this);
    }

    @Override
    public void removedFromEngine(Engine engine)
    {
        engine.removeEntityListener(this);

        RenderManager.instance.UnregisterRenderable(RenderManager.RenderPass.OpaqueUnlit, this);
    }

    @Override
    public void entityAdded(Entity entity)
    {
        CSpatial spatial = Mapper.spatial.get(entity);
        CSprite sprite = Mapper.sprite.get(entity);

        if(sprite != null)
            spatial.quadTreeData.bounds.setSize(sprite.sprite.getWidth(), sprite.sprite.getHeight());
    }

    @Override
    public void entityRemoved(Entity entity)
    {
    }

    @Override
    public void update(float deltaTime)
    {
        timer += deltaTime;

        if(timer < 0.5f)
            return;

        timer = 0;

        quadTree.FastClear();

        for(Entity e : entities)
        {
            CSpatial spatial = Mapper.spatial.get(e);
            CTransform transform = Mapper.transform.get(e);

            spatial.quadTreeData.bounds.set(transform.position.x, transform.position.y, 0, 0);

            quadTree.Add(spatial.quadTreeData);
        }
    }

    @Override
    public void Render(RenderState renderState)
    {
        renderState.shapeRenderer.setProjectionMatrix(renderState.mainCamera.combined);

        Draw(renderState.shapeRenderer, quadTree);
    }

    private void Draw(ShapeRenderer shapeRenderer, QuadTree<Entity> node)
    {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        {
            shapeRenderer.rect(node.area.x, node.area.y, node.area.width, node.area.height);
        }
        shapeRenderer.end();

		if(node.child == null)
			return;

		for(int i = 0; i < node.child.size; ++i)
			Draw(shapeRenderer, node.child.get(i));
    }
}
