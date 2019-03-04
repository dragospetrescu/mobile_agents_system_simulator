package message;

public interface MessageInterface {

    void travel();
    Host getNextHop();
    Host getHostDestination();
    Host getHostSource();
    Agent getAgentSource();
    Agent getAgentDestination();

}
