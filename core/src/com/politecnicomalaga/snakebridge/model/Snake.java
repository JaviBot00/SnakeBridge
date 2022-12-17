package com.politecnicomalaga.snakebridge.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.politecnicomalaga.snakebridge.manager.AssetsManager;
import com.politecnicomalaga.snakebridge.manager.SettingsManager;

import java.util.ArrayList;

public class Snake {

    // ATRIBUTOS
    protected Texture imgHead = new Texture(AssetsManager.IMG_HEAD);
    protected Texture imgBody = new Texture(AssetsManager.IMG_BODY);
    protected ArrayList<Square> myBody;
    protected Square.Direction myDirection;

    // CONSTRUCTOR
    public Snake() {
        float posX = (SettingsManager.SITES_WIDTH / 2) * SettingsManager.SIDE_SIZE;
        float posY = (SettingsManager.SITES_HEIGHT / 2) * SettingsManager.SIDE_SIZE;
        myBody = new ArrayList<>();
        Square aux = new Square(posX, posY, SettingsManager.SIDE_SIZE, imgHead);
        myBody.add(aux);
        int iRandom = (int) (Math.random() * 3);
        switch (iRandom) {
            case (0):
                myDirection = Square.Direction.UP;
                break;
            case (1):
                myDirection = Square.Direction.DOWN;
                break;
            case (2):
                myDirection = Square.Direction.LEFT;
                break;
            case (3):
                myDirection = Square.Direction.RIGHT;
                break;
        }
//        miDireccion = Square.ARRIBA;
    }

    // Pintame
    public void render(SpriteBatch miSB) {
        for (Square aux : myBody) {
            aux.render(miSB);
        }
    }

    // Comportamiento
    public void Move() {
        // Y si crezco y elimino el ultimo??
        this.GrowUp();
        myBody.remove(myBody.size() - 1);
    }

    public void GrowUp() {
        // Para crecer, creo un nuevo cuadrado como en el constructor,
        // lo muevo, y lo añado como nueva cabezaa
        Square newHead = new Square(myBody.get(0));
        newHead.selfMove(myDirection);
        myBody.add(0, newHead);

        Square newBody = new Square(myBody.get(1).getPosX(), myBody.get(1).getPosY(), myBody.get(1).getSide(), imgBody);
        myBody.remove(1);
        myBody.add(1, newBody);
    }

    public void changeDirection(Square.Direction iNewDir) {
        boolean bNoChange;
        bNoChange = (myDirection == Square.Direction.UP && iNewDir == Square.Direction.DOWN);
        bNoChange = bNoChange || (myDirection == Square.Direction.DOWN && iNewDir == Square.Direction.UP);
        bNoChange = bNoChange || (myDirection == Square.Direction.LEFT && iNewDir == Square.Direction.RIGHT);
        bNoChange = bNoChange || (myDirection == Square.Direction.RIGHT && iNewDir == Square.Direction.LEFT);
        if (!bNoChange) {
            myDirection = iNewDir;
        }
    }

    public boolean isInside(float lmtMinX, float lmtMaxX, float lmtMinY, float lmtMaxY) {
        return (this.getHeadX() >= lmtMinX &&
                this.getHeadX() <= lmtMaxX - myBody.get(0).getSide() &&
                this.getHeadY() >= lmtMinY &&
                this.getHeadY() <= lmtMaxY - myBody.get(0).getSide());
    }

    public boolean Colision() {
        Square aux = myBody.get(0);
        for (int i = 4; i < myBody.size(); i++) {
            if (myBody.get(i).Colision(aux)) {
                return true;
            }
        }
        return false;
    }

    public boolean inBridge01(Bridge brg) {
        Square aux = myBody.get(0);
        return aux.Colision(brg.getSq01());
    }

    public boolean inBridge02(Bridge brg) {
        Square aux = myBody.get(0);
        return aux.Colision(brg.getSq02());
    }

    public void dispose() {
        for (Square sq : myBody) {
            if (sq.getImg() != null) sq.getImg().dispose();
        }
    }

    // Getters & Setters
    public Square getHead() {
        return myBody.get(0);
    }

    public float getHeadX() {
        return myBody.get(0).getPosX();
    }

    public float getHeadY() {
        return myBody.get(0).getPosY();
    }

}
