package fi.hh.swd20.bookstore.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Category {
	
	// attribuutit
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long categoryid;
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	//@JsonIgnore
	@JsonIgnoreProperties("category")
	private List<Book> books;

	// konstruktorit
	public Category(String name) {
		super();
		this.name = name;
	}
	
	public Category() {
		super();
		this.categoryid = 0L;
		this.name = null;
	}
	
	// getterit ja setterit
	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	// toString
	@Override
	public String toString() {
		return "Category [categoryid=" + categoryid + ", name=" + name + "]";
	}
}
