package patterns.proxy.machine;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Extends UnicastRemoteObject to have ability to act as a remote service
 */
public class GumballMachine extends UnicastRemoteObject implements GumballMachineRemote {
  String location;
  int count;
  State state;

  public GumballMachine(String location, int count) throws RemoteException {
    this.location = location;
    this.count = count;
  }

  public String getLocation() {
    return location;
  }

  public int getCount() {
    count = 200;
    System.out.println(200);
    return count;
  }

  public State getState() {
    State state = new NoQuarterState();
    System.out.println(state);
    return state;
  }
}
