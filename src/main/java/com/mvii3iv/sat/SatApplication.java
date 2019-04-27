package com.mvii3iv.sat;

import com.mvii3iv.sat.components.bills.BillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SatApplication extends SpringBootServletInitializer implements CommandLineRunner  {


	@Autowired
	BillsRepository repository;


	public static void main(String[] args) {
		SpringApplication.run(SatApplication.class, args);
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SatApplication.class);
	}


	@Override
	public void run(String... args) throws Exception {
		/*
		repository.deleteAll();

		// save a couple of customers
		repository.save(new Bills(
				"AB930177-609A-4FBB-AA5E-5A730BC712AA",
				"LULR860821MTA",
				"ROSA IVET LUNA LOPEZ",
				"ARD631206SX5",
				"AUTOTRANSPORTES RAPIDOS DELICIAS SA DE CV",
				"2017-01-05T12:02:46",
				"2017-01-05T12:02:47",
				"CCC1007293K0",
				"$15,405.99",
				"Ingreso",
				"Vigente"
				));


		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Bills customer : repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFiscalId(\"AB930177-609A-4FBB-AA5E-5A730BC712AA\"):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByFiscalId("AB930177-609A-4FBB-AA5E-5A730BC712AA"));

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Bills income : repository.findByEmisorRFC("LULR860821MTA")) {
			System.out.println(income);
		}
		*/

	}

}
