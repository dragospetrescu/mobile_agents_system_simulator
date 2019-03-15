package message;

import host.router.Graph;

import java.util.*;

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
        Set<Message> messages = travelingMessages.keySet();
        Set<Message> messagesToBeRemoved = new HashSet<>();
        for (Message message : messages) {
            int leftDistance = travelingMessages.get(message) - 1;
            if (leftDistance <= 0) {
                messagesToBeRemoved.add(message);
                arrivedMessages.add(message);
            } else {
                travelingMessages.put(message, leftDistance);
            }
        }
        messagesToBeRemoved.forEach(message -> travelingMessages.remove(message));
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
