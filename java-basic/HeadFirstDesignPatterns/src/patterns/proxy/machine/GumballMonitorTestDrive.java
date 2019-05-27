package patterns.proxy.machine;

import java.rmi.Naming;

public class GumballMonitorTestDrive {
  public static void main(String[] args) {
    String[] location = { "agility.io", "asnet.io" };

    GumballMonitor[] monitor = new GumballMonitor[location.length];

    for (int i = 0; i < location.length; i++) {
      try {
        // Return a proxy to the remote GumballMachine
        // (or throws an exception if one can't be located)
        GumballMachineRemote gumballMachineRemote = (GumballMachineRemote) Naming.lookup(location[i]);
        monitor[i] = new GumballMonitor(gumballMachineRemote);
        System.out.println(monitor[i]);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    for (int i = 0; i < monitor.length; i++) {
      monitor[i].report();
    }


  }
}

// Run app
// javac proxy/patterns/machine/GumballMachineTestDrive.java
// javac proxy/patterns/machine/GumballMonitorTestDrive.java
// rmiregistry
// java proxy/patterns/machine/GumballMachineTestDrive agility.io 100 => wait a little time to run
// java proxy/patterns/machine/GumballMachineTestDrive asnet.io 100 => wait a little time to run
// java proxy/patterns/machine/GumballMonitorTestDrive