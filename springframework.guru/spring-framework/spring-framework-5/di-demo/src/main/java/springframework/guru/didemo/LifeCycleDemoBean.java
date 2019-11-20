package springframework.guru.didemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class LifeCycleDemoBean implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware, ApplicationContextAware {

    public LifeCycleDemoBean() {
        System.out.println("LifeCycleBean Constructor");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("Bean factory has been set");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("Application context has been set");
    }

    public void beforeInit() {
        System.out.println("Before Init - Called by Bean Post Processor");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("The Post Construct annotated method has been called");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("The LifecycleBean has its properties set");
    }

    public void afterInit() {
        System.out.println("After Init - Called by Bean Post Processor");
    }

    @PreDestroy
    public void preConstruct() {
        System.out.println("The Pre Construct annotated method has been called");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("The Lifecycle been has been terminated");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("");
    }

}
