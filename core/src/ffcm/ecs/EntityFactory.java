
package ffcm.ecs;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import ffcm.antsim.Log;

public class EntityFactory
{
	public static EntityFactory _instance = new EntityFactory();
	
	private HashMap<String, Entity> entityMap;
	
	public EntityFactory()
	{
		entityMap = new HashMap<String, Entity>();		
	}
	
	public void InitEntities()
	{
		ParseEntitiesDescriptionFile("data/entities.json");
	}
	
	private void ParseEntitiesDescriptionFile(String filePath)
	{
		JsonReader reader = new JsonReader();
		JsonValue root = reader.parse(Gdx.files.internal(filePath));
		
		JsonValue child = root.child;
		
		while(child != null)
		{
			Entity entity = BuildEntity(child);
			entityMap.put(child.name, entity);
			
			child = child.next;
		}
	}
	
	private Entity BuildEntity(final JsonValue entityDescription)
	{
		JsonValue comps = entityDescription.get("components");
		
		if(comps == null)
		{
			Log.Debug("No components in '" + entityDescription.name + "' entity description");
			return entity;
		}
		
		JsonValue comp = comps.child;
		
		while(comp != null)
		{
			Component cp = ComponentFactory._instance.CreateComponent(comp.name);
			cp.InitComponent(comp);
			
			entity.InsertComponent(cp);
			
			comp = comp.next;
		}
		
		return entity;
	}
	
	public final Entity GetEntity(String name)
	{
		Entity entity = entityMap.get(name);
	}
}
