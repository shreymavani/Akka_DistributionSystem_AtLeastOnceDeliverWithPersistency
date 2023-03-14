package org.example;

import akka.actor.ActorSelection;
import akka.persistence.AbstractPersistentActorWithAtLeastOnceDelivery;

import java.io.Serializable;
class MyPersistentActor extends AbstractPersistentActorWithAtLeastOnceDelivery implements Message{
    private final ActorSelection destination;

    public MyPersistentActor(String path) {
        this.destination = getContext().actorSelection(path);
    }

    @Override
    public String persistenceId() {
        return "persistence-id";
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        String.class,
                        s -> {
                            persist(new MsgSent(s), evt -> updateState(evt));
                        })
                .match(
                        Confirm.class,
                        confirm -> {
                            persist(new MsgConfirmed(confirm.deliveryId), evt -> updateState(evt));
                        })
                .build();
    }

    @Override
    public Receive createReceiveRecover() {
        return receiveBuilder().match(Object.class, evt -> updateState(evt)).build();
    }

    void updateState(Object event) {
        if (event instanceof MsgSent) {
            final MsgSent evt = (MsgSent) event;
            deliver(destination, deliveryId -> new Msg(deliveryId, evt.s));
        } else if (event instanceof MsgConfirmed) {
            final MsgConfirmed evt = (MsgConfirmed) event;
            confirmDelivery(evt.deliveryId);
        }
    }
}
