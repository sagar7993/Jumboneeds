package com.jumboneeds.services;

import com.jumboneeds.beans.*;
import com.jumboneeds.entities.Block;
import com.jumboneeds.entities.Building;
import com.jumboneeds.repositories.BlockRepository;
import com.jumboneeds.utils.Constants;
import org.apache.log4j.Logger;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BlockService {
    private static final String TAG = "BlockService : ";

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    public Block findById(String id){
        return blockRepository.findById(id);
    }

    public BlocksBean findByBuilding(IdBean idBean){
        BlocksBean blocksBean = new BlocksBean();

        Building retrievedBuilding = buildingService.findById(idBean.getId());

        if(retrievedBuilding == null) {
            blocksBean.setStatus(0);
            blocksBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return blocksBean;
        }

        blocksBean.setBlockBeans(blockRepository.findByBuilding(retrievedBuilding));
        blocksBean.setStatus(1);
        return blocksBean;
    }

    public Long countByBuilding(Building building){
        return blockRepository.countByBuilding(building);
    }

    public void save(Block block){
        blockRepository.save(block);
    }

    public BlockBuildingListBean findBlockByBuilding(String buildingId) {

        BlockBuildingListBean blockBuildingListBean = new BlockBuildingListBean();
        blockBuildingListBean.setStatus(0); blockBuildingListBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        Building building = buildingService.findById(buildingId);
        if(building == null) {
            blockBuildingListBean.setMessage("No such building found");
            return blockBuildingListBean;
        }
        String blockBuildingQuery = "SELECT block.id, block.block_name, block.active, building.building_name, building.id AS building_id, " +
            "COUNT(user.id) AS user_count FROM block " +
            "LEFT JOIN building ON building.id = block.building " +
            "LEFT JOIN user ON user.block = block.id " +
            "WHERE building.id = :buildingId " +
            "GROUP BY block.id";
        List<BlockBuildingBean> blockBuildingList = entityManager.createNativeQuery(blockBuildingQuery, "BlockBuildingList").setParameter("buildingId", buildingId).getResultList();
        blockBuildingListBean.setBlockBuildingBeanList(blockBuildingList);
        blockBuildingListBean.setBuildingName(building.getBuildingName());
        blockBuildingListBean.setStatus(1); blockBuildingListBean.setMessage("Blocks fetched successfully");
        return blockBuildingListBean;
    }

    @Transactional
    public StatusBean addBlock(Block block){
        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0); statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        Block b = new Block(); Building building = buildingService.findById(block.getBuilding().getId());
        if(building == null) {
            statusBean.setMessage("Building not found");
        } else {
            b.setActive(block.getActive()); b.setBlockName(block.getBlockName().trim()); b.setBuilding(building);
            try {
                blockRepository.save(b); statusBean.setStatus(1); statusBean.setMessage("Block added successfully");
            } catch (Exception e){
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            }
        }
        return statusBean;
    }

    @Transactional
    public StatusBean editBlock(Block block){
        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0); statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        Block b = blockRepository.findById(block.getId());
        if(b == null) {
            statusBean.setMessage("Block not found");
        } else {
            b.setBlockName(block.getBlockName().trim()); b.setActive(block.getActive());
            try {
                blockRepository.save(b); statusBean.setStatus(1); statusBean.setMessage("Block updated successfully");
            } catch (Exception e){
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            }
        }
        return statusBean;
    }

    @Transactional
    public StatusBean deleteBlock(String blockId){
        StatusBean statusBean = new StatusBean(); statusBean.setStatus(0); statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        Block block = blockRepository.findById(blockId);
        if(block == null) {
            statusBean.setMessage("Block not found");
        } else {
            if(CollectionUtils.isEmpty(userService.findByBlock(block))) {
                try {
                    blockRepository.delete(block); statusBean.setStatus(1); statusBean.setMessage("Block deleted successfully");
                } catch (Exception e){
                    statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                }
            } else {
                statusBean.setMessage("Cannot delete a block if it has any users");
            }
        }
        return statusBean;
    }

}