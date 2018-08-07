package builder.pattern.bank;

/**
 * Product
 */
class Pizza {
  private String dough = "";
  private String sauce = "";
  private String topping = "";

  public void setDough(String dough) {
    this.dough = dough;
  }

  public void setSauce(String sauce) {
    this.sauce = sauce;
  }

  public void setTopping(String topping) {
    this.topping = topping;
  }
}


/**
 * Abstract Builder
 */
abstract class PizzaBuilder {
  protected Pizza pizza;

  public Pizza getPizza() {
    return pizza;
  }

  public void createNewPizzaProduct() {
    pizza = new Pizza();
  }

  public abstract void buildDough();
  public abstract void buildSauce();
  public abstract void buildTopping();
}

/**
 * ConcreteBuilder
 */
class NYPizzaBuilder extends PizzaBuilder {

  @Override
  public void buildDough() {
    pizza.setDough("pan baked");
  }

  @Override
  public void buildSauce() {
    pizza.setSauce("hot");
  }

  @Override
  public void buildTopping() {
    pizza.setTopping("pep");
  }
}

/**
 * ConcreteBuilder
 */
class ChicagoPizzaBuilder extends PizzaBuilder {

  @Override
  public void buildDough() {
    pizza.setSauce("cross");
  }

  @Override
  public void buildSauce() {
    pizza.setSauce("mild");
  }

  @Override
  public void buildTopping() {
    pizza.setTopping("ham");
  }
}

/**
 * Director
 */
class Waiter {
  private PizzaBuilder pizzaBuilder;

  public void setPizzaBuilder(PizzaBuilder bh) {
    pizzaBuilder = bh;
  }

  public Pizza getPizza() {
    return pizzaBuilder.getPizza();
  }

  public void constructPizza() {
    pizzaBuilder.createNewPizzaProduct();
    pizzaBuilder.buildDough();
    pizzaBuilder.buildSauce();
    pizzaBuilder.buildTopping();
  }
}

/**
 * A customer ordering a pizza
 */
public class PizzaBuilderDemo {
  public static void main(String[] args) {
    Waiter waiter = new Waiter();
    PizzaBuilder nyPizzaBuilder = new NYPizzaBuilder();
    PizzaBuilder chicagoBuilder = new ChicagoPizzaBuilder();

    waiter.setPizzaBuilder(nyPizzaBuilder);
    waiter.constructPizza();

    Pizza pizza = waiter.getPizza();
  }

}