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
import TestPage from './pages/TestPage';
import AdminModal from './components/AdminModal';
import EditJournal from './pages/EditJournal';
import Journal from './pages/Journal';
import { useState } from 'react';
import { GlobalModal } from './components/modal/GlobalModal';

const App = () => {
  const [isOpenAdminModal, setIsOpenAdminModal] = useState(false);
  return (
    <div className="app">
      <Router basename="/">
        <Header />
        <div className="content">
          <Routes>
            <Route path="/" element={<TutorList />} />
            <Route path="/tutorlist" element={<TutorList />} />
            <Route path="/tuteelist" element={<TuteeList />} />
            <Route path="/tutoringlist" element={<TutoringList />} />
            <Route path="/admin" element={<Admin />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/tutoring" element={<Tutoring />} />
            <Route path="/tutorprofile" element={<TutorProfile />} />
            <Route path="/tuteeprofile" element={<TuteeProfile />} />
            <Route path="/userinfo" element={<UserInfo />} />
            <Route path="/addprofile" element={<AddProfile />} />
            <Route path="/editprofile" element={<EditProfile />} />
            <Route path="/message" element={<Message />} />
            <Route path="/myprofile" element={<MyProfile />} />
            <Route path="/test" element={<TestPage />} />
            <Route path="/journal" element={<Journal />} />
            <Route path="/test" element={<TestPage />} />
            <Route path="/editjournal" element={<EditJournal />} />
          </Routes>
          {isOpenAdminModal && (
            <AdminModal setIsOpenAdminModal={setIsOpenAdminModal} />
          )}
        </div>
      </Router>
      <Footer />
      <GlobalModal />
    </div>
  );
};

export default App;
