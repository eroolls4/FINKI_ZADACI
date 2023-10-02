package ChatSystem;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;



class NoSuchRoomException extends Exception{
    public NoSuchRoomException(String roomName) {
        super(String.format("%s doesnt exist",roomName));
    }
}


class NoSuchUserException extends Exception{
    public NoSuchUserException(String username) {
        super(String.format("%s doesnt exist",username));
    }
}



class User implements Comparable<User> {
    String username;

    public User(String username) {
        this.username = username;
    }

    @Override
    public int compareTo(User other) {
        return username.compareTo(other.username);
    }

    @Override
    public String toString() {
        return String.format("%s", username);
    }
}



class ChatRoom{
    String name;
    Set<User> users;

    Map<String,User> userByName;

    public ChatRoom(String name) {
        this.name = name;
        users=new HashSet<>();
        userByName=new TreeMap<>();
    }
    void addUser(String username){
        User u=new User(username);
        userByName.put(username,u);
        users.add(u);
    }
    void removeUser(String username){
           if(userByName.containsKey(username)){
              userByName.remove(username);
           }
    }

    public boolean hasUser(String username){
        return userByName.containsKey(username);
    }

    public int numUsers(){
       return userByName.values().size();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
       StringBuilder sb=new StringBuilder();
       sb.append(String.format("%s\n",this.name));
       if(userByName.values().size() < 1){
           sb.append("EMPTY");
       }else{
           userByName.values().stream().forEach(u-> sb.append(String.format("%s\n",u)));
       }
       return sb.toString();
    }
}

class ChatSystem{

    List<ChatRoom> rooms;
    Set<User> users;


    Map<String,ChatRoom> roomByName;

   // Map<Integer,ChatRoom> roomByNrOfUsers;


    Comparator<ChatRoom> register=Comparator.comparing(ChatRoom::numUsers)
                                           .thenComparing(ChatRoom::getName);


    public ChatSystem() {
        rooms = new ArrayList<>();
        roomByName = new TreeMap<>();
        users = new TreeSet<>(Comparator.comparing(User::toString));
      //  roomByNrOfUsers=new TreeMap<>();
    }


    public  void addRoom(String roomName){
        ChatRoom chatRoom=new ChatRoom(roomName);

        rooms.add(chatRoom);
        roomByName.put(roomName,chatRoom);
       // roomByNrOfUsers.put(chatRoom.numUsers(), chatRoom);
    }

    public  void removeRoom(String roomName){
        if(roomByName.containsKey(roomName)){
            roomByName.remove(roomName);
        }
    }

    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
         if(!roomByName.containsKey(roomName)){
             throw new NoSuchRoomException(roomName);
         }else{
             return roomByName.get(roomName);
         }
    }

    public User getUser(String user) {
        return users.stream().filter(u -> u.username.equals(user)).findFirst().orElse(null);
    }


    public void register(String userName) {
        users.add(new User(userName));

        if (!roomByName.isEmpty()) {
            ChatRoom chr = roomByName.values().stream().min(register).get();
            chr.addUser(userName);
        }
    }
   public   void registerAndJoin(String userName, String roomName) throws NoSuchRoomException {
       users.add(new User(userName));
       joinRoom(userName, roomName);
    }
    public  void joinRoom(String userName, String roomName) throws NoSuchRoomException {
        getRoom(roomName).addUser(userName);
    }

    public   void leaveRoom(String username, String roomName) throws NoSuchRoomException {
        getRoom(roomName).removeUser(username);
    }

    public void followFriend(String user, String friend_username) throws NoSuchUserException, NoSuchRoomException {
        User userObject = getUser(user);
        User friendObject = getUser(friend_username);

        if (userObject != null && friendObject != null) {
            for (Map.Entry<String, ChatRoom> cr : roomByName.entrySet()) {
                if (cr.getValue().hasUser(friend_username)) {
                    joinRoom(userObject.username, cr.getKey());
                }
            }
        } else {
            throw new NoSuchUserException("One or both users do not exist.");
        }
    }

}

public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr.addUser(jin.next());
                if ( k == 1 ) cr.removeUser(jin.next());
                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if ( n == 0 ) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr2.addUser(jin.next());
                if ( k == 1 ) cr2.removeUser(jin.next());
                if ( k == 2 ) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if ( k == 1 ) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while ( true ) {
                String cmd = jin.next();
                if ( cmd.equals("stop") ) break;
                if ( cmd.equals("print") ) {
                    System.out.println(cs.getRoom(jin.next())+"\n");continue;
                }
                for ( Method m : mts ) {
                    if ( m.getName().equals(cmd) ) {
                        String params[] = new String[m.getParameterTypes().length];
                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                        m.invoke(cs,params);
                    }
                }
            }
        }
    }

}
