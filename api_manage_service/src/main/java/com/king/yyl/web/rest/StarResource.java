package com.king.yyl.web.rest;

import com.king.yyl.domain.Star;
import com.king.yyl.repository.StarRepository;
import com.king.yyl.service.dto.StarDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StarResource {
    Logger logger = LoggerFactory.getLogger(StarResource.class);

    @Inject
    private StarRepository starRepository;

    @RequestMapping(value="/stars", method = RequestMethod.POST, headers = "Accept=application/json")
    public Star create(@RequestBody StarDTO starDTO) {
	logger.debug("Creating a new star with information: " + starDTO);

	Star star = new Star();
	star.setName(starDTO.getName());
	star.setDescription(starDTO.getDescription());
	return starRepository.save(star);
    }

    @RequestMapping(value="/stars/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public Star delete(@PathVariable("id") String id) {
	logger.debug("Deleting star with id: " + id);

	Star deleted = starRepository.findOne(id);

	starRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value="/stars", method = RequestMethod.GET)
    public List<Star> findAll() {
	logger.debug("Finding all stars");

	return starRepository.findAll();
    }

    @RequestMapping(value="/stars/{id}", method = RequestMethod.GET)
    public Star findById(@PathVariable("id") String id) {
	logger.debug("Finding star by id: " + id);

	return starRepository.findOne(id);
    }

    @RequestMapping(value="/stars/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public Star update(@PathVariable("id") Long id, @RequestBody StarDTO updated) {
	logger.debug("Updating star with information: " + updated);

	Star star = starRepository.findOne(updated.getId());

	star.setName(updated.getName());
	star.setDescription(updated.getDescription());

	return star;
    }
}
