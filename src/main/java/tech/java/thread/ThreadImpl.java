package tech.java.thread;

// thread is essentially a lightweight process , unit of execution with in a given process so
// single process may contain several thread.

// a thread in a process shares multiple memory and resources and thats why programmer have to
// deal with concurrent programming.

// time slicing algorithm : processing time for a single processor is shared among multiple
// processes and threads
public class ThreadImpl {

  public static void main (String[] args) {
    System.out.println (Runtime.getRuntime ().availableProcessors ());
    var threadSet = Thread.getAllStackTraces ().entrySet ();
    threadSet.forEach (System.out::println);

    /**
     * Thread[#19,Common-Cleaner,8,InnocuousThreadGroup]=[Ljava.lang.StackTraceElement;@1d81eb93
     * Thread[#9,Reference Handler,10,system]=[Ljava.lang.StackTraceElement;@7291c18f
     * Thread[#20,Monitor Ctrl-Break,5,main]=[Ljava.lang.StackTraceElement;@34a245ab
     * Thread[#1,main,5,main]=[Ljava.lang.StackTraceElement;@7cc355be
     * Thread[#12,Attach Listener,5,system]=[Ljava.lang.StackTraceElement;@6e8cf4c6
     * Thread[#10,Finalizer,8,system]=[Ljava.lang.StackTraceElement;@12edcd21
     * Thread[#21,Notification Thread,9,system]=[Ljava.lang.StackTraceElement;@34c45dca
     * Thread[#11,Signal Dispatcher,9,system]=[Ljava.lang.StackTraceElement;@52cc8049
     * */
  }
}
