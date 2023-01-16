package projekr.webapp.com.example.projektrwebapp.Service;

import lombok.AllArgsConstructor;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import projekr.webapp.com.example.projektrwebapp.DTO.ItemDTO;
import projekr.webapp.com.example.projektrwebapp.DTO.RelocationDTO;
import projekr.webapp.com.example.projektrwebapp.Entity.Item;
import projekr.webapp.com.example.projektrwebapp.Entity.ItemType;
import projekr.webapp.com.example.projektrwebapp.Entity.Location;
import projekr.webapp.com.example.projektrwebapp.Entity.Model;
import projekr.webapp.com.example.projektrwebapp.Repository.ItemRepository;
import projekr.webapp.com.example.projektrwebapp.Repository.ItemTypeRepository;
import projekr.webapp.com.example.projektrwebapp.Repository.LocationRepository;
import projekr.webapp.com.example.projektrwebapp.Repository.ModelRepository;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemTypeRepository itemTypeRepository;
    private final ModelRepository modelRepository;
    private final LocationRepository locationRepository;

    public String addItem(ItemDTO itemDTO) {

        boolean newEntity = false;

        //check if there is an item type in db
        ArrayList<ItemType> allItemTypes = itemTypeRepository.getAllItemTypes();
        long newItemTypeId = 0;

        for (ItemType itemType : allItemTypes) {
            if (itemType.getTypeName().equals(itemDTO.getItemType())) {
                newItemTypeId = itemType.getIdItemtype();
            }
        }

        if (newItemTypeId == 0) {
            itemTypeRepository.save(ItemType.builder()
                    .typeName(itemDTO.getItemType())
                    .build());
            newEntity = true;
        }

        //check if there is a model in db
        ArrayList<Model> allModels = modelRepository.getAllModels();

        long newModelId = 0;
        for (Model model : allModels) {
            if (model.getModelName().equals(itemDTO.getModel())) {
                newModelId = model.getIdModel();
            }
        }
        if (newModelId == 0) {
            modelRepository.save(Model.builder()
                    .modelName(itemDTO.getModel())
                    .build());
            newEntity = true;
        }

        //check if there is a location in db
        ArrayList<Location> allLocations = locationRepository.getAllLocations();
        long newLocationId = 0;
        for (Location location : allLocations) {
            if (location.getSectorName().equals(itemDTO.getLocation())) {
                newLocationId = location.getIdLocation();
            }
        }
        if (newLocationId == 0) {
            locationRepository.save(Location.builder()
                    .sectorName(itemDTO.getLocation())
                    .build());
            newEntity = true;
        }

        //check if there is an item in db
        ArrayList<Item> allItems = itemRepository.getAllItems();
        long newItemId = 0;
        for (Item item : allItems) {
            if (item.getBarcode().equals(itemDTO.getBarcode()) && item.getId_location().getId() == newLocationId) {
                newItemId = item.getIdItem();
            }
        }

        if (newItemId == 0 || newEntity) {
            itemRepository.save(Item.builder()
                    .amount(itemDTO.getAmount())
                    .barcode(itemDTO.getBarcode())
                    .id_itemtype(AggregateReference.to(itemTypeRepository.getItemTypeByTypeName(itemDTO.getItemType()).getIdItemtype()))
                    .id_model(AggregateReference.to(modelRepository.getModelByModelName(itemDTO.getModel()).getIdModel()))
                    .id_location(AggregateReference.to(locationRepository.getLocationBySectorName(itemDTO.getLocation()).getIdLocation()))
                    .build());
            return "Novi artikl uspjesno dodan.";
        } else {
            itemRepository.updateItem(newItemId, itemDTO.getAmount());
            return "Postojeci artikl uspjesno nadodan.";
        }
    }

    public String removeItem(ItemDTO itemDTO){

        ArrayList<Item> allItems = itemRepository.getAllItems();

        for(Item item : allItems) {
            if(item.getBarcode().equals(itemDTO.getBarcode()) && item.getAmount() >= itemDTO.getAmount()) {
                Location location = locationRepository.getLocationBySectorName(itemDTO.getLocation());
                if(location.getIdLocation() == item.getId_location().getId()){
                    long itemID = item.getIdItem();
                    itemRepository.updateItem(itemID, -itemDTO.getAmount());
                    return "Artikli izdani.";
                }
            }
        }

        return "Navedena kombinacija artikala ne postoji u skladistu.";

    }

    public String relocateItem(RelocationDTO relocationDTO){

        ItemDTO fromRelocationDTO = new ItemDTO(relocationDTO.getItemType(), relocationDTO.getModel(),
                    relocationDTO.getLocationFrom(), relocationDTO.getAmount(), relocationDTO.getBarcode());
        String removing = this.removeItem(fromRelocationDTO);

        if(removing.equals("Artikli izdani.")){
            ItemDTO toRelocationDTO = new ItemDTO(relocationDTO.getItemType(), relocationDTO.getModel(),
                    relocationDTO.getLocationTo(), relocationDTO.getAmount(), relocationDTO.getBarcode());
            this.addItem(toRelocationDTO);
            return "Artikli premjesteni.";
        }
        else {
            return "Nece.";
        }



    }
}
