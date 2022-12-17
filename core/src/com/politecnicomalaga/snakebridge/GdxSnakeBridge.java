package com.politecnicomalaga.snakebridge;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.politecnicomalaga.snakebridge.manager.AssetsManager;
import com.politecnicomalaga.snakebridge.manager.SettingsManager;
import com.politecnicomalaga.snakebridge.model.Bridge;
import com.politecnicomalaga.snakebridge.model.Snake;
import com.politecnicomalaga.snakebridge.model.Square;
import com.politecnicomalaga.snakebridge.view.PanelNumeros;

public class GdxSnakeBridge extends ApplicationAdapter {
    private SpriteBatch myBatch;
    private Texture imgStart, imgGame, imgDead;

    private enum Screens {START, GAME, DEAD}

    private Screens myScreens;

    float mouseX, mouseY; // Posicion del Raton al Clikar
    float headX, headY; // Posicion de la Cabeza de la Snake
    float difX, difY; // Diferencia entre ambas Posiciones

    private Snake myBody;
    private int iCounter, iBdg;
    private Bridge myBridge;
    private boolean bUpDown;

    private Music mMusic, mDead;
    private boolean bMusicOnOFF;
    private OrthographicCamera camera;
    private PanelNumeros scorePanel;


    @Override
    public void create() {
        myBatch = new SpriteBatch();
        imgStart = new Texture(AssetsManager.IMG_START);
        imgGame = new Texture(AssetsManager.IMG_GAME);
        imgDead = new Texture(AssetsManager.IMG_DEAD);
        myScreens = Screens.START;

        myBody = new Snake();
        iCounter = 0; iBdg = 0;
        myBridge = new Bridge();
        bUpDown = true;

        mMusic = Gdx.audio.newMusic(Gdx.files.internal(AssetsManager.MSC_GAME));
        mMusic.setLooping(true);
        mDead = Gdx.audio.newMusic(Gdx.files.internal(AssetsManager.MSC_DEAD));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, SettingsManager.SCREEN_WIDTH, SettingsManager.SCREEN_HEIGHT);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        myBatch.setProjectionMatrix(camera.combined);
        myBatch.begin();
        myBatch.end();
        switch (myScreens) {
            case START:
                startScreen();
                break;
            case GAME:
                gameScreen();
                break;
            case DEAD:
                deadScreen();
                break;
        }
    }

    //Métodos de trabajo de cada una de las pantallas
    private void startScreen() {
        // Pintar Pantalla de Inicio
        myBatch.begin();
        myBatch.draw(imgStart, 0, 0, SettingsManager.SCREEN_WIDTH, SettingsManager.SCREEN_HEIGHT);
        scorePanel = new PanelNumeros(450f, 470f, 90f);
        scorePanel.setData(0);
        myBatch.end();

        if (Gdx.input.justTouched()) {
            myScreens = Screens.GAME;
            myBody = new Snake();
            myBridge = new Bridge();
        }
    }

    private void gameScreen() {
        // Pintar Pantalla de Juego
        myBatch.begin();
        myBatch.draw(imgGame, 0, 0, SettingsManager.SCREEN_WIDTH, SettingsManager.SCREEN_HEIGHT);
        scorePanel.pintarse(myBatch);
        myBatch.end();
        if (!bMusicOnOFF) {
            mMusic.play();
            bMusicOnOFF = true;
        }

        // Cuando nos tocan
        if (Gdx.input.justTouched()) {

            // Sabemos donde han tocado
            mouseX = Math.round(Gdx.input.getX() * (SettingsManager.SCREEN_WIDTH / SettingsManager.SYS_WIDTH)); // Ancho
            mouseY = Math.round((SettingsManager.SCREEN_HEIGHT - Gdx.input.getY()) * (SettingsManager.SCREEN_HEIGHT / SettingsManager.SYS_HEIGHT)); // Invertir coordenadas.... // Alto

            headX = myBody.getHeadX();
            headY = myBody.getHeadY();
            difX = Math.abs(mouseX - headX);
            difY = Math.abs(mouseY - headY);

            if (difX > difY) { // En el Eje X
                if (mouseX > headX) { // Derecha
                    myBody.changeDirection(Square.Direction.RIGHT);
                } else { // Izquierda
                    myBody.changeDirection(Square.Direction.LEFT);
                }
            } else { // En el eje Y
                if (mouseY > headY) { // Arriba
                    myBody.changeDirection(Square.Direction.UP);
                } else { // Abajo
                    myBody.changeDirection(Square.Direction.DOWN);
                }
            }
        }
        iCounter++;

        // Configurar Estados
        if (myBody.isInside(SettingsManager.LMT_MINX, SettingsManager.LMT_MAXX, SettingsManager.LMT_MINY, SettingsManager.LMT_MAXY)) {
            // Self Movement
            if (iCounter % 10 == 0) {
                myBody.Move();
            } else if (iCounter == 59) {
                myBody.GrowUp();
                iCounter = 0;
                scorePanel.incrementa(1);
                iBdg++;
                if (iBdg == 10) {
                    myBridge.dispose();
                    myBridge = new Bridge();
                    bUpDown = true;
                    iBdg = 0;
                }
            }
            // Use Bridge & Bridge Live
            if (bUpDown) {
                if (myBody.inBridge01(myBridge)) {
                    bUpDown = false;
//                    myBridge.dispose();
                    myBody.getHead().setPosX(myBridge.getSq02().getPosX());
                    myBody.getHead().setPosY(myBridge.getSq02().getPosY());
                } else if ((myBody.inBridge02(myBridge))) {
                    bUpDown = false;
//                    myBridge.dispose();
                    myBody.getHead().setPosX(myBridge.getSq01().getPosX());
                    myBody.getHead().setPosY(myBridge.getSq01().getPosY());
                }
            }

            if (myBody.Colision()) {
                // Aqui tiene que haber sangreeeeeee
                isDead();
            }

        } else {
            isDead();
        }
        // Pintamos la Boa
        myBody.render(myBatch);
        myBridge.render(myBatch);
    }

    private void deadScreen() {
        // Pintar Pantalla de Muerto
        myBatch.begin();
        myBatch.draw(imgDead, 0, 0, SettingsManager.SCREEN_WIDTH, SettingsManager.SCREEN_HEIGHT);
        myBatch.end();
        if (Gdx.input.justTouched()) {
            mDead.stop();
            myScreens = Screens.START;
        }
    }

    private void isDead() {
        mMusic.stop();
        bMusicOnOFF = false;
        myScreens = Screens.DEAD;
        mDead.play();
    }

    @Override
    public void dispose() {
        myBatch.dispose();
        imgStart.dispose();
        imgGame.dispose();
        imgDead.dispose();
        myBody.dispose();
        myBridge.dispose();
        mMusic.dispose();
        mDead.dispose();
        scorePanel.dispose();
    }
}
