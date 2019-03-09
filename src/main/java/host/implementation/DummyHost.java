package host.implementation;

import host.Host;
import message.Message;

public class DummyHost extends Host {

    public DummyHost() {
        super();
    }

//    public DummyHost(int id) {
//        super(id);
//        this.id = id;
//    }

    public void receiveMessage(Message message) {
    }

    public boolean wantsToSendMessage() {
        return false;
    }

    public Message prepareMessage() {
        return null;
    }
}
