package mefs;

import java.util.List;

import core.message.Message;
import core.message.MessageInterface;

public class MEFSSyncMessage extends Message {

	private Integer sourceAgentId;
	private List<MessageInterface> messages;

	public MEFSSyncMessage(Integer sourceHost, Integer destinationHost, Integer sourceAgentId) {
		super(sourceHost, destinationHost);
		this.sourceAgentId = sourceAgentId;
	}

	public List<MessageInterface> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageInterface> messages) {
		this.messages = messages;
	}

	public Integer getSourceAgentId() {
		return sourceAgentId;
	}
}
