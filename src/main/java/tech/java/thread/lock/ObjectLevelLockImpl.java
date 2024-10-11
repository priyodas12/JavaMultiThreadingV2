package tech.java.thread.lock;

import java.util.ArrayList;
import java.util.List;

public class ObjectLevelLockImpl {

  public static void main (String[] args) {

    List<Thread> threadList = new ArrayList<> ();

    ObjectLevelLock objectLock = new ObjectLevelLock ();

    for (int i = 0; i < 100; i++) {
      String threadSuffix = "Thread - ".concat (String.valueOf (i));
      Thread t = new Thread (() -> objectLock.performTask (threadSuffix));
      threadList.add (t);
    }

    threadList.parallelStream ().forEach (Thread::start);

    System.out.println ("All threads have completed execution.Final Counter value:: "
                        + objectLock.getCounterValue ());
  }
}


class ObjectLevelLock {

  private int counter = 0;

  public synchronized void performTask (String threadName) {
    System.out.println (threadName + " has acquired the object-level lock.");
    try {
      Thread.sleep (100);
    }
    catch (InterruptedException e) {
      Thread.currentThread ().interrupt ();
    }
    counter++;
    System.out.println (threadName + " has released the object-level lock.");
  }

  public int getCounterValue () {
    return counter;
  }
}