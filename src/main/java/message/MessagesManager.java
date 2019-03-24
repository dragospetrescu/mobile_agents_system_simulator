package message;

import host.router.Graph;

import java.util.*;

public class MessagesManager {

    private Map<MessageInterface, Integer> travelingMessages;
    private List<MessageInterface> arrivedMessages;
    private Graph graph;

    public MessagesManager(Graph graph) {
        this.graph = graph;
        travelingMessages = new HashMap<>();
        arrivedMessages = new ArrayList<>();
    }

    public void addMessage(MessageInterface message) {
        travelingMessages.put(message, graph.getDistance(message.getPreviousHop(), message.getNextHop()));
    }

    public void travelMessages() {
        Set<MessageInterface> messages = travelingMessages.keySet();
        Set<MessageInterface> messagesToBeRemoved = new HashSet<>();
        for (MessageInterface message : messages) {
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

    public List<MessageInterface> getArrivedMessages() {
        List<MessageInterface> oldArrivedMessages = arrivedMessages;
        arrivedMessages = new ArrayList<>();
        return oldArrivedMessages;
    }

    public void addAllMessages(List<MessageInterface> messages) {
        messages.forEach(this::addMessage);
    }
}
