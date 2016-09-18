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
public class PublisherNew {
   
   private String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
   
}
