package com.politecnicomalaga.snakebridge.manager;

import com.badlogic.gdx.Gdx;

public class SettingsManager {

    // Constants
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 600;
    public static final int SYS_WIDTH = Gdx.graphics.getWidth();
    public static final int SYS_HEIGHT = Gdx.graphics.getHeight();

    // Limits
    public static final short LMT_MINX = 20;
    public static final short LMT_MAXX = SCREEN_WIDTH - 20;
    public static final short LMT_MINY = 20;
    public static final short LMT_MAXY = SCREEN_HEIGHT - 160;

    // Dimensions
    public static final int SIDE_SIZE = 20;

    // Sites
    public static final short SITES_WIDTH = LMT_MAXX / SIDE_SIZE;
    public static final short SITES_HEIGHT = LMT_MAXY / SIDE_SIZE;
}
