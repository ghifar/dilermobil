package com.ghifar.demoku;

import com.ghifar.demoku.domain.CarRepository;
import com.ghifar.demoku.domain.OwnerRepository;
import com.ghifar.demoku.domain.User;
import com.ghifar.demoku.domain.UserRepository;
import com.ghifar.demoku.entity.Car;
import com.ghifar.demoku.entity.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemokuApplication {
	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemokuApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(){
		return args -> {

			Owner owner1= new Owner("Abudzar","Al Ghiffari");
			Owner owner2= new Owner("Lombok","Kuning");

			ownerRepository.save(owner1);
			ownerRepository.save(owner2);

			carRepository.save(new Car("Ford","SUV","Red","DD799PP",2017,100000, owner1));
			carRepository.save(new Car("Ford","Sedan","Green","DD799PO",2017,200000,owner1));
			carRepository.save(new Car("Mercedes","Sedan","white","DDBIG",2019,220000,owner2));

			//password user
			userRepository.save(new User("user","$2a$04$1.YhMIgNX/8TkCKGFUONWO1waedKhQ5KrnB30fl0Q01QKqmzLf.Zi",
					"USER"));

			//password admin
			userRepository.save(new User("admin",
					"$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG",
					"ADMIN"));

		};
	}

}
