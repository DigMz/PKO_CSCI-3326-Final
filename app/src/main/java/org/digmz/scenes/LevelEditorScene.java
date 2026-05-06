package org.digmz.scenes;

import org.digmz.game.Camera;
import org.digmz.game.CharacterObject;
import org.digmz.game.GameObject;
import org.digmz.game.Transform;
import org.digmz.game.Window;
import org.digmz.renderer.DebugDraw;
import org.digmz.game.MouseListener;
import org.digmz.game.Prefabs;
import org.digmz.components.SpriteRenderer;
import org.digmz.components.Spritesheet;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.digmz.components.Animation;
import org.digmz.components.AnimationManager;
import org.digmz.components.MouseControls;
import org.digmz.components.Sprite;

import org.digmz.game.KeyListener;

import org.digmz.util.AssetPool;
import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {

  private CharacterObject player1;
  private CharacterObject player2;
  private GameObject player1health;
  private GameObject player2health;
  private Spritesheet sprites;
  SpriteRenderer obj1Sprite;

  GameObject levelEditorStuff = new GameObject("levelEditor", new Transform(), 0);

  public LevelEditorScene() {}
  
  @Override
  public void init() {
    levelEditorStuff.addComponent(new MouseControls());

    loadResources();

    sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png");

    this.camera = new Camera(new Vector2f(0.0f, 0.0f));

    // DebugDraw.addLine2D(new Vector2f(0, 0), new Vector2f(800, 800), new Vector3f(1, 0, 0), 10000);

    if (levelLoaded) {
      if (gameObjects.size() > 0) {
        this.activeGameObject = gameObjects.get(0);
      }
      return;
    }

    player1 = new CharacterObject(
        "Player",
        new Transform(new Vector2f(100, 0), new Vector2f(400, 400)),
        1,
        new SpriteRenderer(),
        new AnimationManager(new ArrayList<Animation>(Arrays.asList(
            new Animation(AssetPool.getSpriteSheet("assets/images/animations/hit-Sheet.png"), false),
            new Animation(AssetPool.getSpriteSheet("assets/images/animations/block-Sheet.png"), false)
            ))),
        new ArrayList<boolean[]>(Arrays.asList(
            new boolean[]{false, false, true, false, false},
            new boolean[81]
            ))
        );
    player1.getComponent(SpriteRenderer.class).setColor(new Vector4f(1.0f, 0.5f, 0.5f, 1.0f));

    player2 = new CharacterObject(
        "Player",
        new Transform(new Vector2f(620, 0), new Vector2f(-400, 400)),
        1,
        new SpriteRenderer(),
        new AnimationManager(new ArrayList<Animation>(Arrays.asList(
            new Animation(AssetPool.getSpriteSheet("assets/images/animations/hit-Sheet.png"), false),
            new Animation(AssetPool.getSpriteSheet("assets/images/animations/block-Sheet.png"), false)
            ))),
        new ArrayList<boolean[]>(Arrays.asList(
            new boolean[]{false, false, true, false, false},
            new boolean[81]
            ))
        );
    player2.getComponent(SpriteRenderer.class).setColor(new Vector4f(0.5f, 0.5f, 1.0f, 1.0f));

    GameObject background = new GameObject("box", new Transform(new Vector2f(0,0), new Vector2f(720, 480)), -1);
    SpriteRenderer bkgdSprite = new SpriteRenderer();
    bkgdSprite.setColor(new Vector4f(0.5f, 0.5f, 0.5f, 1));
    background.addComponent(bkgdSprite);
    this.addGameObjectToScene(background);

    player1health = new GameObject("box", new Transform(new Vector2f(50-25,0), new Vector2f(50, 480)), -1);
    SpriteRenderer p1hSprite = new SpriteRenderer();
    p1hSprite.setColor(new Vector4f(0, 1, 0, 1));
    player1health.addComponent(p1hSprite);
    this.addGameObjectToScene(player1health);

     player2health = new GameObject("box", new Transform(new Vector2f(720-75,0), new Vector2f(50, 480)), -1);
    SpriteRenderer p2hSprite = new SpriteRenderer();
    p2hSprite.setColor(new Vector4f(0, 1, 0, 1));
    player2health.addComponent(p2hSprite);
    this.addGameObjectToScene(player2health);

    this.addGameObjectToScene(player1);
    this.addGameObjectToScene(player2);

  }

  private void loadResources() {
    AssetPool.getShader("assets/shaders/default.glsl");

    AssetPool.addSpriteSheet("assets/images/animations/hit-Sheet.png",
        new Spritesheet(AssetPool.getTexture("assets/images/animations/hit-Sheet.png"),
          64, 64, 5, 0));

    AssetPool.addSpriteSheet("assets/images/animations/block-Sheet.png",
        new Spritesheet(AssetPool.getTexture("assets/images/animations/block-Sheet.png"),
          64, 64, 5, 0));

    AssetPool.addSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png",
        new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/decorationsAndBlocks.png"),
            16, 16, 81, 0));
    AssetPool.getTexture("assets/images/blendImage2.png");

    for (GameObject g : gameObjects) {
      if (g.getComponent(SpriteRenderer.class) != null) {
        SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
        if (spr.getTexture() != null) {
          spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
        }
      }
    }
  }

  float tlen = 2.0f;
  float t = tlen;
  @Override
  public void update(float dt) {
    // System.out.println("FPS " + (1.0f/dt) );
    levelEditorStuff.update(dt);

    if (player1.isActionable()) {
      if (KeyListener.isKeyJustPressed(GLFW_KEY_1)) {
        player1.playAnim(0);
      }
      if (KeyListener.isKeyJustPressed(GLFW_KEY_2)) {
        player1.playAnim(1);
      }
    }
    if (player2.isActionable()) {
      if (KeyListener.isKeyJustPressed(GLFW_KEY_3)) {
        player2.playAnim(0);
      }
      if (KeyListener.isKeyJustPressed(GLFW_KEY_4)) {
        player2.playAnim(1);
      }
    }

    if (player1.justHit) {
      player1.justHit = false;
      if (!player2.blockCheck()) {
        player2health.getTransform().scale = new Vector2f(50, player2health.getTransform().scale.y - 50);
        if (player2health.getTransform().scale.y < 0) {
          Window.get().winner = true;
          Window.changeScene(1);
        }
      }
    }
    if (player2.justHit) {
      player2.justHit = false;
      if (!player1.blockCheck()) {
        player1health.getTransform().scale = new Vector2f(50, player1health.getTransform().scale.y - 50);
        if (player1health.getTransform().scale.y < 0) {
          Window.get().winner = false;
          Window.changeScene(1);
        }
      }
    }


    // if ( t <= 360.0f ) {
    //   t += 1.0f;
    // } else { t = 0.0f; }
    // DebugDraw.addBox2D( new Vector2f(400, 300), new Vector2f(64, 32), t, new Vector3f(0, 0, 1), 1 );
    // DebugDraw.addCircle( new Vector2f(400, 300), 100);

    for (GameObject _go : this.gameObjects) {
      _go.update(dt);
    }

    

    this.renderer.render();
  }

}
