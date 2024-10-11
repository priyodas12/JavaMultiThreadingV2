package tech.java.thread.communication;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadComImpl {

  public static int counter = 0;


  public static void main (String[] args) {
    AtomicInteger atomicInteger = new AtomicInteger (0);
    process (atomicInteger);
  }

  public static void process (AtomicInteger atomicInteger) {
    Thread t1 = new Thread (new Runnable () {
      @Override
      public void run () {
        for (int i = 0; i < 10000; i++) {
          counter++;
          atomicInteger.getAndIncrement ();
          //System.out.printf ("%s , %s", i, Thread.currentThread ().getName ());
        }
      }
    });

    Thread t2 = new Thread (new Runnable () {
      @Override
      public void run () {
        for (int i = 0; i < 10000; i++) {
          counter++;
          atomicInteger.getAndIncrement ();
          //System.out.printf ("%s , %s", i, Thread.currentThread ().getName ());
        }
      }
    });

    t1.start ();
    t2.start ();

    try {
      t1.join ();
      t2.join ();
    }
    catch (InterruptedException e) {
      throw new RuntimeException (e);
    }
    finally {
      System.out.println ("FINAL VALUE OF COUNTER:: " + counter);
      System.out.println ("FINAL VALUE OF ATOMIC COUNTER:: " + atomicInteger.get ());
      System.out.println ("EXECUTION FINISHED :: " + Thread.currentThread ().getName ());
    }
  }
}
