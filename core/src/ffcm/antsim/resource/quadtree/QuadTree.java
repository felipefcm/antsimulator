
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
	
	public static int numComp = 0;
	
	public ArrayList<QuadTreeDataNode<T>> data;
	public QuadTree<T>[] child;
	public Rectangle area;
	
	public QuadTree(final Rectangle area)
	{	
		data = new ArrayList<QuadTreeDataNode<T>>(BucketSize);
		
		this.area = area;
	}
	
	public boolean Add(final QuadTreeDataNode<T> newNode)
	{
		if(!area.contains(newNode.position))
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
	
	public Vector2[] SearchArea(final Rectangle searchArea)
	{
		ArrayList<Vector2> points = new ArrayList<Vector2>();
		
		numComp++;
		
		if(!area.overlaps(searchArea))
			return new Vector2[0];
		
		for(int i = 0; i < data.size(); ++i)
		{
			Vector2 point = data.get(i).position;
			
			if(searchArea.contains(point))
				points.add(point);
		}
		
		if(child != null)
		{
			for(int i = 0; i < child.length; ++i)
				points.addAll(Arrays.asList(child[i].SearchArea(searchArea)));
		}
			
		Vector2[] vecs = points.toArray(new Vector2[points.size()]);
		
		return vecs;
	}
	
	public Vector2[] GetPathToPoint(final Vector2 point)
	{
		ArrayList<Vector2> points = new ArrayList<Vector2>();
		
		numComp++;
		
		if(!area.contains(point))
			return new Vector2[0];
		
		for(QuadTreeDataNode<T> i : data)
			points.add(i.position);
		
		if(child != null)
		{
			for(int i = 0; i < child.length; ++i)
				points.addAll(Arrays.asList(child[i].GetPathToPoint(point)));
		}
		
		Vector2[] vecs = points.toArray(new Vector2[points.size()]);
		
		return vecs;
	}
	
	public boolean Contains(final Vector2 point)
	{
		numComp++;
		
		if(!area.contains(point))
			return false;
		
		for(int i = 0; i < data.size(); ++i)
		{
			Vector2 dataPoint = data.get(i).position;
			
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
		data = new ArrayList<QuadTreeDataNode<T>>(BucketSize);
		
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