package org.digmz.components;

import org.digmz.game.Transform;
import org.digmz.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {
  private Vector4f color = new Vector4f(1,1,1,1);
  private Sprite sprite = new Sprite();

  private transient Transform lastTransform;
  private transient boolean isDirty = true;

  // public SpriteRenderer(Vector4f color) {
  //   this.color = color;
  //   this.sprite = new Sprite(null);
  //   this.isDirty = true;
  // }
  //
  // public SpriteRenderer(Sprite sprite) {
  //   this.sprite = sprite;
  //   this.color = new Vector4f(1, 1, 1, 1);
  //   this.isDirty = true;
  // }

  @Override
  public void start() {
    this.lastTransform = gameObject.transform.copy();
  }

  @Override
  public void update(float dt) {
    if (!this.lastTransform.equals(this.gameObject.transform)) {
      this.gameObject.transform.copyTo(this.lastTransform);
      isDirty = true;
    }
  }

  public Vector4f getColor() {
    return this.color;
  }

  public Texture getTexture() {
    return sprite.getTexture();
  }

  public Vector2f[] getTexCoords() {
    return sprite.getTexCoords();
  }

  public void setSprite(Sprite sprite) {
    this.sprite = sprite;
    this.isDirty = true;
  }

  public void setColor(Vector4f color) {
    if (!this.color.equals(color)) {
      this.color.set(color);
      isDirty = true;
    }
  }

  public boolean isDirty() {
    return this.isDirty;
  }

  public void clean() {this.isDirty = false;}

  public void setTexture(Texture tex) {
    this.sprite.setTexture(tex);
  }
}
