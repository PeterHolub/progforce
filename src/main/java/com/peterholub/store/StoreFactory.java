package com.peterholub.store;

import com.peterholub.store.impl.Fozzy;
import com.peterholub.store.impl.Novus;

/**
 * Factory for stores
 */

public class StoreFactory {
    private static Novus novus;
    private static Fozzy fozzy;

    /**
     * Method to return Novus instance
     */
    public static Novus getNovusInstance() {

        if (novus != null) {
            return novus;
        }
        synchronized (StoreFactory.class) {

            if (novus == null) {

                novus = new Novus();
            }
        }
        return novus;
    }

    /**
     * Method to return Fozzy instance
     */
    public static Fozzy getFozzyInstance() {

        if (fozzy != null) {
            return fozzy;
        }
        synchronized (StoreFactory.class) {

            if (fozzy == null) {

                fozzy = new Fozzy();
            }
        }
        return fozzy;
    }
}
