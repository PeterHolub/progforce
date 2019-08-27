package com.peterholub.store;

import com.peterholub.store.impl.Fozzy;
import com.peterholub.store.impl.Novus;

/**
 * Factory for stores
 */

public class StoreFactoryMethod {
    private final static String NOVUS_TYPE = "Novus";
    private final static String FOZZY_TYPE = "Fozzy";
    private static Novus novus;
    private static Fozzy fozzy;

    /**
     * Method to return Concrete Store instance
     */
    public static AbstractStore getStore(String store) {
        if (NOVUS_TYPE.equalsIgnoreCase(store)) {
            if (novus != null) {
                return novus;
            }
            synchronized (StoreFactoryMethod.class) {
                if (novus == null) {
                    novus = new Novus();
                }
            }
            return novus;

        } else if (FOZZY_TYPE.equalsIgnoreCase(store)) {
            if (fozzy != null) {
                return fozzy;
            }
            synchronized (StoreFactoryMethod.class) {

                if (fozzy == null) {

                    fozzy = new Fozzy();
                }
            }
            return fozzy;
        }

        return null;
    }

}
