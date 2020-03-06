package Register;

import login.ToolsIds;
import servPattern.IContext;
import servPattern.IProtocole;
import login.ToolsIds;
import java.io.*;

public class ProtocoleRegister  implements IProtocole {

    private ToolsIds Ids = new ToolsIds();

    public void execute(IContext c , InputStream unInput , OutputStream unOutput ) {
        String inputReq;
        BufferedReader is = new BufferedReader(new InputStreamReader(
                unInput));
        PrintStream os = new PrintStream(unOutput);
        boolean test_nom = false;
        while (!test_nom) {
            try {
                if ((inputReq = is.readLine()) != null) {
                    System.out.println("Ordre Recu " + inputReq);
                    String nom = inputReq;
                    String mdp = is.readLine();
                    System.out.println(nom + mdp);
                    if (!Ids.isUser(nom)) {
                        Ids.addUser(nom, mdp);
                        System.out.println("L'utilisateur a été créer");
                        test_nom = true;
                    } else {
                        System.out.println("Le nom d'utilisatuer existe déjà");
                    }
                    os.println(Boolean.toString(test_nom));
                }
            } catch (Exception e) {
                System.out.println(" Pb d'exception ");
            }
        }
    }
}