package fi.hh.swd20.bookstore.domain;

import org.springframework.data.repository.CrudRepository;

// tietokantakäsittelyn rajapinta
public interface BookRepository extends CrudRepository<Book, Long> {
	
	

}
