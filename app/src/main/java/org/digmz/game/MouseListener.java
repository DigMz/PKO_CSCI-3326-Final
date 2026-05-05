package org.digmz.game;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector4f;

public class MouseListener {
  private static MouseListener instance;
  private double scrollX, scrollY;
  private double xPos, yPos, xLast, yLast;
  private boolean MouseButtonPressed[] = new boolean[9];
  private boolean isDragging;

  
  private MouseListener() {
    this.scrollX = 0.0; this.scrollY = 0.0;
    this.xPos = 0.0   ; this.yPos = 0.0;
    this.xLast = 0.0  ; this.yLast = 0.0;
  }

  public static MouseListener get() {
    if (instance == null) {
      instance = new MouseListener();
    }

    return instance;
  }

  public static void mousePosCallback(long window, double xPos, double yPos) {
    get().xLast = get().xPos;
    get().yLast = get().yPos;
    get().xPos = xPos;
    get().yPos = yPos;

    get().isDragging = false;
    for (boolean button : get().MouseButtonPressed) {
      if (button = true) {get().isDragging = true;}
    }
  }

  public static void mouseButtonCallback(long window, int button, int action, int mods) {
    if (button > get().MouseButtonPressed.length) {return;}
    if (action == GLFW_PRESS) {
      get().MouseButtonPressed[button] = true;
    } else if (action == GLFW_RELEASE) {
      get().MouseButtonPressed[button] = false;
      get().isDragging = false;
    }
  }

  public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
    get().scrollX = xOffset;
    get().scrollY = yOffset;
  }

  public static void endFrame() {
    get().scrollX = 0.0;
    get().scrollY = 0.0;
    get().xLast = get().xPos;
    get().yLast = get().yPos;

  }

  public static float getX() {
    return (float) get().xPos;
  }

  public static float getY() {
    return (float) get().yPos;
  }

  public static float getOrthoX() {
    float currentX = getX();
    currentX = (currentX / (float) Window.getWidth()) * 2 - 1;
    Vector4f temp = new Vector4f(currentX, 0, 0, 1);
    temp.mul(Window.getScene().camera().getInverseProjectionMatrix()).mul(Window.getScene().camera().getInverseViewMatrix());
    currentX = temp.x;

    return currentX;
  }

  public static float getOrthoY() {
    float currentY = Window.getHeight() - getY();
    currentY = (currentY / (float) Window.getHeight()) * 2 - 1;
    Vector4f temp = new Vector4f(0, currentY, 0, 1);
    temp.mul(Window.getScene().camera().getInverseProjectionMatrix()).mul(Window.getScene().camera().getInverseViewMatrix());
    currentY = temp.y;

    return currentY;
  }

  public static float getDx() {
    return (float) (get().xLast - get().xPos);
  }

  public static float getDy() {
    return (float) (get().yLast - get().yPos);
  }

  public static float getScrollX() {
    return (float) get().scrollX;
  }

  public static float getScrollY() {
    return (float) get().scrollY;
  }

  public static boolean isDragging() {
    return get().isDragging;
  }

  public static boolean mouseButtonDown(int button) {
    if (button > get().MouseButtonPressed.length) {return false;}
    return get().MouseButtonPressed[button];
  }
}
