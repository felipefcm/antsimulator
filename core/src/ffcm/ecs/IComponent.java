
package ffcm.ecs;

import com.badlogic.gdx.utils.JsonValue;

public interface IComponent
{
	public IComponent CreateFromJson(final JsonValue jsonObj);
	public IComponent Clone();
}
