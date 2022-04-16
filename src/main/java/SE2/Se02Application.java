package SE2;

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
