package com.king.yyl.web.rest;

import com.king.yyl.domain.GenericEntity;
import com.king.yyl.domain.GenericEntityField;
import com.king.yyl.repository.GenericEntityRepository;
import com.king.yyl.service.dto.GenericEntityDTO;
import com.king.yyl.service.dto.GenericEntityFieldDTO;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GenericEntityResource {
    Logger logger = LoggerFactory.getLogger(GenericEntityResource.class);

    @Inject
    private GenericEntityRepository genericEntityRepository;

    @Autowired
    private GenerateApiResource generateApiController;

    @RequestMapping(value="/entities", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    GenericEntity create(@RequestBody GenericEntityDTO genericEntityDTO) {
	logger.debug("Creating a new entity with information: " + genericEntityDTO);

	String entityName = genericEntityDTO.getName();

	GenericEntity entity = new GenericEntity();
	entity.setName(entityName);
	entity.setDescription(genericEntityDTO.getDescription());

	List<GenericEntityFieldDTO> fieldDTOs = genericEntityDTO.getFields();
	if (fieldDTOs != null && !fieldDTOs.isEmpty()) {
	    List<GenericEntityField> fields = new ArrayList<GenericEntityField>();
	    GenericEntityField field = null;
	    for (GenericEntityFieldDTO fieldDTO : fieldDTOs) {
		field = new GenericEntityField();
		field.setName(fieldDTO.getName());
		field.setType(fieldDTO.getType());
		fields.add(field);
	    }
	    entity.setFields(fields);
	}

	entity = genericEntityRepository.save(entity);
	return entity;
    }

    @RequestMapping(value="/entities/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    GenericEntity delete(@PathVariable("id") String id) {
	logger.debug("Deleting entity with id: " + id);

	GenericEntity deleted = genericEntityRepository.findOne(id);

	genericEntityRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value="/entities", method = RequestMethod.GET)
    public @ResponseBody
    List<GenericEntity> findAll() {
	logger.debug("Finding all entities");

	return genericEntityRepository.findAll();
    }

    @RequestMapping(value="/entities/{id}", method = RequestMethod.GET)
    public @ResponseBody
    GenericEntity findById(@PathVariable("id") String id) {
	logger.debug("Finding entity by id: " + id);

	return genericEntityRepository.findOne(id);
    }

    @RequestMapping(value="/entities/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    GenericEntity update(@PathVariable("id") String id, @RequestBody GenericEntityDTO updated, @RequestParam(value = "generateApi", required = false) boolean generateApi) throws JSONException, MalformedURLException {
	logger.debug("Updating entity with information: " + updated);

	GenericEntity entity = genericEntityRepository.findOne(updated.getId());

	entity.setName(updated.getName());
	entity.setDescription(updated.getDescription());

	List<GenericEntityFieldDTO> fieldsDTOs = updated.getFields();
	if(fieldsDTOs != null){
	    List<GenericEntityField> fields = entity.getFields() != null ? entity.getFields(): new ArrayList<GenericEntityField>();
	    for(GenericEntityFieldDTO fieldDTO : fieldsDTOs){
		GenericEntityField field = new GenericEntityField();
		field.setName(fieldDTO.getName());
		field.setType(fieldDTO.getType());

		if(!fields.contains(field)){
		    fields.add(field);
		}
	    }

	    entity.setFields(fields);
	}

	genericEntityRepository.save(entity);

	if(generateApi){
	    generateApiController.generateApiByEntity(entity);
	}

	return entity;
    }
}
