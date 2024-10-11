package tech.java.thread.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockImpl {

  public static void main (String[] args) {

    List<Thread> threadList = new ArrayList<> ();

    PublicResource publicResource = new PublicResource ();

    for (int i = 0; i < 10; i++) {
      String threadSuffix = "Thread - ".concat (String.valueOf (i));
      Thread t = new Thread (() -> publicResource.increment (threadSuffix));
      threadList.add (t);
    }

    threadList.parallelStream ().forEach (Thread::start);

    for (Thread t : threadList) {
      try {
        t.join ();
      }
      catch (InterruptedException e) {
        throw new RuntimeException (e);
      }
    }

    System.out.println ("All threads have completed execution.Final Counter value:: "
                        + publicResource.getCounterValue ());
  }

}

class PublicResource {

  private final ReentrantLock lock = new ReentrantLock ();
  private int counter = 0;

  public void increment (String threadName) {

    lock.lock ();
    try {
      System.out.println ("------" + threadName + " acquired the lock.");
      counter++;

      Thread.sleep (1000);
      System.out.println (threadName + " incremented counter to: --------->" + counter);
    }
    catch (InterruptedException e) {
      Thread.currentThread ().interrupt ();
    }
    finally {
      System.out.println ("------" + threadName + " released the lock.");
      lock.unlock ();
    }
  }

  public int getCounterValue () {
    return counter;
  }
}
