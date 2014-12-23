
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
	
	public void Init(String entityDescriptionFile)
	{
		ParseEntitiesDescriptionFile(entityDescriptionFile);
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
			Log.Error("Could not create entity " + type.getName() + ": " + e.getMessage());
		}
		
		return entity;
	}
	
	public <T extends Entity> T CreateEntity(Class<T> type)
	{		
		T newEntity = CreateEntityInstance(type);
		
		Entity src = entityMap.get(type.getName());		
		newEntity.Clone(src);
		
		return newEntity;
	}
	
	//TODO change the name of this method to something more meaningful
	private void ParseEntitiesDescriptionFile(String filePath)
	{
		JsonReader reader = new JsonReader();
		JsonValue root = reader.parse(Gdx.files.internal(filePath));
		
		JsonValue child = root.child;
		
		while(child != null)
		{
			//fully qualified name of the entity
			String entityName = child.name;
			
			Entity entity = null;
			
			try
			{
				entity = CreateEntityInstance(Class.forName(entityName).asSubclass(Entity.class));
			}
			catch(Exception e)
			{
				Log.Error("Failed to create entity instance " + entityName + ": " + e.getMessage());
			}
			
			if(entity == null)
			{
				Log.Error("Skipping null entity in creation: " + entityName);
				continue;
			}
			
			JsonValue comp = child.getChild("components");
			
			while(comp != null)
			{
				IComponent component = null;
				
				try
				{
					component = (IComponent) Class.forName(comp.name).newInstance();
					component.CreateFromJson(comp);
				}
				catch(Exception e)
				{
					Log.Error("Failed to create component '" + comp.name + "' for entity '" + entityName + "': " + e.getMessage());
				}
				
				entity.AddComponent(component);
				
				comp = comp.next;
			}
			
			entityMap.put(entityName, entity);
			
			child = child.next;
		}
	}
}










