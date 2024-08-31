// Жилой дом
class ResidentialBuilding extends Building {
    private int numberOfResidents;

    public ResidentialBuilding(String address, int numberOfResidents) {
        super(address);
        this.numberOfResidents = numberOfResidents;
    }

    public int getNumberOfResidents() {
        return numberOfResidents;
    }

    public void setNumberOfResidents(int numberOfResidents) {
        this.numberOfResidents = numberOfResidents;
    }

    @Override
    public void initializeFromString(String data) {
        // Пример: "address,numberOfResidents"
        String[] parts = data.split(",");
        this.address = parts[0];
        this.numberOfResidents = Integer.parseInt(parts[1]);
    }

    @Override
    public void printInfo() {
        System.out.println("Residential Building at " + address + " with " + numberOfResidents + " residents.");
    }
}
