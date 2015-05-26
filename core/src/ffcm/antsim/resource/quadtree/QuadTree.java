
package ffcm.antsim.resource.quadtree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class QuadTree<T>
{	
	public static final int BucketSize = 5;
	
	private static final int TopLeft = 0;
	private static final int TopRight = 1;
	private static final int BottomLeft = 2;
	private static final int BottomRight = 3;
	
	public Array<QuadTreeData<T>> data;
	public Array<QuadTree<T>> child;
	public Rectangle area;
	
	public QuadTree(final Rectangle area)
	{	
		data = new Array<>(false, BucketSize);
		child = null;
		
		this.area = area;
	}
	
	public boolean Add(final QuadTreeData<T> newNode)
	{
		if(!area.overlaps(newNode.bounds))
			return false;
		
		if(data.size < BucketSize)
		{
			data.add(newNode);
			return true;
		}
		
		if(child == null)
			Split();

		if(child.get(TopLeft).Add(newNode))
			return true;
		
		if(child.get(BottomLeft).Add(newNode))
			return true;
		
		if(child.get(BottomRight).Add(newNode))
			return true;
		
		if(child.get(TopRight).Add(newNode))
			return true;
		
		Gdx.app.error("QuadTree", "Could not add node in tree");
		return false;
	}

	private void Split()
	{
		child = new Array<>(false, 4);
		
		float halfWidth = area.width * 0.5f;
		float halfHeight = area.height * 0.5f;

		//TopLeft
		child.add(new QuadTree<T>(new Rectangle(area.x, area.y + halfHeight, halfWidth, halfHeight)));

		//BottomLeft
		child.add(new QuadTree<T>(new Rectangle(area.x, area.y, halfWidth, halfHeight)));

		//BottomRight
		child.add(new QuadTree<T>(new Rectangle(area.x + halfWidth, area.y, halfWidth, halfHeight)));

		//TopRight
		child.add(new QuadTree<T>(new Rectangle(area.x + halfWidth, area.y + halfHeight, halfWidth, halfHeight)));
	}

	public T[] SearchArea(final Rectangle searchArea)
	{
		Array<T> inside = new Array<>();
		
		if(!area.overlaps(searchArea))
			return inside.toArray();

		for(QuadTreeData<T> i : data)
		{
			Rectangle box = i.bounds;
			
			if(searchArea.overlaps(box))
				inside.add(i.info);
		}
		
		if(child != null)
		{
			for(QuadTree<T> i : child)
				inside.addAll(i.SearchArea(searchArea));
		}
			
		return inside.toArray();
	}

	public T[] GetPathToPoint(final Vector2 point)
	{
		Array<T> points = new Array<>();
		
		if(!area.contains(point))
			return points.toArray();
		
		for(QuadTreeData<T> i : data)
			points.add(i.info);
		
		if(child != null)
		{
			for(QuadTree<T> i : child)
				points.addAll(i.GetPathToPoint(point));
		}
		
		return points.toArray();
	}
	
	public boolean Contains(final Vector2 point)
	{	
		if(!area.contains(point))
			return false;
		
		for(QuadTreeData<T> i : data)
		{
			Vector2 dataPoint = new Vector2();
			i.bounds.getPosition(dataPoint);
			
			if(dataPoint.epsilonEquals(point, 0.05f))
				return true;
		}
		
		if(child != null)
		{
			for(QuadTree<T> i : child)
				if(i.Contains(point))
					return true;
		}
		
		return false;
	}
	
	public void Remove(final Vector2 point)
	{
		throw new UnsupportedOperationException();
	}
	
	public void FastClear()
	{
		data.clear();
		data = new Array<>(BucketSize);
		
		child = null;
	}
	
	public void Clear()
	{
		data.clear();
		
		if(child != null)
		{
			for(int i = 0; i < child.size; ++i)
				child.get(i).Clear();
			
			child = null;
		}
	}
}
