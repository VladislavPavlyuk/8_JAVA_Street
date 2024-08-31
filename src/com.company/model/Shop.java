package com.company.model;
import com.company.model.Building;

// Магазин
class Shop extends Building {
    private int numberOfDepartments;
    private enum ShopType {
        Supermarket("A large self-service retail store that offers a wide range of food, beverages, household products, and other consumer goods organized into different sections for easy shopping."),
        DepartmentStore("A retail establishment that offers a wide selection of merchandise across various departments, including clothing, electronics, home appliances, toys, cosmetics, and more, providing a one-stop shopping experience."),
        ConvenienceStore("A small retail store that offers a selection of everyday items, snacks, beverages, and other convenience products."),
        GroceryStore("A store that sells food and other household items, including fresh produce, dairy products, canned goods, and packaged foods."),
        Kiosk("A small retail booth or stand typically found in malls, airports, or busy public areas. Kiosks often sell a specific type of product or service, such as snacks, beverages, phone accessories, or tickets."),
        Stall("A small retail space typically found in markets or fairs, where vendors sell a variety of goods, including fresh produce, handmade crafts, clothing, or artisanal products."),
        OnlineEcommerceStore("An online or e-commerce store is a virtual marketplace where you can buy a wide variety of products and services over the internet. It offers convenience, extensive product selection, and secure payment options, allowing you to shop from anywhere at any time."),
        ClothingBoutique("A small shop that specializes in fashionable clothing, accessories, and unique items, often targeting specific demographics or styles."),
        DiscountStore("A retail store that offers products at lower prices compared to traditional retail stores, often providing a variety of items across different categories at affordable rates."),
        WarehouseStore("A warehouse store is a retail establishment that focuses on offering a wide range of products at discounted prices. With a large floor area, warehouse stores often feature bulk quantities of items, including groceries, household goods, electronics, and more. They cater to shoppers looking for value and savings by providing a no-frills shopping experience and typically require membership to access their offerings."),
        ElectronicsStore("A retail outlet that sells a variety of electronic devices, including televisions, computers, smartphones, and home appliances."),
        Bookstore("A shop that sells books, magazines, and other printed materials, often providing a cozy environment for browsing and reading."),
        JewelryStore("A store that sells jewelry items such as rings, necklaces, bracelets, and watches, made from various materials like gold, silver, or precious gemstones."),
        ToyStore("A retail establishment that offers a wide selection of toys, games, puzzles, and playthings for children of different ages."),
        SportingGoodsStore("A store specializing in sports equipment, gear, and apparel for various sports and outdoor activities."),
        CarBikeDealership("A retail establishment where new and used cars or motorbikes are sold. Dealerships typically have a showroom where customers can explore different models, receive information, and arrange test drives. They may also provide financing options and maintenance services."),
        FurnitureStore("A retail location that sells a range of furniture items for homes and offices, including sofas, tables, chairs, beds, and storage units."),
        PetStore("A shop that sells pet supplies, including food, toys, accessories, and sometimes live animals like dogs, cats, fish, or birds."),
        ShoeStore("A store that focuses on footwear, offering a variety of shoes, boots, sandals, and sneakers for men, women, and children."),
        HardwareStore("A store that sells tools, building materials, hardware supplies, and equipment for home improvement, repairs, and construction."),
        StationeryStore("A shop that sells various paper products, writing instruments, office supplies, greeting cards, and art materials."),
        AutoPartsStore("A store specializing in automotive parts, accessories, and maintenance products for cars, motorcycles, and other vehicles."),
        Bakery("A store that sells freshly baked goods such as bread, pastries, cakes, cookies, and other sweet treats."),
        HealthFoodStore("A retail location offering organic and natural food products, dietary supplements, vitamins, and health-related items."),
        WineShop("A store that specializes in selling a variety of wines, often providing wine tastings and knowledgeable staff for recommendations."),
        AntiqueShop("A shop that sells collectible and vintage items, including furniture, jewelry, artwork, books, and decorative objects."),
        ArtGallery("A space that exhibits and sells artworks, including paintings, sculptures, photographs, and mixed media creations."),
        MusicStore("A retail outlet that sells musical instruments, sheet music, accessories, and recordings, catering to musicians and music enthusiasts."),
        LingerieStore("A store that offers intimate apparel, including bras, underwear, sleepwear, and hosiery for both women and men."),
        GiftShop("A store that sells a variety of gift items, souvenirs, novelty goods, and seasonal decorations for special occasions."),
        FarmersMarket("A market where local farmers sell fresh produce, meats, dairy products, and other locally sourced food items."),
        ButcherStore("A specialty store that sells various cuts of meat, including beef, pork, poultry, and sometimes game meats. Butcher stores often provide personalized service, expertise in meat selection, and may offer custom cuts or specialty items."),
        Florist("A retail store that specializes in selling flowers, floral arrangements, and related products. Florists create bouquets, centerpieces, and other floral designs for various occasions, such as weddings, funerals, or gift-giving."),
        CandyShop("A store that offers a wide variety of candies, chocolates, and sweet treats. Candy shops often have an extensive selection of confectionery items, including nostalgic favorites, international candies, and unique flavors."),
        MobilePhoneStore("A retail location that specializes in selling mobile phones, smartphones, accessories, and mobile service plans."),
        OpticalStore("A shop that provides eyewear, such as prescription glasses, sunglasses, contact lenses, and related eye care products."),
        Pharmacy("Also called a drug store, a pharmacy is a specialized retail store where you can find a wide range of medicinal drugs, over-the-counter medications, and health-related products. It is a go-to destination for filling prescriptions, seeking professional advice from pharmacists, and purchasing various health and wellness items such as vitamins, first aid supplies, and personal care products."),
        HomeDecorStore("A retail establishment that sells decorative items, furnishings, lighting, and accessories for home interiors."),
        PartySupplyStore("A shop that offers party decorations, balloons, tableware, costumes, and other supplies for celebrations and events."),
        GardenCenter("A retail store specializing in plants, gardening tools, seeds, fertilizers, and outdoor decor for gardening enthusiasts."),
        MaternityStore("A store that caters to expectant mothers, offering maternity clothing, accessories, and products for prenatal and postnatal care."),
        CosmeticsStore("A retail outlet offering a wide range of beauty and skincare products, including makeup, perfumes, lotions, and haircare items."),
        LiquorStore("A shop that sells alcoholic beverages, including spirits, wines, beers, and mixers for various drink preferences."),
        BicycleShop("A retail location that sells bicycles, cycling gear, accessories, and provides repair and maintenance services."),
        HomeApplianceStore("A retail outlet that offers a wide selection of household appliances, such as refrigerators, washing machines, and ovens."),
        CheeseShop("A store that specializes in selling a variety of cheeses, often providing tastings and knowledgeable staff for recommendations."),
        CraftStore("A shop that sells crafting supplies, including fabrics, yarns, beads, paints, tools, and materials for DIY projects."),
        ThriftStore("A store that sells second-hand items, including clothing, furniture, books, and household goods, usually supporting charitable causes."),
        CoffeeTeaShop("A store that specializes in a wide range of teas and/or coffees, offering different varieties, blends, accessories, and sometimes, even tea ceremonies."),
        EyewearBoutique("A boutique-style store that offers a curated selection of high-end designer eyewear and premium optical services."),
        HandbagsStore("A retail store dedicated to selling fashionable handbags and related accessories like purses, clutches, and wallets. These stores offer a variety of designs and styles to suit different tastes and fashion preferences."),
        BabyStore("A shop that sells products and essentials for infants and young children, including clothing, strollers, toys, and nursery items."),
        CoffeeRoastery("A retail establishment that roasts and sells fresh coffee beans, often offering a variety of blends and single-origin options."),
        TobaccoShop("A store that sells tobacco products, including cigarettes, cigars, pipe tobacco, smoking accessories, and related items."),
        ArtSupplyStore("A shop that provides a wide range of art supplies, including paints, brushes, canvases, sketchbooks, and tools for artists.")

        private final String type;

        // Constructor
        ShopType(String type) {
            this.type = type;
        }

        // Getter method
        public String getType() {
            return type;
        }

    }

    public Shop(String address, int numberOfDepartments, ShopType type) {
        super(address);
        this.numberOfDepartments = numberOfDepartments;
    }

    public int getNumberOfDepartments() {
        return numberOfDepartments;
    }

    public void setNumberOfDepartments(int numberOfDepartments) {
        this.numberOfDepartments = numberOfDepartments;
    }

    @Override
    public void initializeFromString(String data) {
        // Пример: "address,numberOfDepartments"
        String[] parts = data.split(",");
        this.address = parts[0];
        this.numberOfDepartments = Integer.parseInt(parts[1]);
    }

    @Override
    public void printInfo() {
        System.out.println("Shop at " + address + " with " + numberOfDepartments + " departments.");
    }
}
