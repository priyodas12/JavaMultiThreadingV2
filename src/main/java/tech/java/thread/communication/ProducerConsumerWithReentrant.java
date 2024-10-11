package tech.java.thread.communication;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerWithReentrant {

  public static void main (String[] args) {

    Worker worker = new Worker ();

    Thread t1 = new Thread (worker::produce);
    Thread t2 = new Thread (worker::consume);

    t1.start ();
    t2.start ();
  }
}

class Worker {

  private final ReentrantLock lock = new ReentrantLock ();
  private final Condition condition = lock.newCondition ();
  private final int capacity = 10;
  private final BlockingQueue<UUID> queue = new ArrayBlockingQueue<> (capacity);

  public void produce () {
    lock.lock ();
    try {
      while (true) {
        if (queue.size () == capacity) {
          System.out.println ("\n" + Thread.currentThread ().getName ()
                              + " :: **************** Product List is full, Consumer Can Consume "
                              + "now!****************\n");
          condition.await ();
        }
        else {

          var queueSize = queue.size ();
          sleep (1300);
          var productId = UUID.randomUUID ();
          queue.add (productId);
          System.out.println (Thread.currentThread ().getName () +
                              " :: Added New ProductId - " + productId + ", Product List Size: " + (
                                  queueSize + 1));

          condition.signalAll ();
        }
      }
    }
    catch (Exception e) {
      System.out.println (e.getMessage ());
      Thread.currentThread ().interrupt ();
    }
    finally {
      lock.unlock ();
      System.out.println ("Producer Releasing Lock");
    }
  }

  // Utility methods
  public void sleep (int milliSec) {
    try {
      Thread.sleep (milliSec);
    }
    catch (InterruptedException e) {
      Thread.currentThread ().interrupt ();
    }
  }

  public void consume () {
    lock.lock ();
    try {
      while (true) {
        if (queue.isEmpty ()) {
          System.out.println (
              "\n" + Thread.currentThread ().getName ()
              + " :: ****************** Product List is Empty, Producer Can Produce new "
              + "batch****************\n");
          condition.await ();
        }
        else {
          var queueSize = queue.size ();
          sleep (1300);
          var productId = queue.remove ();
          System.out.println (Thread.currentThread ().getName () +
                              " :: Consumed New ProductId - " + productId
                              + ", Product List Size: " + (queueSize - 1));
          condition.signalAll ();
        }
      }
    }
    catch (Exception e) {
      System.out.println (e.getMessage ());
      Thread.currentThread ().interrupt ();
    }
    finally {
      lock.unlock ();
      System.out.println (Thread.currentThread ().getName () + " :: Consumer Releasing Lock");
    }
  }
}
