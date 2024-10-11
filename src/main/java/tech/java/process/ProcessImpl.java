package tech.java.process;

import java.io.IOException;
import java.util.Map;

public class ProcessImpl {
  //cmd: tasklist
  //every process associated with PID
  //Image Name                     PID Session Name        Session#    Mem Usage
  //========================= ======== ================ =========== ============
  //System Idle Process              0 Services                   0          8 K
  //System                           4 Services                   0      2,720 K
  //Secure System                  140 Services                   0     73,756 K

  public static void main (String[] args) throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder ();

    Map<String, String> environment = processBuilder.environment ();
    //environment.forEach ((key, value) -> System.out.println (key + value));

    Process process = new ProcessBuilder ("java", "-version").start ();

    System.out.println (process.pid ());
    var processhandleList = process.children ().toList ();

    processhandleList.forEach (System.out::println);
  }
}
