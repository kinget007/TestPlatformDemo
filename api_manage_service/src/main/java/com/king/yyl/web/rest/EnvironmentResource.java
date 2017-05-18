package com.king.yyl.web.rest;

import com.king.yyl.domain.Environment;
import com.king.yyl.domain.EnvironmentProperty;
import com.king.yyl.repository.EnvironmentRepository;
import com.king.yyl.service.dto.EnvironmentDTO;
import com.king.yyl.service.dto.EnvironmentPropertyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EnvironmentResource {
    Logger logger = LoggerFactory.getLogger(EnvironmentResource.class);

    @Inject
    private EnvironmentRepository environmentRepository;

    @RequestMapping(value="/environments", method = RequestMethod.POST, headers = "Accept=application/json")
    public Environment create(@RequestBody EnvironmentDTO environmentDTO) {
        logger.debug("Creating a new environment with information: " + environmentDTO);

        Environment environment = new Environment();
        environment.setName(environmentDTO.getName());
        environment.setDescription(environmentDTO.getDescription());

        List<EnvironmentPropertyDTO> propertyDTOs = environmentDTO.getProperties();
        if (propertyDTOs != null && !propertyDTOs.isEmpty()) {
            List<EnvironmentProperty> properties = new ArrayList<EnvironmentProperty>();
            EnvironmentProperty property = null;
            for (EnvironmentPropertyDTO propertyDTO : propertyDTOs) {
                property = new EnvironmentProperty();
                property.setPropertyName(propertyDTO.getPropertyName());
                property.setPropertyValue(propertyDTO.getPropertyValue());
                properties.add(property);
            }
            environment.setProperties(properties);
        }

        environment = environmentRepository.save(environment);
        return environment;
    }

    @RequestMapping(value="/environments/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public Environment delete(@PathVariable("id") String id) {
        logger.debug("Deleting environment with id: " + id);

        Environment deleted = environmentRepository.findOne(id);

        environmentRepository.delete(deleted);

        return deleted;
    }

    @RequestMapping(value="/environments", method = RequestMethod.GET)
    public List<Environment> findAll() {
        logger.debug("Finding all environments");

        return environmentRepository.findAll();
    }

    @RequestMapping(value="/environments/{id}", method = RequestMethod.GET)
    public Environment findById(@PathVariable("id") String id) {
        logger.debug("Finding environment by id: " + id);

        return environmentRepository.findOne(id);
    }

    @RequestMapping(value="/environments/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public Environment update(@PathVariable("id") String id, @RequestBody EnvironmentDTO updated) {
        logger.debug("Updating environment with information: " + updated);

        Environment environment = environmentRepository.findOne(updated.getId());

        environment.setName(updated.getName());
        environment.setDescription(updated.getDescription());

        List<EnvironmentPropertyDTO> propertyDTOs = updated.getProperties();
        if (propertyDTOs != null && !propertyDTOs.isEmpty()) {
            List<EnvironmentProperty> properties = new ArrayList<EnvironmentProperty>();
            EnvironmentProperty property = null;
            for (EnvironmentPropertyDTO propertyDTO : propertyDTOs) {
                property = new EnvironmentProperty();
                property.setPropertyName(propertyDTO.getPropertyName());
                property.setPropertyValue(propertyDTO.getPropertyValue());
                properties.add(property);
            }
            environment.setProperties(properties);
        }
        environment = environmentRepository.save(environment);
        return environment;
    }
}
