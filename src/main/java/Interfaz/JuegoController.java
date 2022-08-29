/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Interfaz;

import TDAs.BinaryTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.GameData;
import model.Juego;

/**
 *
 * @author Grupo 6
 */
public class JuegoController implements Initializable {
    
    

    private static BinaryTree<String> arbol;
    private final GameData data = GameData.getInstance();
    Stack<BinaryTree<String>> pila = new Stack<>();
    private Juego juego;
    private Map<String, ArrayList<String>> mapaAnimales;
    @FXML
    private Label labelPregunta;
    @FXML
    private Button btnSi;
    @FXML
    private Button btnNo;
    @FXML
    private VBox sinoContainer;
    private int cont;
    String camino = "";
    Boolean adivinanzaExacta;

    /**
     * Initializes the controller class.
     *
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // leer rutas.txt   
        File archivo = null;
        
        FileReader fr = null;
        BufferedReader br = null;
        try {
            archivo = new File("rutas.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String linea;
            linea = br.readLine();      
            System.out.println(linea);
            data.setFilePreguntas(new File(linea));                    
            linea = br.readLine();
            System.out.println(linea);
            data.setFileRespuestas(new File(linea));
                                
        } catch (Exception e) {
            e.printStackTrace();
        }
        juego = new Juego(data.getFilePreguntas(), data.getFileRespuestas());
        initGame();
    }

    public void initGame() {
        cont = 0;
        arbol = juego.getArbol();
        getAnimalDirectionMap();
        reloadQuestion();
    }

    public int getTreeLength() {
        for (String animal : mapaAnimales.keySet()) {
            return mapaAnimales.get(animal).size();
        }
        return 0;
    }

    private void getAnimalDirectionMap() {
        mapaAnimales = new HashMap<>();
        for (String animal : juego.getRespuestas()) {
            String[] lista = animal.split(" ");
            mapaAnimales.put(lista[0], new ArrayList<>());
            for (int i = 1; i < lista.length; i++) {
                mapaAnimales.get(lista[0]).add(lista[i]);
            }
        }
    }

    @FXML
    public void btnSiAction() {  
       
        int num = 0;
        // leer rutas.txt   
        File archivo = null;
        
        FileReader fr = null;
        BufferedReader br = null;
        try {
            archivo = new File("numPreguntas.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String linea;
            linea = br.readLine();      
            //System.out.println(linea);
            num = Integer.parseInt(linea);
                                
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        if (cont != num && juego.getPreguntas().size()-1!= cont) {                        
            //reloadQuestion();
            pila.push(arbol);
            arbol = arbol.getLeft();
            labelPregunta.setText(arbol.getRootContent()); 
            cont++;
            camino += "si ";
        } else {            
            showResults(arbol, pila);
            sinoContainer.getChildren().clear();
            fillAnswer();

        }
    }

    @FXML
    public void btnNoAction() {
        int num = 0;
        // leer rutas.txt   
        File archivo = null;
        
        FileReader fr = null;
        BufferedReader br = null;
        try {
            archivo = new File("numPreguntas.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            // Lectura del fichero
            String linea;
            linea = br.readLine();      
            //System.out.println(linea);
            num = Integer.parseInt(linea);
                                
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (cont !=num && juego.getPreguntas().size() -1 != cont) {
            //reloadQuestion();
            pila.push(arbol);
            arbol = arbol.getRight();
            labelPregunta.setText(arbol.getRootContent());
            cont++;
            camino += "no ";
        } else {
            showResults(arbol, pila);
            sinoContainer.getChildren().clear();
            fillAnswer();
        }
    }

    private void reloadQuestion() {
        labelPregunta.setText(arbol.getRootContent());
    }

    private void showResults(BinaryTree<String> arbol, Stack<BinaryTree<String>> pila) {
        if (arbol != null) {
            if (arbol.getRootContent().contains("?")) {
                adivinanzaExacta = false;
                showResults(arbol.getRight(), pila);
            } else {
                adivinanzaExacta = true;
                labelPregunta.setText("El animal es: " + arbol.getRootContent());
            }
        } else {
            List<String> posiblesResp = pila.pop().preOrderTraversalIterative();
            posiblesResp = posiblesResp.stream().filter(a -> !a.contains("?")).collect(Collectors.toList());
            String posiblesString = String.join(", ", posiblesResp);
            labelPregunta.setText("El animal podría ser... " + posiblesString);
        }

    }

    private void fillAnswer() {
        if (adivinanzaExacta) {
            Button btnsalir = new Button("Salir");
            btnsalir.setOnAction(eh -> {
                closeApp();
            });
            sinoContainer.getChildren().add(btnsalir);
        } else {
            HBox hb = new HBox();
            TextField animalNuevo = new TextField();
            Label txtAyudaMejora = new Label("¡Ayúdanos a mejorar!");
            Label txtIndicacion = new Label("Dinos el animal que pensaste para próximas adivinanzas.");
            hb.getChildren().add(animalNuevo);
            hb.setAlignment(Pos.CENTER);
            Button btnConf = new Button("Guardar");
            for (int i = 0; i < this.getTreeLength()-juego.getPreguntas().size(); i++) {
                camino+="no ";
            }
            btnConf.setOnAction(eh -> {
                writeInFile(data.getFileRespuestas(),"\n"+animalNuevo.getText()+" "+camino);
                closeApp();
            });
            sinoContainer.getChildren().addAll(txtAyudaMejora, txtIndicacion, hb, btnConf);
        }
    }

    private void writeInFile(File file, String text) {
        try {
            Files.write(Paths.get(file.getPath()), text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
    private void closeApp(){
        Stage s= (Stage) this.labelPregunta.getScene().getWindow();
        s.close();
    }

}
