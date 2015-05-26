
package ffcm.ecs.comps;

import com.badlogic.ashley.core.Component;

import ffcm.antsim.resource.quadtree.QuadTreeData;

public class CSpatial<T> extends Component
{
    public QuadTreeData<T> quadTreeData;

    public CSpatial()
    {
        quadTreeData = new QuadTreeData<>();
    }
}
