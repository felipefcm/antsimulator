
package ffcm.ecs;

public interface ISystem
{
	public static final int HighPriority = 0;
	public static final int NormalPriority = 5;
	public static final int LowPriority = 10;
	public static final int RenderPriority = 99;
	
	public void Start();
	public void End();
	
	public void Update(final NodeMap nodeMap);
	
	public int GetPriority();
}
