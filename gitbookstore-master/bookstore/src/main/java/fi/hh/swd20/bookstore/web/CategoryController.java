package fi.hh.swd20.bookstore.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.hh.swd20.bookstore.domain.Category;
import fi.hh.swd20.bookstore.domain.CategoryRepository;

@Controller
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;
	// kategorialistaus
	@RequestMapping(value = "/categorylist", method = RequestMethod.GET)
	public String getCategories(Model model) {
			List<Category> categories =  (List<Category>) categoryRepository.findAll(); // haetaan tietokannasta kategoriat
			model.addAttribute("categories", categories); // välitetään kategorialista templatelle model-olion avulla
			return "categorylist"; 
	}
	
	// tyhjän kategorialomakkeen muodostaminen
	@RequestMapping(value = "/newcategory", method = RequestMethod.GET)
	public String getNewCategoryForm(Model model) {
		model.addAttribute("category", new Category());
		return "newcategory";
	}
	
	// kategorialomakkeella syötettyjen tietojen vastaanotto ja tallennus
	@RequestMapping(value = "/savecategory", method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute Category category) {
		categoryRepository.save(category);
		return "redirect:/categorylist";
	}
}
