
package ffcm.ecs.resources.quadtree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class QuadTree
{	
	public static final int BucketSize = 1;
	
	private static final int TopLeft = 0;
	private static final int TopRight = 1;
	private static final int BottomLeft = 2;
	private static final int BottomRight = 3;
	
	public Rectangle area;

	public Array<QuadTreeData> data;
	public QuadTree[] child;

	public QuadTree(final Rectangle area)
	{	
		data = new Array<>(false, BucketSize);
		child = null;
		
		this.area = area;
	}
	
	public boolean Add(final QuadTreeData newNode)
	{
		if(!area.contains(newNode.point))
			return false;

		if(child == null) //leaf node
        {
            if(data.size < BucketSize)
            {
                data.add(newNode);
            }
            else
            {
                Split();

                for(QuadTreeData d : data)
                    Add(d);

                Add(newNode);

                data = null;
            }

            return true;
        }

        //inner node

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

	private void Split()
	{
		child = new QuadTree[4];
		
		float halfWidth = area.width * 0.5f;
		float halfHeight = area.height * 0.5f;

		child[TopLeft] = new QuadTree(new Rectangle(area.x, area.y + halfHeight, halfWidth, halfHeight));
		child[BottomLeft] = new QuadTree(new Rectangle(area.x, area.y, halfWidth, halfHeight));
		child[BottomRight] = new QuadTree(new Rectangle(area.x + halfWidth, area.y, halfWidth, halfHeight));
		child[TopRight] = new QuadTree(new Rectangle(area.x + halfWidth, area.y + halfHeight, halfWidth, halfHeight));
	}

	public void Remove(final QuadTreeData node)
	{
	}

    public QuadTreeData Search(final Vector2 point)
    {
        if(!area.contains(point))
			return null;

		if(child == null)
        {
            for(QuadTreeData i : data)
                if(i.point.equals(point))
                    return i;
        }
		else
		{
		    QuadTreeData result;

			for(QuadTree i : child)
				if((result = i.Search(point)) != null)
					return result;
		}

		return null;
    }

    public QuadTreeData[] SearchArea(final Rectangle searchArea)
	{
		Array<QuadTreeData> inside = new Array<>();

		if(!area.overlaps(searchArea))
			return inside.toArray();

        if(child == null)
        {
            for(QuadTreeData i : data)
                if(searchArea.contains(i.point))
                    inside.add(i);
        }
		else
		{
			for(QuadTree i : child)
				inside.addAll(i.SearchArea(searchArea));
		}

		return inside.toArray();
	}

	public boolean Contains(final Vector2 point)
    {
        return Search(point) != null;
    }
	
	public void Clear()
	{
		data = new Array<>(BucketSize);
		child = null;
	}
}
