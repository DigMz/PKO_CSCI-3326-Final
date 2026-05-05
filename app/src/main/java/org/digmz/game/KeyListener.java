package org.digmz.game;

import static org.lwjgl.glfw.GLFW.*;
import java.util.Arrays;

public class KeyListener {
  private static KeyListener instance;

  private boolean keyPressed[]      = new boolean[GLFW_KEY_LAST];
  private boolean keyJustPressed[]  = new boolean[GLFW_KEY_LAST];
  private boolean keyJustReleased[] = new boolean[GLFW_KEY_LAST];

  private KeyListener() {}

  public static KeyListener get() {
    if (instance == null) {
      instance = new KeyListener();
    }

    return instance;
  }

  public static void endFrame() {
    Arrays.fill(get().keyJustPressed, false);
    Arrays.fill(get().keyJustReleased, false);
  }

  public static void keyCallback(long window, int key, int scancode, int action, int mods) {
    if (action == GLFW_PRESS) {
      get().keyPressed[key] = true;
      get().keyJustPressed[key] = true;
    } else if (action == GLFW_RELEASE) {
      get().keyPressed[key] = false;
      get().keyJustReleased[key] = true;
    }
  }

  public static boolean isKeyPressed(int keyCode) {
    return get().keyPressed[keyCode];
  }

  public static boolean isKeyJustPressed(int keyCode) {
    return get().keyJustPressed[keyCode];
  }

  public static boolean isKeyJustReleased(int keyCode) {
    return get().keyJustReleased[keyCode];
  }
}
