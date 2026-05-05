package org.digmz.components;

import java.util.ArrayList;
import java.util.List;

public class AnimationManager extends Component {

  private ArrayList<Animation> animations;
  private Animation currAnimation;

  int animation = 0;
  boolean playing = false;

  public AnimationManager(ArrayList<Animation> animations) {
    this.animations = animations;
    currAnimation = animations.get(0);
    animation = 0;
  }

  public void play() {playing = true;}
  public void stop() {playing = false;}

  @Override
  public void update(float dt) {
    if (!playing) {return;}

    if (currAnimation.intervalTime < currAnimation.intervalLength) {
      currAnimation.intervalTime += dt;
    } else {
      if (currAnimation.frame > currAnimation.imageCount-1) {
        if (currAnimation.loop) {
          currAnimation.frame = 0;
          currAnimation.frameChanged = true;
        }
        else {
          currAnimation.frame = currAnimation.imageCount-1;
        }
      }
      currAnimation.frame++;
      currAnimation.frameChanged = true;
      currAnimation.intervalTime = 0.0f;
    }
  }

  public boolean frameChanged() {
    return currAnimation.frameChanged;
  }

  public int currentFrame() {
    return currAnimation.frame;
  }

  public void setAnimation(int index) {
    currAnimation = animations.get(index);
    animation = index;
    playing = false;
  }

  public Animation getAnimation(int index) {
    return animations.get(index);
  }

  public void resetCurrAnim() {
    currAnimation.frame = 0;
  }

  public Sprite currentSprite() {
    return currAnimation.currentSprite();
  }

  public Animation getCurrAnim() {return currAnimation;}
  public int getCurrAnimIndex() {return animation;}

  public boolean isPlaying() {return playing;}

}
