package structure.proxy.pattern.demo;

public class ProxyPatternEx {
  public static void main(String[] args) {
    System.out.println("Proxy pattern demo");
    Proxy px = new Proxy();
    px.doSomeWork();
  }
}
