package tech.java.thread.communication;

public class WaitNotifyImpl {

  public static void main (String[] args) {
    Process p = new Process ();
    Thread t1 = new Thread (() -> {
      try {
        p.produce ();
      }
      catch (InterruptedException e) {
        throw new RuntimeException (e);
      }
    });

    Thread t2 = new Thread (() -> {
      try {
        p.consume ();
      }
      catch (InterruptedException e) {
        throw new RuntimeException (e);
      }
    });

    t1.start ();
    t2.start ();
  }
}

class Process {

  public void produce () throws InterruptedException {
    System.out.println ("Producer: " +
                        Thread.currentThread ().getName () + ">>" + Thread.currentThread ()
                            .getState ());
    synchronized (this) {
      System.out.println ("<Produce> starts...");
      wait ();
      System.out.println ("Producer: " +
                          Thread.currentThread ().getName () + ">>" + Thread.currentThread ()
                              .getState ());
      System.out.println ("<Produce> restarts...");
      for (int i = 0; i < 10; i++) {
        System.out.println (Thread.currentThread ().getName () + ">>" + i);
      }
    }
  }

  public void consume () throws InterruptedException {
    System.out.println ("Consumer: " +
                        Thread.currentThread ().getName () + ">>" + Thread.currentThread ()
                            .getState ());
    Thread.sleep (5000);
    synchronized (this) {
      System.out.println ("<Consume> starts...");
      notify ();
      System.out.println ("Consumer: " +
                          Thread.currentThread ().getName () + ">>" + Thread.currentThread ()
                              .getState ());
    }
  }
}
