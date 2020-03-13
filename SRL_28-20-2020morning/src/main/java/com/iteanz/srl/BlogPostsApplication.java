package com.iteanz.srl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class BlogPostsApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(BlogPostsApplication.class, args);
       // RunMeTask  run = new RunMeTask();
		//run.printMe();
      // Notifier notifier = new Notifier();
      // notifier.run(); 
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlogPostsApplication.class);
    }
    

   // private static Class<Application> applicationClass = Application.class;
}
