<<<<<<<< HEAD:src/main/java/SE2/Se02Application.java
package SE2;
========
package SE2.user;
>>>>>>>> origin/trangdh:src/main/java/SE2/user/Se02Application.java

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

<<<<<<< HEAD
<<<<<<< HEAD
@SpringBootApplication
=======
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
>>>>>>> origin/phuonglt
=======
@SpringBootApplication
>>>>>>> origin/thuyntm
public class Se02Application {

    public static void main(String[] args) {
        SpringApplication.run(Se02Application.class, args);
    }

}
