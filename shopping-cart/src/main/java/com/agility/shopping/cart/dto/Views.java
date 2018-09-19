package com.agility.shopping.cart.dto;

/**
 * This class used to define views for admin and member user
 * Apply JSON Views to serialize/desialize objects, customize the views
 */
public class Views {

    public interface Member {
    }

    public interface Admin extends Member {
    }

    public interface ObjectList {
    }

    public interface ObjectDetail extends ObjectList {
    }

    public interface MemberAndObjectDetail extends Member, ObjectDetail {
    }

}
