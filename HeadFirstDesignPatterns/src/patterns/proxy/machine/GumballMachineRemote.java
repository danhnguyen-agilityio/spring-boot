package patterns.proxy.machine;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface in Server side
 */
public interface GumballMachineRemote extends Remote {
  int getCount() throws RemoteException;
  String getLocation() throws RemoteException;
  State getState() throws RemoteException;
}
