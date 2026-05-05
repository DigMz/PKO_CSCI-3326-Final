package org.digmz.scenes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.digmz.game.Camera;
import org.digmz.game.GameObject;
import org.digmz.renderer.Renderer;
import org.digmz.components.Component;

public abstract class Scene {
  public Scene() {}

  protected Renderer renderer = new Renderer();
  protected Camera camera;
  protected List<GameObject> gameObjects = new ArrayList<>();
  private boolean isRunning = false;
  protected GameObject activeGameObject = null;
  protected boolean levelLoaded = false;

  public void init() {}

  public void start() {
    for (GameObject go : gameObjects) {
      go.start();
      this.renderer.add(go);
    }
    isRunning = true;
  }

  public void addGameObjectToScene(GameObject go) {
    if (!isRunning) {
      gameObjects.add(go);
    } else {
      gameObjects.add(go);
      go.start();
      this.renderer.add(go);
    }
  }

  public abstract void update(float dt);

  public Camera camera() {
    return this.camera;
  }

}
