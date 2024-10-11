package tech.java.thread.communication;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerImpl {

  public static void main (String[] args) {

    Processor p = new Processor ();

    Thread t1 = new Thread (p::produce);

    Thread t2 = new Thread (p::consume);

    t1.start ();
    t2.start ();
  }
}

class Processor {

  final Object lock = new Object ();
  int capacity = 10;
  BlockingQueue<UUID> queue = new ArrayBlockingQueue<> (capacity);

  public void produce () {
    synchronized (lock) {
      while (true) {
        if (queue.size () == capacity) {
          System.out.println (
              "\n**************** Product List is full, Consumer Can Consume now ! "
              + Thread.currentThread ()
                  .getName ());
          try {
            lock.wait ();
          }
          catch (InterruptedException e) {
            throw new RuntimeException (e);
          }
        }
        else {
          var queueSize = queue.size ();
          sleep (300);
          var productId = UUID.randomUUID ();
          queue.add (productId);
          System.out.println (
              "Added New ProductId- " + productId + ", by - " + Thread.currentThread ().getName ()
              + ", Product List Size: " + queueSize);
          lock.notify ();
        }
      }
    }
  }

  public void sleep (int milliSec) {
    try {
      Thread.sleep (milliSec);
    }
    catch (InterruptedException e) {
      throw new RuntimeException (e);
    }
  }

  public void consume () {
    synchronized (lock) {
      while (true) {
        if (queue.isEmpty ()) {
          System.out.println (
              "\n****************** Product List is Empty, Producer Can Produce new batch "
              + Thread.currentThread ()
                  .getName ());
          try {
            lock.wait ();
          }
          catch (InterruptedException e) {
            throw new RuntimeException (e);
          }
        }
        else {
          var queueSize = queue.size ();
          sleep (200);
          var productId = queue.remove ();
          System.out.println (
              "Consumed New ProductId- " + productId + " by - " + Thread.currentThread ()
                  .getName () + ", Product List Size: " + queueSize);
          lock.notify ();
        }
      }
    }
  }
}