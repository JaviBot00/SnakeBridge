package com.politecnicomalaga.snakebridge.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.politecnicomalaga.snakebridge.manager.AssetsManager;
import com.politecnicomalaga.snakebridge.manager.SettingsManager;

public class Bridge {

    private final Square sq01, sq02;
    private final Texture imgBridge;

    public Bridge() {
        this.imgBridge = new Texture(AssetsManager.IMG_BRIDGE);

        float posY01 = (float) (((int) (Math.random() * SettingsManager.SITES_HEIGHT) - 1) * SettingsManager.SIDE_SIZE) + SettingsManager.LMT_MINY;
        float posX01 = (float) (((int) (Math.random() * SettingsManager.SITES_WIDTH) - 1) * SettingsManager.SIDE_SIZE) + SettingsManager.LMT_MINX;
        this.sq01 = new Square(posX01, posY01, SettingsManager.SIDE_SIZE, imgBridge);


        float posY02 = (float) (((int) (Math.random() * SettingsManager.SITES_HEIGHT) - 1) * SettingsManager.SIDE_SIZE) + SettingsManager.LMT_MINY;
        float posX02 = (float) (((int) (Math.random() * SettingsManager.SITES_WIDTH) - 1) * SettingsManager.SIDE_SIZE) + SettingsManager.LMT_MINX;
        this.sq02 = new Square(posX02, posY02, SettingsManager.SIDE_SIZE, imgBridge);
    }

    // Pintame
    public void render(SpriteBatch myBatch) {
       sq01.render(myBatch);
       sq02.render(myBatch);
    }

    public void dispose() {
        if (imgBridge != null) imgBridge.dispose();
    }

    // Getters & Setters
    public Square getSq01() {
        return sq01;
    }

    public Square getSq02() {
        return sq02;
    }
}
