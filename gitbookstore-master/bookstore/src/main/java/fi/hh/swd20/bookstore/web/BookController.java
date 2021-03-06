package fi.hh.swd20.bookstore.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.hh.swd20.bookstore.domain.Book;
import fi.hh.swd20.bookstore.domain.BookRepository;
import fi.hh.swd20.bookstore.domain.CategoryRepository;

@Controller
public class BookController {

	   // Spring-alusta luo sovelluksen käynnistyessä BookRepository-rajapintaa toteuttavan luokan olion 
	   // ja kytkee olion BookController-luokasta luodun olion attribuuttiolioksi
	@Autowired
	BookRepository bookRepository; 
	
	@Autowired
	CategoryRepository categoryRepository;
	
	// REST palvelu, joka palauttaa kaikki kirjat
    @RequestMapping(value="/books", method = RequestMethod.GET)
    public @ResponseBody List<Book> getBooksRest() {	
        return (List<Book>) bookRepository.findAll();
    } 
    
	// REST palvelu, joka palauttaa yhden kirjan id:n perusteella
    @RequestMapping(value="/books/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Book> findBookRest(@PathVariable("id") Long bId) {	
    	return bookRepository.findById(bId);
    } 
    
    // REST metodi, joka lisää tietokantaan JSON:na tulleen uuden kirjan tiedot
   @PostMapping("/books")
   public @ResponseBody Book saveBookRest(@RequestBody Book book) {
	   bookRepository.save(book);
	   return book;
   }

	// kirjalistaus
	@RequestMapping(value = "/booklist", method = RequestMethod.GET)
	public String getBooks(Model model) {
			List<Book> books =  (List<Book>) bookRepository.findAll(); // haetaan tietokannasta kirjat
			model.addAttribute("books", books); // välitetään kirjalista templatelle model-olion avulla
			return "booklist"; // DispatherServlet saa tämän template-nimen ja kutsuu seuraavaksi booklist.html-templatea,
								// joka prosessoidaan palvelimella
	}
	
	// tyhjän kirjalomakkeen muodostaminen
		@RequestMapping(value = "/newbook", method = RequestMethod.GET)
		public String getNewBookForm(Model model) {
			model.addAttribute("book", new Book()); // "tyhjä" kirja-olio
			model.addAttribute("categories", categoryRepository.findAll());
			return "addbook";
		}

		// kirjalomakkeella syötettyjen tietojen vastaanotto ja tallennus
		@RequestMapping(value = "/save", method = RequestMethod.POST)
		public String saveBook(@ModelAttribute Book book) {
			// talletetaan yhden kirjan tiedot tietokantaan
			bookRepository.save(book);
			return "redirect:/booklist";
		}

		// kirjan poisto
		@RequestMapping(value = "/deletebook/{id}", method = RequestMethod.GET)
		@PreAuthorize("hasRole('ADMIN')")
		public String deleteBook(@PathVariable("id") Long bookId) {
			bookRepository.deleteById(bookId);
			return "redirect:../booklist";
		}
		
		// kirjan muokkaus lomake
		@RequestMapping(value = "/editbook/{id}")
		public String showUpdateForm(@PathVariable("id") Long bookId, Model model) {
			model.addAttribute("book", bookRepository.findById(bookId));
			model.addAttribute("categories", categoryRepository.findAll());
			return "editbook";
		}
}
