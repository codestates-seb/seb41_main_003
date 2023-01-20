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
import { useResetRecoilState } from 'recoil';
import axios from 'axios';
import Profile from './recoil/profile';

const App = () => {
  const footerRef = useRef(null);
  const resetProfile = useResetRecoilState(Profile);

  axios.interceptors.request.use(
    (config) => {
      config.headers.Authorization =
        sessionStorage.getItem('authorization') ||
        localStorage.getItem('authorization');
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
      console.log(error);

      const {
        config,
        response: { status },
      } = error;

      if (config.sent) return Promise.reject(error);

      if (
        status === 403 &&
        error.response.data.message === 'EXPIRED ACCESS TOKEN'
      ) {
        const originReq = config;
        originReq.sent = true;

        const userId = await (sessionStorage.getItem('userId') ||
          localStorage.getItem('userId'));
        try {
          const {
            data: { authorization },
          } = await axios.get(`/auth/reissue-token/${userId}`);

          localStorage.setItem('authorization', authorization);
          sessionStorage.setItem('authorization', authorization);

          originReq.headers.Authorization = authorization;
          return axios(originReq);
        } catch (err) {
          console.log(err);
          localStorage.clear();
          sessionStorage.clear();
          resetProfile();
          location.href = '/login';
        }
      }
      return Promise.reject(error);
    }
  );

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
