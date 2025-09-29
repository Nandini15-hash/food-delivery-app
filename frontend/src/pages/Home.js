import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
  const restaurants = [
    { id: 1, name: 'Pizza Palace', cuisine: 'Italian', rating: 4.5, deliveryTime: '25-35 min' },
    { id: 2, name: 'Burger Hub', cuisine: 'American', rating: 4.2, deliveryTime: '20-30 min' },
    { id: 3, name: 'Sushi Spot', cuisine: 'Japanese', rating: 4.7, deliveryTime: '30-40 min' },
    { id: 4, name: 'Taco Fiesta', cuisine: 'Mexican', rating: 4.3, deliveryTime: '15-25 min' },
  ];

  return (
    <div className="container">
      <div className="hero">
        <h1>Craving something delicious?</h1>
        <p>Get your favorite food delivered to your doorstep in minutes</p>
      </div>

      <div className="restaurants">
        <h2>Popular Restaurants</h2>
        <div className="restaurant-list">
          {restaurants.map(restaurant => (
            <div key={restaurant.id} className="restaurant-card">
              <h3>{restaurant.name}</h3>
              <p style={{color: '#666', marginBottom: '0.5rem'}}>{restaurant.cuisine}</p>
              <div style={{display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
                <span>⭐ {restaurant.rating}</span>
                <span>{restaurant.deliveryTime}</span>
              </div>
              <Link to={'/restaurant/' + restaurant.id}>View Menu</Link>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Home;
