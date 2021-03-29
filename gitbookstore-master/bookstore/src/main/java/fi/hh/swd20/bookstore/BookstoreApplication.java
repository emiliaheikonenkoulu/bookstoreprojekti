package fi.hh.swd20.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.hh.swd20.bookstore.domain.Book;
import fi.hh.swd20.bookstore.domain.BookRepository;
import fi.hh.swd20.bookstore.domain.Category;
import fi.hh.swd20.bookstore.domain.CategoryRepository;
import fi.hh.swd20.bookstore.domain.User;
import fi.hh.swd20.bookstore.domain.UserRepository;

@SpringBootApplication
public class BookstoreApplication {
	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);  // loggeriattribuutti

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

//  testidatan luonti H2-testitietokantaan aina sovelluksen käynnistyessä
	@Bean
	public CommandLineRunner BookDemo(BookRepository bookRepository, CategoryRepository categoryRepository, UserRepository userRepository) { 
		return (args) -> {
			log.info("save a couple of categories and books");
			categoryRepository.save(new Category("Thriller"));
			categoryRepository.save(new Category("Romance"));
			categoryRepository.save(new Category("Mystery"));
			
			bookRepository.save(new Book("Promised Land, A", "Barack Obama", 2020, "1232323-21", 34.95, categoryRepository.findByName("Thriller").get(0)));
			bookRepository.save(new Book("Midnight Sun", "Stephenie Meyer", 2019, "1232323-53", 19.95, categoryRepository.findByName("Romance").get(0)));
			
			// käyttäjät
			User user1 = new User("user", "$2a$10$2DxQgGDhrCNFvzp8N.RbhuMj7QdnrOSg8sd2v.KUpT1fwDuHPoDsW", "user@user1.com", "USER");
			User user2 = new User("admin", "$2a$10$XvadCvITo0MLCu54zoxfyOiZVSAutMXcKpUo5qs4FM2oJj83VYeE.", "admin@user2.com", "ADMIN");
			userRepository.save(user1);
			userRepository.save(user2);
		
			log.info("fetch all books");
			for (Book book : bookRepository.findAll()) {
				log.info(book.toString());
			}

		};
}
}
