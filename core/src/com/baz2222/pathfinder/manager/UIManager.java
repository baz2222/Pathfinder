package com.baz2222.pathfinder.manager;

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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.baz2222.pathfinder.listeners.*;
import com.baz2222.pathfinder.Pathfinder;
import com.baz2222.pathfinder.tools.GPadKeyMap;

public class UIManager {
    private Pathfinder game;
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
    public Table gamePadSetupTable;

    public Label gameNameLabel;
    public Label byLabel;

    public Label leftKeyLabel, rightKeyLabel, upKeyLabel, downKeyLabel, confirmKeyLabel, cancelKeyLabel;
    public Label inputInfoLabel;
    public Label infoSetupLabel, confirmSetupLabel, cancelSetupLabel, vAxisSetupLabel, hAxisSetupLabel;
    public TextButton changeDeviceInputBtn, changeKeysInputBtn;
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
    public TextButton inputBackBtn;
    public TextButton tasksBtn;
    public TextButton tasksBackBtn, selectLevelBackBtn;
    public TextButton newGameBtn;
    public TextButton loadGameBtn;
    public TextButton continueGameBtn, continueBtn, restartBtn, exitBtn;
    public TextButton world1Btn, world2Btn, world3Btn, selectWorldBackBtn;

    public ImageButton pauseBtn;

    public UIManager(Pathfinder game) {
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
        continueBtn = createTextButton("CONTINUE", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new com.baz2222.pathfinder.listeners.ContinueLevelBtnListener(game));
        restartBtn = createTextButton("RESTART LEVEL", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new com.baz2222.pathfinder.listeners.RestartBtnListener(game));
        exitBtn = createTextButton("EXIT TO MENU", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new com.baz2222.pathfinder.listeners.ExitBtnListener(game));
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

    public void createGamePadSetupTable(){
        gamePadSetupTable = new Table();
        gamePadSetupTable.setFillParent(true);
        gamePadSetupTable.center();
        infoSetupLabel = new Label("GamePad setup...", labelStyle);
        vAxisSetupLabel = new Label("GamePad VERTICAL AXIS key code : none.", labelStyle);
        hAxisSetupLabel = new Label("GamePad HORIZONTAL AXIS key code : none.", labelStyle);
        confirmSetupLabel = new Label("GamePad CONFIRM BUTTON key code : none.", labelStyle);
        cancelSetupLabel = new Label("GamePad CANCEL BUTTON key code : none.", labelStyle);
        gamePadSetupTable.add(infoSetupLabel).pad(10);
        gamePadSetupTable.row();
        gamePadSetupTable.add(hAxisSetupLabel).pad(10);
        gamePadSetupTable.row();
        gamePadSetupTable.add(vAxisSetupLabel).pad(10);
        gamePadSetupTable.row();
        gamePadSetupTable.add(confirmSetupLabel).pad(10);
        gamePadSetupTable.row();
        gamePadSetupTable.add(cancelSetupLabel).pad(10);
        gamePadSetupTable.row();
        stage.addActor(gamePadSetupTable);
        Pathfinder.log("gamepad setup screen table created");
    }//create gamepad setup table

    public void removeGamePadSetupTable(){
        if (gamePadSetupTable != null) {
            gamePadSetupTable.remove();
            gamePadSetupTable = null;
        }//if
        Pathfinder.log("gamepad setup screen table removed");
    }//remove gamepad setup table

    public void removePauseTable() {
        if (pauseTable != null) {
            game.inputManager.removeAllISA();
            pauseTable.remove();
            pauseTable = null;
        }//if
        Pathfinder.log("pause table removed");
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
        pauseBtn = createImageButton(game.graphicsManager.leftArrowBtnTex, new com.baz2222.pathfinder.listeners.PauseBtnListener(game));
        levelScreenTable.add(pauseBtn).padLeft(10).padRight(320);
        levelNameLabel = new Label("LEVEL " + game.currWorld + "-" + game.currLevel, labelStyle);
        levelScreenTable.add(levelNameLabel).padRight(380);
        stage.addActor(levelScreenTable);
        Pathfinder.log("level screen table created");
    }

    public void removeLevelScreenTable() {
        if (levelScreenTable != null) {
            levelScreenTable.remove();
            levelScreenTable = null;
        }//if
        Pathfinder.log("level screen table removed");
    }

    public void createMenuTable() {
        menuTable = new Table();
        menuTable.setFillParent(true);
        menuTable.center();
        stage.addActor(menuTable);
        gameNameLabel = new Label("PATHFINDER", labelStyle);
        menuTable.add(gameNameLabel).colspan(2).pad(20);
        menuTable.row();
        playMenuBtn = createTextButton("PLAY", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new com.baz2222.pathfinder.listeners.PlayBtnListener(game));
        game.inputManager.addISA(playMenuBtn);
        menuTable.add(playMenuBtn).pad(20);
        tasksBtn = createTextButton("TASKS", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new com.baz2222.pathfinder.listeners.TasksBtnListener(game));
        game.inputManager.addISA(tasksBtn);
        menuTable.add(tasksBtn).pad(20);
        game.inputManager.setCurrentISA(0);
        menuTable.row();
        byLabel = new Label("by Vasyl Velhus", labelStyle);
        menuTable.add(byLabel).colspan(2).padTop(40);
        Pathfinder.log("menu table created");
    }

    public void removeMenuTable() {
        if (menuTable != null) {
            game.inputManager.removeAllISA();
            menuTable.remove();
            menuTable = null;
            Pathfinder.log("menu table removed");
        }//if
    }

    public void createPlayMenuTable() {
        playMenuTable = new Table();
        playMenuTable.bottom();
        playMenuTable.setFillParent(true);
        newGameBtn = createTextButton("NEW GAME", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new com.baz2222.pathfinder.listeners.NewGameBtnListener(game));
        loadGameBtn = createTextButton("  SELECT GAME  ", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new com.baz2222.pathfinder.listeners.LoadGameBtnListener(game));
        continueGameBtn = createTextButton("CONTINUE", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new ContinueGameBtnListener(game));
        playMenuTable.add(newGameBtn).pad(20).padBottom(160).padLeft(115);
        game.inputManager.addISA(newGameBtn);
        playMenuTable.add(loadGameBtn).pad(20).padBottom(160);
        game.inputManager.addISA(loadGameBtn);
        playMenuTable.add(continueGameBtn).pad(20).padBottom(160).padRight(115);
        game.inputManager.addISA(continueGameBtn);
        playMenuTable.row();
        playMenuBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, game.graphicsManager.backCheckedBtnTex, new com.baz2222.pathfinder.listeners.PlayMenuBackBtnListener(game));
        playMenuTable.add(playMenuBackBtn).colspan(3).right();
        game.inputManager.addISA(playMenuBackBtn);
        game.inputManager.setCurrentISA(0);
        stage.addActor(playMenuTable);
        Pathfinder.log("play menu table created");
    }

    public void createLevelOverTable(String type) {
        levelOverTable = new Table();
        levelOverTable.center();
        levelOverTable.setFillParent(true);
        if (type == "next") {
            Pathfinder.log("next");
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
        Pathfinder.log("level over table created");
    }

    public void removeLevelOverTable() {
        if (levelOverTable != null) {
            game.inputManager.removeAllISA();
            levelOverTable.remove();
            levelOverTable = null;
            Pathfinder.log("level over table removed");
        }//if
    }

    public void createSelectWorldTable() {
        selectWorldTable = new Table();
        selectWorldTable.bottom();
        selectWorldTable.setFillParent(true);
        world1Btn = createTextButton("WORLD 1", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new com.baz2222.pathfinder.listeners.SelectWorldBtnListener(game, 1));
        world2Btn = createTextButton("WORLD 2", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new com.baz2222.pathfinder.listeners.SelectWorldBtnListener(game, 2));
        world3Btn = createTextButton("WORLD 3", game.graphicsManager.playMenuBtnTex, game.graphicsManager.playMenuCheckedBtnTex, new com.baz2222.pathfinder.listeners.SelectWorldBtnListener(game, 3));
        game.inputManager.addISA(world1Btn);
        game.inputManager.addISA(world2Btn);
        game.inputManager.addISA(world3Btn);
        selectWorldTable.add(world1Btn).pad(20).padBottom(160).padLeft(145);
        selectWorldTable.add(world2Btn).pad(20).padBottom(160);
        selectWorldTable.add(world3Btn).pad(20).padBottom(160).padRight(145);
        selectWorldTable.row();
        selectWorldBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, game.graphicsManager.backCheckedBtnTex, new com.baz2222.pathfinder.listeners.SelectWorldBackBtnListener(game));
        game.inputManager.addISA(selectWorldBackBtn);
        game.inputManager.setCurrentISA(0);
        selectWorldTable.add(selectWorldBackBtn).colspan(3).right();
        stage.addActor(selectWorldTable);
        Pathfinder.log("select world table created");
    }

    public void createSelectLevelTable() {
        selectLevelTable = new Table();
        selectLevelTable.bottom().right();
        selectLevelTable.setFillParent(true);
        selectLevelBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, game.graphicsManager.backCheckedBtnTex, new com.baz2222.pathfinder.listeners.SelectLevelBackBtnListener(game));
        for (int i = 1; i <= 10; i++) {
            if (i % 5 == 0) {
                TextButton btn = createTextButton(String.valueOf(i), game.graphicsManager.selectLevelBtnTex, game.graphicsManager.selectLevelCheckedBtnTex, new com.baz2222.pathfinder.listeners.SelectLevelBtnListener(game, i));
                game.inputManager.addISA(btn);
                selectLevelTable.add(btn).pad(40).fill(1.5f, 1).padRight(140);
                selectLevelTable.row();
            } else {
                TextButton btn = createTextButton(String.valueOf(i), game.graphicsManager.selectLevelBtnTex, game.graphicsManager.selectLevelCheckedBtnTex, new com.baz2222.pathfinder.listeners.SelectLevelBtnListener(game, i));
                selectLevelTable.add(btn).pad(40).fill(1.5f, 1);
                game.inputManager.addISA(btn);
            }
        }
        selectLevelTable.row();
        selectLevelTable.add(selectLevelBackBtn).colspan(5).right().padTop(100);
        game.inputManager.addISA(selectLevelBackBtn);
        game.inputManager.setCurrentISA(0);
        stage.addActor(selectLevelTable);
        Pathfinder.log("select world table created");
    }

    public void removeSelectLevelTable() {
        if (selectLevelTable != null) {
            game.inputManager.removeAllISA();
            selectLevelTable.remove();
            selectLevelTable = null;
            Pathfinder.log("select level table removed");
        }//if
    }

    public void removeSelectWorldTable() {
        if (selectWorldTable != null) {
            game.inputManager.removeAllISA();
            selectWorldTable.remove();
            selectWorldTable = null;
            Pathfinder.log("select world table removed");
        }//if
    }

    public void removePlayMenuTable() {
        if (playMenuTable != null) {
            game.inputManager.removeAllISA();
            playMenuTable.remove();
            playMenuTable = null;
            Pathfinder.log("play menu table removed");
        }//if
    }

    public void createInputTable() {
        inputTable = new Table();
        inputTable.bottom();
        inputTable.setFillParent(true);
        changeKeysInputBtn = createTextButton("CHANGE KEYS", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new com.baz2222.pathfinder.listeners.ChangeKeysInputBtnListener(game));
        changeDeviceInputBtn = createTextButton("CHANGE DEVICE", game.graphicsManager.menuBtnTex, game.graphicsManager.menuCheckedBtnTex, new com.baz2222.pathfinder.listeners.ChangeDeviceInputBtnListener(game));
        game.inputManager.addISA(changeKeysInputBtn);
        game.inputManager.addISA(changeDeviceInputBtn);
        inputTable.add(changeKeysInputBtn).padRight(60).padBottom(20).padLeft(140);
        inputTable.add(changeDeviceInputBtn).padLeft(70).padBottom(20).padRight(176);
        inputTable.row();
        if(game.inputManager.currentGPadKeyMap != null){
            leftKeyLabel = new Label("Left Key : " + game.inputManager.currentGPadKeyMap.left, labelStyle);
            rightKeyLabel = new Label("Right Key : " + game.inputManager.currentGPadKeyMap.right, labelStyle);
            upKeyLabel = new Label("Up Key : " + game.inputManager.currentGPadKeyMap.up, labelStyle);
            downKeyLabel = new Label("Down Key : " + game.inputManager.currentGPadKeyMap.down, labelStyle);
            confirmKeyLabel = new Label("OK Key : " + game.inputManager.currentGPadKeyMap.confirm, labelStyle);
            cancelKeyLabel = new Label("Back Key : " + game.inputManager.currentGPadKeyMap.cancel, labelStyle);
        }else{
            leftKeyLabel = new Label("Left Key : none", labelStyle);
            rightKeyLabel = new Label("Right Key : none", labelStyle);
            upKeyLabel = new Label("Up Key : none", labelStyle);
            downKeyLabel = new Label("Down Key : none", labelStyle);
            confirmKeyLabel = new Label("OK Key : none", labelStyle);
            cancelKeyLabel = new Label("Back Key : none", labelStyle);
        }//else
        VerticalGroup vg = new VerticalGroup();
        vg.addActor(leftKeyLabel);
        vg.addActor(rightKeyLabel);
        vg.addActor(upKeyLabel);
        vg.addActor(downKeyLabel);
        vg.addActor(confirmKeyLabel);
        vg.addActor(cancelKeyLabel);
        vg.columnLeft().padBottom(5).padRight(10).padLeft(100);
        inputTable.add(vg).left().padLeft(60);
        list = new List(listStyle);
        Array<String> items = new Array<>();
        if (game.inputManager.GPads != null) {
            for(GPadKeyMap map : game.inputManager.GPadKeyMaps){
                items.add(map.name);
            }//for
            list.setItems(items);
        }//if
        list.setAlignment(Align.left);
        pane = new ScrollPane(list);
        items = list.getItems();
        for (int i = 0; i < items.size; i++) {
            if(game.inputManager.currentGPadKeyMap != null && items.get(i) == game.inputManager.currentGPadKeyMap.name){
                list.setSelectedIndex(i);
            }
        }
        pane.setSmoothScrolling(false);
        inputTable.add(pane).size(300, 230).left().top().padLeft(50);
        inputTable.row();
        if(game.inputManager.currentGPad != null){
            inputInfoLabel = new Label("CURRENT DEVICE: " + game.inputManager.currentGPad.getName(), labelStyle);
        } else {
            inputInfoLabel = new Label("CURRENT DEVICE: No connected devices", labelStyle);
        }//else
        inputTable.add(inputInfoLabel).left().padLeft(100).padTop(10).padBottom(20).colspan(2);
        inputTable.row();
        inputBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, game.graphicsManager.backCheckedBtnTex, new com.baz2222.pathfinder.listeners.InputBackBtnListener(game));
        game.inputManager.addISA(inputBackBtn);
        inputTable.add(inputBackBtn).colspan(2).right();
        game.inputManager.setCurrentISA(0);
        stage.addActor(inputTable);
        Pathfinder.log("input table created");
    }

    public void removeInputTable() {
        if (inputTable != null) {
            game.inputManager.removeAllISA();
            inputTable.remove();
            inputTable = null;
            Pathfinder.log("input table removed");
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
        tasksBackBtn = createTextButton("BACK", game.graphicsManager.backBtnTex, game.graphicsManager.backCheckedBtnTex, new com.baz2222.pathfinder.listeners.TasksBackBtnListener(game));
        tasksTable.add(tasksBackBtn).colspan(2).right();
        game.inputManager.addISA(tasksBackBtn);
        game.inputManager.setCurrentISA(0);
        stage.addActor(tasksTable);
        Pathfinder.log("tasks table created");
    }//create task table

    public void removeTasksTable() {
        if (tasksTable != null) {
            game.inputManager.removeAllISA();
            tasksTable.remove();
            tasksTable = null;
            Pathfinder.log("tasks table removed");
        }//if
    }//remove task table
}
