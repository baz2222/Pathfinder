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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
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

    public Label leftKeyLabel, rightKeyLabel, upKeyLabel, downKeyLabel, confirmKeyLabel, cancelKeyLabel;
    public Label inputInfoLabel;
    public TextButton findInputBtn, changeInputBtn;
    public List<String> list;
    public List.ListStyle listStyle;
    public ScrollPane pane;

    public Label levelNameLabel;
    public Label levelOverLabel;
    public Label messageLabel;

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
        listStyle = new List.ListStyle();
        listStyle.font = midFont;
        listStyle.fontColorSelected = Color.YELLOW;
        listStyle.fontColorUnselected = Color.WHITE;
        listStyle.selection = new TextureRegionDrawable(game.graphicsManager.menuBtnTex);
    }//stage setup

    public void createPauseTable() {
        pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.center();
        continueBtn = createTextButton("CONTINUE", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new ContinueLevelBtnListener(game));
        restartBtn = createTextButton("RESTART LEVEL", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new RestartBtnListener(game));
        exitBtn = createTextButton("EXIT TO MENU", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new ExitBtnListener(game));
        pauseTable.add(continueBtn).pad(10);
        game.inputManager.addISA(continueBtn);
        pauseTable.row();
        pauseTable.add(restartBtn).pad(10);
        game.inputManager.addISA(restartBtn);
        pauseTable.row();
        pauseTable.add(exitBtn).pad(10);
        game.inputManager.addISA(exitBtn);
        game.inputManager.setCurrentISA(0);
        stage.addActor(pauseTable);
    }//create pause table

    public void removePauseTable() {
        if (pauseTable != null) {
            game.inputManager.removeAllISA();
            pauseTable.remove();
            pauseTable = null;
        }//if
        log("pause table removed");
    }//remove pause table

    public void hideLevelScreenTable() {
        levelScreenTable.remove();
    }

    public void showLevelScreenTable() {
        stage.addActor(levelScreenTable);
    }

    public void createLevelScreenTable() {
        levelScreenTable = new Table();
        levelScreenTable.setFillParent(true);
        levelScreenTable.top();
        levelScreenTable.padTop(20);
        pauseBtn = createImageButton(game.graphicsManager.leftArrowBtnTex, new PauseBtnListener(game));
        levelScreenTable.add(pauseBtn).padLeft(10).padRight(320);
        levelNameLabel = new Label("LEVEL " + game.currWorld + "-" + game.currLevel, labelStyle);
        levelScreenTable.add(levelNameLabel).padRight(380);
        stage.addActor(levelScreenTable);
        log("level screen table created");
    }

    public void removeLevelScreenTable() {
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
        playMenuBtn = createTextButton("PLAY", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new PlayBtnListener(game));
        game.inputManager.addISA(playMenuBtn);
        menuTable.add(playMenuBtn).pad(20);
        inputBtn = createTextButton("INPUT", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new InputBtnListener(game));
        game.inputManager.addISA(inputBtn);
        menuTable.add(inputBtn).pad(20);
        tasksBtn = createTextButton("TASKS", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new TasksBtnListener(game));
        game.inputManager.addISA(tasksBtn);
        menuTable.add(tasksBtn).pad(20);
        game.inputManager.setCurrentISA(0);
        menuTable.row();
        byLabel = new Label("by Vasyl Velhus", labelStyle);
        menuTable.add(byLabel).colspan(3).padTop(40);
        //menuTable.debug();
        log("menu table created");
    }

    public void removeMenuTable() {
        if (menuTable != null) {
            game.inputManager.removeAllISA();
            menuTable.remove();
            menuTable = null;
            log("menu table removed");
        }//if
    }

    public void createPlayMenuTable() {
        playMenuTable = new Table();
        playMenuTable.bottom();
        playMenuTable.setFillParent(true);
        newGameBtn = createTextButton("NEW GAME", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new NewGameBtnListener(game));
        loadGameBtn = createTextButton("  SELECT GAME  ", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new LoadGameBtnListener(game));
        continueGameBtn = createTextButton("CONTINUE", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new ContinueGameBtnListener(game));
        playMenuTable.add(newGameBtn).pad(20).padBottom(160).padLeft(115);
        game.inputManager.addISA(newGameBtn);
        playMenuTable.add(loadGameBtn).pad(20).padBottom(160);
        game.inputManager.addISA(loadGameBtn);
        playMenuTable.add(continueGameBtn).pad(20).padBottom(160).padRight(115);
        game.inputManager.addISA(continueGameBtn);
        playMenuTable.row();
        playMenuBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, game.graphicsManager.backCheckedBtnTex, new PlayMenuBackBtnListener(game));
        playMenuTable.add(playMenuBackBtn).colspan(3).right();
        game.inputManager.addISA(playMenuBackBtn);
        game.inputManager.setCurrentISA(0);
        stage.addActor(playMenuTable);
        log("play menu table created");
    }

    public void createLevelOverTable(String type) {
        levelOverTable = new Table();
        levelOverTable.center();
        levelOverTable.setFillParent(true);
        if (type == "next") {
            log("next");
            levelOverLabel = new Label("Congrats! Level done!", labelStyle);
            levelOverTable.add(levelOverLabel).colspan(2).pad(20).padBottom(60);
            levelOverTable.row();
            levelOverBtn = createTextButton("   EXIT TO MENU   ", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new LevelOverBtnListener(game));
            levelOverTable.add(levelOverBtn).pad(20).padBottom(60);
            game.inputManager.addISA(levelOverBtn);
            nextLevelBtn = createTextButton("   NEXT LEVEL   ", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new NextLevelBtnListener(game));
            levelOverTable.add(nextLevelBtn).pad(20).padBottom(60);
            game.inputManager.addISA(nextLevelBtn);
            game.inputManager.setCurrentISA(1);
        }
        if (type == "over") {
            levelOverLabel = new Label("Congrats! All levels done!", labelStyle);
            levelOverTable.add(levelOverLabel).colspan(1).pad(20).padBottom(60);
            levelOverTable.row();
            levelOverBtn = createTextButton("   EXIT TO MENU   ", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new LevelOverBtnListener(game));
            levelOverTable.add(levelOverBtn).pad(20).padBottom(60);
            game.inputManager.addISA(levelOverBtn);
            game.inputManager.setCurrentISA(0);
        }
        if (type == "fail") {
            levelOverLabel = new Label("You are busted :) Try again!", labelStyle);
            levelOverTable.add(levelOverLabel).colspan(2).pad(20).padBottom(60);
            levelOverTable.row();
            levelOverBtn = createTextButton("   EXIT TO MENU   ", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new LevelOverBtnListener(game));
            levelOverTable.add(levelOverBtn).pad(20).padBottom(60);
            game.inputManager.addISA(levelOverBtn);
            nextLevelBtn = createTextButton("   RESTART LEVEL   ", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new RestartLevelBtnListener(game));
            levelOverTable.add(nextLevelBtn).pad(20).padBottom(60);
            game.inputManager.addISA(nextLevelBtn);
            game.inputManager.setCurrentISA(1);
        }
        stage.addActor(levelOverTable);
        log("level over table created");
    }

    public void removeLevelOverTable() {
        if (levelOverTable != null) {
            game.inputManager.removeAllISA();
            levelOverTable.remove();
            levelOverTable = null;
            log("level over table removed");
        }//if
    }

    public void createSelectWorldTable() {
        selectWorldTable = new Table();
        selectWorldTable.bottom();
        selectWorldTable.setFillParent(true);
        world1Btn = createTextButton("WORLD 1", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new SelectWorldBtnListener(game, 1));
        world2Btn = createTextButton("WORLD 2", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new SelectWorldBtnListener(game, 2));
        world3Btn = createTextButton("WORLD 3", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new SelectWorldBtnListener(game, 3));
        game.inputManager.addISA(world1Btn);
        game.inputManager.addISA(world2Btn);
        game.inputManager.addISA(world3Btn);
        selectWorldTable.add(world1Btn).pad(20).padBottom(160).padLeft(145);
        selectWorldTable.add(world2Btn).pad(20).padBottom(160);
        selectWorldTable.add(world3Btn).pad(20).padBottom(160).padRight(145);
        selectWorldTable.row();
        selectWorldBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, game.graphicsManager.backCheckedBtnTex, new SelectWorldBackBtnListener(game));
        game.inputManager.addISA(selectWorldBackBtn);
        game.inputManager.setCurrentISA(0);
        selectWorldTable.add(selectWorldBackBtn).colspan(3).right();
        stage.addActor(selectWorldTable);
        log("select world table created");
    }

    public void createSelectLevelTable() {
        selectLevelTable = new Table();
        selectLevelTable.bottom().right();
        selectLevelTable.setFillParent(true);
        selectLevelBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, game.graphicsManager.backCheckedBtnTex, new SelectLevelBackBtnListener(game));
        for (int i = 1; i <= 10; i++) {
            if (i % 5 == 0) {
                TextButton btn = createTextButton(String.valueOf(i), game.graphicsManager.selectLevelBtnTex, game.graphicsManager.selectLevelCheckedBtnTex, new SelectLevelBtnListener(game, i));
                game.inputManager.addISA(btn);
                selectLevelTable.add(btn).pad(40).fill(1.5f, 1).padRight(140);
                selectLevelTable.row();
            } else {
                TextButton btn = createTextButton(String.valueOf(i), game.graphicsManager.selectLevelBtnTex, game.graphicsManager.selectLevelCheckedBtnTex, new SelectLevelBtnListener(game, i));
                selectLevelTable.add(btn).pad(40).fill(1.5f, 1);
                game.inputManager.addISA(btn);
            }
        }
        selectLevelTable.row();
        selectLevelTable.add(selectLevelBackBtn).colspan(5).right().padTop(100);
        game.inputManager.addISA(selectLevelBackBtn);
        game.inputManager.setCurrentISA(0);
        stage.addActor(selectLevelTable);
        log("select world table created");
    }

    public void removeSelectLevelTable() {
        if (selectLevelTable != null) {
            game.inputManager.removeAllISA();
            selectLevelTable.remove();
            selectLevelTable = null;
            log("select level table removed");
        }//if
    }

    public void removeSelectWorldTable() {
        if (selectWorldTable != null) {
            game.inputManager.removeAllISA();
            selectWorldTable.remove();
            selectWorldTable = null;
            log("select world table removed");
        }//if
    }

    public void removePlayMenuTable() {
        if (playMenuTable != null) {
            game.inputManager.removeAllISA();
            playMenuTable.remove();
            playMenuTable = null;
            log("play menu table removed");
        }//if
    }

    public void createInputTable() {
        inputTable = new Table();
        inputTable.bottom();
        inputTable.setFillParent(true);
        changeInputBtn = createTextButton("CHANGE KEYS", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new ChangeInputBtnListener(game));
        findInputBtn = createTextButton("FIND DEVICE", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new FindInputBtnListener(game));
        inputTable.add(changeInputBtn).padRight(20).padBottom(50).padLeft(100);
        inputTable.add(findInputBtn).padLeft(60).padBottom(50).padRight(60);
        inputTable.row();
        leftKeyLabel = new Label("LEFT KEY : NONE", labelStyle);
        rightKeyLabel = new Label("RIGHT KEY : NONE", labelStyle);
        upKeyLabel = new Label("UP KEY : NONE", labelStyle);
        downKeyLabel = new Label("DOWN KEY : NONE", labelStyle);
        confirmKeyLabel = new Label("OK KEY : NONE", labelStyle);
        cancelKeyLabel = new Label("BACK KEY : NONE", labelStyle);
        VerticalGroup vg = new VerticalGroup();
        vg.addActor(leftKeyLabel);
        vg.addActor(rightKeyLabel);
        vg.addActor(upKeyLabel);
        vg.addActor(downKeyLabel);
        vg.addActor(confirmKeyLabel);
        vg.addActor(cancelKeyLabel);
        vg.columnLeft().padBottom(40).padRight(10).padLeft(100);
        inputTable.add(vg);
        list = new List(listStyle);
        list.setItems("1sdfsdcsdcdssdsd","2dfdsdsdfsdfddsf","sdfdsfsdfsdfsdfdsfsd3","2dfdsdsdfsdfddsf","sdfdsfsdfsdfsdfdsfsd3","sdfdsfsdfsdfsdfdsfsd3","2dfdsdsdfsdfddsf","sdfdsfsdfsdfsdfdsfsd3","2dfdsdsdfsdfddsf","sdfdsfsdfsdfsdfdsfsd3");
        list.setAlignment(Align.left);
        pane = new ScrollPane(list);
        pane.setBounds(0,0,100,100);
        pane.setSmoothScrolling(false);
        inputTable.add(pane).padRight(120).padLeft(120).padBottom(10);
        //inputTable.add(inputLabel).left().padLeft(65).padRight(65);
        inputTable.row();

        //inputGPADInfoLabel = new Label(game.inputManager.getCurrControllerName(), labelStyle);
        //inputTable.add(inputGPADInfoLabel).left().padLeft(65).padRight(65);
        //inputTable.row();

        inputBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, game.graphicsManager.backCheckedBtnTex, new InputBackBtnListener(game));
        game.inputManager.addISA(inputBackBtn);
        inputTable.add(inputBackBtn).colspan(2).right();
        game.inputManager.setCurrentISA(0);
        stage.addActor(inputTable);
        log("input table created");
    }

    public void removeInputTable() {
        if (inputTable != null) {
            game.inputManager.removeAllISA();
            inputTable.remove();
            inputTable = null;
            log("input table removed");
        }//if
    }

    public TextButton createTextButton(String text, Texture defaultTex, Texture checkedTex, InputListener listener) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = midFont;
        style.down = new TextureRegionDrawable(defaultTex);
        style.up = new TextureRegionDrawable(defaultTex);
        style.checked = new TextureRegionDrawable(checkedTex);
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
        messageLabel = new Label(message, labelStyle);
        messageLabel.setWrap(true);
        messageLabel.setWidth(800);
        messageLabel.setAlignment(Align.center);
        messageLabel.setPosition(game.width / 2 - messageLabel.getWidth() / 2, game.height * 0.75f);
        game.uiManager.stage.addActor(messageLabel);
        Timer timer = new Timer();
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                if (messageLabel != null) {
                    messageLabel.remove();
                    messageLabel = null;
                }
            }//run
        };//timer task
        timer.scheduleTask(task, 8);
    }//show message

    public void createTasksTable() {
        Texture tex;
        int width;
        Image img;
        tasksTable = new Table();
        tasksTable.bottom();
        tasksTable.padTop(75);
        tasksTable.setFillParent(true);
        for (int i = 1; i <= 12; i++) {
            tex = game.taskManager.tasks.get(i - 1).icon;
            width = tex.getWidth() / 2;
            if (!game.taskManager.tasks.get(i - 1).completed) {
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
        tasksBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, game.graphicsManager.backCheckedBtnTex, new TasksBackBtnListener(game));
        tasksTable.add(tasksBackBtn).colspan(2).right();
        game.inputManager.addISA(tasksBackBtn);
        game.inputManager.setCurrentISA(0);
        stage.addActor(tasksTable);
        log("tasks table created");
    }//create task table

    public void removeTasksTable() {
        if (tasksTable != null) {
            game.inputManager.removeAllISA();
            tasksTable.remove();
            tasksTable = null;
            log("tasks table removed");
        }//if
    }//remove task table
}
