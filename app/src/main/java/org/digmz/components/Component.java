package org.digmz.components;

import org.digmz.game.GameObject;

public abstract class Component {

  private static int ID_COUNTER = 0;
  private int uid = -1;

  public transient GameObject gameObject = null;

  public void update(float dt) {};
  
  public void start() {};

  public void generateId() {
    if (this.uid == -1) {
      this.uid = ID_COUNTER++;
    }
  }

  public int getUid() {
    return this.uid;
  }

  public static void init(int maxId) {
    ID_COUNTER = maxId;
  }
}
