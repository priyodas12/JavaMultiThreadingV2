package tech.java.thread.communication;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadComImpl {

  private static int counter1 = 0;
  private static int counter2 = 0;

  public static void main (String[] args) {
    AtomicInteger atomicInteger = new AtomicInteger (0);
    process (atomicInteger);
  }

  public static void process (AtomicInteger atomicInteger) {
    Thread t1 = new Thread (() -> {
      for (int i = 0; i < 10000; i++) {
        increment1 ();
        atomicInteger.getAndIncrement ();
        //System.out.printf ("<%d ---> %s>%n", i, Thread.currentThread ().getName ());
      }
    });

    Thread t2 = new Thread (() -> {
      for (int i = 0; i < 10000; i++) {
        increment2 ();
        atomicInteger.getAndIncrement ();
        //System.out.printf ("<%d ---> %s>%n", i, Thread.currentThread ().getName ());
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
      System.out.println ("FINAL VALUE OF COUNTER1:: " + counter1);
      System.out.println ("FINAL VALUE OF COUNTER2:: " + counter2);
      System.out.println ("FINAL VALUE OF ATOMIC COUNTER:: " + atomicInteger.get ());
      System.out.println (
          "EXECUTION FINISHED :: " + Thread.currentThread ().getName ().toUpperCase ());
    }

  }

  private static synchronized void increment1 () {
    counter1++;
  }

  private static void increment2 () {
    counter2++;
  }
}
