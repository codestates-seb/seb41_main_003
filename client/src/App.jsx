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
import EditJournal from './pages/EditJournal';
import Journal from './pages/Journal';
import AddJournal from './pages/AddJournal';
import { GlobalModal } from './components/modal/GlobalModal';
import { useEffect, useRef } from 'react';
import { useRecoilValue, useSetRecoilState, useResetRecoilState } from 'recoil';
import Profile from './recoil/profile';
import ModalState from './recoil/modal';

const App = () => {
  const footerRef = useRef(null);

  const profile = useRecoilValue(Profile);
  const resetProfile = useResetRecoilState(Profile);

  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const statusNoneProps = {
    isOpen: true,
    modalType: 'bothHandler',
    props: {
      text: `서비스 이용을 위해 회원 정보 입력이 필요합니다. 
      회원 정보를 입력하시겠습니까?

      (입력이 되지 않으면 정상적인 서비스가 불가하여,
        취소 시 로그아웃 처리됩니다.)`,
      modalHandler: (e) => {
        const { name } = e.target;
        if (name === 'yes') {
          resetModal();
          location.href = '/userinfo';
        } else {
          localStorage.clear();
          sessionStorage.clear();
          resetProfile();
          resetModal();
        }
      },
    },
  };
  useEffect(() => {
    console.log(location.pathname);
    if (profile.userStatus === 'NONE' && location.pathname !== '/userinfo')
      setModal(statusNoneProps);
    if (profile.isLogin === true && profile.profileId === 0)
      setModal({
        isOpen: true,
        modalType: 'admin',
      });
  });
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
