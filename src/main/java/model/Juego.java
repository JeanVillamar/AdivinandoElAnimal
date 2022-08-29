package model;

import TDAs.BinaryTree;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 *
 * @author Grupo 6
 */
public class Juego {
    private GameData data = GameData.getInstance();
    private String txtRespuestas;
    private String txtPreguntas;
    private File filePreguntas;
    private File fileRespuestas;
    private ArrayList<String> preguntas;
    private ArrayList<String> respuestas;
    Map<String, ArrayList<String>> mapaAnimales;
    private BinaryTree<String> arbol;
    
    public Juego(File archivoPreguntas, File archivoRespuestas) {
        this.filePreguntas = archivoPreguntas;
        this.fileRespuestas = archivoRespuestas;
        this.txtRespuestas = fileRespuestas.getName();
        this.txtPreguntas = filePreguntas.getName();
        obtenerDatos();
        getAnimalDirectionMap();
        crearArbol();        
    }
    
    public ArrayList<String> getPreguntas() {
        return preguntas;
    }

    public ArrayList<String> getRespuestas() {
        return respuestas;
    }

    //Abre los archivos de preguntas y respuestas para devolver un mapa con el formato: <"Preguntas":[ArrayList<String>], "Respuestas":[ArrayList<String>]>
    private void obtenerDatos() {
        //BufferedReader readerPreguntas;
        //BufferedReader readerRespuestas;
        File ruta1 = null;
        File ruta2 = null;
        FileReader fr1 = null;
        FileReader fr2 = null;
        BufferedReader br1 = null;
        BufferedReader br2 = null;
        
        
        preguntas = new ArrayList<>();
        respuestas = new ArrayList<>();
        
        try {
            //ruta1 = new File();
            fr1 = new FileReader(filePreguntas);
            br1 = new BufferedReader(fr1);
            fr2 = new FileReader(fileRespuestas);
            br2 = new BufferedReader(fr2);
            //readerPreguntas = new BufferedReader(new FileReader(filePreguntas));
            //readerRespuestas = new BufferedReader(new FileReader(fileRespuestas));
            String pregunta = br1.readLine();
            String respuesta = br2.readLine();
            while (pregunta != null) {
                preguntas.add(pregunta);
                System.out.println(pregunta);
                pregunta = br1.readLine();
            }
            while (respuesta != null) {
                respuestas.add(respuesta);
                System.out.println(respuesta);
                respuesta = br2.readLine();
            }
            //br1.close();
            //br2.close();
        } catch (IOException e) {
            System.out.println("No se pudo abrir los archivos: ");
        }
    }

    public BinaryTree<String> getArbol() {
        return arbol;
    }

    public int getTreeLength() {
        for (String animal : mapaAnimales.keySet()) {
            return mapaAnimales.get(animal).size();
        }
        return 0;
    }

    private void crearArbol() {
        BinaryTree<String> tmpTree = crearArbol(0);
        llenaAnimales(tmpTree);
        this.arbol = tmpTree;
    }

    private BinaryTree<String> crearArbol(int start) {
        int treeLength = getTreeLength();
        BinaryTree<String> tmpTree = new BinaryTree<>();
        tmpTree.setRootContent(preguntas.get(start));
        //System.out.println(preguntas.get(start));
        if (start != treeLength - 1) {
            tmpTree.setLeft(crearArbol(start + 1));
            tmpTree.setRight(crearArbol(start + 1));
        }
        return tmpTree;
    }

    private void llenaAnimales(BinaryTree<String> arbol) {
        BinaryTree<String> arbolL;
        for (String animal : mapaAnimales.keySet()) {
            arbolL = arbol;
            llenaAnimal(animal, arbolL);
        }
    }

    private void llenaAnimal(String animal, BinaryTree<String> arbol) {
        for (String direccion : mapaAnimales.get(animal)) {
            if (arbol.hasChildren()) {
                if (direccion.equals("SI")) {
                    arbol = arbol.getLeft();
                } else {
                    arbol = arbol.getRight();
                }
            } else {
                if (direccion.equals("SI")) {
                    arbol.setLeft(new BinaryTree<>(animal));
                } else {
                    arbol.setRight(new BinaryTree<>(animal));
                }
            }
        }
    }

    public void play() {
        BinaryTree<String> temp = arbol;
        int cont = 0;
        System.out.println("Bienvenido (a), vamos a jugar.\n¿Listo?");
        String resp;
        Stack<BinaryTree<String>> pila = new Stack<>();
        while (cont != this.getTreeLength()) {
            String question = temp.getRootContent();
            resp = yesOrNo(question);
            pila.push(temp);
            if (resp.toUpperCase().equals("SI")) {
                temp = temp.getLeft();
            } else {
                temp = temp.getRight();
            }
            cont++;
        }
        showResults(temp,pila);        
    }
    private void showResults(BinaryTree<String> arbol, Stack<BinaryTree<String>> pila){
        if (arbol != null) {
            System.out.println("El animal es:");
            System.out.println(arbol.getRootContent());
        } else {
            List<String> posiblesResp = pila.pop().preOrderTraversalIterative();
            posiblesResp = posiblesResp.stream().filter((String a) -> !a.contains("?")).collect(Collectors.toList());
            String posiblesString = String.join(", ", posiblesResp);
            System.out.println("El animal podría ser un...");
            System.out.println(posiblesString);
        }
        
    }
    private String yesOrNo(String question) {
        Scanner sc = new Scanner(System.in);
        System.out.println(question);
        String resp = sc.nextLine();
        while (!(resp.toUpperCase().equals("SI") || resp.toUpperCase().equals("NO"))) {
            System.out.println("Debe escribir 'SI' o 'NO'");
            System.out.println(question);
            resp = sc.nextLine();
        }
        return resp;
    }

    /**
     * Generates a map with the directions of every animal in the tree.
     */
    private void getAnimalDirectionMap() {
        mapaAnimales = new HashMap<>();
        for (String animal : respuestas) {
            String[] lista = animal.split(" ");
            mapaAnimales.put(lista[0], new ArrayList<>());
            for (int i = 1; i < lista.length; i++) {
                mapaAnimales.get(lista[0]).add(lista[i]);
            }
        }
        //System.out.println(mapaAnimales);
    }
}
