package proxy.pattern.machine;

import java.io.Serializable;

/**
 * Extends Serializable to State in all subclasses can be transferred over the network
 */
public interface State extends Serializable {
  void insertQuarter();
  void ejectQuarter();
  void turnCrank();
  void dispense();
}
