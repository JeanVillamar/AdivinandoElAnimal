package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
/**
 *
 * @author Grupo 6
 */
public class GameData {

    private static GameData INSTANCE;
    private String path_preguntas;
    private String path_respuestas;
    private File filePreguntas;
    private File fileRespuestas;
    private ArrayList<String> preguntas;
    private ArrayList<String> respuestas;
    private int cantPreguntas;
    Map<String, ArrayList<String>> mapaAnimales;

    private GameData() {}

    public static GameData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameData();
        }
        return INSTANCE;
    }

    public int getCantPreguntas() {
        return cantPreguntas;
    }

    public void setCantPreguntas(int cantPreguntas) {
        this.cantPreguntas = cantPreguntas;
    }
    
    public File getFilePreguntas() {
        return filePreguntas;
    }

    public void setFilePreguntas(File filePreguntas) {
        this.filePreguntas = filePreguntas;
    }

    public File getFileRespuestas() {
        return fileRespuestas;
    }

    public void setFileRespuestas(File fileRespuestas) {
        this.fileRespuestas = fileRespuestas;
    }

    public ArrayList<String> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(ArrayList<String> preguntas) {
        this.preguntas = preguntas;
    }

    public ArrayList<String> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(ArrayList<String> respuestas) {
        this.respuestas = respuestas;
    }

    public Map<String, ArrayList<String>> getMapaAnimales() {
        return mapaAnimales;
    }

    public void setMapaAnimales(Map<String, ArrayList<String>> mapaAnimales) {
        this.mapaAnimales = mapaAnimales;
    }

    public String getPath_preguntas() {
        return path_preguntas;
    }

    public void setPath_preguntas(String path_preguntas) {
        this.path_preguntas = path_preguntas;
    }

    public String getPath_respuestas() {
        return path_respuestas;
    }

    public void setPath_respuestas(String path_respuestas) {
        this.path_respuestas = path_respuestas;
    }

}
