import { useSetRecoilState } from 'recoil';
import Profile from '../recoil/profile';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import ModalState from '../recoil/modal';

const OAuth = () => {
  const setProfile = useSetRecoilState(Profile);
  const setModal = useSetRecoilState(ModalState);
  const navigate = useNavigate();

  const urlParams = new URL(location.href).searchParams;
  const Authorization = urlParams.get('Authorization');
  const userStatus = urlParams.get('userStatus');
  const userId = urlParams.get('userId');
  const code = urlParams.get('code');
  const message = urlParams.get('message');

  useEffect(() => {
    if (code === '409' && message === 'USER EMAIL EXISTS') {
      navigate('/login');
      setModal({
        isOpen: true,
        modalType: 'alert',
        props: {
          text: '이미 일반회원으로 가입된 계정입니다.\n일반 로그인을 이용해주세요.',
        },
      });
      return;
    }

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
