import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {

	//The User class binds a username with a Socket. Why do we need this? So we can refer to clients in other ways than just
	//calling their observable methods. This also allows us to refer to clients as Usernames instead of Sockets
	
	public String username;
	public Socket sock;
	public DataOutputStream send;
	public DataInputStream receive;
	
	public User(String u, Socket s) throws IOException {
		username = u;
		sock = s;
		send = new DataOutputStream(s.getOutputStream());
		receive = new DataInputStream(s.getInputStream());
	}
	
	public String toString() {
		return username;
	}
	
}
