package com.agility.shopping.cart.dto;

/**
 * This class used to define views for admin and member user
 * Apply JSON Views to serialize/desialize objects, customize the views
 */
public class Views {

    public static class Member {
    }

    public static class Admin extends Member {
    }

    public static class ObjectList {
    }

    public static class ObjectDetail extends ObjectList {
    }
}
