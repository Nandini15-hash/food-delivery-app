import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const Cart = () => {
  const [cartItems, setCartItems] = useState([
    { id: 1, name: 'Margherita Pizza', price: 12.99, quantity: 2 },
    { id: 2, name: 'Garlic Bread', price: 4.99, quantity: 1 }
  ]);

  const updateQuantity = (id, newQuantity) => {
    if (newQuantity === 0) {
      setCartItems(cartItems.filter(item => item.id !== id));
    } else {
      setCartItems(cartItems.map(item =>
        item.id === id ? { ...item, quantity: newQuantity } : item
      ));
    }
  };

  const getTotalPrice = () => {
    return cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
  };

  return (
    <div className="container">
      <h1>Your Shopping Cart</h1>
      
      {cartItems.length === 0 ? (
        <div style={{textAlign: 'center', marginTop: '50px'}}>
          <p>Your cart is empty</p>
          <Link to="/" style={{color: '#ff6b6b'}}>Browse Restaurants</Link>
        </div>
      ) : (
        <>
          <div className="cart-items">
            {cartItems.map(item => (
              <div key={item.id} className="cart-item">
                <div className="item-details">
                  <h3>{item.name}</h3>
                  <p></p>
                </div>
                <div className="quantity-controls">
                  <button onClick={() => updateQuantity(item.id, item.quantity - 1)}>-</button>
                  <span>{item.quantity}</span>
                  <button onClick={() => updateQuantity(item.id, item.quantity + 1)}>+</button>
                </div>
                <p className="item-total"></p>
              </div>
            ))}
          </div>

          <div className="cart-summary">
            <div className="total">
              <h3>Total: </h3>
            </div>
            <button className="checkout-btn">
              Proceed to Checkout
            </button>
            <Link to="/" className="continue-shopping">Add more items</Link>
          </div>
        </>
      )}
    </div>
  );
};

export default Cart;
