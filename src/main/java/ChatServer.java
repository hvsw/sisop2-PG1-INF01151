import java.io.IOException;

import java.util.logging.*;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/chat")
public class ChatServer {

	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

	@OnMessage
	public void onMessage(String message, Session session) 
		throws IOException {

		synchronized(clients) {
			// Iterate over the connected sessions
			// and broadcast the received message
			for(Session client : clients) {
				// if (!client.equals(session)) {
					client.getBasicRemote().sendText("Session " + session.getId() + ": " + message);
				// }
			}
		}
	}

	@OnOpen
	public void onOpen (Session session) {
		Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Session started " + session.getId());

		try {
			this.onMessage("Session " + session.getId() + " joined!", session);

			// Add session to the connected sessions set
			clients.add(session);
		} catch (Exception ex) {
			Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	@OnClose
	public void onClose (Session session) {
		Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Session closed " + session.getId());

		try {
			this.onMessage("Session " + session.getId() + " left!", session);

			// Remove session from the connected sessions set
			clients.remove(session);
		} catch (Exception ex) {
			Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
}
