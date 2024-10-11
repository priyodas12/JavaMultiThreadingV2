package tech.java.thread.generation;

public class ThreadCreateImpl {

  public static void main (String[] args) throws InterruptedException {

    Worker1 worker1 = new Worker1 ("Worker1");
    Worker2 worker2 = new Worker2 ("worker2");

    Thread t1 = new Thread (worker1);

    Thread t2 = new Thread (worker2);

    t1.start ();
    t1.join ();
    t2.start ();
    t2.join ();

    System.out.println ("Worker 1 has finished work!!");

  }
}

class Worker1 implements Runnable {

  private final String name;

  public Worker1 (String name) {
    this.name = name;
  }

  @Override
  public void run () {
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep (800);
      }
      catch (InterruptedException e) {
        throw new RuntimeException (e);
      }
      System.out.printf ("%s , %d printed by ------------> %s%n", name, i,
                         Thread.currentThread ().getName ()
                        );
    }
  }
}

class Worker2 implements Runnable {

  private final String name;

  public Worker2 (String name) {
    this.name = name;
  }

  @Override
  public void run () {
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep (500);
      }
      catch (InterruptedException e) {
        throw new RuntimeException (e);
      }
      System.out.printf ("%s , %d printed by --> %s%n", name, i,
                         Thread.currentThread ().getName ()
                        );
    }
  }
}
