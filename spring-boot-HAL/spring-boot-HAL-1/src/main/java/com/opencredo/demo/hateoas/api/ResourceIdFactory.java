package com.opencredo.demo.hateoas.api;

import com.opencredo.demo.hateoas.domain.Author;
import com.opencredo.demo.hateoas.domain.Book;
import com.opencredo.demo.hateoas.domain.Publisher;

/**
 * Encapsulate the logic to get the resource ID out of the domain entity
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public abstract class ResourceIdFactory {

   public static String getId(Author author) {
      return author.getHandle();
   }
   
   public static String getId(Book book) {
      return book.getIsbn();
   }
   
   public static String getId(Publisher publisher) {
      return publisher.getId().toString();
   }
}
