package org.digmz.scenes;

import org.digmz.game.Camera;
import org.digmz.game.GameObject;
import org.digmz.game.Transform;
import org.digmz.game.Window;
import org.digmz.renderer.DebugDraw;
import org.digmz.game.MouseListener;
import org.digmz.game.Prefabs;
import org.digmz.components.SpriteRenderer;
import org.digmz.components.Spritesheet;
import org.digmz.components.MouseControls;
import org.digmz.components.Sprite;

import org.digmz.util.AssetPool;
import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {

  private GameObject obj1;
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

    obj1 = new GameObject("Object 1", new Transform(new Vector2f(0, 0), new Vector2f(Window.getInitWidth(), Window.getInitHeight())), -1);
    obj1Sprite = new SpriteRenderer();
    obj1Sprite.setColor(new Vector4f(1,0,0,1));
    obj1.addComponent(obj1Sprite);
    this.addGameObjectToScene(obj1);

    GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(300, 100), new Vector2f(256, 256)), 1);
    SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
    Sprite obj2Sprite = new Sprite();
    obj2Sprite.setTexture(AssetPool.getTexture("assets/images/testImage.png"));
    obj2SpriteRenderer.setSprite(obj2Sprite);
    obj2.addComponent(obj2SpriteRenderer);
    this.addGameObjectToScene(obj2);

  }

  private void loadResources() {
    AssetPool.getShader("assets/shaders/default.glsl");

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

  // float t = 0.0f;
  @Override
  public void update(float dt) {
    // System.out.println("FPS " + (1.0f/dt) );
    levelEditorStuff.update(dt);

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
