package context;

import servPattern.IContext;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class LeContext implements IContext {
    private HashMap<String, User> connectedUserList = new HashMap<>();

    private HashMap<String, ArrayList<String>> groupChatList = new HashMap<>();

    public LeContext(){
        this.groupChatList.put("General",new ArrayList<String>());
    }

    public void createGroupChat(String groupName){
        this.groupChatList.put(groupName, new ArrayList<String>());
    }

    public void addUserToGroupChat(String userName, String groupName){
        if (!(this.groupChatList.get(groupName).contains(userName))) {
            this.groupChatList.get(groupName).add(userName);
        }
    }

    public void removeUserOfGroupChat(String userName, String groupName){
        if (this.groupChatList.get(groupName).contains(userName)) {
            this.groupChatList.get(groupName).remove(this.groupChatList.get(groupName).indexOf(userName));
        }
    }

    public void deleteGroupChat(String groupName){
        this.groupChatList.get(groupName).clear();
        this.groupChatList.remove(groupName);
    }

    public void addConnectedUser(String userName, BufferedReader socIn, PrintStream socOut){
        User user = new User(userName, socIn, socOut);
        this.connectedUserList.put(userName, user);
        this.addUserToGroupChat(userName, "General");
    }

    public void removeConnectedUser(String userName){
        this.connectedUserList.remove(userName);
        this.removeUserOfGroupChat(userName, "General");
    }

    public BufferedReader getUserSocIn(String userName){
        return this.connectedUserList.get("userName").getSocIn();
    }

    public PrintStream getUserSocOut(String userName){
        return this.connectedUserList.get("userName").getSocOut();
    }

    public ArrayList getConnectedUserList(){
        return new ArrayList<String>(this.connectedUserList.keySet());
    }

    public void sendMessageToUser(String fromUser, String userName, String Text){
        this.getUserSocOut(fromUser).println(fromUser);
        this.getUserSocOut(fromUser).println(userName);
        this.getUserSocOut(fromUser).println(Text);

        this.getUserSocOut(userName).println(fromUser);
        this.getUserSocOut(userName).println(userName);
        this.getUserSocOut(userName).println(Text);
    }

    public void sendMessageToAll(String fromUser, String Text){
        ArrayList listDest = this.groupChatList.get("General");
        PrintStream os;
        for (int i = 0 ; i < listDest.size(); i++) {
            os = this.getUserSocOut((String) listDest.get(i));
            os.println(fromUser);
            os.println("General");
            os.println(Text);
        }
    }

    public void sendMessageToGroup(String fromUser, String groupName,String Text){
        ArrayList listDest = this.groupChatList.get(groupName);
        PrintStream os;
        for (int i = 0 ; i < listDest.size(); i++) {
            os = this.getUserSocOut((String) listDest.get(i));
            os.println(fromUser);
            os.println(groupName);
            os.println(Text);
        }
    }
}
