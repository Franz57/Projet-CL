package appGui;

import Register.ClientRegister;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import launchPattern.MonGrosClient;
import login.ClientLogin;

public class LoginUI extends Application implements ILoginUI {
    public VBox mainPane;
    public Stage primaryStage;
    public String unNomServeur = "localhost";
    public int unNumero = 6666;
    public MonGrosClient monGrosClient;

    public void start(Stage primaryStage) throws Exception {
        try {
            this.primaryStage = primaryStage;
            mainPane = new VBox();
            mainPane.getStyleClass().add("loginWin");
            this.primaryStage.setTitle("LampaulSkype");
            Scene myScene = new Scene(mainPane, 300, 400);
            myScene.getStylesheets().add(getClass().getResource("testSty.css").toExternalForm());
            this.primaryStage.setScene(myScene);
            this.primaryStage.setResizable(false);
            this.primaryStage.show();
            monGrosClient = new MonGrosClient(unNomServeur, unNumero);
            monGrosClient.connecterAuServeur();

            this.primaryStage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            LoginWin();
        } catch(Exception e) {e.printStackTrace();}
    }

    public void LoginWin() throws Exception {
        mainPane = new VBox();
        mainPane.getStyleClass().add("loginWin");

        //--TEXTFIELDS--
        TextField usernameTextField = new TextField();
        usernameTextField.getStyleClass().add("loginTextField");
        usernameTextField.setPromptText("Username");
        usernameTextField.setMinSize(250,40);
        PasswordField passwordTextField = new PasswordField();
        passwordTextField.getStyleClass().add("loginTextField");
        passwordTextField.setPromptText("password");
        passwordTextField.setMinSize(250,40);
        //Error labels
        Label errorUsername = new Label("");
        errorUsername.getStyleClass().add("labelError");
        errorUsername.setAlignment(Pos.CENTER_LEFT);
        Label errorPassword = new Label("");
        errorPassword.getStyleClass().add("labelError");
        errorPassword.setAlignment(Pos.CENTER_LEFT);

        addTextLimiter(usernameTextField,errorUsername);
        addTextLimiter(passwordTextField,errorPassword);

        //TextField area
        VBox areaText = new VBox();
        areaText.getChildren().addAll(usernameTextField,errorUsername,passwordTextField,errorPassword);
        VBox.setMargin(areaText, new Insets(10,10,10,10));
        areaText.setSpacing(10);
        areaText.setAlignment(Pos.BOTTOM_CENTER);

        //--BUTTONS--
        Button butLogin = new Button("Login");
        butLogin.setMinSize(150,40);
        butLogin.getStyleClass().add("buttype1");
        butLogin.setOnAction(actionevent -> {
            //creating Login Protocole
            String log = usernameTextField.getText();
            String pwd = passwordTextField.getText();
            ClientLogin protocoleLog = new ClientLogin(unNomServeur, unNumero, log, pwd);
            //En argument de new ClientLogin(), mettre la socIn et la socOut qui ont été crées dans MonGrosClient
            ErrorLoginUI errorLogUI = new ErrorLoginUI(usernameTextField,errorUsername,passwordTextField,errorPassword);

            //Trying to connect
            try {
                //Reset error borders
                errorLogUI.resetError();
                //Setting right protocol
                monGrosClient.transmettreOrdre("login");
                String msgServeur = protocoleLog.transmettreLogin(monGrosClient.getSocOut(),monGrosClient.getSocIn());
                errorLogUI.verifMsgServeur(msgServeur);
                //protocoleLog.deconnecterDuServeur();
                if (msgServeur.equals("true")) {
                    new MainUI(log, monGrosClient).start(new Stage());
                    primaryStage.close();
                }
            } catch (Exception e) {errorLogUI.showError(e);}

        });
        Button butNewAccount = new Button("Create new Account");
        butNewAccount.setMinSize(150,40);
        butNewAccount.getStyleClass().add("buttype2");
        butNewAccount.setOnAction(actionevent -> {
            try {RegisterWin();} catch (Exception e) {e.printStackTrace();}
        });
        //Button Area
        VBox areaButton = new VBox();
        areaButton.getChildren().addAll(butLogin,butNewAccount);
        VBox.setMargin(areaButton, new Insets(10,10,10,10));
        areaButton.setSpacing(30);
        areaButton.setAlignment(Pos.CENTER);

        //textfield keypress events
        usernameTextField.setOnKeyPressed((e -> {
            if (e.getCode() == KeyCode.ENTER) {
                passwordTextField.requestFocus();}
        }));
        passwordTextField.setOnKeyPressed((e -> {
            if (e.getCode() == KeyCode.ENTER) {
                butLogin.fire();}
        }));

        //Creating main Pane
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setSpacing(30);
        mainPane.getChildren().addAll(areaText,areaButton);
        Scene myScene = new Scene(mainPane, 300, 400);
        myScene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    public void RegisterWin() throws Exception {
        mainPane = new VBox();
        mainPane.getStyleClass().add("loginWin");
        //--TEXTFIELDS--
        TextField usernameTextField = new TextField();
        usernameTextField.getStyleClass().add("loginTextField");
        usernameTextField.setPromptText("Enter your Username");
        usernameTextField.setMinSize(250,40);
        PasswordField passwordTextField = new PasswordField();
        passwordTextField.getStyleClass().add("loginTextField");
        passwordTextField.setPromptText("Enter your Password");
        passwordTextField.setMinSize(250,40);
        PasswordField verifPasswordTextField = new PasswordField();
        verifPasswordTextField.getStyleClass().add("loginTextField");
        verifPasswordTextField.setPromptText("Confirm Password");
        verifPasswordTextField.setMinSize(250,40);
        //textfield keypress events
        usernameTextField.setOnKeyPressed((e -> {
            if (e.getCode() == KeyCode.ENTER) {passwordTextField.requestFocus();}
        }));
        passwordTextField.setOnKeyPressed((e -> {
            if (e.getCode() == KeyCode.ENTER) {verifPasswordTextField.requestFocus();}
        }));
        //Error labels
        Label errorUsername = new Label("");
        errorUsername.getStyleClass().add("labelError");
        errorUsername.setAlignment(Pos.CENTER_LEFT);
        Label errorPassword = new Label("");
        errorPassword.getStyleClass().add("labelError");
        errorPassword.setAlignment(Pos.CENTER_LEFT);
        Label errorVerifPassword = new Label("");
        errorVerifPassword.getStyleClass().add("labelError");
        errorVerifPassword.setAlignment(Pos.CENTER_LEFT);

        addTextLimiter(usernameTextField,errorUsername);
        addTextLimiter(passwordTextField,errorPassword);
        addTextLimiter(verifPasswordTextField,errorVerifPassword);
        //TextField area
        VBox areaText = new VBox();
        areaText.getChildren().addAll(usernameTextField,errorUsername,passwordTextField,errorPassword,verifPasswordTextField,errorVerifPassword);
        VBox.setMargin(areaText, new Insets(10,10,10,10));
        areaText.setSpacing(5);
        areaText.setAlignment(Pos.BOTTOM_CENTER);

        //Buttons
        Button butCreateAccount = new Button("Create New Account");
        Button butLogin = new Button("I already have an account");
        butCreateAccount.setMinSize(150,40);
        butCreateAccount.getStyleClass().add("buttype1");
        butCreateAccount.setOnAction(actionevent -> {
            String log = usernameTextField.getText();
            String pwd = passwordTextField.getText();
            String verifpwd = verifPasswordTextField.getText();
            ClientRegister protocoleReg = new ClientRegister(unNomServeur, unNumero, log, pwd, verifpwd);
            ErrorRegisterUI errorRegUI = new ErrorRegisterUI(usernameTextField,errorUsername,passwordTextField,errorPassword,verifPasswordTextField,errorVerifPassword);
            try {
                //Reset error borders
                errorRegUI.resetError();
                this.monGrosClient.transmettreOrdre("register");
                String msgServeur = protocoleReg.transmettreReg(monGrosClient.getSocOut(),monGrosClient.getSocIn());
                errorRegUI.verifMsgServeur(msgServeur);
                if (msgServeur.equals("true")) {
                    butCreateAccount.setDisable(true);
                    butCreateAccount.setText("Account successfully created");
                    butLogin.setText("Go back to Login");
                }
            }
            catch (Exception e) {
                errorRegUI.showError(e);
                System.out.print("dommageFromage");}
        });
        butLogin.setMinSize(150,40);
        butLogin.getStyleClass().add("buttype2");
        butLogin.setOnAction(actionevent -> {
            try {LoginWin();} catch (Exception e) {e.printStackTrace();}
        });
        VBox areaButton = new VBox();
        areaButton.getChildren().addAll(butCreateAccount,butLogin);
        VBox.setMargin(areaButton, new Insets(10,10,10,10));
        areaButton.setSpacing(30);
        areaButton.setAlignment(Pos.CENTER);


        //Creating main Pane
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setSpacing(30);
        mainPane.getChildren().addAll(areaText,areaButton);
        Scene myScene = new Scene(mainPane, 300, 400);
        myScene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
        primaryStage.setScene(myScene);
        primaryStage.show();
    }


    /* To make Textfield creation easier
    * maximum of 20 char
    * every character except whitespace, backslash and # allowed */
    public static void addTextLimiter(final TextField textField, Label errorLabel) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            textField.getStyleClass().remove("error");
            errorLabel.setText("");
            if (textField.getText().length() > 20) {
                textField.setText(oldValue);
                textField.getStyleClass().add("error");
                errorLabel.setText("Must be 20 characters maximum");
            }
            else if (!oldValue.equals(newValue) && (newValue.matches("[\\s\\\\#%\\[\\]+]") || !newValue.matches("^[\\s\\\\#%\\[\\]+]"))) {
                textField.setText(newValue.replaceAll("[\\s\\\\#%\\[\\]+]", ""));
                if (textField.getText().equals(oldValue)) {
                    textField.getStyleClass().add("error");
                    errorLabel.setText("Character not allowed : blank,\\,#,%,[,],+");}
            }
        });
    }
}