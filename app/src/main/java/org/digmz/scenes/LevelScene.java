package org.digmz.scenes;

import org.digmz.game.Camera;
import org.digmz.game.Window;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

import org.digmz.game.CharacterObject;
import org.digmz.game.GameObject;
import org.digmz.game.KeyListener;
import org.digmz.game.Transform;
import org.digmz.renderer.DebugDraw;
import org.digmz.game.MouseListener;
import org.digmz.game.Prefabs;
import org.digmz.components.SpriteRenderer;
import org.digmz.components.Spritesheet;

import java.util.ArrayList;
import java.util.Arrays;

import org.digmz.components.Animation;
import org.digmz.components.AnimationManager;
import org.digmz.components.MouseControls;
import org.digmz.components.Sprite;

import org.digmz.util.AssetPool;
import org.joml.Math;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class LevelScene extends Scene{
  public LevelScene() {
    System.out.println("Inside LevelScene");
    // if (Window.get().winner) {
    //   Window.get().clearColor = new float[]{1.0f, 0f, 0f, 0.0f};
    // } else {
    //   Window.get().clearColor = new float[]{0f, 0f, 1.0f, 0.0f};
    // }
  }

  @Override
  public void init() {
    this.camera = new Camera(new Vector2f(0.0f, 0.0f));

    // DebugDraw.addLine2D(new Vector2f(0, 0), new Vector2f(800, 800), new Vector3f(1, 0, 0), 10000);

    GameObject background = new GameObject("box", new Transform(new Vector2f(0,0), new Vector2f(720, 480)), -1);
    SpriteRenderer bkgdSprite = new SpriteRenderer();
    if (Window.get().winner) {
      bkgdSprite.setColor(new Vector4f(1, 0, 0, 1));
    } else {
      bkgdSprite.setColor(new Vector4f(0, 0, 1, 1));
    }
    background.addComponent(bkgdSprite);
    this.addGameObjectToScene(background);


    // this.addGameObjectToScene(player);
    // this.addGameObjectToScene(player2);
  }

  @Override
  public void update(float dt) {
    if (KeyListener.isKeyJustPressed(GLFW_KEY_ENTER)) {
      Window.changeScene(0);
    }
    
    for (GameObject _go : this.gameObjects) {
      _go.update(dt);
    }
    
    this.renderer.render();

  } 

}
