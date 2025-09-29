import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <nav>
      <Link to="/" style={{fontSize: '1.5rem', fontWeight: 'bold'}}>
        🍕 FoodDelivery
      </Link>
      <div style={{marginLeft: 'auto', display: 'flex', gap: '15px'}}>
        <Link to="/">Home</Link>
        <Link to="/orders">Orders</Link>
        <Link to="/cart">Cart</Link>
        <Link to="/login">Login</Link>
        <Link to="/register">Register</Link>
      </div>
    </nav>
  );
};

export default Navbar;
