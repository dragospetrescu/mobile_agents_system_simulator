package core.message;

import java.util.*;

import core.host.router.NetworkGraph;
import core.statistics.Statistics;

/**
 * Represents the physical layer, the wire through which the messages are traveling.
 * It makes sure that the messages are traveling according to the distances between hosts 
 * described in the network file. 
 */
public class MessagesManager {

    /**
     * All messages that did not arrive at the destination yet
     */
    private Map<MessageInterface, Integer> travelingMessages;
    /**
     * The messages that arrived at the destination this turn and will be received by hosts
     */
    private List<MessageInterface> arrivedMessages;
    /**
     * Describes the hosts' network
     */
    private NetworkGraph networkGraph;

    /**
     * @param graph - the networkGraph
     */
    public MessagesManager(NetworkGraph graph) {
        this.networkGraph = graph;
        travelingMessages = new HashMap<>();
        arrivedMessages = new ArrayList<>();
    }

    /**
     * Adds a new message that will travel from previousHop to the nextHop
     * 
     * @param message - the traveling message
     */
    public void addMessage(MessageInterface message) {
    	Integer prevId = message.getPreviousHopId();
    	Integer nextId = message.getNextHopId();
    	int distance = networkGraph.getDistance(prevId, nextId);
        travelingMessages.put(message, distance);
    }

    /**
     * Marks a new time unit has passed
     */
    public void travelMessages() {
        Set<MessageInterface> messages = travelingMessages.keySet();
        
        Statistics statistics = Statistics.getStatistics();
        statistics.calculateNetworkLoad(messages.size());
        Set<MessageInterface> messagesToBeRemoved = new HashSet<>();
        for (MessageInterface message : messages) {
            Integer leftDistance = travelingMessages.get(message) - 1;
            if (leftDistance <= 0) {
                messagesToBeRemoved.add(message);
                arrivedMessages.add(message);
            } else {
                travelingMessages.put(message, leftDistance);
            }
        }
        messagesToBeRemoved.forEach(message -> travelingMessages.remove(message));
    }

    /**
     * @return the messages that arrived in order to be handed out to the destination hosts
     */
    public List<MessageInterface> getArrivedMessages() {
        List<MessageInterface> oldArrivedMessages = arrivedMessages;
//        oldArrivedMessages.stream().filter(ms -> ms.getMessageId().equals(0)).forEach(m -> System.out.println(m.getHostDestinationId()));
        
        arrivedMessages = new ArrayList<>();
        return oldArrivedMessages;
    }

    /**
     * Adds a list of new messages that will travel from previousHop to the nextHop
     * 
     * @param messages - the traveling messages
     */
    public void addAllMessages(List<MessageInterface> messages) {
        messages.forEach(this::addMessage);
    }
}
