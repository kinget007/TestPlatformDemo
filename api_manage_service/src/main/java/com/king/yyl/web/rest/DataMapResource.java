package com.king.yyl.web.rest;

import com.king.yyl.domain.DataMap;
import com.king.yyl.repository.DataMapRepository;
import com.king.yyl.service.dto.DataMapDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DataMapResource {
    Logger logger = LoggerFactory.getLogger(DataMapResource.class);

    @Inject
    private DataMapRepository dataMapRepository;

    @RequestMapping(value="/dataMaps", method = RequestMethod.POST, headers = "Accept=application/json")
    public DataMap create(@RequestBody DataMapDTO dataMapDTO) {
        logger.debug("Creating a new dataMap with information: " + dataMapDTO);

        DataMap dataMap = new DataMap();
        dataMap.setDataKey(dataMapDTO.getKey());
        dataMap.setValue(dataMapDTO.getValue());
        dataMap.setType(dataMapDTO.getType());
        return dataMapRepository.save(dataMap);
    }

    @RequestMapping(value="/dataMaps/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public DataMap delete(@PathVariable("id") String id) {
        logger.debug("Deleting dataMap with id: " + id);

        DataMap deleted = dataMapRepository.findOne(id);

        dataMapRepository.delete(deleted);

        return deleted;
    }

    @RequestMapping(value="/dataMaps", method = RequestMethod.GET)
    public List<DataMap> findAll() {
        logger.debug("Finding all dataMaps");

        return dataMapRepository.findAll();
    }

    @RequestMapping(value="/dataMaps/{id}", method = RequestMethod.GET)
    public DataMap findById(@PathVariable("id") String id) {
        logger.debug("Finding dataMap by id: " + id);

        return dataMapRepository.findOne(id);
    }

    @RequestMapping(value="/dataMaps/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public DataMap update(@PathVariable("id") Long id, @RequestBody DataMapDTO updated) {
        logger.debug("Updating dataMap with information: " + updated);

        DataMap dataMap = dataMapRepository.findOne(updated.getId());

        dataMap.setDataKey(updated.getKey());
        dataMap.setValue(updated.getValue());

        return dataMap;
    }
}
