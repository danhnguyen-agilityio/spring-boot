package factory.pattern.pizza;

abstract class Pizza {
  String name;
  Dough dough;
  Sauce sauce;
  Veggies veggies[];
  Cheese cheese;
  Clams clam;

  abstract void prepare();

  void bake() {
    System.out.println("Bake for 25 minutes at 350");
  }

  void cut() {
    System.out.println("Cutting the pizza into diagonal slices");
  }

  void box() {
    System.out.println("Place pizza in official PizzaStore box");
  }

  public String getName() {
    return name;
  }

  void setName(String name) {
    this.name = name;
  }

  public String toString() {
    return null;
  }
}

class CheesePizza extends Pizza {
  PizzaIngredientFactory ingredientFactory;

  public CheesePizza(PizzaIngredientFactory ingredientFactory) {
    this.ingredientFactory = ingredientFactory;
  }

  @Override
  void prepare() {
    System.out.println("Preparing " + name);
    dough = ingredientFactory.createDough();
    sauce = ingredientFactory.createSauce();
    cheese = ingredientFactory.createCheese();
  }
}

class ClamPizza extends Pizza {
  PizzaIngredientFactory ingredientFactory;

  public ClamPizza(PizzaIngredientFactory ingredientFactory) {
    this.ingredientFactory = ingredientFactory;
  }

  @Override
  void prepare() {
    System.out.println("Preparing " + name);
    dough = ingredientFactory.createDough();
    sauce = ingredientFactory.createSauce();
    cheese = ingredientFactory.createCheese();
    clam = ingredientFactory.createClam();
  }
}

class VeggiePizza extends Pizza {
  PizzaIngredientFactory ingredientFactory;

  public VeggiePizza(PizzaIngredientFactory ingredientFactory) {
    this.ingredientFactory = ingredientFactory;
  }

  @Override
  void prepare() {
    System.out.println("Preparing " + name);
    dough = ingredientFactory.createDough();
    sauce = ingredientFactory.createSauce();
    cheese = ingredientFactory.createCheese();
    veggies = ingredientFactory.createVeggies();
  }
}

abstract class PizzaStore {
  public Pizza orderPizza(String type) {
    Pizza pizza;

    pizza = createPizza(type);

    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();
    return pizza;
  }

  protected abstract Pizza createPizza(String type);
}

class NYPizzaStore extends PizzaStore {

  @Override
  protected Pizza createPizza(String type) {
    Pizza pizza = null;
    PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();

    if (type.equals("cheese")) {
      pizza = new CheesePizza(ingredientFactory);
      pizza.setName("New York Style Cheese Pizza");
    } else if (type.equals("clam")) {
      pizza = new ClamPizza(ingredientFactory);
      pizza.setName("New York Style Veggie Pizza");
    } else if (type.equals("veggie")) {
      pizza = new VeggiePizza(ingredientFactory);
      pizza.setName("New York Style Clam Pizza");
    }
    return pizza;
  }
}

class ChicagoPizzaStore extends PizzaStore {

  @Override
  protected Pizza createPizza(String type) {
    Pizza pizza = null;

    if (type.equals("cheese")) {
//      pizza = new ChicagoStyleCheesePizza();
    } else if (type.equals("clam")) {
//      pizza = new ChicagoStyleClamPizza();
    } else if (type.equals("veggie")) {
//      pizza = new ChicagoStyleVeggiePizza();
    }
    return pizza;
  }
}

interface Dough {

}

class ThinCrustDough implements Dough {

}

interface Sauce {

}

class MarinaraSauce implements Sauce {

}

interface Cheese {

}

class ReggianoCheese implements Cheese {

}

interface Veggies {

}

class Garlic implements Veggies {

}

class Onion implements Veggies {

}

class Mushroom implements Veggies {

}

interface Clams {

}

class FreshClams implements Clams {

}

interface PizzaIngredientFactory {
  Dough createDough();
  Sauce createSauce();
  Cheese createCheese();
  Veggies[] createVeggies();
  Clams createClam();
}

class NYPizzaIngredientFactory implements PizzaIngredientFactory {

  @Override
  public Dough createDough() {
    return new ThinCrustDough();
  }

  @Override
  public Sauce createSauce() {
    return new MarinaraSauce();
  }

  @Override
  public Cheese createCheese() {
    return new ReggianoCheese();
  }

  @Override
  public Veggies[] createVeggies() {
    Veggies veggies[] = { new Garlic(), new Onion(), new Mushroom() };
    return veggies;
  }

  @Override
  public Clams createClam() {
    return new FreshClams();
  }
}

class Main {

  public static void main(String[] args) {
    PizzaStore nyStore = new NYPizzaStore();
//    PizzaStore chicagoStore = new ChicagoPizzaStore();

    Pizza pizza = nyStore.orderPizza("cheese");
    System.out.println("Ethan ordered a " + pizza.getName() + "\n");

//    pizza = chicagoStore.orderPizza("cheese");
//    System.out.println("Joel ordered a " + pizza.getName() + "\n");
  }
}