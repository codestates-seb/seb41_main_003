import styles from './Header.module.css';
import { Link, useNavigate } from 'react-router-dom';
import { useState, useEffect, useRef } from 'react';
import { MdMenu, MdNotifications } from 'react-icons/md';
import { useSetRecoilState, useRecoilValue, useResetRecoilState } from 'recoil';
import ModalState from '../recoil/modal.js';
import defaultUser from '../assets/defaultUser.png';
import Profile from '../recoil/profile';
import axios from 'axios';
import useOutSideRef from '../util/useOutSideRef';
import CurrentRoomIdState from '../recoil/currentRoomId';
import ChangeJournal from '../recoil/journal';
import NotificationBox from './NotificationBox';

const Header = () => {
  const [isNoti, setIsNoti] = useState(false);
  const navigate = useNavigate();

  const profile = useRecoilValue(Profile);
  const setProfile = useSetRecoilState(Profile);
  const setModal = useSetRecoilState(ModalState);
  const resetProfile = useResetRecoilState(Profile);
  const resetModal = useResetRecoilState(ModalState);
  const resetCurrentRoom = useResetRecoilState(CurrentRoomIdState);
  const resetJournal = useResetRecoilState(ChangeJournal);

  const menuRef = useRef(null);
  const navRef = useRef(null);
  const [dropDownRef, isUserMenu, setIsUserMenu] = useOutSideRef(menuRef);
  const [sideRef, isNav, setIsNav] = useOutSideRef(navRef);

  const adminProps = (isFirstLogin) => {
    return {
      isOpen: true,
      modalType: 'admin',
      backDropHandle: isFirstLogin,
      props: {},
    };
  };

  const statusNoneProps = {
    isOpen: true,
    modalType: 'bothHandler',
    backDropHandle: true,
    props: {
      text: `서비스 이용을 위해 회원 정보 입력이 필요합니다. 
      회원 정보를 입력하시겠습니까?`,
      modalHandler: ({ target: { name } }) => {
        if (name === 'yes') {
          resetModal();
          navigate('/userinfo');
        } else {
          setModal({
            isOpen: true,
            modalType: 'bothHandler',
            backDropHandle: true,
            props: {
              text: `회원 정보가 입력이 되지 않으면
              정상적인 서비스가 불가하여 로그아웃 처리됩니다.
              로그아웃 하시겠습니까?`,
              modalHandler: ({ target: { name } }) => {
                if (name === 'yes') {
                  sessionStorage.clear();
                  resetProfile();
                  resetModal();
                } else {
                  setModal(statusNoneProps);
                }
              },
            },
          });
        }
      },
    },
  };

  useEffect(() => {
    if (profile.userStatus === 'NONE' && location.pathname !== '/userinfo')
      setModal(statusNoneProps);
    else if (
      profile.isLogin === true &&
      profile.profileId === 0 &&
      location.pathname !== '/userinfo'
    )
      setModal(adminProps(true));
  });

  useEffect(() => {
    window.addEventListener('resize', () => {
      if (window.innerWidth > 768) setIsNav(false);
    });
    return () => {
      window.removeEventListener('resize', () => {
        if (window.innerWidth > 768) setIsNav(false);
      });
    };
  }, []);

  useEffect(() => {
    const { isLogin, profileId } = profile;

    if (!isLogin || profileId === 0) return;

    const checkAlarmAPI = async () => {
      const {
        data: {
          data: { alarmCheck },
        },
      } = await axios.get(`/alarm/${profile.profileId}`);
      setProfile((prev) => ({ ...prev, alarmCheck }));
    };

    checkAlarmAPI();
  }, [location.pathname, profile.profileId]);

  const verify2ndPassword = async (value, path) => {
    await axios
      .post(
        `/auth/verify-second-password/${sessionStorage.getItem('userId')}`,
        { secondPassword: value }
      )
      .then(() => {
        navigate(path);
      })
      .catch(({ response }) => {
        if (response.data.message === 'WRONG SECOND PASSWORD') {
          setModal({
            isOpen: true,
            modalType: 'handlerAlert',
            props: {
              text: '2차 비밀번호가 틀렸습니다.',
              modalHandler: () => {
                setModal(verify2ndPwProps);
              },
            },
          });
        } else return;
      });
  };

  const verify2ndPwProps = (path) => {
    return {
      isOpen: true,
      modalType: 'confirmText',
      props: {
        text: '2차 비밀번호를 입력해주세요.',
        placeHolder: '2차 비밀번호',
        inputType: 'password',
        modalHandler: (_, value) => {
          verify2ndPassword(value, path);
          resetModal();
        },
      },
    };
  };

  const logoutProps = {
    isOpen: true,
    modalType: 'redConfirm',
    props: {
      text: '로그아웃 하시겠습니까?',
      modalHandler: () => {
        sessionStorage.clear();
        localStorage.removeItem('addProfile');
        resetJournal();
        resetProfile();
        resetModal();
        resetCurrentRoom();
        navigate('/');
      },
    },
  };

  const notAuthNavigate = (path) => {
    if (!profile.isLogin) navigate('/login');
    else navigate(path);
  };

  return (
    <header className={styles.header}>
      <div className={styles.logo}>
        <Link to="/">
          <svg
            className={styles.textLogo}
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 403.87 101.06"
          >
            <path d="M4.14,67.46q5.88,0,12.3-.16V38.44h17.1V66.75Q49.17,66,60.33,64.58l1.09,12.85a219.17,219.17,0,0,1-26,3q-13.56.8-28.53.79H1.42L0,67.52Q2.72,67.52,4.14,67.46ZM57.82,19.28q0,11.16-.49,19.35a168.48,168.48,0,0,1-2.12,18.43l-17.1-1.3q1.43-8.88,1.85-16.2t.55-16.26H5V9.47H57.82ZM64,0H81.78V39.42h12V53.9h-12v46.83H64Z" />
            <path d="M144,9A25.67,25.67,0,0,1,154.17,18a23.45,23.45,0,0,1,3.73,13,24,24,0,0,1-9.42,19.3,28.33,28.33,0,0,1-10.29,5V66.7q12.85-.81,22.33-2.12l1.3,12.52a217.71,217.71,0,0,1-29.51,3.68q-15,.84-32.89.89l-2-13.94q12.86,0,23.31-.27V55.27a28.36,28.36,0,0,1-10.24-5,24.46,24.46,0,0,1-7-8.41A23.64,23.64,0,0,1,101.06,31a23.27,23.27,0,0,1,3.78-13A26,26,0,0,1,115.13,9,33.13,33.13,0,0,1,144,9ZM119.9,36.73a9.13,9.13,0,0,0,3.94,3.59,13.35,13.35,0,0,0,5.64,1.28,12.24,12.24,0,0,0,8.19-2.83A9.83,9.83,0,0,0,140.91,31c0-3.42-1.1-6-3.21-7.79a12.28,12.28,0,0,0-8.22-2.67,11.73,11.73,0,0,0-8,2.67c-2,1.78-3.06,4.37-3,7.79A10.85,10.85,0,0,0,119.9,36.73ZM165.63,0H183V101.06H165.63Z" />
            <path d="M233.86,45a38.14,38.14,0,0,0,7.19,13,36.28,36.28,0,0,0,13,9.74l-9.47,13.18a36,36,0,0,1-12.85-9.15,47.53,47.53,0,0,1-8.61-13.88,48.33,48.33,0,0,1-9,15,37.21,37.21,0,0,1-13.75,9.7L191,69.15a34.88,34.88,0,0,0,13.47-9.83A39.73,39.73,0,0,0,212,45.68a51,51,0,0,0,2.34-14.75V29.62H194.93V15.79H214.2V2.61h17.54V15.79h19V29.62h-19v1.31A45.63,45.63,0,0,0,233.86,45ZM256.35,0h17.53V37.79h13.61V52.27H273.88v48.46H256.35Z" />
            <path d="M297.65,23.88a29.06,29.06,0,0,1,9.75-12.69,23.68,23.68,0,0,1,14.07-4.44,23,23,0,0,1,13.81,4.44,29.33,29.33,0,0,1,9.61,12.69A48.92,48.92,0,0,1,348.37,43a49.79,49.79,0,0,1-3.48,19.36,29.47,29.47,0,0,1-9.61,12.77,22.86,22.86,0,0,1-13.81,4.46,23.53,23.53,0,0,1-14.1-4.46,29.29,29.29,0,0,1-9.72-12.77A49.41,49.41,0,0,1,294.14,43,48.55,48.55,0,0,1,297.65,23.88Zm14.23,34.57q2.73,5.2,7.74,5.15,4.68,0,7.37-5.12T329.74,43q0-10.17-2.75-15.35t-7.37-5.12c-3.27,0-5.83,1.64-7.68,5s-2.78,8.45-2.78,15.16Q309.16,53.26,311.88,58.45Zm63.06,42.61H357.3V0h17.64Z" />
            <path d="M388,99a10.71,10.71,0,0,1-3.84-3.84,10.39,10.39,0,0,1-1.41-5.31,10.13,10.13,0,0,1,1.41-5.23A10.63,10.63,0,0,1,388,80.84a10.4,10.4,0,0,1,5.31-1.42,10.1,10.1,0,0,1,5.15,1.42,11.26,11.26,0,0,1,3.92,3.81,9.66,9.66,0,0,1,1.49,5.23,10.07,10.07,0,0,1-1.47,5.31A10.93,10.93,0,0,1,398.48,99a10.18,10.18,0,0,1-5.17,1.41A10.39,10.39,0,0,1,388,99Z" />
          </svg>
          <svg
            className={styles.symbolLogo}
            viewBox="0 0 38 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path d="M10.2842 12.0731L7.5404 12.7203V5.34757L0 7.12178V4.6255L10.2842 2.20659V12.0731Z" />
            <path d="M20.0397 0.233812C20.8124 0.48404 21.48 0.983852 21.9377 1.65473C22.4255 2.39108 22.6732 3.26042 22.6469 4.14327C22.6445 5.12502 22.401 6.09115 21.9377 6.95673C21.4738 7.84765 20.827 8.63061 20.0397 9.25444C19.279 9.87378 18.3889 10.3144 17.4352 10.5438C16.5866 10.7643 15.6933 10.7429 14.8564 10.482C14.0871 10.2401 13.4237 9.74232 12.9764 9.07135C12.4959 8.33419 12.2529 7.46747 12.2801 6.58797C12.2786 5.59992 12.5176 4.62635 12.9764 3.75129C13.4304 2.85245 14.0714 2.06111 14.8564 1.43037C15.6069 0.810348 16.4888 0.369398 17.4352 0.140976C18.2947 -0.0745862 19.1977 -0.0423996 20.0397 0.233812ZM15.2148 7.26103C15.4303 7.59299 15.758 7.83643 16.138 7.94699C16.5599 8.06365 17.0055 8.06365 17.4274 7.94699C17.9 7.84384 18.3434 7.63599 18.725 7.33875C19.1066 7.04151 19.4166 6.66246 19.6323 6.22951C19.8555 5.77844 19.9686 5.28089 19.9624 4.77765C19.9847 4.32442 19.8696 3.87502 19.6323 3.48825C19.4178 3.17593 19.0985 2.95085 18.7323 2.85387C18.3082 2.74805 17.8639 2.75427 17.4429 2.87192C16.9761 2.97647 16.5366 3.1786 16.1535 3.46504C15.7632 3.74565 15.4421 4.11169 15.2148 4.53525C14.9881 4.98157 14.8731 5.47623 14.8796 5.97679C14.8611 6.42868 14.9779 6.8758 15.2148 7.26103Z" />
            <path d="M21.9559 15.7814L18.5673 16.5756C18.5777 18.716 19.8903 19.6805 22.5052 19.4691L21.3731 21.9524C19.2292 22.0229 17.7808 21.3447 17.0278 19.9178C16.2817 21.7006 14.8375 23.0613 12.6954 24L11.5479 22.053C12.992 21.4908 14.0063 20.8032 14.5908 19.99C15.163 19.2078 15.4718 18.2642 15.4728 17.2951L12.1848 18.0688V15.7479L15.7951 14.8994V12.4212L18.3456 11.8203V14.3089L21.9559 13.4579V15.7814Z" />
            <path d="M31.6546 9.86304C32.4279 10.112 33.0959 10.6121 33.5526 11.284C34.0396 12.0225 34.2863 12.8935 34.2592 13.7777C34.2568 14.7591 34.0142 15.725 33.5526 16.5911C33.0886 17.4827 32.4419 18.2665 31.6546 18.8914C30.893 19.5109 30.0021 19.9516 29.0475 20.1808C28.199 20.4008 27.3061 20.3802 26.4687 20.1215C25.7003 19.8779 25.0374 19.3806 24.5887 18.7109C24.109 17.9735 23.8669 17.1068 23.895 16.2275C23.893 15.2397 24.1311 14.2662 24.5887 13.3908C25.044 12.4928 25.6848 11.7017 26.4687 11.0699C27.2199 10.4509 28.1015 10.0101 29.0475 9.78052C29.9069 9.56155 30.8108 9.59015 31.6546 9.86304ZM26.8194 16.8903C27.0355 17.2211 27.3631 17.4635 27.7426 17.5736C28.1643 17.6917 28.6103 17.6917 29.032 17.5736C29.5019 17.4696 29.9424 17.2617 30.3214 16.965C30.7123 16.6626 31.0279 16.2739 31.2436 15.8292C31.4592 15.3845 31.569 14.8959 31.5644 14.4017C31.5864 13.9482 31.4704 13.4986 31.2317 13.1123C31.0196 12.7912 30.6978 12.5584 30.3265 12.4573C29.9025 12.3507 29.458 12.3569 29.0371 12.4754C28.5699 12.5788 28.1303 12.781 27.7477 13.0685C27.3527 13.3585 27.0326 13.7386 26.8141 14.1773C26.5957 14.616 26.4852 15.1005 26.4919 15.5905C26.4696 16.0467 26.5836 16.4991 26.8194 16.8903Z" />
            <path d="M35.1516 19.5077C34.9466 19.4434 34.7693 19.3117 34.6488 19.1338C34.5215 18.9414 34.4567 18.7145 34.4631 18.484C34.4653 18.2276 34.5289 17.9756 34.6488 17.749C34.7729 17.5079 34.9438 17.294 35.1516 17.1198C35.3532 16.9483 35.59 16.8233 35.8453 16.7536C36.0659 16.6888 36.3004 16.6888 36.521 16.7536C36.732 16.8223 36.9153 16.9573 37.0435 17.1385C37.1717 17.3197 37.2381 17.5374 37.2327 17.7593C37.2306 18.0199 37.1642 18.2759 37.0393 18.5046C36.9105 18.7485 36.7353 18.9648 36.5235 19.1415C36.3269 19.3105 36.0953 19.4338 35.8453 19.5026C35.6193 19.5707 35.3786 19.5725 35.1516 19.5077Z" />
          </svg>
        </Link>
      </div>
      <button
        ref={navRef}
        className={styles.hamBtn}
        onClick={() => {
          setIsNav(!isNav);
        }}
      >
        <MdMenu />
      </button>

      <nav className={`${styles.nav} ${isNav && styles.active}`} ref={sideRef}>
        <ul>
          <li>
            <Link to="/tutorlist">튜터 찾기</Link>
          </li>
          <li>
            <Link to="/tuteelist">학생 찾기</Link>
          </li>
          <li>
            <button onClick={() => notAuthNavigate(`/tutoringlist`)}>
              과외 관리
            </button>
          </li>
        </ul>
      </nav>

      {profile.isLogin ? (
        <div className={styles.memberMenu}>
          <ul className={styles.menuContainer}>
            <li>
              <button
                className={styles.notiButton}
                onClick={() => setIsNoti(!isNoti)}
              >
                <MdNotifications />
                {profile.alarmCheck && <span className={styles.notiRed} />}
              </button>
            </li>
            <li className={styles.login}>
              <button
                className={styles.profileButton}
                onClick={() => {
                  setIsUserMenu(!isUserMenu);
                }}
                ref={menuRef}
              >
                <img
                  src={profile.url || defaultUser}
                  className={styles.profileImage}
                  alt="프로필 이미지"
                />
                <span>{profile.name}</span>
              </button>
            </li>
          </ul>
          {isNoti && <NotificationBox />}

          {isUserMenu && (
            <ul className={styles.dropdown} ref={dropDownRef}>
              <li>
                <Link to={`/myprofile`}>나의 프로필</Link>
              </li>
              <li>
                <Link to={`/message`}>메세지함</Link>
              </li>
              <li>
                <button onClick={() => setModal(adminProps(false))}>
                  프로필 전환
                </button>
              </li>
              <li>
                <button onClick={() => setModal(verify2ndPwProps('/admin'))}>
                  전체 프로필 관리
                </button>
              </li>
              <li>
                <button onClick={() => setModal(verify2ndPwProps('/userinfo'))}>
                  회원정보 수정
                </button>
              </li>
              <li>
                <button onClick={() => setModal(logoutProps)}>로그아웃</button>
              </li>
            </ul>
          )}
        </div>
      ) : (
        <div className={styles.memberMenu}>
          <ul className={styles.menuContainer}>
            <li className={styles.login}>
              <Link to="/login">로그인</Link>
            </li>
            <li className={styles.center}> | </li>
            <li className={styles.signup}>
              <Link to="/signup">회원가입</Link>
            </li>
          </ul>
        </div>
      )}
    </header>
  );
};

export default Header;
