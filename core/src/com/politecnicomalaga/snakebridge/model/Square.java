package com.politecnicomalaga.snakebridge.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Square {

    //ATRIBUTOS
    public enum Direction {UP, DOWN, LEFT, RIGHT}

    protected float posX, posY;
    protected Texture img;
    protected int side;

    // CONSTRUCTORES
    public Square(float newX, float newY, int newSide, Texture newimg) {
        posX = newX;
        posY = newY;
        side = newSide;
        img = newimg;
    }

    public Square(Square aux) {
        posX = aux.getPosX();
        posY = aux.getPosY();
        side = aux.getSide();
        img = aux.getImg();
    }

    // Pintame
    public void render(SpriteBatch myBatch) {
        myBatch.begin();
        myBatch.draw(img, posX, posY, side, side);
        myBatch.end();
    }

    // Comportamiento
    public void selfMove(Direction iDir) {
        switch (iDir) {
            case UP:
                posY += side;
                break;
            case DOWN:
                posY -= side;
                break;
            case LEFT:
                posX -= side;
                break;
            case RIGHT:
                posX += side;
                break;
        }
    }

    public boolean Colision(Square aux) {
        return (posX == aux.getPosX() && posY == aux.getPosY());
    }

    // Getters & Setters
    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public int getSide() {
        return side;
    }

    public Texture getImg() {
        return img;
    }
}
