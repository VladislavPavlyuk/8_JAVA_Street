// Абстрактный класс для всех типов домов
abstract class Building {
    protected String address;

    public Building(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract void initializeFromString(String data);

    public abstract void printInfo();
}
