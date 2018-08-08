package proxy.pattern.remotedemo;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote Interface (Server side)
 */
public interface MyRemote extends Remote {
  String sayHello() throws RemoteException;
}
