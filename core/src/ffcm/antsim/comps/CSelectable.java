
package ffcm.antsim.comps;

import com.badlogic.gdx.utils.JsonValue;

import ffcm.ecs.IComponent;

public class CSelectable implements IComponent
{
	@Override
	public IComponent Clone()
	{
		return null;
	}

	@Override
	public IComponent CreateFromJson(JsonValue jsonObj)
	{
		return this;
	}
}
