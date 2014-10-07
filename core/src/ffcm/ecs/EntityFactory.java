
package ffcm.ecs;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import ffcm.antsim.Ant;

public class EntityFactory
{
	public static EntityFactory _instance = new EntityFactory();
	
	private HashMap<String, Entity> entityMap;
	
	public EntityFactory()
	{
		entityMap = new HashMap<String, Entity>();
	}
	
	public void Init()
	{
		ParseEntitiesDescriptionFile("data/entities.json");
	}
	
	public Ant CreateAnt()
	{		
		Entity src = entityMap.get("Ant");
		Ant ant = new Ant();
		
		ant.Clone(src);
		
		return ant;
	}
	
	private void ParseEntitiesDescriptionFile(String filePath)
	{
		JsonReader reader = new JsonReader();
		JsonValue root = reader.parse(Gdx.files.internal(filePath));
		
		for(JsonValue child = root.child; child != null; child = child.next)
		{
			String entityName = child.name;
			
			Entity entity = CreateEntityClass(entityName);
			
			if(entity == null)
				continue;
			
			entity.LoadFromDisk(child);
			
			entityMap.put(entityName, entity);
		}
	}
	
	private Entity CreateEntityClass(String name)
	{
		//TODO search for some reflection black magic to do the trick
		
		if(name.equalsIgnoreCase("ant"))
		{
			return new Ant();
		}
		
		return null;
	}
}










