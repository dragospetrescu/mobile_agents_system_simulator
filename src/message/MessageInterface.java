package message;

import agent.implementation.Agent;
import host.implementation.Host;

public interface MessageInterface {

    void travel();
    Host getNextHop();
    Host getHostDestination();
    Host getHostSource();
    Agent getAgentSource();
    Agent getAgentDestination();

}
