package com.opencredo.demo.hateoas.api.resources;

import java.util.ArrayList;
import java.util.List;

/**
 *  Doesn't extend ResourceSupport being used for request only
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public class BookNew {
   
   private String isbn;
   private String title;
   private List<BookNew.Author> authors = new ArrayList<>();
   private String publisherId;
   
   public static class Author {
      private String firstName;
      private String lastName;
      public String getFirstName() {
         return firstName;
      }
      public void setFirstName(String firstName) {
         this.firstName = firstName;
      }
      public String getLastName() {
         return lastName;
      }
      public void setLastName(String lastName) {
         this.lastName = lastName;
      }
   }

   public String getIsbn() {
      return isbn;
   }

   public void setIsbn(String isbn) {
      this.isbn = isbn;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public List<BookNew.Author> getAuthors() {
      return authors;
   }

   public void setAuthors(List<BookNew.Author> authors) {
      this.authors = authors;
   }

   public String getPublisherId() {
      return publisherId;
   }

   public void setPublisherId(String publisherId) {
      this.publisherId = publisherId;
   }
   
   
}
