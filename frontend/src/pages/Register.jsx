import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api';

function Register() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    mobile: '',
    address: '',
    isProvider: false,
    category: 'CLEANING',
  });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const endpoint = formData.isProvider ? '/auth/register/provider' : '/auth/register';
      await api.post(endpoint, formData);
      navigate('/login');
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed.');
    }
  };

  return (
    <div className="row justify-content-center">
      <div className="col-md-6">
        <div className="card shadow-sm">
          <div className="card-body p-5">
            <h2 className="text-center mb-4">Register</h2>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleRegister}>
              <div className="mb-3">
                <label className="form-label">Name</label>
                <input type="text" name="name" className="form-control" value={formData.name} onChange={handleChange} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Email</label>
                <input type="email" name="email" className="form-control" value={formData.email} onChange={handleChange} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Password</label>
                <input type="password" name="password" className="form-control" value={formData.password} onChange={handleChange} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Mobile</label>
                <input type="text" name="mobile" className="form-control" value={formData.mobile} onChange={handleChange} required />
              </div>
              
              <div className="mb-3 form-check">
                <input type="checkbox" name="isProvider" className="form-check-input" id="isProvider" checked={formData.isProvider} onChange={handleChange} />
                <label className="form-check-label" htmlFor="isProvider">Register as Provider</label>
              </div>

              {formData.isProvider && (
                <div className="mb-3">
                  <label className="form-label">Category</label>
                  <select name="category" className="form-select" value={formData.category} onChange={handleChange}>
                    <option value="CLEANING">Cleaning</option>
                    <option value="COOKING">Cooking</option>
                    <option value="ELDER_CARE">Elder Care</option>
                    <option value="MAINTENANCE">Maintenance</option>
                  </select>
                </div>
              )}

              <button type="submit" className="btn btn-primary w-100">Register</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Register;
