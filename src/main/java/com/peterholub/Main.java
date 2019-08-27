package com.peterholub;

import com.peterholub.entity.Product;
import com.peterholub.entity.ProductStatus;
import com.peterholub.store.Store;
import com.peterholub.store.StoreFactoryMethod;
import com.peterholub.store.impl.Fozzy;
import com.peterholub.store.impl.Novus;
import com.peterholub.util.SQLScriptRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private final static String FILENAME_INIT_SCRIPT = "init.sql";
    private final static String FILENAME_DESTROY_SCRIPT = "destroy.sql";
    private final static int HALPH_OF_PRODUCTS = 2;

    private final static int NOVUS_ID = 1;
    private final static int BAKERY_CATEGORY_ID = 1;
    private final static int FRUITS_AND_VEGETABLES_CATEGORY_ID = 2;

    private final static int FOZZY_ID = 2;
    private final static int DRINKS_CATEGORY_ID = 3;
    private final static int FROZEN_CATEGORY_ID = 4;

    private final static String NOVUS_TYPE = "Novus";
    private final static String FOZZY_TYPE = "Fozzy";

    //По поводу 2-х потоков, можно посмотреть на счет выноса параметров в абстракную таску имплементирующую  Runnable
    // и одну ее выполнять в разных потоках.Но на первый взгяд эта таска будет с кучей параметров
    //что тоже не очень читабельно. Возможно разбивавка на еще больше методов поможет.
    public static void main(String[] args) throws InterruptedException {
        //Initialization of database with values

        SQLScriptRunner.runScript(FILENAME_INIT_SCRIPT);
        Store novus = StoreFactoryMethod.getStore(NOVUS_TYPE);
        Store fozzy = StoreFactoryMethod.getStore(FOZZY_TYPE);

        Thread novusThread = new Thread(() -> {
            // Записать по 3-4 продукта в категории;
            novus.addListOfProducts(supplyProductsForNovus());
            //В какой-то из категорий изменить статусы всех товаров на «Absent»
            novus.changeProductStatus(ProductStatus.ABSENT, BAKERY_CATEGORY_ID);
            //Половине товаров, из оставшихся категорий, изменить статус на «Expected».
            List<Product> fruitsAndVegatables = novus.getAllProductsByStoreAndCategory(NOVUS_ID, FRUITS_AND_VEGETABLES_CATEGORY_ID);
            for (int i = 0; i < fruitsAndVegatables.size() / HALPH_OF_PRODUCTS; i++) {
                fruitsAndVegatables.get(i).setStatus(ProductStatus.EXPECTED);
            }
            novus.addListOfProducts(fruitsAndVegatables);
            //По товарам, что доступны увеличить цену на 20%;
            novus.changeProductPriceByStoreAndStatus(1.2, NOVUS_ID, ProductStatus.AVAILABLE);
        });

        Thread fozzyThread = new Thread(() -> {
            // Записать по 3-4 продукта в категории;
            fozzy.addListOfProducts(supplyProductsForFozzy());
            //В какой-то из категорий изменить статусы всех товаров на «Absent»
            fozzy.changeProductStatus(ProductStatus.ABSENT, DRINKS_CATEGORY_ID);
            //Половине товаров, из оставшихся категорий, изменить статус на «Expected».
            List<Product> frozen = fozzy.getAllProductsByStoreAndCategory(FOZZY_ID, FROZEN_CATEGORY_ID);

            for (int i = 0; i < frozen.size() / HALPH_OF_PRODUCTS; i++) {
                frozen.get(i).setStatus(ProductStatus.EXPECTED);
            }
            fozzy.addListOfProducts(frozen);
            //По товарам, что доступны увеличить цену на 20%;
            fozzy.changeProductPriceByStoreAndStatus(1.2, FOZZY_ID, ProductStatus.AVAILABLE);
        });


        novusThread.start();
        Thread.sleep(10000);
        fozzyThread.start();
        novusThread.join();
        fozzyThread.join();
        System.out.println("Process finished for 2 Threads");
    }

    private static List<Product> supplyProductsForNovus() {
        Product loaves = new Product(BigDecimal.valueOf(11.99), ProductStatus.AVAILABLE, "Loaves", BAKERY_CATEGORY_ID, NOVUS_ID);
        Product branBread = new Product(BigDecimal.valueOf(14.45), ProductStatus.AVAILABLE, "Bran bread", BAKERY_CATEGORY_ID, NOVUS_ID);
        Product pita = new Product(BigDecimal.valueOf(13.99), ProductStatus.AVAILABLE, "Pita", BAKERY_CATEGORY_ID, NOVUS_ID);

        Product melons = new Product(BigDecimal.valueOf(26.99), ProductStatus.AVAILABLE, "Melons", FRUITS_AND_VEGETABLES_CATEGORY_ID, NOVUS_ID);
        Product waterMelons = new Product(BigDecimal.valueOf(14.45), ProductStatus.AVAILABLE, "Water melons", FRUITS_AND_VEGETABLES_CATEGORY_ID, NOVUS_ID);
        Product grapes = new Product(BigDecimal.valueOf(58.99), ProductStatus.AVAILABLE, "Grapes", FRUITS_AND_VEGETABLES_CATEGORY_ID, NOVUS_ID);

        return new ArrayList<>(Arrays.asList(loaves, branBread, pita, melons, waterMelons, grapes));

    }

    private static List<Product> supplyProductsForFozzy() {
        Product chardonnay = new Product(BigDecimal.valueOf(90.99), ProductStatus.AVAILABLE, "Chardonnay", DRINKS_CATEGORY_ID, FOZZY_ID);
        Product saperavi = new Product(BigDecimal.valueOf(120.99), ProductStatus.AVAILABLE, "Saperavi", DRINKS_CATEGORY_ID, FOZZY_ID);
        Product muscat = new Product(BigDecimal.valueOf(120.99), ProductStatus.AVAILABLE, "Muscat", DRINKS_CATEGORY_ID, FOZZY_ID);

        Product iceCream = new Product(BigDecimal.valueOf(10.99), ProductStatus.AVAILABLE, "Ice Cream", FROZEN_CATEGORY_ID, FOZZY_ID);
        Product mushrooms = new Product(BigDecimal.valueOf(45.99), ProductStatus.AVAILABLE, "Mushrooms", FROZEN_CATEGORY_ID, FOZZY_ID);
        Product pork = new Product(BigDecimal.valueOf(90.99), ProductStatus.AVAILABLE, "Pork", FROZEN_CATEGORY_ID, FOZZY_ID);

        return new ArrayList<>(Arrays.asList(chardonnay, saperavi, muscat, iceCream, mushrooms, pork));
    }
}
