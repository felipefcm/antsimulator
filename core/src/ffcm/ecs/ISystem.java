
package ffcm.ecs;

public interface ISystem
{
	public void Start();
	public void Update(final NodeMap nodeMap);
	public void End();
}
