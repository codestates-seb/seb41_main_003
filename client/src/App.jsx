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
import { useResetRecoilState } from 'recoil';
import axios from 'axios';
import Profile from './recoil/profile';
import { useEffect } from 'react';

const App = () => {
  const resetProfile = useResetRecoilState(Profile);

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

      if (
        status === 403 &&
        error.response.data.message === 'EXPIRED ACCESS TOKEN'
      ) {
        console.log('Access Token 재발급');
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
          location.href = '/login';
        }
      }
      return Promise.reject(error);
    }
  );

  axios.defaults.baseURL = process.env.REACT_APP_BASE_URL;

  useEffect(() => {
    if (!sessionStorage.getItem('authorization')) resetProfile();
  });

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
            <Route path="/tutorprofile/:profileId" element={<TutorProfile />} />
            <Route path="/tuteeprofile/:profileId" element={<TuteeProfile />} />
            <Route path="/userinfo" element={<UserInfo />} />
            <Route path="/addprofile" element={<AddProfile />} />
            <Route path="/editprofile" element={<EditProfile />} />
            <Route path="/message" element={<Message />} />
            <Route path="/myprofile" element={<MyProfile />} />
            <Route
              path="/journal/:tutoringId/:dateNoticeId"
              element={<Journal />}
            />
            <Route
              path="/editjournal/:dateNoticeId"
              element={<EditJournal />}
            />
            <Route path="/addjournal/:tutoringId" element={<AddJournal />} />
          </Routes>
        </div>
        <GlobalModal />
      </Router>
      <Footer />
    </div>
  );
};

export default App;
