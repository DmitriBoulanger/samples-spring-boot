package com.opencredo.demo.hateoas.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.opencredo.demo.hateoas.api.resourceassemblers.IndexResourceAssembler;
import com.opencredo.demo.hateoas.api.resources.IndexResource;

/**
 * 
 * HATEOAS Provider
 * 
 * @author Dmitri Boulanger, Hombach
 *
 * D. Knuth: Programs are meant to be read by humans and 
 *           only incidentally for computers to execute 
 *
 */

@RestController
@RequestMapping("/hateoas")
public class HateoasController {

   private final IndexResourceAssembler indexResourceAssembler;
   
   
   @Autowired
   public HateoasController(IndexResourceAssembler indexResourceAssembler) {
      this.indexResourceAssembler = indexResourceAssembler;
   }


   @RequestMapping(method=RequestMethod.GET)
   public ResponseEntity<IndexResource> index() {
      return ResponseEntity.ok(indexResourceAssembler.buildIndex());
   }
}
