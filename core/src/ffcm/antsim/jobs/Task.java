
package ffcm.antsim.jobs;

public class Task
{
    public enum Type
    {
        HAUL_ITEM
    }

    public int priority;
    public Type type;
    public Object info;

    public Task(int priority, Type type, Object info)
    {
        this.priority = priority;
        this.type = type;
        this.info = info;
    }
}
