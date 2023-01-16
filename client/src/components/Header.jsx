import styles from './Header.module.css';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import { MdNotifications } from 'react-icons/md';
import { ButtonRed } from './Button';
import { useSetRecoilState } from 'recoil';
import ModalState from '../recoil/modal.js';
import defaultUser from '../assets/defaultUser.png';

const Header = () => {
  const [isLogin, setIsLogin] = useState(false);
  const [isMenu, setIsMenu] = useState(false);
  const [isNoti, setIsNoti] = useState(false);

  const setModal = useSetRecoilState(ModalState);
  const adminProps = {
    isOpen: true,
    modalType: 'admin',
    props: {},
  };

  return (
    <header className={styles.header}>
      <div className={styles.logo}>
        <Link to="/">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 403.87 101.06">
            <path d="M4.14,67.46q5.88,0,12.3-.16V38.44h17.1V66.75Q49.17,66,60.33,64.58l1.09,12.85a219.17,219.17,0,0,1-26,3q-13.56.8-28.53.79H1.42L0,67.52Q2.72,67.52,4.14,67.46ZM57.82,19.28q0,11.16-.49,19.35a168.48,168.48,0,0,1-2.12,18.43l-17.1-1.3q1.43-8.88,1.85-16.2t.55-16.26H5V9.47H57.82ZM64,0H81.78V39.42h12V53.9h-12v46.83H64Z" />
            <path d="M144,9A25.67,25.67,0,0,1,154.17,18a23.45,23.45,0,0,1,3.73,13,24,24,0,0,1-9.42,19.3,28.33,28.33,0,0,1-10.29,5V66.7q12.85-.81,22.33-2.12l1.3,12.52a217.71,217.71,0,0,1-29.51,3.68q-15,.84-32.89.89l-2-13.94q12.86,0,23.31-.27V55.27a28.36,28.36,0,0,1-10.24-5,24.46,24.46,0,0,1-7-8.41A23.64,23.64,0,0,1,101.06,31a23.27,23.27,0,0,1,3.78-13A26,26,0,0,1,115.13,9,33.13,33.13,0,0,1,144,9ZM119.9,36.73a9.13,9.13,0,0,0,3.94,3.59,13.35,13.35,0,0,0,5.64,1.28,12.24,12.24,0,0,0,8.19-2.83A9.83,9.83,0,0,0,140.91,31c0-3.42-1.1-6-3.21-7.79a12.28,12.28,0,0,0-8.22-2.67,11.73,11.73,0,0,0-8,2.67c-2,1.78-3.06,4.37-3,7.79A10.85,10.85,0,0,0,119.9,36.73ZM165.63,0H183V101.06H165.63Z" />
            <path d="M233.86,45a38.14,38.14,0,0,0,7.19,13,36.28,36.28,0,0,0,13,9.74l-9.47,13.18a36,36,0,0,1-12.85-9.15,47.53,47.53,0,0,1-8.61-13.88,48.33,48.33,0,0,1-9,15,37.21,37.21,0,0,1-13.75,9.7L191,69.15a34.88,34.88,0,0,0,13.47-9.83A39.73,39.73,0,0,0,212,45.68a51,51,0,0,0,2.34-14.75V29.62H194.93V15.79H214.2V2.61h17.54V15.79h19V29.62h-19v1.31A45.63,45.63,0,0,0,233.86,45ZM256.35,0h17.53V37.79h13.61V52.27H273.88v48.46H256.35Z" />
            <path d="M297.65,23.88a29.06,29.06,0,0,1,9.75-12.69,23.68,23.68,0,0,1,14.07-4.44,23,23,0,0,1,13.81,4.44,29.33,29.33,0,0,1,9.61,12.69A48.92,48.92,0,0,1,348.37,43a49.79,49.79,0,0,1-3.48,19.36,29.47,29.47,0,0,1-9.61,12.77,22.86,22.86,0,0,1-13.81,4.46,23.53,23.53,0,0,1-14.1-4.46,29.29,29.29,0,0,1-9.72-12.77A49.41,49.41,0,0,1,294.14,43,48.55,48.55,0,0,1,297.65,23.88Zm14.23,34.57q2.73,5.2,7.74,5.15,4.68,0,7.37-5.12T329.74,43q0-10.17-2.75-15.35t-7.37-5.12c-3.27,0-5.83,1.64-7.68,5s-2.78,8.45-2.78,15.16Q309.16,53.26,311.88,58.45Zm63.06,42.61H357.3V0h17.64Z" />
            <path d="M388,99a10.71,10.71,0,0,1-3.84-3.84,10.39,10.39,0,0,1-1.41-5.31,10.13,10.13,0,0,1,1.41-5.23A10.63,10.63,0,0,1,388,80.84a10.4,10.4,0,0,1,5.31-1.42,10.1,10.1,0,0,1,5.15,1.42,11.26,11.26,0,0,1,3.92,3.81,9.66,9.66,0,0,1,1.49,5.23,10.07,10.07,0,0,1-1.47,5.31A10.93,10.93,0,0,1,398.48,99a10.18,10.18,0,0,1-5.17,1.41A10.39,10.39,0,0,1,388,99Z" />
          </svg>
        </Link>
      </div>
      <nav className={styles.nav}>
        <ul>
          <li>
            <Link to="/tutorlist">튜터 찾기</Link>
          </li>
          <li>
            <Link to="/tuteelist">학생 찾기</Link>
          </li>
          <li>
            <Link to="/tutoringlist">과외 관리</Link>
          </li>
        </ul>
      </nav>
      {isLogin ? (
        <div className={styles.memberMenu}>
          <ul>
            <li>
              <button
                onClick={() => setIsNoti(!isNoti)}
                onBlur={() => {
                  setTimeout(() => setIsNoti(false), 100);
                }}
              >
                <MdNotifications />
              </button>
            </li>
            <li>
              <button
                onClick={() => setIsMenu(!isMenu)}
                onBlur={() => {
                  setTimeout(() => setIsMenu(false), 100);
                }}
              >
                {/* TODO: 프로필이 전환되면 이미지도 변경되어야 함 */}
                <img
                  src={defaultUser}
                  className={styles.profileImage}
                  alt="프로필 이미지"
                />
              </button>
            </li>
          </ul>
          {isNoti && (
            <div className={styles.noti}>기능 추가 될 예정입니다.</div>
          )}

          {isMenu && (
            <ul className={styles.dropdown}>
              <li>
                <Link to="/myprofile">프로필</Link>
              </li>
              <li>
                <Link to="/message">메세지함</Link>
              </li>
              <li>
                <button onClick={() => setModal(adminProps)}>
                  프로필 전환
                </button>
              </li>
              <li>
                <Link to="/admin">전체 프로필 관리</Link>
              </li>
              <li>
                <Link to="/userinfo">회원정보 수정</Link>
              </li>
              <li>
                <Link to="">로그아웃</Link>
              </li>
            </ul>
          )}
        </div>
      ) : (
        <div className={styles.memberMenu}>
          <ul>
            <li>
              <Link to="/login">로그인</Link>
            </li>
            <li>|</li>
            <li>
              <Link to="/signup">회원가입</Link>
            </li>
          </ul>
        </div>
      )}
      <ButtonRed
        text="⚠로그인 상태 변경"
        buttonHandler={() => setIsLogin(!isLogin)}
      />
    </header>
  );
};

export default Header;
