package creational.abstractfactory.pattern.demo;

interface ITollywoodMovie {
  String movieName();
}

interface IBollywoodMovie {
  String movieName();
}

interface IMovieFactory {
  ITollywoodMovie getTollywoodMovie();
  IBollywoodMovie getBollywoodMovie();
}

class TollywoodActionMovie implements ITollywoodMovie {

  @Override
  public String movieName() {
    return "Tollywood Action Movie";
  }
}

class TollywoodComedyMovie implements ITollywoodMovie {

  @Override
  public String movieName() {
    return "Tollywood Comedy Movie";
  }
}

class BollywoodActionMovie implements IBollywoodMovie {

  @Override
  public String movieName() {
    return "Bollywood Action Movie";
  }
}

class BollywoodComedyMovie implements IBollywoodMovie {

  @Override
  public String movieName() {
    return "Bollywood Comedy Movie";
  }
}

/**
 * Action movie factory
 */
class ActionMovieFactory implements IMovieFactory {

  @Override
  public ITollywoodMovie getTollywoodMovie() {
    return new TollywoodActionMovie();
  }

  @Override
  public IBollywoodMovie getBollywoodMovie() {
    return new BollywoodActionMovie();
  }
}

/**
 * Comedy movie factory
 */
class ComedyMovieFactory implements IMovieFactory {

  @Override
  public ITollywoodMovie getTollywoodMovie() {
    return new TollywoodComedyMovie();
  }

  @Override
  public IBollywoodMovie getBollywoodMovie() {
    return new BollywoodComedyMovie();
  }
}

public class AbstractFactoryPatternEx {
  public static void main(String[] args) {
    ActionMovieFactory actionMovieFactory = new ActionMovieFactory();
    ITollywoodMovie tAction = actionMovieFactory.getTollywoodMovie();
    IBollywoodMovie bAction = actionMovieFactory.getBollywoodMovie();
    System.out.println("Action movies: ");
    System.out.println(tAction.movieName());
    System.out.println(bAction.movieName());

    ComedyMovieFactory comedyMovieFactory = new ComedyMovieFactory();
    ITollywoodMovie tComedy = comedyMovieFactory.getTollywoodMovie();
    IBollywoodMovie bComedy = comedyMovieFactory.getBollywoodMovie();
    System.out.println("Comedy movies: ");
    System.out.println(tComedy.movieName());
    System.out.println(bComedy.movieName());
  }
}
