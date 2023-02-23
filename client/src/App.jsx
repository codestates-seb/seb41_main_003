import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from 'react-router-dom';
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
import Error from './pages/Error';
import { GlobalModal } from './components/modal/GlobalModal';
import { useResetRecoilState, useSetRecoilState } from 'recoil';
import axios from 'axios';
import Profile from './recoil/profile';
import { useEffect, useState } from 'react';
import OAuth from './pages/OAuth';
import ModalState from './recoil/modal';
import ScrollToTop from './util/ScrollToTop';
import Offline from './components/Offline';

const App = () => {
  const [isOffline, setIsOffline] = useState(!navigator.onLine);
  const resetProfile = useResetRecoilState(Profile);
  const setModal = useSetRecoilState(ModalState);

  axios.interceptors.request.use(
    (config) => {
      config.headers.Authorization = sessionStorage.getItem('authorization');
      return config;
    },
    (err) => {
      console.log(err);
      return Promise.reject(err);
    }
  );

  axios.interceptors.response.use(
    (response) => response,
    async (error) => {
      const {
        config,
        response: { status },
      } = error;

      if (config.sent) return Promise.reject(error);

      if (status === 500)
        setModal({
          isOpen: true,
          modalType: 'alert',
          props: {
            text: `서버 오류가 발생했습니다. 
            잠시 후 다시 시도해주세요.`,
          },
        });

      if (status === 404)
        setModal({
          isOpen: true,
          modalType: 'alert',
          props: {
            text: `알 수 없는 요청입니다. 
            관리자에게 문의하세요.`,
          },
        });

      if (
        status === 403 &&
        error.response.data.message === 'EXPIRED ACCESS TOKEN'
      ) {
        const originReq = config;
        originReq.sent = true;

        const userId = sessionStorage.getItem('userId');

        try {
          const {
            data: { authorization },
          } = await axios.get(`/auth/reissue-token/${userId}`);

          sessionStorage.setItem('authorization', authorization);

          originReq.headers.Authorization = authorization;
          return axios(originReq);
        } catch (err) {
          console.log(err);
          sessionStorage.clear();
          resetProfile();
          return <Navigate to="/login" />;
        }
      }
      return Promise.reject(error);
    }
  );

  axios.defaults.baseURL = process.env.REACT_APP_BASE_URL;
  axios.defaults.withCredentials = true;

  useEffect(() => {
    if (!sessionStorage.getItem('authorization')) resetProfile();

    window.addEventListener('online', () => {
      setIsOffline(false);
    });

    window.addEventListener('offline', () => {
      setIsOffline(true);
    });

    return () => {
      window.removeEventListener('online', () => {
        setIsOffline(false);
      });

      window.removeEventListener('offline', () => {
        setIsOffline(true);
      });
    };
  }, []);

  return (
    <div className="app">
      <Router basename="/">
        <ScrollToTop />
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
            <Route path="/journal" element={<Journal />} />
            <Route path="/editjournal" element={<EditJournal />} />
            <Route path="/addjournal" element={<AddJournal />} />
            <Route path="/auth" element={<OAuth />} />
            <Route path="/*" element={<Error />} />
          </Routes>
        </div>
        <GlobalModal />
        {isOffline && <Offline />}
      </Router>
      <Footer />
    </div>
  );
};

export default App;
