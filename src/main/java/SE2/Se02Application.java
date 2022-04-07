<<<<<<<< HEAD:src/main/java/se2Project/Se02Application.java
package se2Project;
========
package SE2;
>>>>>>>> main:src/main/java/SE2/Se02Application.java

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan("se2Project.repository")
public class Se02Application {
    public static void main(String[] args) {
        SpringApplication.run(Se02Application.class, args);
    }

}
