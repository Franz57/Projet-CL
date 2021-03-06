package login;

import java.io.*;
import java.net.*;

public class ClientLogin {
    private int numeroPort;

    private String nomServeur;

    private Socket socketServeur;

    private PrintStream socOut;

    private BufferedReader socIn;

    private String login;

    private String mdp;


    public ClientLogin(String unNomServeur, int unNumero, String log, String m) {
        numeroPort = unNumero;
        nomServeur = unNomServeur;
        login = log;
        mdp = m;

    }

    public boolean connecterAuServeur() {
        boolean ok = false;
        try {
            System.out.println("Tentative : " + nomServeur + " -- " + numeroPort);
            socketServeur = new Socket( nomServeur, numeroPort);
            socOut = new PrintStream(socketServeur.getOutputStream());
            socIn = new BufferedReader (
                    new InputStreamReader (socketServeur.getInputStream()));
            ok = true;
            System.out.println("Connexion faite");

        } catch (UnknownHostException e) {
            System.err.println("Serveur inconnu : " + e);

        } catch (ConnectException e) {
            System.err.println("Exception lors de la connexion:" + e);
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("Exception lors de l'echange de donnees:" + e);
        }
        return ok;
    }
    public void deconnecterDuServeur() {
        try {
            System.out.println("[ClientTCP] CLIENT : " + socketServeur);
            socOut.close();
            socIn.close();
            socketServeur.close();
        } catch (Exception e) {
            System.err.println("Exception lors de la deconnexion :  " + e);
        }
    }

    public String transmettreLogin(PrintStream socOut, BufferedReader socIn) {
        String msgServeur = null;
        try {
            System.out.println( "Tentative de connection en tant que : " + this.login);
            socOut.println( this.login );
            socOut.println(this.mdp);
            socOut.flush();
            System.out.println("Envoyé !");
            msgServeur = socIn.readLine();
            System.out.println( "Reponse serveur : " + msgServeur );
            //deconnecterDuServeur();
        } catch (UnknownHostException e) {
            System.err.println("Serveur inconnu : " + e);
        } catch (IOException e) {
            System.err.println("Exception entree/sortie:  " + e);
            e.printStackTrace();
        }
        return msgServeur;
    }


    /* A utiliser pour ne pas deleguer la connexion aux interfaces GUI */
    //ok ça roule
    public String transmettreChaineConnexionPonctuelle(String uneChaine) {
        String msgServeur = null;
        String chaineRetour = "";
        System.out.println("\nClient connexionTransmettreChaine " + uneChaine);
        if (connecterAuServeur()) {
            try {
                socOut.println(uneChaine);
                socOut.flush();
                msgServeur = socIn.readLine();
                while( msgServeur != null && msgServeur.length() >0) {
                    chaineRetour += msgServeur + "\n";
                    msgServeur = socIn.readLine();
                }
                System.out.println("Client msgServeur " + chaineRetour);
                deconnecterDuServeur();
            } catch (Exception e) {
                System.err.println("Exception lors de la connexion client:  " + e);
            }
        }
        else
        {
            System.err.println("Connexion echouee");
        }
        return chaineRetour;
    }

}


