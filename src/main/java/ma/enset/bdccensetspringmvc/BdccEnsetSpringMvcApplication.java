package ma.enset.bdccensetspringmvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ma.enset.bdccensetspringmvc.entities.Product;
import ma.enset.bdccensetspringmvc.repository.ProductRepository;

@SpringBootApplication
public class BdccEnsetSpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdccEnsetSpringMvcApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner
            (ProductRepository productRepository) {
        return args -> {
            productRepository.save(
                    Product.builder()
                            .name("Computer")
                            .price(5400)
                            .quantity(23)
                            .build()
            );
            productRepository.save(
                    Product.builder()
                            .name("Printer")
                            .price(1200)
                            .quantity(22)
                            .build()
            );
            productRepository.save(
                    Product.builder()
                            .name("Smart Phone")
                            .price(1200)
                            .quantity(10)
                            .build()
            );
            productRepository.findAll()
                    .forEach(p->{
                        System.out.println(p.toString());
                    });
        };
    }


}