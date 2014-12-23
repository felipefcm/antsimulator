
package ffcm.ecs;

import java.util.HashMap;
import java.util.LinkedList;

public class NodeMap
{
	private HashMap<String, LinkedList<INode>> nodes;
	
	public NodeMap()
	{
		nodes = new HashMap<String, LinkedList<INode>>();
	}
	
	public <T extends INode> void AddNode(Class<T> nodeClass, INode node)
	{
		String key = nodeClass.getSimpleName();
		LinkedList<INode> list = null;
		
		if(nodes.containsKey(key))
			list = nodes.get(key);
		else
			nodes.put(key, list = new LinkedList<INode>());
		
		list.add(node);
	}
	
	public <T extends INode> LinkedList<INode> GetNodes(Class<T> nodeClass)
	{
		return nodes.get(nodeClass.getSimpleName());
	}
}
