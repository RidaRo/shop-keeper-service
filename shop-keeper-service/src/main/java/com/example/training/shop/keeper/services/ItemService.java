package com.example.training.shop.keeper.services;

import com.example.training.shop.keeper.dto.ItemDTO;
import com.example.training.shop.keeper.exceptions.ItemNotFoundException;
import com.example.training.shop.keeper.models.Item;
import com.example.training.shop.keeper.publisher.SNSPublisher;
import com.example.training.shop.keeper.repositories.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);
    private final ItemRepository itemRepository;
    private final SNSPublisher snsPublisher;

    @Autowired
    public ItemService(ItemRepository itemRepository, SNSPublisher snsPublisher) {
        this.itemRepository = itemRepository;
        this.snsPublisher = snsPublisher;
    }

    public List<ItemDTO> findAll() {
        return itemRepository.findAll()
                .stream()
                .map(this::convertEntityToDTO)
                .toList();
    }

    public ItemDTO findByCode(UUID itemCode){
        Item item = itemRepository.findByCode(itemCode)
                .orElseThrow(() -> new ItemNotFoundException("Item with code " + itemCode + " wasn't found "));
        return convertEntityToDTO(item);
    }

    public ItemDTO addItem(Item item) {
        logger.info("Adding new item [item={}]", item);

        snsPublisher.publishTopic(item.getCode().toString());
        Item savedItem = itemRepository.save(item);

        logger.info("Saved item [id={}, code={}, name={}]", savedItem.getId(), savedItem.getCode(), savedItem.getName());

        return convertEntityToDTO(savedItem);
    }

    public ItemDTO updateItem(UUID itemCode, Item updatedItem) {
        logger.info("Updating item with code [item={}, code{}]", updatedItem, itemCode);

        Item item = itemRepository.findByCode(itemCode)
                .orElseThrow(() -> new ItemNotFoundException("Item with code " + itemCode + " wasn't found "));

        item.setName(updatedItem.getName());
        item.setQuantity(updatedItem.getQuantity());
        item.setPrice(updatedItem.getPrice());

        Item newItem = itemRepository.save(item);

        snsPublisher.publishTopic(item.getCode().toString());
        logger.info("Updated item [item={}]", newItem);

        return convertEntityToDTO(newItem);
    }


    public void deleteItem(UUID itemCode) {
        logger.info("Deleting item by code [code={}]", itemCode);

        Item item = itemRepository.findByCode(itemCode)
                .orElseThrow(() -> new ItemNotFoundException("Item with code " + itemCode + " wasn't found "));
        itemRepository.delete(item);

        logger.info("Deleted item [item={}]", item);
    }

    public ItemDTO convertEntityToDTO(Item item) {
        logger.debug("Converting item to itemDTO [item={}]", item);

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setCode(item.getCode());
        itemDTO.setName(item.getName());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setQuantity(item.getQuantity());


        logger.debug("Converted item to itemDTO [itemDTO={}]", itemDTO);

        return itemDTO;
    }

}
