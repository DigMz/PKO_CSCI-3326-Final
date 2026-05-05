package org.digmz.game;

import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.List;

import org.digmz.components.AnimationManager;
import org.digmz.components.SpriteRenderer;

public class CharacterObject extends GameObject {

  private SpriteRenderer sprRend;
  private AnimationManager animan;
  private List<boolean[]> hitframes;

  private boolean hitting = false;
  private boolean prevHit = false;
  private boolean justHit = false;

  public CharacterObject(
      String name, Transform transform, int zIndex, SpriteRenderer sprRend,
      AnimationManager animan, List<boolean[]> hitframes) {
    super(name, transform, zIndex);
    this.animan = animan;
    this.hitframes = hitframes;
    this.sprRend = sprRend;
    this.addComponent(animan);
    this.addComponent(sprRend);

    sprRend.setSprite(animan.currentSprite());
  }

  public void update(float dt) {
    super.update(dt);

    if (animan.frameChanged()) {
      sprRend.setSprite(animan.currentSprite());
    };

    hitting = hitframes.get(animan.getCurrAnimIndex())[animan.currentFrame()];

    if (prevHit == false && hitting == true) {
      justHit = true;
    }

    if (justHit) {
      System.out.println("Hit Registered");

      justHit = false;
    }

    prevHit = hitting;
  }

  public void start() {
    super.start();
  }

  public void setAnim(int index) {
    animan.setAnimation(index);
  }

  public void playAnim(int index) {
    this.setAnim(index);
    animan.play();
  }

  public void resetAnim() {
    this.animan.resetCurrAnim();
  }

  public boolean isHitting() {
    return hitting;
  }
 
}
