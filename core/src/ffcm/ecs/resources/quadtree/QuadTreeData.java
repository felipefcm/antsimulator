
package ffcm.ecs.resources.quadtree;

import com.badlogic.gdx.math.Vector2;

public class QuadTreeData<T>
{
	public Vector2 point;
	public T info;

	public QuadTreeData()
	{
		point = new Vector2();
	}
}
