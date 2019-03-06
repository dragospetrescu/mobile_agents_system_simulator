package message;

import helpers.RandomAssigner;
import host.Host;

public abstract class Message {

    private int id;
    private Host currentHost;
    private Host destinationHost;
    private int travelingTime;

    private static int noMessages = 0;

    public Message(int id, Host currentHost, Host destinationHost) {
        this.id = id;
        this.currentHost = currentHost;
        this.destinationHost = destinationHost;
        this.travelingTime = RandomAssigner.assignMigrateTime(currentHost, destinationHost);
    }

    public Message(Host currentHost, Host destinationHost) {
        this(noMessages, currentHost, destinationHost);
        noMessages++;
    }

    public boolean isArrived() {
        return travelingTime == 0;
    }

    public void travel() {
        travelingTime--;
    }

    public Host getHostDestination() {
        return destinationHost;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Message " + id;
    }
}
