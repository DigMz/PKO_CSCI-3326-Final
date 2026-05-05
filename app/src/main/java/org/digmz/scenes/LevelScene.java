package org.digmz.scenes;

import org.digmz.game.Window;

public class LevelScene extends Scene{
  public LevelScene() {
    System.out.println("Inside LevelScene");
    Window.get().clearColor = new float[]{1.0f, 1.0f, 1.0f, 0.0f};
  }

  @Override
  public void update(float dt) {

  } 

}
