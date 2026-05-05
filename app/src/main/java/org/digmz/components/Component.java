package org.digmz.components;

import org.digmz.game.GameObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.joml.Vector3f;
import org.joml.Vector4f;

import imgui.ImGui;

public abstract class Component {

  private static int ID_COUNTER = 0;
  private int uid = -1;

  public transient GameObject gameObject = null;

  public void update(float dt) {};
  
  public void start() {};

  public void imgui() {
    try {
      Field[] fields = this.getClass().getDeclaredFields(); 
      for (Field field : fields) {
        boolean isPrivate = Modifier.isPrivate(field.getModifiers());
        if (isPrivate) {
          field.setAccessible(true);
        }
        boolean isTransient = Modifier.isTransient(field.getModifiers());
        if (isTransient) {
          continue;
        }

        Class type = field.getType();
        Object value = field.get(this);
        String name = field.getName();

        if (type == int.class) {
          int val = (Integer) value;
          int[] imInt = {val};
          if (ImGui.dragInt(name + ": ", imInt)) {
            field.set(this, imInt[0]);
          }
          continue;
        }
        if (type == float.class) {
          float val = (Float) value;
          float[] imFloat = {val};
          if (ImGui.dragFloat(name + ": ", imFloat)) {
            field.set(this, imFloat[0]);
          }
          continue;
        }
        if (type == boolean.class) {
          boolean val = (boolean) value;
          if (ImGui.checkbox(name + ": ", val)) {
            field.set(this, !val);
          }
          continue;
        }
        if (type == Vector3f.class) {
          Vector3f val = (Vector3f) value;
          float[] imVec = {val.x, val.y, val.z};
          if (ImGui.dragFloat3(name + ": ", imVec)) {
            val.set(imVec[0], imVec[1], imVec[2]);
          }
          continue;
        }
        if (type == Vector4f.class) {
          Vector4f val = (Vector4f) value;
          float[] imVec = {val.x, val.y, val.z};
          if (ImGui.dragFloat4(name + ": ", imVec)) {
            val.set(imVec[0], imVec[1], imVec[2]);
          }
          continue;
        }

        if (isPrivate) {
          field.setAccessible(false);
        }
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

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
