package launchPattern;

import Register.ProtocoleRegister;
import login.ProtocoleLogin;
import servPattern.IContext;
import servPattern.IProtocole;

import java.io.*;

public class MonGrosProtocole implements IProtocole {
    IProtocole ProtocoleCourant;
    private String name;

    public void execute(IContext c, InputStream unInput, OutputStream unOutput) {
        String inputReq;
        BufferedReader is = new BufferedReader(new InputStreamReader(
                unInput));
        PrintStream os = new PrintStream(unOutput);

        while(true) {
            try {
                String valeurExpediee = "";
                if ((inputReq = is.readLine()) != null) {
                    System.out.println("Ordre Recu " + inputReq);
                    if (inputReq.equals("register")) {
                        ProtocoleCourant = new ProtocoleRegister();
                        System.out.println("Protocole " + inputReq + "lancé");
                        ProtocoleCourant.executebis(c, is, os);
                        this.name = this.ProtocoleCourant.getName();
                        c.addConnectedUser(this.name,is,os);
                        System.out.println("Protocole " + inputReq + "fini");
                    }
                    if (inputReq.equals("login")) {
                        ProtocoleCourant = new ProtocoleLogin();
                        System.out.println("Protocole " + inputReq + "lancé");
                        ProtocoleCourant.executebis(c, is, os);
                        this.name = this.ProtocoleCourant.getName();
                        c.addConnectedUser(this.name,is,os);
                        System.out.println("Protocole " + inputReq + "fini");
                    }
//                if (inputReq.equals("chat")){
//                    ProtocoleCourant = new ProtocoleChat();
//                }
//                if (inputReq.equals("invitation")){
//                    ProtocoleCourant = new ProtocoleInvitation();
//                }
//                if (inputReq.equals("loggout")){
//                    c.sendMessageToAll(this.name,this.name + "has disconnected");
//                    c.removeConnectedUser(this.name);
//                }

                }
            } catch (IOException ignored) {

            }
        }
    }


    public void executebis(IContext aContext, BufferedReader is, PrintStream os) {

    }
    public String getName(){
        return "";
    }
}
