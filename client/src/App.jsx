import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import TutorList from './pages/TutorList';
import TuteeList from './pages/TuteeList';
import TutoringList from './pages/TutoringList';
import Admin from './pages/Admin';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import Tutoring from './pages/Tutoring';
import TutorProfile from './pages/TutorProfile';
import TuteeProfile from './pages/TuteeProfile';
import UserInfo from './pages/UserInfo';
import AddProfile from './pages/AddProfile';
import EditProfile from './pages/EditProfile';
import Message from './pages/Message';
import MyProfile from './pages/MyProfile';
import Header from './components/Header';
import Footer from './components/Footer';
import EditJournal from './pages/EditJournal';
import Journal from './pages/Journal';
import AddJournal from './pages/AddJournal';
import { GlobalModal } from './components/modal/GlobalModal';
import { useRef } from 'react';
import axios from 'axios';

const App = () => {
  const footerRef = useRef(null);

  axios.defaults.headers.common['Authorization'] =
    sessionStorage.getItem('authorization') ||
    localStorage.getItem('authorization');

  axios.defaults.baseURL = process.env.REACT_APP_BASE_URL;

  return (
    <div className="app">
      <Router basename="/">
        <Header />
        <div className="content">
          <Routes>
            <Route path="/" element={<TutorList footerRef={footerRef} />} />
            <Route
              path="/tutorlist"
              element={<TutorList footerRef={footerRef} />}
            />
            <Route
              path="/tuteelist"
              element={<TuteeList footerRef={footerRef} />}
            />
            <Route path="/tutoringlist" element={<TutoringList />} />
            <Route path="/admin" element={<Admin />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/tutoring" element={<Tutoring />} />
            <Route path="/tutorprofile/:profileId" element={<TutorProfile />} />
            <Route path="/tuteeprofile/:profileId" element={<TuteeProfile />} />
            <Route path="/userinfo" element={<UserInfo />} />
            <Route path="/addprofile" element={<AddProfile />} />
            <Route path="/editprofile/:profileId" element={<EditProfile />} />
            <Route path="/message/:profileId" element={<Message />} />
            <Route path="/myprofile/:profileId" element={<MyProfile />} />
            <Route path="/journal" element={<Journal />} />
            <Route path="/editjournal" element={<EditJournal />} />
            <Route path="/addjournal" element={<AddJournal />} />
          </Routes>
        </div>
        <GlobalModal />
      </Router>
      <Footer ref={footerRef} />
    </div>
  );
};

export default App;
