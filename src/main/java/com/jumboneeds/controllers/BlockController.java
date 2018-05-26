package com.jumboneeds.controllers;

import com.jumboneeds.beans.BlockBuildingListBean;
import com.jumboneeds.beans.BlocksBean;
import com.jumboneeds.beans.IdBean;
import com.jumboneeds.beans.StatusBean;
import com.jumboneeds.entities.Block;
import com.jumboneeds.entities.Building;
import com.jumboneeds.services.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/block")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @RequestMapping(value = "/findByBuilding", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody BlocksBean findByBuilding(@RequestBody IdBean idBean) {
        return blockService.findByBuilding(idBean);
    }

    @RequestMapping(value = "/findBlockByBuilding", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody BlockBuildingListBean findBlockByBuilding(@RequestBody Building building) {
        return blockService.findBlockByBuilding(building.getId());
    }

    @RequestMapping(value = "/addBlock", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean addBlock(@RequestBody Block block) {
        return blockService.addBlock(block);
    }

    @RequestMapping(value = "/editBlock", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean editBlock(@RequestBody Block block) {
        return blockService.editBlock(block);
    }


    @RequestMapping(value = "/deleteBlock", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean deleteBlock(@RequestBody Block block) {
        return blockService.deleteBlock(block.getId());
    }

}