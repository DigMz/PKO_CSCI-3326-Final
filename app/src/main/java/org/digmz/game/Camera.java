package org.digmz.game;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
  private Matrix4f projectionMatrix, viewMatrix, inverseProjection, inverseView;
  public Vector2f position;
  private Vector2f projectionSize = new Vector2f(Window.getWidth(), Window.getHeight());

  public Camera(Vector2f position) {
    this.position = position;
    projectionMatrix = new Matrix4f();
    viewMatrix = new Matrix4f();
    inverseProjection = new Matrix4f();
    inverseView = new Matrix4f();
    adjustProjection();
  }

  public void adjustProjection() {
    projectionMatrix.identity();
    projectionMatrix.ortho(0.0f, projectionSize.x, 0.0f, projectionSize.y, 0.0f, 100.0f);
    projectionMatrix.invert(inverseProjection);
  }

  public Matrix4f getViewMatrix() {
    Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
    Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);

    this.viewMatrix.identity();
    viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.0f),
                      cameraFront.add(position.x, position.y, 0.0f),
                      cameraUp);
    this.viewMatrix.invert(inverseView);

    return this.viewMatrix;
  }

  public Matrix4f getInverseViewMatrix() {
    return this.inverseView;
  }

  public Matrix4f getProjectionMatrix() {
    return this.projectionMatrix;
  }

  public Matrix4f getInverseProjectionMatrix() {
    return this.inverseProjection;
  }

  public Vector2f getProjectionSize() {
    return this.projectionSize;
  }

}
