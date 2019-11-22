# Course

https://www.udemy.com/course/apache-maven-beginner-to-guru/learn/lecture/12667238#questions

## Knowledge

- mvn clean: run clean lifecycle
- mvn clean:clean: run goal clean of mvn-clean-plugin. The mvn-clean-plugin only have one goal: clean 
- mvn test: test project, not clean target first, because clean is not default life cycle
- mvn clean test 
- mvn install: build the project and it installed that artifact into my local m2 repository
- mvn dependency:tree : resolve artifact conflict or dependency conflict
- mvn help:effective-pom : Maven pom will inherit not only from any parent pom that it  has, but also the Maven environment, show all dependency
- 

## Question

1. What is plugin Binding? What plugin Binding actually means?
- Plugin binding means binding a Maven lifestyle phase to a goal of a plugin
- For example, Maven comes with a jar plugin with three goals: help, jar, and test-jar. The package phase of the default Maven life cycle is bound to the jar goal
- so when you execute the package phase as mvn package, under the hood the jar goal of the jar plugin gets executed. 

## Link ref

- [Dependency Scope](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#Dependency_Scope)
