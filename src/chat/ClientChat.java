package chat;

import java.io.*;
import java.net.*;

public class ClientChat {
    private String nmcl;

    private int numeroPort;

    private String nomServeur;

    private Socket socketServeur;

    private PrintStream socOut;

    private BufferedReader socIn;

    private String message;

    private String groupe_cible;

    public ClientChat(String unNomServeur, int unNumero, String msg, String user, String nom_client){
        numeroPort = unNumero;
        nomServeur = unNomServeur;
        message = msg;
        groupe_cible = user;
        nmcl = nom_client;
    }

    public void transmettreMessage(PrintStream socOut, BufferedReader socIn) {
        try {
            System.out.println("requete client : envoie de " + this.message + " à " + this.groupe_cible);
            socOut.println(this.nmcl);
            socOut.println(this.groupe_cible);
            socOut.println(this.message);
            socOut.flush();
            System.out.println("Envoyé !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//qui, pour qui, message