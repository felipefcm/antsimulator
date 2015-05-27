
package ffcm.ecs.comps;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import ffcm.ecs.resources.quadtree.QuadTreeData;

public class CSpatial extends Component
{
    public QuadTreeData<Entity> quadTreeData;

    public CSpatial(final Entity entity)
    {
        quadTreeData = new QuadTreeData<>();
        quadTreeData.info = entity;
    }
}
