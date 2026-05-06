import { Link } from 'react-router-dom';

function Home() {
  return (
    <div className="py-5 text-center">
      <h1 className="display-4 fw-bold mb-4">Expert Care for Your Home</h1>
      <p className="lead mb-4">Connect with trusted professionals for cleaning, cooking, elderly care, and more.</p>
      
      <div className="d-flex justify-content-center gap-3">
        <Link to="/register" className="btn btn-primary btn-lg px-4">Get Started</Link>
        <Link to="/login" className="btn btn-outline-primary btn-lg px-4">Find Pros</Link>
      </div>
    </div>
  );
}

export default Home;
