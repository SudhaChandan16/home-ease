import { useState, useEffect } from 'react';
import api from '../api';

function UserDashboard() {
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    fetchDashboard();
  }, []);

  const fetchDashboard = async () => {
    try {
      const res = await api.get('/user/dashboard');
      setBookings(res.data);
    } catch (err) {
      console.error('Failed to fetch dashboard', err);
    }
  };

  return (
    <div>
      <h2>User Dashboard</h2>
      <div className="card mt-4">
        <div className="card-header">My Bookings</div>
        <div className="card-body">
          {bookings.length === 0 ? (
            <p>No bookings yet.</p>
          ) : (
            <ul className="list-group">
              {bookings.map(booking => (
                <li key={booking.id} className="list-group-item d-flex justify-content-between align-items-center">
                  <div>
                    <strong>{booking.provider?.user?.name}</strong> - {booking.status}
                    <br />
                    <small>{booking.scheduledTime}</small>
                  </div>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
    </div>
  );
}

export default UserDashboard;
