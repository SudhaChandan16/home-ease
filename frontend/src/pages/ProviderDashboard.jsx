import { useState, useEffect } from 'react';
import api from '../api';

function ProviderDashboard() {
  const [data, setData] = useState({ provider: null, bookings: [] });

  useEffect(() => {
    fetchDashboard();
  }, []);

  const fetchDashboard = async () => {
    try {
      const res = await api.get('/provider/dashboard');
      setData(res.data);
    } catch (err) {
      console.error('Failed to fetch provider dashboard', err);
    }
  };

  const toggleAvailability = async () => {
    try {
      await api.post(`/provider/availability?isAvailable=${!data.provider?.available}`);
      fetchDashboard();
    } catch (err) {
      console.error('Failed to update availability', err);
    }
  };

  if (!data.provider) return <div>Loading...</div>;

  return (
    <div>
      <h2>Provider Dashboard</h2>
      
      <div className="card mt-4 mb-4">
        <div className="card-body">
          <h5 className="card-title">Profile Status</h5>
          <p>Status: <span className={data.provider.available ? 'text-success fw-bold' : 'text-danger fw-bold'}>
            {data.provider.available ? 'Available' : 'Unavailable'}
          </span></p>
          <button className="btn btn-outline-primary" onClick={toggleAvailability}>
            Toggle Availability
          </button>
        </div>
      </div>

      <div className="card">
        <div className="card-header">Service Requests</div>
        <div className="card-body">
          {data.bookings.length === 0 ? (
            <p>No requests yet.</p>
          ) : (
            <ul className="list-group">
              {data.bookings.map(booking => (
                <li key={booking.id} className="list-group-item d-flex justify-content-between align-items-center">
                  <div>
                    <strong>{booking.user?.name}</strong> - {booking.status}
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

export default ProviderDashboard;
