package org.example;

import akka.actor.AbstractActor;

import java.io.Serial;
import java.io.Serializable;

public class ReceiverActor extends AbstractActor implements Message {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(
                        Msg.class,
                        msg -> {
                            System.out.println("Received msg :"+msg.s);
                            getSender().tell(new Confirm(msg.deliveryId), getSelf());
                        })
                .build();
    }
}