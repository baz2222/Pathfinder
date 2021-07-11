package com.baz2222.gap2.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.baz2222.gap2.GapGame2;
import com.baz2222.gap2.listeners.*;

import static com.baz2222.gap2.GapGame2.log;

public class UIManager {
    private GapGame2 game;
    public Stage stage;
    public Viewport viewport;
    public Batch batch;
    public OrthographicCamera camera;
    public OrthographicCamera box2DCamera;

    public BitmapFont midFont, bigFont;
    public AssetManager manager;
    public Label.LabelStyle labelStyle, bigLabelStyle;

    public Table tasksTable;
    public Table pauseTable;
    public Table levelScreenTable;
    public Table menuTable;
    public Table inputTable;
    public Table playMenuTable;
    public Table levelOverTable;
    public Table selectWorldTable;
    public Table selectLevelTable;

    public Label gameNameLabel;
    public Label byLabel;
    public Label inputLabel;
    public Label levelNameLabel;
    public Label levelOverLabel;

    public TextButton playMenuBtn;
    public TextButton levelOverBtn;
    public TextButton nextLevelBtn;
    public TextButton playMenuBackBtn;
    public TextButton inputBtn;
    public TextButton inputBackBtn;
    public TextButton tasksBtn;
    public TextButton tasksBackBtn, selectLevelBackBtn;
    public TextButton newGameBtn;
    public TextButton loadGameBtn;
    public TextButton continueGameBtn, continueBtn, restartBtn, exitBtn;
    public TextButton world1Btn, world2Btn, world3Btn, selectWorldBackBtn;

    public ImageButton pauseBtn;

    public UIManager(GapGame2 game) {
        this.game = game;
        stageSetup();
    }

    public void stageSetup() {
        camera = new OrthographicCamera(game.width, game.height);
        box2DCamera = new OrthographicCamera(game.width / game.scale, game.height / game.scale);
        camera.position.set(game.width / 2, game.height / 2, 0);
        box2DCamera.position.set(game.width / game.scale / 2, game.height / game.scale / 2, 0);
        viewport = new FitViewport(game.width, game.height, camera);
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        manager = new AssetManager();
        loadFonts();
        bigFont = manager.get("fonts/big-font.fnt", BitmapFont.class);
        midFont = manager.get("fonts/mid-font.fnt", BitmapFont.class);
        labelStyle = new Label.LabelStyle(midFont, Color.WHITE);
        bigLabelStyle = new Label.LabelStyle(bigFont, Color.WHITE);
    }

    public void createPauseTable() {
        pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.center();
        continueBtn = createTextButton("CONTINUE", game.graphicsManager.menuBtnTex, new ContinueLevelBtnListener(game));
        restartBtn = createTextButton("RESTART LEVEL", game.graphicsManager.menuBtnTex, new RestartBtnListener(game));
        exitBtn = createTextButton("EXIT TO MENU", game.graphicsManager.menuBtnTex, new ExitBtnListener(game));
        pauseTable.add(continueBtn).pad(10);
        pauseTable.row();
        pauseTable.add(restartBtn).pad(10);
        pauseTable.row();
        pauseTable.add(exitBtn).pad(10);
        stage.addActor(pauseTable);
    }//create pause table

    public void removePauseTable() {
        if (pauseTable != null) {
            pauseTable.remove();
            pauseTable = null;
        }//if
        log("pause table removed");
    }//remove pause table

    public void hideLevelScreenTable(){
        levelScreenTable.remove();
    }

    public void showLevelScreenTable(){
        stage.addActor(levelScreenTable);
    }

    public void createLevelScreenTable(){
        levelScreenTable = new Table();
        levelScreenTable.setFillParent(true);
        levelScreenTable.top();
        levelScreenTable.padTop(20);
        pauseBtn = createImageButton(game.graphicsManager.leftArrowBtnTex,new PauseBtnListener(game));
        levelScreenTable.add(pauseBtn).padLeft(10).padRight(320);
        levelNameLabel = new Label("LEVEL " + game.currWorld + "-" + game.currLevel, labelStyle);
        levelScreenTable.add(levelNameLabel).padRight(380);
        stage.addActor(levelScreenTable);
        log("level screen table created");
    }

    public void removeLevelScreenTable(){
        if (levelScreenTable != null) {
            levelScreenTable.remove();
            levelScreenTable = null;
        }//if
        log("level screen table removed");
    }

    public void createMenuTable() {
        menuTable = new Table();
        menuTable.setFillParent(true);
        menuTable.center();
        stage.addActor(menuTable);
        gameNameLabel = new Label("GAP II", bigLabelStyle);
        menuTable.add(gameNameLabel).colspan(3).pad(20);
        menuTable.row();
        playMenuBtn = createTextButton("PLAY", game.graphicsManager.menuBtnTex, new PlayBtnListener(game));
        menuTable.add(playMenuBtn).pad(20);
        inputBtn = createTextButton("INPUT", game.graphicsManager.menuBtnTex, new InputBtnListener(game));
        menuTable.add(inputBtn).pad(20);
        tasksBtn = createTextButton("TASKS", game.graphicsManager.menuBtnTex, new TasksBtnListener(game));
        menuTable.add(tasksBtn).pad(20);
        menuTable.row();
        byLabel = new Label("by Vasyl Velhus", labelStyle);
        menuTable.add(byLabel).colspan(3).padTop(40);
        //menuTable.debug();
        log("menu table created");
    }

    public void removeMenuTable() {
        if (menuTable != null) {
            menuTable.remove();
            menuTable = null;
            log("menu table removed");
        }//if
    }

    public void createPlayMenuTable(){
        playMenuTable = new Table();
        playMenuTable.bottom();
        playMenuTable.setFillParent(true);
        newGameBtn = createTextButton("NEW GAME", game.graphicsManager.playMenuBtnTex, new NewGameBtnListener(game));
        loadGameBtn = createTextButton("  SELECT GAME  ", game.graphicsManager.playMenuBtnTex, new LoadGameBtnListener(game));
        continueGameBtn = createTextButton("CONTINUE", game.graphicsManager.playMenuBtnTex, new ContinueGameBtnListener(game));
        playMenuTable.add(newGameBtn).pad(20).padBottom(160).padLeft(115);
        playMenuTable.add(loadGameBtn).pad(20).padBottom(160);
        playMenuTable.add(continueGameBtn).pad(20).padBottom(160).padRight(115);
        playMenuTable.row();
        playMenuBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, new PlayMenuBackBtnListener(game));
        playMenuTable.add(playMenuBackBtn).colspan(3).right();
        stage.addActor(playMenuTable);
        log("play menu table created");
    }

    public void createLevelOverTable(String type){
        levelOverTable = new Table();
        levelOverTable.center();
        levelOverTable.setFillParent(true);
        if(type == "next"){
            log("next");
            levelOverLabel = new Label("Congrats! Level done!", labelStyle);
            levelOverTable.add(levelOverLabel).colspan(2).pad(20).padBottom(60);
            levelOverTable.row();
            levelOverBtn = createTextButton("   EXIT TO MENU   ", game.graphicsManager.playMenuBtnTex, new LevelOverBtnListener(game));
            levelOverTable.add(levelOverBtn).pad(20).padBottom(60);
            nextLevelBtn = createTextButton("   NEXT LEVEL   ", game.graphicsManager.playMenuBtnTex, new NextLevelBtnListener(game));
            levelOverTable.add(nextLevelBtn).pad(20).padBottom(60);
        }
        if(type == "over"){
            levelOverLabel = new Label("Congrats! All levels done!", labelStyle);
            levelOverTable.add(levelOverLabel).colspan(1).pad(20).padBottom(60);
            levelOverTable.row();
            levelOverBtn = createTextButton("   EXIT TO MENU   ", game.graphicsManager.playMenuBtnTex, new LevelOverBtnListener(game));
            levelOverTable.add(levelOverBtn).pad(20).padBottom(60);
        }
        if(type == "fail"){
            levelOverLabel = new Label("You are busted :) Try again!", labelStyle);
            levelOverTable.add(levelOverLabel).colspan(2).pad(20).padBottom(60);
            levelOverTable.row();
            levelOverBtn = createTextButton("   EXIT TO MENU   ", game.graphicsManager.playMenuBtnTex, new LevelOverBtnListener(game));
            levelOverTable.add(levelOverBtn).pad(20).padBottom(60);
            nextLevelBtn = createTextButton("   RESTART LEVEL   ", game.graphicsManager.playMenuBtnTex, new RestartLevelBtnListener(game));
            levelOverTable.add(nextLevelBtn).pad(20).padBottom(60);
        }
        stage.addActor(levelOverTable);
        log("level over table created");
    }

    public void removeLevelOverTable() {
        if (levelOverTable != null) {
            levelOverTable.remove();
            levelOverTable = null;
            log("level over table removed");
        }//if
    }

    public void createSelectWorldTable(){
        selectWorldTable = new Table();
        selectWorldTable.bottom();
        selectWorldTable.setFillParent(true);
        world1Btn = createTextButton("WORLD 1", game.graphicsManager.playMenuBtnTex, new SelectWorldBtnListener(game, 1));
        world2Btn = createTextButton("WORLD 2", game.graphicsManager.playMenuBtnTex, new SelectWorldBtnListener(game, 2));
        world3Btn = createTextButton("WORLD 3", game.graphicsManager.playMenuBtnTex, new SelectWorldBtnListener(game, 3));
        selectWorldTable.add(world1Btn).pad(20).padBottom(160).padLeft(145);
        selectWorldTable.add(world2Btn).pad(20).padBottom(160);
        selectWorldTable.add(world3Btn).pad(20).padBottom(160).padRight(145);
        selectWorldTable.row();
        selectWorldBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, new SelectWorldBackBtnListener(game));
        selectWorldTable.add(selectWorldBackBtn).colspan(3).right();
        stage.addActor(selectWorldTable);
        log("select world table created");
    }

        public void createSelectLevelTable(){
            selectLevelTable = new Table();
            selectLevelTable.bottom().right();
            selectLevelTable.setFillParent(true);
            selectLevelBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, new SelectLevelBackBtnListener(game));
            for (int i = 1; i <= 10; i++) {
                if(i % 5 == 0) {
                    selectLevelTable.add(createTextButton(String.valueOf(i), game.graphicsManager.selectLevelBtnTex, new SelectLevelBtnListener(game, i))).pad(40).fill(1.5f,1).padRight(140);
                    selectLevelTable.row();
                }else {
                    selectLevelTable.add(createTextButton(String.valueOf(i), game.graphicsManager.selectLevelBtnTex, new SelectLevelBtnListener(game, i))).pad(40).fill(1.5f,1);
                }
            }
            selectLevelTable.row();
            selectLevelTable.add(selectLevelBackBtn).colspan(5).right().padTop(100);
            stage.addActor(selectLevelTable);
            log("select world table created");
        }

        public void removeSelectLevelTable(){
            if (selectLevelTable != null) {
                selectLevelTable.remove();
                selectLevelTable = null;
                log("select level table removed");
            }//if
        }

    public void removeSelectWorldTable() {
        if (selectWorldTable != null) {
            selectWorldTable.remove();
            selectWorldTable = null;
            log("select world table removed");
        }//if
    }

    public void removePlayMenuTable() {
        if (playMenuTable != null) {
            playMenuTable.remove();
            playMenuTable = null;
            log("play menu table removed");
        }//if
    }

    public void createInputTable() {
        inputTable = new Table();
        inputTable.bottom();
        inputTable.setFillParent(true);
        inputLabel = new Label("MOVE --- LEFT/RIGHT ARROW or SWIPE LEFT/RIGHT\nJUMP --- UP ARROW or TAP", labelStyle);
        inputTable.add(inputLabel).left().padLeft(65).padRight(65);
        inputTable.row();
        inputBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, new InputBackBtnListener(game));
        inputTable.add(inputBackBtn).right().padTop(300);
        stage.addActor(inputTable);
        log("input table created");
    }

    public void removeInputTable() {
        if (inputTable != null) {
            inputTable.remove();
            inputTable = null;
            log("input table removed");
        }//if
    }

    public TextButton createTextButton(String text, Texture texture, InputListener listener) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = midFont;
        style.down = new TextureRegionDrawable(texture);
        style.up = new TextureRegionDrawable(texture);
        style.checked = new TextureRegionDrawable(texture);
        TextButton button = new TextButton(text, style);
        button.addListener(listener);
        return button;
    }

    public ImageButton createImageButton(Texture texture, InputListener listener) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.down = new TextureRegionDrawable(texture);
        style.up = new TextureRegionDrawable(texture);
        style.checked = new TextureRegionDrawable(texture);
        ImageButton button = new ImageButton(style);
        button.addListener(listener);
        return button;
    }

    public void loadFonts() {
        manager.load("fonts/big-font.fnt", BitmapFont.class);
        manager.load("fonts/mid-font.fnt", BitmapFont.class);

        manager.finishLoading();
    }

    public void showMessage(String message) {

    }

    public void createTasksTable() {
        Texture tex;
        int width;
        Image img;
        tasksTable = new Table();
        tasksTable.bottom();
        tasksTable.padTop(75);
        tasksTable.setFillParent(true);
        for (int i = 1; i <= 12; i++) {
            tex = game.taskManager.tasks.get(i-1).icon;
            width = tex.getWidth() / 2;
            if (!game.taskManager.tasks.get(i-1).completed) {
                img = new Image(new TextureRegion(tex, 0, 0, width, 96));
            } else {
                img = new Image(new TextureRegion(tex, 0 + width, 0, width, 96));
            }
            if (i > 1 && i % 2 != 0) {
                tasksTable.row();
            }
            tasksTable.add(img).padTop(5).padBottom(5).padLeft(30).padRight(40);
        }
        tasksTable.row();
        tasksBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, new TasksBackBtnListener(game));
        tasksTable.add(tasksBackBtn).colspan(2).right();
        stage.addActor(tasksTable);
        log("tasks table created");
    }//create task table

    public void removeTasksTable() {
        if (tasksTable != null) {
            tasksTable.remove();
            tasksTable = null;
            log("tasks table removed");
        }//if
    }//remove task table
}
