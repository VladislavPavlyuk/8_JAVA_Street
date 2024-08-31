import java.util.ArrayList;
import java.util.List;

// Улица
class Street {
    private List<Building> buildings;

    public Street() {
        this.buildings = new ArrayList<>();
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public void removeBuilding(Building building) {
        buildings.remove(building);
    }

    public void printStreetInfo() {
        for (Building building : buildings) {
            building.printInfo();
        }
    }

    public List<Shop> findShopsNearResidentialBuilding(ResidentialBuilding residentialBuilding, int range, String departmentType) {
        List<Shop> nearbyShops = new ArrayList<>();
        int index = buildings.indexOf(residentialBuilding);
        for (int i = Math.max(0, index - range); i <= Math.min(buildings.size() - 1, index + range); i++) {
            if (buildings.get(i) instanceof Shop) {
                Shop shop = (Shop) buildings.get(i);
                if (shop.getNumberOfDepartments() >= 1) { // Пример проверки типа отдела
                    nearbyShops.add(shop);
                }
            }
        }
        return nearbyShops;
    }
}
