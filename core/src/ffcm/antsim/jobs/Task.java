
package ffcm.antsim.jobs;

public class Task
{
    public int priority;

    public enum Type
    {
        HAUL_ITEM
    }

    public Type type;

    public Task(int priority, Type type)
    {
        this.priority = priority;
        this.type = type;
    }
}
