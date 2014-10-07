
package ffcm.ecs;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import ffcm.antsim.resource.Log;

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
	
	public <T extends Entity> T CreateEntity(Class<T> type)
	{		
		Entity src = entityMap.get(type.getName());
		
		T newEntity = CreateEntityInstance(type);
		
		newEntity.Clone(src);
		
		return newEntity;
	}
	
	private void ParseEntitiesDescriptionFile(String filePath)
	{
		JsonReader reader = new JsonReader();
		JsonValue root = reader.parse(Gdx.files.internal(filePath));
		
		for(JsonValue child = root.child; child != null; child = child.next)
		{
			//fully qualified name of the entity
			String entityName = child.name;
			Entity entity = null;
			
			try
			{
				//TODO guarantee that the entityName is an Entity object
				entity = CreateEntityInstance((Class<? extends Entity>) Class.forName(entityName));
			}
			catch(Exception e)
			{
				Log.Error("Failed to create entity for name " + entityName + ": " + e.getMessage());
			}
			
			if(entity == null)
				continue;
			
			entity.LoadFromDisk(child);
			
			entityMap.put(entityName, entity);
		}
	}
	
	private <T extends Entity> T CreateEntityInstance(Class<T> type)
	{
		T entity = null;

		try
		{
			entity = type.cast(Class.forName(type.getName()).newInstance());
		}
		catch(Exception e)
		{
			Log.Error("Could not create entity: " + type.getName() + ": " + e.getMessage());
		}
		
		return entity;
	}
}










