package com.basic.leanring.java.generic;

import com.basic.leanring.java.generic.model.Fruit;

/**
 * @author sunzihan
 * @version $Id: Plate.java V 0.1 2/23/17 12:06 sunzihan EXP $
 */
public class Plate<T> {

   private T item;

   protected Plate(T  t){
       this.item = t;
   }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}

