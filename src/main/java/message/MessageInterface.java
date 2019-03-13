package message;

import agent.Agent;
import host.Host;

public interface MessageInterface {

    Host getNextHop();
    Host getPreviousHop();
    Host getHostDestination();
    Host getHostSource();
    Agent getAgentSource();
    Agent getAgentDestination();
    void setNextHopHost(Host nextHop);
}
