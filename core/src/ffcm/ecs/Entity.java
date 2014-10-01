
package ffcm.ecs;

public class Entity
{
	private static int nextId = 1;
	
	private int id;
	
	public Entity()
	{
		id = nextId++;
	}
	
	public int GetId()
	{
		return id;
	}
	
	public void Update()
	{
	
	}
}
