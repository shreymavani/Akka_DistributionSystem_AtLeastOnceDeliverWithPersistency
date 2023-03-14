package org.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Config regularConfig = ConfigFactory.load();
        Config file = ConfigFactory.load("config.conf");
        Config combined = file.withFallback(regularConfig);
        Config complete = ConfigFactory.load(combined);

//        System.out.println(complete.root().render());
        final ActorSystem actorSystem = ActorSystem.create("actor-system", complete);

// Create the receiver actor
        final ActorRef receiverActor = actorSystem.actorOf(Props.create(ReceiverActor.class), "receiver-actor");
        System.out.println(receiverActor.path());
// Wait for messages to be received
        System.in.read();
    }
}