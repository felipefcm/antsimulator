
package ffcm.ecs.resources.quadtree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class QuadTree
{
	private static final int TopLeft = 0;
	private static final int TopRight = 1;
	private static final int BottomLeft = 2;
	private static final int BottomRight = 3;

	public static int BucketSize = 1;

	public Rectangle area;

	public Array<QuadTreeData> data;
	public QuadTree[] child;

    /**
     * Creates a QuadTree with the specified area
     */
	public QuadTree(final Rectangle area)
	{	
		data = new Array<>(false, BucketSize);
		child = null;
		
		this.area = area;
	}

    /**
     * Add a {@link QuadTreeData} to the tree
     *
     * @return True if the insertion was successful
     */
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

    /**
     * Remove a given {@link QuadTreeData} from the tree
     *
     * @return True if the removal was successful
     */
	public boolean Remove(final QuadTreeData node)
	{
		return Remove(node.point);
	}

    /**
     * Remove a {@link QuadTreeData} with the given point
     *
     * @return True if the removal was successful
     */
	public boolean Remove(final Vector2 point)
	{
	    if(!area.contains(point))
	        return false;

        if(child == null)
        {
            for(int i = 0; i < data.size; ++i)
            {
                if(data.get(i).point.equals(point))
                {
                    data.removeIndex(i);
                    return true;
                }
            }
        }
        else
        {
            for(QuadTree i : child)
            {
                if(i.Remove(point))
                {
                    Contract();
                    return true;
                }
            }
        }

        return false;
	}

	private void Contract()
    {
        Array<QuadTreeData> childData = new Array<>();

        for(QuadTree i : child)
        {
            if(i.child == null)
                childData.addAll(i.data);
            else
                return; //found a inner node, no contract operation can be done
        }

        if(childData.size <= BucketSize)
        {
            //contract node
            child = null;

            data = new Array<>(childData);
            data.ordered = false;
        }
    }

    /**
     * Search a given point in the tree
     *
     * @return The {@link QuadTreeData} associated with the point, or null if not found
     */
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

    /**
     * Searches for all the points in a given area
     *
     * @return A {@link QuadTreeData} array containing all the points inside the area
     */
    public QuadTreeData[] SearchArea(final Rectangle searchArea)
	{
		Array<QuadTreeData> inside = new Array<>(QuadTreeData.class);

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

    /**
     * Check whether the tree contains a given point
     */
	public boolean Contains(final Vector2 point)
    {
        return Search(point) != null;
    }

    /**
     * Clear the tree
     */
	public void Clear()
	{
		data = new Array<>(BucketSize);
		child = null;
	}
}
