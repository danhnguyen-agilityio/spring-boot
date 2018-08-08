package proxy.pattern.remotedemo;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Remote service (Service side)
 */
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

  protected MyRemoteImpl() throws RemoteException {
  }

  @Override
  public String sayHello() throws RemoteException {
    return "Server says Hey";
  }

  public static void main(String[] args) {
    try {
      MyRemote service = new MyRemoteImpl();
      // put the stub in the registry
      Naming.rebind("RemoteHello", service);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
