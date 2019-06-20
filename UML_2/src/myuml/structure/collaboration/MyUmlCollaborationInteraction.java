package myuml.structure.collaboration;

import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.interact.format.UmlCollaborationInteraction;
import myuml.function.Cache;
import myuml.function.Find;

public class MyUmlCollaborationInteraction
        implements UmlCollaborationInteraction {
    private Cache cache;

    public MyUmlCollaborationInteraction(Cache cache) {
        this.cache = cache;
    }

    @Override
    public int getParticipantCount(String interactionName)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        cache.checkInteraction(interactionName);
        return Find.getInteractionByName(interactionName).getParticipantCount();
    }

    @Override
    public int getMessageCount(String interactionName)
            throws InteractionNotFoundException,
            InteractionDuplicatedException {
        cache.checkInteraction(interactionName);
        return Find.getInteractionByName(interactionName).getMessageCount();
    }

    @Override
    public int getIncomingMessageCount(String interactionName,
                                       String lifelineName)
            throws InteractionNotFoundException, InteractionDuplicatedException,
            LifelineNotFoundException, LifelineDuplicatedException {
        cache.checkInteraction(interactionName);
        MyInteraction i = Find.getInteractionByName(interactionName);
        if (!i.containsLifeline(lifelineName)) {
            throw new LifelineNotFoundException(interactionName, lifelineName);
        } else if (i.containsDupLifeline(lifelineName)) {
            throw new LifelineDuplicatedException(
                    interactionName, lifelineName);
        } else {
            return i.getIncomingMessageCount(lifelineName);
        }
    }
}
