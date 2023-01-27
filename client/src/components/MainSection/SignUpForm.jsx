import styles from './SignUpForm.module.css';
import { LabelTextInput, TextInput } from '../Input';
import { useState, useEffect } from 'react';
import validation from '../../util/validation';
import axios from 'axios';
import ModalState from '../../recoil/modal.js';
import { useResetRecoilState, useSetRecoilState } from 'recoil';
import { useNavigate } from 'react-router-dom';

const initialSignupdata = {
  nickName: '',
  email: '',
  password: '',
  passwordConfirm: '',
};

const SignUpForm = () => {
  const [signupData, setSignupData] = useState(initialSignupdata);
  const [confirmPassword, setConfirmPassword] = useState(false);

  const navigate = useNavigate();

  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const inputHandler = (e) => {
    const { id, value } = e.target;
    setSignupData({ ...signupData, [id]: value });
  };

  const confirmHandler = () => {
    if (signupData.password === signupData.passwordConfirm) {
      setConfirmPassword(true);
    } else {
      setConfirmPassword(false);
    }
  };

  const signUpconfirmProps = {
    isOpen: true,
    modalType: 'handlerAlert',
    backDropHandle: true,
    props: {
      text: '회원 가입이 완료되었습니다!',
      modalHandler: () => {
        navigate('/login');
        resetModal();
      },
    },
  };

  const alert = {
    isOpen: true,
    modalType: 'alert',
    props: {
      text: '이미 가입된 이메일입니다.\n\n카카오/구글 계정으로 로그인 한 적이 있으시다면\n해당 계정으로 로그인 해주세요.',
    },
  };

  useEffect(() => {
    confirmHandler();
  }, [signupData.passwordConfirm, signupData.password]);

  const postSignUpData = async () => {
    await axios
      .post(`/users`, {
        ...signupData,
      })
      .then(() => {
        setModal(signUpconfirmProps);
      })
      .catch(({ response }) => {
        if (
          response.data.status === 409 &&
          response.data.message === 'USER EMAIL EXISTS'
        )
          setModal(alert);
      });
  };

  const signUpVali = {
    isOpen: true,
    modalType: 'confirm',
    backDropHandle: true,
    props: {
      text: '이 정보로 회원 가입하시겠습니까?',
      modalHandler: () => {
        postSignUpData();
      },
    },
  };

  const submitHandler = (e) => {
    e.preventDefault();
    if (
      validation(signupData.nickName, 'name') &&
      validation(signupData.email, 'email') &&
      validation(signupData.password, 'password') &&
      confirmPassword
    ) {
      setModal(signUpVali);
    }
  };

  return (
    <article className={styles.signUpContainer}>
      <h2 className={styles.signupText}>회원가입</h2>
      <span className={styles.required}>
        <span className={styles.requiredIcon} />은 필수 입력 사항입니다.
      </span>
      <form id="signUp" className={styles.inputArea} onSubmit={submitHandler}>
        <LabelTextInput
          id="nickName"
          name="닉네임"
          placeHolder="닉네임"
          type="text"
          value={signupData.nickName}
          handler={inputHandler}
          required
        />
        <span
          className={
            validation(signupData.nickName, 'name') && styles.validationText
          }
        >
          이름은 한글 2~6자, 영어 4~12글자까지 쓸 수 있습니다.
        </span>
        <LabelTextInput
          id="email"
          name="이메일"
          placeHolder="이메일"
          type="email"
          value={signupData.email}
          handler={inputHandler}
          required
        />
        <span
          className={
            validation(signupData.email, 'email') && styles.validationText
          }
        >
          유효하지 않은 이메일 형식입니다.
        </span>
        <LabelTextInput
          id="password"
          name="비밀번호"
          placeHolder="비밀번호"
          type="password"
          value={signupData.password}
          handler={inputHandler}
          required
        />
        <TextInput
          id="passwordConfirm"
          type="password"
          placeHolder="비밀번호 확인"
          value={signupData.passwordConfirm}
          handler={inputHandler}
          required
        />
        {validation(signupData.password, 'password') ? (
          <span className={confirmPassword && styles.validationText}>
            비밀번호를 확인해주세요
          </span>
        ) : (
          <span
            className={
              validation(signupData.password, 'password') &&
              styles.validationText
            }
          >
            비밀번호는 영문과 숫자를 포함한 8자이상이어야 합니다.
          </span>
        )}
      </form>
      <div className={styles.buttonContainer}>
        <button form="signUp" className={styles.signupButton} type="submit">
          회원가입
        </button>
        <a
          href="http://ec2-15-165-186-53.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/kakao"
          className={styles.kakaoLoginButton}
        >
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
        </a>
        <a
          href="http://ec2-15-165-186-53.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/google"
          className={styles.googleLoginButton}
        >
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
        </a>
      </div>
    </article>
  );
};

export default SignUpForm;
