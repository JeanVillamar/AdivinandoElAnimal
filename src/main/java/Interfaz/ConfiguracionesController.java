/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Interfaz;

import Interfaz.App;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.GameData;
import model.Juego;
import static model.Alerts.makeAlert;

/**
 *
 * @author Grupo 6
 */
public class ConfiguracionesController implements Initializable {

    GameData data = GameData.getInstance();
    @FXML
    public TextField cantPreguntasTXT;
    @FXML
    private Button btnPreguntas;
    @FXML
    private Button btnRespuestas;

    @FXML
    private Label lblPreguntas;
    @FXML
    private Label lblRespuestas;
    @FXML
    private Button btnJugar;
    @FXML
    private TextField txtRutaPreguntas;
    @FXML
    private TextField txtRutaRespuestas;
    
    public Label fileLabel1 = new Label();
    public Label fileLabel2 = new Label();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void cargarPreguntas(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter ("TEXT files (*.txt)", "*.txt"));
        File preguntasTxt = fc.showOpenDialog(null);
        try{
            if(preguntasTxt!=null){
                fileLabel1.setText(preguntasTxt.getPath());                
                txtRutaPreguntas.setText(fileLabel1.getText());
                try {
                    String fichero = "rutas.txt";
                    String ruta = txtRutaPreguntas.getText();
                    
                    File file = new File(fichero);
                    // Si el archivo no existe es creado
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fw = new FileWriter(file);
                    PrintWriter pw = new PrintWriter(fw);
                    pw.println(ruta);                    
                    pw.close();
                    fw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception ex){
            Alert a=new Alert(Alert.AlertType.ERROR,"Error");
            a.show();
        } 
    }

    @FXML
    private void cargarRespuestas(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter ("TEXT files (*.txt)", "*.txt"));
        File repsuestasTxt = fc.showOpenDialog(null);
        try{
            if(repsuestasTxt!=null){
                fileLabel2.setText(repsuestasTxt.getPath());                
                txtRutaRespuestas.setText(fileLabel2.getText());
                try {
                    String fichero = "rutas.txt";
                    String ruta = txtRutaRespuestas.getText();
                    
                    File file = new File(fichero);
                    // Si el archivo no existe es creado                    
                    FileWriter fw = new FileWriter(file,true);                    
                    PrintWriter pw = new PrintWriter(fw);
                    pw.println(ruta);
                    pw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception ex){
            Alert a=new Alert(Alert.AlertType.ERROR,"Error");
            a.show();
        }      
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @FXML
    private void empezarJuego(MouseEvent event) throws IOException {
                
        try {
            String fichero = "numPreguntas.txt";
            String n = cantPreguntasTXT.getText();
            
            File file = new File(fichero);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
                   
            PrintWriter pw = new PrintWriter(fw);
            pw.println(n);                    
            pw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (!isNumeric(cantPreguntasTXT.getText()) && ( txtRutaRespuestas.getText()== null || txtRutaPreguntas.getText() == null)) {
            makeAlert("No ha ingresado datos en ninguno de los campos", "Debe cargar los archivos .txt e \ningresar la cantidad maxima de preguntas que desea permitir");
        }         
        else if (("".equals(txtRutaRespuestas.getText()) && "".equals(txtRutaPreguntas.getText())) && isNumeric(cantPreguntasTXT.getText())) {            
            makeAlert("No ha seleccionado las rutas", "Debe cargar los archivos .txt");
        }       
        else if ("".equals(txtRutaRespuestas.getText()) && isNumeric(cantPreguntasTXT.getText())) {            
            makeAlert("No ha seleccionado las ruta de repuestas", "Debe cargar el archivo .txt");
        }
        else if (("".equals(txtRutaRespuestas.getText()) && "".equals(txtRutaPreguntas.getText())) && isNumeric(cantPreguntasTXT.getText())) {            
            makeAlert("No ha seleccionado las ruta de preguntas", "Debe cargar el  archivo .txt");
        }
        else if (!isNumeric(cantPreguntasTXT.getText())) {
            makeAlert("Ha ingresado un dato incorrecto en el maximo de preguntas a realizar!", "Debe ingresar un numero entero en la caja.");
        } 
       
       // Parent root = FxmlLoader.loadFXML("Juego");
       App.setRoot("Juego");
    }

    

}
