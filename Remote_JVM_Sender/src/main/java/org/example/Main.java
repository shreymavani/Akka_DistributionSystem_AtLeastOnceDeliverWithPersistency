package org.example;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Main {
    public static void main(String[] args) {


        Config regularConfig = ConfigFactory.load();
        Config file = ConfigFactory.load("config.conf");
        Config combined = file.withFallback(regularConfig);
        Config complete = ConfigFactory.load(combined);

        final ActorSystem actorSystem = ActorSystem.create("actor-system1",complete);

// Create the sender actor
        final ActorRef senderActor = actorSystem.actorOf(Props.create(MyPersistentActor.class, "akka://actor-system@127.0.0.1:25520/user/receiver-actor"), "sender-actor");
//        System.out.println(senderActor.path());
// Send a message to the receiver actor
        senderActor.tell("Hello world!", ActorRef.noSender());
    }
}