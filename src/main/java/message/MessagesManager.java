package message;

import host.router.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesManager {

    private Map<Message, Integer> travelingMessages;
    private List<Message> arrivedMessages;
    private Graph graph;

    public MessagesManager(Graph graph) {
        this.graph = graph;
        travelingMessages = new HashMap<Message, Integer>();
        arrivedMessages = new ArrayList<Message>();
    }

    public void addMessage(Message message) {
        travelingMessages.put(message, graph.getDistance(message.getPreviousHop(), message.getNextHop()));
    }

    public void travelMessages() {
        for (Message message : travelingMessages.keySet()) {
            int leftDistance = travelingMessages.get(message) - 1;
            if (leftDistance <= 0) {
                travelingMessages.remove(message);
                arrivedMessages.add(message);
            } else {
                travelingMessages.put(message, leftDistance);
            }
        }
    }

    public List<Message> getArrivedMessages() {
        List<Message> oldArrivedMessages = arrivedMessages;
        arrivedMessages = new ArrayList<Message>();
        return oldArrivedMessages;
    }

    public void addAllMessages(List<Message> messages) {
        messages.forEach(this::addMessage);
    }
}
