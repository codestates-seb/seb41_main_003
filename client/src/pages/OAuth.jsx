import { useSetRecoilState } from 'recoil';
import Profile from '../recoil/profile';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';

const OAuth = () => {
  const setProfile = useSetRecoilState(Profile);
  const navigate = useNavigate();

  const urlParams = new URL(location.href).searchParams;
  const Authorization = urlParams.get('Authorization');
  const userStatus = urlParams.get('userStatus');
  const userId = urlParams.get('userId');

  useEffect(() => {
    sessionStorage.setItem('authorization', Authorization);
    sessionStorage.setItem('userId', userId);
    sessionStorage.setItem('userStatus', userStatus);

    setProfile((prev) => ({
      ...prev,
      isLogin: true,
      userStatus,
    }));

    if (userStatus === 'NONE') {
      navigate('/login');
      console.log('회원정보 입력 필요');
    } else {
      navigate('/');
    }
  });

  return;
};

export default OAuth;
