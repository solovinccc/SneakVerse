import React, { useState } from 'react';
import GuestLanding from './components/GuestLanding';
import UserStore from './components/UserStore';
import UserProfile from './components/UserProfile';

function App() {
  const [token, setToken] = useState(() => {
    const t = localStorage.getItem('token');
    return t && t !== 'undefined' && t !== 'null' ? t : null;
  });
  const [userId, setUserId] = useState(() => {
    const u = localStorage.getItem('userId');
    return u && u !== 'undefined' && u !== 'null' ? u : null;
  });
  const [currentPage, setCurrentPage] = useState('store');

  const handleLoginSuccess = (newToken, newUserId) => {
    localStorage.setItem('token', newToken);
    localStorage.setItem('userId', String(newUserId));
    setToken(newToken);
    setUserId(String(newUserId));
    setCurrentPage('store');
  };

  const handleLogout = () => {
    localStorage.clear();
    setToken(null);
    setUserId(null);
    setCurrentPage('store');
  };

  if (!token) {
    return <GuestLanding onLoginSuccess={handleLoginSuccess} />;
  }

  if (currentPage === 'profile') {
    return (
      <UserProfile 
        userId={userId} 
        token={token} 
        onBack={() => setCurrentPage('store')} 
      />
    );
  }

  return (
    <UserStore
      userId={userId}
      userEmail={localStorage.getItem('userEmail')}
      token={token}
      onLogout={handleLogout}
      onNavigateProfile={() => setCurrentPage('profile')}
    />
  );
}

export default App;