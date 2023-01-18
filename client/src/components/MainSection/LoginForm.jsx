import styles from './LoginForm.module.css';
import { CheckBox, TextInput } from '../Input';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import Profile from '../../recoil/profile';
import ModalState from '../../recoil/modal';
import validation from '../../util/validation';

const initialLogindata = {
  username: '',
  password: '',
};

//TODO: 로그인 성공, 실패에 따라 (상태 번호 : 401 등)
//로그인 버튼 위에 '아이디, 비밀번호가 올바르지 않습니다' 등의 문구 띄우도록 하고
//아이디와 비밀번호 아래에 있는 문구는 형식에 대한 validation 문구가 될듯

const LoginForm = () => {
  const [loginData, setLoginData] = useState(initialLogindata);
  //아이디 저장 체크
  const [isIdChecked, setIsIdChecked] = useState(false);
  //자동 로그인 설정 체크
  const [isAutoLoginChecked, setIsAutoLoginChecked] = useState(false);
  const [isFail, setIsFail] = useState(0);

  const setProfile = useSetRecoilState(Profile);

  const navigate = useNavigate();

  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const statusNoneProps = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: `서비스 이용을 위해 회원 정보 입력이 필요합니다. 
      회원 정보를 입력하시겠습니까?

      (입력이 되지 않으면 정상적인 서비스가 불가합니다.)`,
      modalHandler: () => {
        navigate('/userinfo');
        resetModal();
      },
    },
  };

  const inputHandler = (e) => {
    const { id, value } = e.target;
    setLoginData({ ...loginData, [id]: value });
  };

  const submitHandler = async (e) => {
    e.preventDefault();
    if (
      validation(loginData.username, 'email') &&
      validation(loginData.password, 'password')
    ) {
      await axios
        .post(process.env.REACT_APP_BASE_URL + '/auth/login', loginData)
        .then(({ data: res }) => {
          if (isIdChecked) localStorage.setItem('saveId', loginData.username);
          if (isAutoLoginChecked) {
            localStorage.setItem('authorization', res.data.Authorization);
            localStorage.setItem('userId', res.data.userId);
            localStorage.setItem('userStatus', res.data.userStatus);
          } else {
            sessionStorage.setItem('authorization', res.data.Authorization);
            sessionStorage.setItem('userId', res.data.userId);
            sessionStorage.setItem('userStatus', res.data.userStatus);
          }
          console.log('로그인 완료');
          setProfile((prev) => ({
            ...prev,
            isLogin: true,
            userStatus: res.data.userStatus,
          }));
          if (res.data.userStatus === 'NONE') {
            console.log('회원정보 입력 필요');
            setModal(statusNoneProps);
          } else {
            window.location.href = '/';
          }
        })
        .catch(({ response }) => {
          if (response.status === 401) {
            console.log(response);
            console.log('비밀번호 오류');
            setIsFail(401);
          }
        });
    } else setIsFail(400);
  };

  return (
    <div className={styles.loginContainer}>
      <span className={styles.loginText}>로그인 후 이용하실 수 있습니다.</span>
      <form onSubmit={submitHandler}>
        <div className={styles.inputArea}>
          <TextInput
            id="username"
            placeHolder="이메일"
            type="email"
            value={loginData.username}
            handler={inputHandler}
          />
          <span>
            {!(loginData.username.length === 0) &&
              !validation(loginData.username, 'email') &&
              '이메일 형식으로 입력해야 합니다.'}
          </span>
          <TextInput
            id="password"
            placeHolder="비밀번호"
            type="password"
            value={loginData.password}
            handler={inputHandler}
          />
          <span>
            {!(loginData.password.length === 0) &&
              !validation(loginData.password, 'password') &&
              '비밀번호는 영문/숫자 조합으로 8~16자리여야 합니다.'}
          </span>
        </div>
        <div className={styles.checkboxContainer}>
          <CheckBox
            id="아이디저장"
            value={isIdChecked}
            handler={setIsIdChecked}
          />
          <span> 아이디 저장 </span>
          <CheckBox
            id="자동로그인"
            value={isAutoLoginChecked}
            handler={setIsAutoLoginChecked}
          />
          <span> 자동 로그인 </span>
        </div>
        <div className={styles.buttonContainer}>
          <span className={styles.failAuth}>
            {isFail === 401 && '아이디 또는 비밀번호가 틀렸습니다.'}
            {isFail === 400 && '오류가 발생했습니다.'}
          </span>
          <button type="submit" className={styles.loginButton}>
            로그인
          </button>
          <button type="button" className={styles.kakaoLoginButton}>
            <svg
              width="22"
              height="21"
              viewBox="0 0 22 21"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <g clipPath="url(#clip0_16_12521)">
                <path
                  fillRule="evenodd"
                  clipRule="evenodd"
                  d="M11 0C4.92433 0 0 3.88895 0 8.6925C0 11.6792 1.90484 14.3111 4.80517 15.8729L3.58295 20.4338C3.56012 20.524 3.56483 20.619 3.59648 20.7064C3.62813 20.7937 3.68519 20.8691 3.76005 20.9225C3.83491 20.976 3.92401 21.0049 4.01542 21.0054C4.10684 21.0059 4.19624 20.9779 4.27167 20.9253L9.62134 17.3165C10.0723 17.36 10.5313 17.3849 10.9982 17.3849C17.0732 17.3849 21.9982 13.4961 21.9982 8.6925C21.9982 3.88895 17.0732 0 10.9982 0"
                  fill="black"
                  fillOpacity="0.9"
                />
              </g>
              <defs>
                <clipPath id="clip0_16_12521">
                  <rect width="22" height="21" fill="white" />
                </clipPath>
              </defs>
            </svg>
            <span>카카오 로그인</span>
          </button>
          <button type="button" className={styles.googleLoginButton}>
            <svg
              width="18"
              height="18"
              viewBox="0 0 18 18"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                fillRule="evenodd"
                clipRule="evenodd"
                d="M17.64 9.20456C17.64 8.56637 17.5827 7.95274 17.4764 7.36365H9V10.845H13.8436C13.635 11.97 13.0009 12.9232 12.0477 13.5614V15.8196H14.9564C16.6582 14.2527 17.64 11.9455 17.64 9.20456Z"
                fill="#4285F4"
              />
              <path
                fillRule="evenodd"
                clipRule="evenodd"
                d="M9 18C11.43 18 13.4673 17.1941 14.9564 15.8195L12.0477 13.5613C11.2418 14.1013 10.2109 14.4204 9 14.4204C6.65591 14.4204 4.67182 12.8372 3.96409 10.71H0.957275V13.0418C2.43818 15.9831 5.48182 18 9 18Z"
                fill="#34A853"
              />
              <path
                fillRule="evenodd"
                clipRule="evenodd"
                d="M3.96409 10.7099C3.78409 10.1699 3.68182 9.59313 3.68182 8.99995C3.68182 8.40677 3.78409 7.82995 3.96409 7.28995V4.95813H0.957273C0.347727 6.17313 0 7.54768 0 8.99995C0 10.4522 0.347727 11.8268 0.957273 13.0418L3.96409 10.7099Z"
                fill="#FBBC05"
              />
              <path
                fillRule="evenodd"
                clipRule="evenodd"
                d="M9 3.57955C10.3214 3.57955 11.5077 4.03364 12.4405 4.92545L15.0218 2.34409C13.4632 0.891818 11.4259 0 9 0C5.48182 0 2.43818 2.01682 0.957275 4.95818L3.96409 7.29C4.67182 5.16273 6.65591 3.57955 9 3.57955Z"
                fill="#EA4335"
              />
            </svg>
            <span>Google 계정으로 로그인</span>
          </button>
          <span>회원이 아니신가요?</span>
          <button
            type="button"
            className={styles.signupButton}
            onClick={() => navigate('/signup')}
          >
            회원가입
          </button>
        </div>
      </form>
    </div>
  );
};

export default LoginForm;
