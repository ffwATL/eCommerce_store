package com.ffwatl.admin.offer.service;

import com.ffwatl.admin.offer.service.discount.PromotableOrderItem;

import java.util.Comparator;



public class OrderItemPriceComparator implements Comparator<PromotableOrderItem> {

    private boolean sortBySalePrice;

    public OrderItemPriceComparator(boolean sortBySalePrice){
        this.sortBySalePrice = sortBySalePrice;
    }

    @Override
    public int compare(PromotableOrderItem o1, PromotableOrderItem o2) {


        int price = o1.getPriceBeforeAdjustments(sortBySalePrice);
        int price2 = o2.getPriceBeforeAdjustments(sortBySalePrice);

        int result = 1;
        if(price < price2) {
            result = -1;
        }else if(price == price2){
            result = 0;
        }
        // highest amount first
        return result;
    }
}
