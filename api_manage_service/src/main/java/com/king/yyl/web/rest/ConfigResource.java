package com.king.yyl.web.rest;

import com.king.yyl.domain.Config;
import com.king.yyl.repository.ConfigRepository;
import com.king.yyl.service.dto.ConfigDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConfigResource {
    Logger logger = LoggerFactory.getLogger(ConfigResource.class);

    @Inject
    private ConfigRepository configRepository;

    public Config create(@RequestBody ConfigDTO configDTO) {
	logger.debug("Creating a new config with information: " + configDTO);

	Config config = new Config();

	config.setName(configDTO.getName());
	config.setDescription(configDTO.getDescription());

	config.setConfigKey(configDTO.getConfigKey());
	config.setConfigValue(configDTO.getConfigValue());

	return configRepository.save(config);
    }

    public Config findById(String id) {
	logger.debug("Finding config by id: " + id);

	return configRepository.findOne(id);
    }

    public List<Config> findAll() {
	return configRepository.findAll();
    }
}
