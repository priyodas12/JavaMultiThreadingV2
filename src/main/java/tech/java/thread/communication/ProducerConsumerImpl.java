package tech.java.thread.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
  List<UUID> productIdList = new ArrayList<> ();
  int upper_limit = 10;
  int lower_limit = 1;

  public void produce () {
    synchronized (lock) {
      while (true) {
        if (productIdList.size () == upper_limit) {
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
          sleep (300);
          var productId = UUID.randomUUID ();
          productIdList.add (productId);
          System.out.println (
              "Added New ProductId- " + productId + ", by - " + Thread.currentThread ().getName ()
              + ", Product List Size: " + productIdList.size ());
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
        if (productIdList.size () == lower_limit) {
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
          sleep (200);
          var productId = productIdList.removeLast ();
          System.out.println (
              "Consumed New ProductId- " + productId + " by - " + Thread.currentThread ()
                  .getName () + ", Product List Size: " + productIdList.size ());
          lock.notify ();
        }
      }
    }
  }
}