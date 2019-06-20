package myuml.structure.collaboration;

import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInteraction;
import com.oocourse.uml2.models.elements.UmlLifeline;
import com.oocourse.uml2.models.elements.UmlMessage;

import java.util.HashMap;
import java.util.HashSet;

public class MyInteraction {
    private UmlInteraction interaction;
    private HashMap<String, UmlLifeline> lifelineNameMap;
    private HashMap<String, UmlLifeline> lifelineDupMap;
    private int lifelineSize;
    private HashMap<String, HashSet<String>> messageMap;
    private int messageSize;

    public MyInteraction(UmlElement interaction) {
        this.interaction = (UmlInteraction) interaction;
        this.lifelineNameMap = new HashMap<>();
        this.lifelineDupMap = new HashMap<>();
        this.lifelineSize = 0;
        this.messageMap = new HashMap<>();
        this.messageSize = 0;
    }

    public String getName() {
        return interaction.getName();
    }

    public String getId() {
        return interaction.getId();
    }

    public String getParaentId() {
        return interaction.getParentId();
    }

    public void addLifeline(UmlLifeline lifeline) {
        lifelineSize++;
        String name = lifeline.getName();
        if (lifelineNameMap.containsKey(name)) {
            lifelineDupMap.put(name, lifeline);
        } else {
            lifelineNameMap.put(name, lifeline);
        }
    }

    public void addmessage(UmlMessage message) {
        messageSize++;
        String target = message.getTarget();
        if (!messageMap.containsKey(target)) {
            messageMap.put(target, new HashSet<>());
        }
        messageMap.get(target).add(message.getId());
    }

    public int getParticipantCount() {
        return lifelineSize;
    }

    public int getMessageCount() {
        return messageSize;
    }

    public boolean containsLifeline(String lifelineName) {
        return lifelineNameMap.containsKey(lifelineName);
    }

    public boolean containsDupLifeline(String lifelineName) {
        return lifelineDupMap.containsKey(lifelineName);
    }

    public int getIncomingMessageCount(String lifelineName) {
        if (messageMap.containsKey(lifelineNameMap.get(lifelineName).getId())) {
            return messageMap.get(
                    lifelineNameMap.get(lifelineName).getId()).size();
        } else {
            return 0;
        }
    }
}
