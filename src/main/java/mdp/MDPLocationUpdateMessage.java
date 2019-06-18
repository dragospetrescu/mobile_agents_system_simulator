package mdp;

import java.util.List;

import core.message.Message;
@SuppressWarnings("javadoc")
public class MDPLocationUpdateMessage extends Message {
//#TODO
	private List<Integer> agents;
	private Integer mdpSenderHostId;

	public MDPLocationUpdateMessage(Integer sourceHost, Integer destinationHost, List<Integer> agents,
			Integer mdpSenderHostId) {
		super(sourceHost, destinationHost);
		this.agents = agents;
		this.mdpSenderHostId = mdpSenderHostId;

	}

	public List<Integer> getAgents() {
		return agents;
	}

	public Integer getMdpSenderHostId() {
		return mdpSenderHostId;
	}

}
