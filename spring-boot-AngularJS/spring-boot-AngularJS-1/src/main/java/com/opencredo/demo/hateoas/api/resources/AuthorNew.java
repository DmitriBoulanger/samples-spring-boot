package com.opencredo.demo.hateoas.api.resources;

/**
 *  Doesn't extend ResourceSupport being used for request only
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public class AuthorNew {
   
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
