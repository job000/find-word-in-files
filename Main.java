package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main extends Application {
    public static TextArea textArea;
    public static int countWordOccured,countDirectoryOccured, countFileOccured;

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(getHbox());
        borderPane.setCenter(getOutput());

        primaryStage.setTitle("Find word in files");
        primaryStage.setScene(new Scene(borderPane, 1050, 650));
        primaryStage.show();
    }

    public static HBox getHbox(){

        TextField textFieldDir = new TextField();
        textFieldDir.setPrefWidth(450);
        textFieldDir.setPromptText("Directory or Filename");

        TextField textFieldWord = new TextField();
        textFieldWord.setPrefWidth(450);
        textFieldWord.setPromptText("Word");

        Button searchBtn = new Button("Search");
        searchBtn.setPrefWidth(100);

        searchBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                countFileOccured = 0;
                countWordOccured = 0;
                countDirectoryOccured = 0;
                try {
                    textArea.clear();
                    if (!textFieldDir.getText().isEmpty() && !textFieldWord.getText().isEmpty()) {
                        textArea.appendText("Search Start: \n");
                        textArea.appendText("----------------------------------------------------------\n");
                        findDirectory(textFieldDir.getText(), textFieldWord.getText());
                        textArea.appendText("\n---------------------------------------------------------\n");
                        textArea.appendText("Search End\n");
                        textArea.appendText("Searched: " + countFileOccured + " files and " + " found " + countWordOccured + " of " + "''" + textFieldWord.getText() + "''");
                    }else {
                        textArea.appendText("----------------------------------------------------------\n");
                        textArea.appendText("You must add filename or Directory and word to search!");
                        textArea.appendText("\n----------------------------------------------------------\n");
                    }

                }catch (Exception e){
                    System.err.println(e);
                }
            }
        });

        textFieldDir.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()==KeyCode.ENTER){
                    countFileOccured = 0;
                    countWordOccured = 0;
                    countDirectoryOccured = 0;
                    try {
                        textArea.clear();
                        if (!textFieldDir.getText().isEmpty() && !textFieldWord.getText().isEmpty()) {
                            textArea.appendText("Search Start: \n");
                            textArea.appendText("----------------------------------------------------------\n");
                            findDirectory(textFieldDir.getText(), textFieldWord.getText());
                            textArea.appendText("\n---------------------------------------------------------\n");
                            textArea.appendText("Search End\n");
                            textArea.appendText("Searched: " + countFileOccured + " files and " + " found " + countWordOccured + " of " + "''" + textFieldWord.getText() + "''");
                        }else {
                            textArea.appendText("----------------------------------------------------------\n");
                            textArea.appendText("You must add filename or Directory and word to search!");
                            textArea.appendText("\n----------------------------------------------------------\n");
                        }
                    }catch (Exception e){
                        System.err.println(e);
                    }
                }
            }
        });

        textFieldWord.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()==KeyCode.ENTER){
                    countFileOccured = 0;
                    countWordOccured = 0;
                    countDirectoryOccured = 0;

                    try {
                        textArea.clear();
                        if (!textFieldDir.getText().isEmpty() && !textFieldWord.getText().isEmpty()) {
                            textArea.appendText("Search Start: \n");
                            textArea.appendText("----------------------------------------------------------\n");
                            findDirectory(textFieldDir.getText(), textFieldWord.getText());
                            textArea.appendText("\n---------------------------------------------------------\n");
                            textArea.appendText("Search End\n");
                            textArea.appendText("Searched: " +countDirectoryOccured+" Directory, " + countFileOccured + " files and " + " found " + countWordOccured + " of " + "''" + textFieldWord.getText() + "''");
                        }else {
                            textArea.appendText("----------------------------------------------------------\n");
                            textArea.appendText("You must add filename or Directory and word to search!");
                            textArea.appendText("\n----------------------------------------------------------\n");
                        }
                    }catch (Exception e){
                        System.err.println(e);
                    }
                }
            }
        });

        HBox hbox = new HBox(textFieldDir,textFieldWord,searchBtn);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(15,20,10,10));
        return hbox;
    }

    public static HBox getOutput(){

        textArea = new TextArea();
        textArea.setPrefWidth(1050);
        textArea.setEditable(false);

        HBox hbox = new HBox(textArea);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(20,20,10,10));
        return hbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
    /*******************************************************************************/
    /****File Recursive Search for file and search for specific word in a file****/
    /****************************************************************************/

    public static File findDirectory(String path, String wordToSearch)throws Exception{

        File file = new File(path);
        countDirectoryOccured+=1;
        if (file.isDirectory()){
            for (File subFile : file.listFiles()){
                file = findDirectory(subFile.getAbsolutePath(),wordToSearch);
                textArea.appendText("\n");
                countFileOccured+=1;
            }
        }
        readContentFromFile(findFile(file.getAbsolutePath()), wordToSearch);
        return file;
    }

    public static String findFile(String filePath){
        File files = new File(filePath);

        return files.getAbsolutePath();
    }

    public static void readContentFromFile(String fileName, String word)throws Exception{
        //System.out.println(fileName);
        textArea.appendText("From  "+fileName+":                     ");
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s;
        String keyword = word.toLowerCase();  //HER SKAL INPUT FRA WORD TEXTFIELD INN.

        while ((s = bufferedReader.readLine()) != null){
            if (s.toLowerCase().contains(keyword)){
                textArea.appendText("Found word: ''"+keyword+"'' from the following text: "+s+"\n");
                countWordOccured+=1;
            }
        }
    }
}