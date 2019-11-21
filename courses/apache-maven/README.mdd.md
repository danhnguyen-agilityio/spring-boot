# Course

https://www.udemy.com/course/apache-maven-beginner-to-guru/learn/lecture/12667238#questions

## Question

1. What is plugin Binding? What plugin Binding actually means?
- Plugin binding means binding a Maven lifestyle phase to a goal of a plugin
- For example, Maven comes with a jar plugin with three goals: help, jar, and test-jar. The package phase of the default Maven life cycle is bound to the jar goal
- so when you execute the package phase as mvn package, under the hood the jar goal of the jar plugin gets executed. 