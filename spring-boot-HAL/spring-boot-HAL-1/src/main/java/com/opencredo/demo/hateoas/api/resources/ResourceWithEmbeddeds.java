package com.opencredo.demo.hateoas.api.resources;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * The @JsonUnwrapped annotation is required as EmbeddedWrappers are by default serialised in a "_embedded" container, 
 * that has to be added directly into the top level object
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */
public abstract class ResourceWithEmbeddeds extends ResourceSupport {
   
   @JsonUnwrapped 
   private Resources<EmbeddedWrapper> embeddeds;

   public Resources<EmbeddedWrapper> getEmbeddeds() {
      return embeddeds;
   }

   public void setEmbeddeds(Resources<EmbeddedWrapper> embeddeds) {
      this.embeddeds = embeddeds;
   }   
}
