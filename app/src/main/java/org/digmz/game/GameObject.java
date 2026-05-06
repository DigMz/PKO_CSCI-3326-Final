package org.digmz.game;

import org.digmz.components.Component;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
  private static int ID_COUNTER = 0;
  private int uid = -1;
  private String name;
  public Transform transform;
  private int zIndex;
  private List<Component> components;

  public GameObject(String name, Transform transform, int zIndex) {
    this.name = name;
    this.zIndex = zIndex;
    this.components = new ArrayList<>();
    this.transform  = transform;

    this.uid = ID_COUNTER++;
  }

  public <T extends Component> T getComponent(Class<T> componentClass) {
    for (Component c : components) {
      if (componentClass.isAssignableFrom(c.getClass())) {
        try {
          return componentClass.cast(c);
        } catch (ClassCastException e) {
          assert false : "Error: Casting Component. If you're reading this in terminal, pray.";
        }
      }
    }

    return null;
  }

  public <T extends Component> void removeComponent(Class<T> componentClass) {
    for (int i = 0; i < components.size(); i++) {
      Component c = components.get(i);
      if (componentClass.isAssignableFrom(c.getClass())) {
        components.remove(i); i--;
        return;
      }
    }
  }

  public void addComponent(Component c) {
    c.generateId();
    this.components.add(c);
    c.gameObject = this;
  }

  public void update(float dt) {
    for (int i = 0; i < components.size(); i++) {
      components.get(i).update(dt);
    }
  }

  public void start() {
    for (int i = 0; i < components.size(); i++) {
      components.get(i).start();
    }
  }

  public int zIndex() {return this.zIndex;}

  public int getUid() {
    return this.uid;
  }

  public static void init(int maxId) {
    ID_COUNTER = maxId;
  }

  public List<Component> getAllComponents() {
    return this.components;
  }

  public Transform getTransform() {return transform;}

  public void updateTransform(Transform transform) {
    transform.copyTo(this.transform);
  }
}
