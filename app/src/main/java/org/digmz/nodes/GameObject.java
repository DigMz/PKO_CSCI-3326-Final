package org.digmz.nodes;

import java.util.ArrayList;
import java.util.List;

public class GameObject extends Node {
  private static int ID_COUNTER = 0;
  private int uid = -1;
  private String name;
  public Transform transform;
  private int zIndex;

  public GameObject(String name, Transform transform, int zIndex) {
    this.name = name;
    this.zIndex = zIndex;
    this.children = new ArrayList<>();
    this.transform = transform;

    this.uid = ID_COUNTER++;
  }


  public <T extends Node> T getFirstChild(Class<T> childClass) {
    for (Node c : children) {
      if (childClass.isAssignableFrom(c.getClass())) {
        try {
          return childClass.cast(c);
        } catch (ClassCastException e) {
          assert false : "Error: Casting Component. If you're reading this in terminal, pray.";
        }
      }
    }

    return null;
  }

  public <T extends Node> void removeFirstChild(Class<T> childClass) {
    for (int i = 0; i < children.size(); i++) {
      Node c = children.get(i);
      if (childClass.isAssignableFrom(c.getClass())) {
        children.remove(i); i--;
        return;
      }
    }
  }

  public void addChild(Node c) {
    this.children.add(c);
    c.setParent(this);
  }


  public void _init() {}
  public void _update() {}
  public int zIndex() {return this.zIndex;}
  public int getUid() { return this.uid; }
  public List<Node> getAllChildren() { return this.children; }

}
