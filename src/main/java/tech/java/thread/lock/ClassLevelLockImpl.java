package tech.java.thread.lock;

import java.util.ArrayList;
import java.util.List;

public class ClassLevelLockImpl {
  
  public static void main (String[] args) {
    List<Thread> threadList = new ArrayList<> ();

    for (int i = 0; i < 100; i++) {
      String threadSuffix = "Thread - ".concat (String.valueOf (i));
      Thread t = new Thread (() -> ClassLevelLock.performTask (threadSuffix));
      threadList.add (t);
    }

    threadList.parallelStream ().forEach (Thread::start);

    System.out.println ("All threads have completed execution.Final counter Value:: "
                        + ClassLevelLock.getCounterValue ());
  }
}


class ClassLevelLock {

  private static int counter = 0;

  public static int getCounterValue () {
    return counter;
  }

  // Static synchronized method - holds a class-level lock
  public static synchronized void performTask (String threadName) {
    System.out.println (threadName + " has acquired the class-level lock.");
    try {
      Thread.sleep (1000);
    }
    catch (InterruptedException e) {
      Thread.currentThread ().interrupt ();
    }
    counter++;
    System.out.println (threadName + " has released the class-level lock.");
  }
}