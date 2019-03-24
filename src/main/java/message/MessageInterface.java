package message;

import agent.communication.CommunicatingAgentInterface;
import host.communication.CommunicatingHostInterface;

public interface MessageInterface {

	CommunicatingHostInterface getNextHop();
	CommunicatingHostInterface getPreviousHop();
	CommunicatingHostInterface getHostDestination();
	CommunicatingHostInterface getHostSource();
    CommunicatingAgentInterface getAgentSource();
    CommunicatingAgentInterface getAgentDestination();
    void setNextHopHost(CommunicatingHostInterface nextHop);
    int getId();
}
