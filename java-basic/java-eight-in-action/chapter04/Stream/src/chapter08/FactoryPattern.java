package chapter08;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

interface Product {

}

class Loan implements Product {

}

class Stock implements Product {

}

class Bond implements Product {

}

interface TriFuction<T, U, V, R> {
  R apply(T t, U u, V v);
}

class ProductFactory {
  static Map<String, TriFuction<Integer, Integer, String, Product>> mapDemo = new HashMap<>();
  static {
    mapDemo.put("loan", (x, y, text) ->  new Loan());
    mapDemo.put("stock", (x, y, text) ->  new Stock());
    mapDemo.put("bond", (x, y, text) ->  new Bond());
  }

  final static Map<String, Supplier<Product>> map = new HashMap<>();

  // block static execute before constructor
  static {
    map.put("loan", Loan::new);
    map.put("stock", Stock::new);
    map.put("bond", Bond::new);
  }

  public static Product createProduct(String name) {
    Supplier<Product> p = map.get(name);
    if (p != null) return p.get();
    throw new IllegalStateException("No such product");
  }
}

public class FactoryPattern {
}
