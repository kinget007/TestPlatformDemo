package com.king.yyl.web.rest;

import com.king.yyl.domain.Module;
import com.king.yyl.repository.ModuleRepository;
import com.king.yyl.service.dto.ModuleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ModuleResource {
    Logger logger = LoggerFactory.getLogger(ModuleResource.class);

    @Inject
    private ModuleRepository moduleRepository;

    @RequestMapping(value="/modules", method = RequestMethod.POST, headers = "Accept=application/json")
    public @ResponseBody
    Module create(@RequestBody ModuleDTO moduleDTO) {
	logger.debug("Creating a new module with information: " + moduleDTO);

	Module module = new Module();

	module.setName(moduleDTO.getName());
	module.setDescription(moduleDTO.getDescription());

	return moduleRepository.save(module);
    }

    @RequestMapping(value="/modules/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public @ResponseBody
    Module delete(@PathVariable("id") String id) {
	logger.debug("Deleting module with id: " + id);

	Module deleted = moduleRepository.findOne(id);

	moduleRepository.delete(deleted);

	return deleted;
    }

    @RequestMapping(value="/modules", method = RequestMethod.GET)
    public @ResponseBody
    List<Module> findAll() {
	logger.debug("Finding all modules");

	return moduleRepository.findAll();
    }

    @RequestMapping(value="/modules/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Module findById(@PathVariable("id") String id) {
	logger.debug("Finding module by id: " + id);

	return moduleRepository.findOne(id);
    }

    @RequestMapping(value="/modules/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Module update(@PathVariable("id") String id, @RequestBody ModuleDTO updated) {
	logger.debug("Updating module with information: " + updated);

	Module module = moduleRepository.findOne(updated.getId());

	module.setName(updated.getName());
	module.setDescription(updated.getDescription());

	return module;
    }
}
