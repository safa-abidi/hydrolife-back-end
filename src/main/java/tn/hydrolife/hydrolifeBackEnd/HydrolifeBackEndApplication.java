package tn.hydrolife.hydrolifeBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tn.hydrolife.hydrolifeBackEnd.repositories.CentreRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = CentreRepository.class)
public class HydrolifeBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(HydrolifeBackEndApplication.class, args);
	}

}
