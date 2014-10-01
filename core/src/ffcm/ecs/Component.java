
package ffcm.ecs;

import com.badlogic.gdx.utils.JsonValue;

public abstract class Component
{
	public static final int POSITION_KEY	= 1 << 0;
	public static final int DRAWABLE_KEY	= 1 << 1;
	public static final int MOBILE_KEY		= 1 << 2;
	
	public Entity entity;
	public boolean enabled;
	
	public Component()
	{
		entity = null;
		enabled = true;
	}
	
	public abstract int GetComponentKey();
	
	public abstract boolean InitComponent(JsonValue initData);
	
	public abstract Component Clone();
	
	public void Update()
	{
	}
}
