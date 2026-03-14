package com.coder.springjwt.emuns.seller;

public enum OrderStatus_SR {

    NEW("Order Created"),

    OUT_FOR_PICKUP("Out for Pickup "),

    PICKED_UP("PickDone "),

    SHIPPED("Picked Shipment InScan from Manifest"),

    IN_TRANSIT("InTransit Shipment added in Bag"),

    REACHED_AT_DESTINATION_HUB("Reached at Destination Shipment BagOut From Bag"),

    OUT_FOR_DELIVERY("Out for Delivery Out for delivery: Ex..."),

    DELIVERED("Delivered");

    private final String suggestionText;

    OrderStatus_SR(String suggestionText) {
        this.suggestionText = suggestionText;
    }

    public String getSuggestionText() {
        return suggestionText;
    }

}
