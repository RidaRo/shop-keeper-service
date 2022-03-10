package com.example.training.shop.keeper.services;

import com.example.training.shop.keeper.dto.ItemDTO;
import com.example.training.shop.keeper.exceptions.ItemNotFoundException;
import com.example.training.shop.keeper.models.Item;
import com.example.training.shop.keeper.publisher.SNSPublisher;
import com.example.training.shop.keeper.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    @Autowired
    private SNSPublisher snsPublisher;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDTO> findAll() {
        return itemRepository.findAll()
                .stream()
                .map(this::convertEntityToDTO)
                .toList();
    }

    public ItemDTO addItem(Item item) {
        snsPublisher.publishTopic(item.getCode().toString());
        return convertEntityToDTO(itemRepository.save(item));
    }

    public ItemDTO updateItem(Long itemCode, Item updatedItem) {
        Item item = itemRepository.findByCode(itemCode)
                .orElseThrow(() -> new ItemNotFoundException("Item with id " + itemCode + " wasn't found "));

        item.setName(updatedItem.getName());
        item.setQuantity(updatedItem.getQuantity());
        item.setPrice(updatedItem.getPrice());

        snsPublisher.publishTopic(item.getCode().toString());
        return convertEntityToDTO(itemRepository.save(item));
    }

    public void deleteItem(Long itemCode) {
        Item item = itemRepository.findByCode(itemCode)
                .orElseThrow(() -> new ItemNotFoundException("Item with id " + itemCode + " wasn't found "));
        itemRepository.delete(item);
    }

    public ItemDTO convertEntityToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setCode(item.getCode());
        itemDTO.setName(item.getName());
        itemDTO.setPrice(itemDTO.getPrice());
        itemDTO.setQuantity(itemDTO.getQuantity());
        return itemDTO;
    }

}
