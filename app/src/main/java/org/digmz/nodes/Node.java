package nodes;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class Node {
  public String id = "N/A";
  public static int frame;
  protected ArrayList<Node> nodeMembers = new ArrayList<>();

  // Template Method Pattern for initlization and running code.
  // Only _init and _run need be defined, _init automatically called
  //  at end of object construction.
 
  public static <T extends Node> T create(java.util.function.Supplier<T> supplier) {
    T instance = supplier.get(); // constructor runs fully
    instance.init();    // children search with initlization
    return instance;
  }

  public final void init() {
    for (Field field : this.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      try {
        Object value = field.get(this);
        if (value instanceof Node) {
          nodeMembers.add((Node) value);
        }
      }
      catch (IllegalAccessException e) {}
    }
    _init();
  };
  protected abstract void _init();

  public final void run() {
    // Runs every node component of the node
    for (Node member : nodeMembers) {
      member.run();
    }
    _run();
  }
  protected abstract void _run();

}
