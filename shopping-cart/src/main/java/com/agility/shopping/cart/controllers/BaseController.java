package com.agility.shopping.cart.controllers;

import com.agility.shopping.cart.configs.SecurityConfig;
import com.agility.shopping.cart.mappers.CartItemMapper;
import com.agility.shopping.cart.mappers.ProductMapper;
import com.agility.shopping.cart.mappers.ShoppingCartMapper;
import com.agility.shopping.cart.repositories.CartItemRepository;
import com.agility.shopping.cart.repositories.ProductRepository;
import com.agility.shopping.cart.repositories.ShoppingCartRepository;
import com.agility.shopping.cart.repositories.UserRepository;
import com.agility.shopping.cart.securities.JwtTokenService;
import com.agility.shopping.cart.securities.SecurityService;
import com.agility.shopping.cart.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

public abstract class BaseController {

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    protected JwtTokenService jwtTokenService;

    @Autowired
    protected SecurityService securityService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected SecurityConfig securityConfig;

    @Autowired
    protected ShoppingCartRepository shoppingCartRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected CartItemRepository cartItemRepository;

    @Autowired
    protected CartItemMapper cartItemMapper;

    @Autowired
    protected ShoppingCartService shoppingCartService;

    @Autowired
    protected ProductMapper productMapper;

    @Autowired
    protected ShoppingCartMapper shoppingCartMapper;

}
