
package ffcm.antsim.resource.quadtree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class QuadTree<T>
{	
	public static final int BucketSize = 2;
	
	private static final int TopLeft = 0;
	private static final int TopRight = 1;
	private static final int BottomLeft = 2;
	private static final int BottomRight = 3;
	
	public ArrayList<QuadTreeData<T>> data;
	public QuadTree<T>[] child;
	public Rectangle area;
	
	public QuadTree(final Rectangle area)
	{	
		data = new ArrayList<QuadTreeData<T>>(BucketSize);
		
		this.area = area;
	}
	
	public boolean Add(final QuadTreeData<T> newNode)
	{
		if(!area.overlaps(newNode.bounds))
			return false;
		
		if(data.size() < BucketSize)
		{
			data.add(newNode);
			return true;
		}
		
		if(child == null)
			Split();

		if(child[TopLeft].Add(newNode))
			return true;
		
		if(child[BottomLeft].Add(newNode))
			return true;
		
		if(child[BottomRight].Add(newNode))
			return true;
		
		if(child[TopRight].Add(newNode))
			return true;
		
		Gdx.app.error("QuadTree", "Could not add node in tree");
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private void Split()
	{
		//child = new QuadTree<T>[4];
		child = (QuadTree<T>[]) Array.newInstance(QuadTree.class, 4);
		
		float halfWidth = area.width * 0.5f;
		float halfHeight = area.height * 0.5f;
		
		child[TopLeft] = new QuadTree<T>(new Rectangle(area.x, area.y + halfHeight, halfWidth, halfHeight));
		child[BottomLeft] = new QuadTree<T>(new Rectangle(area.x, area.y, halfWidth, halfHeight));
		child[BottomRight] = new QuadTree<T>(new Rectangle(area.x + halfWidth, area.y, halfWidth, halfHeight));
		child[TopRight] = new QuadTree<T>(new Rectangle(area.x + halfWidth, area.y + halfHeight, halfWidth, halfHeight));
	}
	
	@SuppressWarnings("unchecked")
	public T[] SearchArea(final Rectangle searchArea)
	{
		ArrayList<T> inside = new ArrayList<T>();
		
		if(!area.overlaps(searchArea))
			//return (T[]) inside.toArray();
			return (T[]) Array.newInstance(Object.class, 0);
		
		for(int i = 0; i < data.size(); ++i)
		{
			Rectangle box = data.get(i).bounds;
			
			if(searchArea.overlaps(box))
				inside.add(data.get(i).info);
		}
		
		//TODO make performance optimizations
		
		if(child != null)
		{
			for(int i = 0; i < child.length; ++i)
				inside.addAll(Arrays.asList(child[i].SearchArea(searchArea)));
		}
			
		T[] vecs = (T[]) inside.toArray((T[]) Array.newInstance(Object.class, inside.size()));
		
		return vecs;
	}
	
	@SuppressWarnings("unchecked")
	public T[] GetPathToPoint(final Vector2 point)
	{
		ArrayList<T> points = new ArrayList<T>();
		
		if(!area.contains(point))
			//return (T[]) points.toArray();
			return (T[]) Array.newInstance(Object.class, 0);
		
		for(QuadTreeData<T> i : data)
			points.add(i.info);

		//TODO make performance optimizations
		
		if(child != null)
		{
			for(int i = 0; i < child.length; ++i)
				points.addAll(Arrays.asList(child[i].GetPathToPoint(point)));
		}
		
		T[] vecs = (T[]) points.toArray((T[]) Array.newInstance(Object.class, points.size()));
		
		return vecs;
	}
	
	public boolean Contains(final Vector2 point)
	{	
		if(!area.contains(point))
			return false;
		
		for(int i = 0; i < data.size(); ++i)
		{
			Vector2 dataPoint = data.get(i).bounds.getPosition(Vector2.Zero);
			
			if(dataPoint.epsilonEquals(point, 0.05f))
				return true;
		}
		
		if(child != null)
		{
			for(int i = 0; i < child.length; ++i)
				if(child[i].Contains(point))
					return true;
		}
		
		return false;
	}
	
	public void Remove(final Vector2 point)
	{
	}
	
	public void FastClear()
	{
		data.clear();
		data = new ArrayList<QuadTreeData<T>>(BucketSize);
		
		child = null;
	}
	
	public void Clear()
	{
		data.clear();
		
		if(child != null)
		{
			for(int i = 0; i < child.length; ++i)
				child[i].Clear();
			
			child = null;
		}
	}
}
