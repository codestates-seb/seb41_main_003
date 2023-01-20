import { useEffect, useState } from 'react';
import styles from './AdminModal.module.css';
import axios from 'axios';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import Profile from '../../recoil/profile';
import ModalState from '../../recoil/modal';
import { useNavigate } from 'react-router-dom';
import { ButtonNightBlue } from '../Button';
import { MdOutlineWarning } from 'react-icons/md';

const AdminModal = () => {
  const [profiles, setProfiles] = useState([0]);

  const setProfile = useSetRecoilState(Profile);
  const resetModal = useResetRecoilState(ModalState);
  const navigate = useNavigate();

  const switchHandler = (profileId, name, url) => {
    setProfile((prev) => ({ ...prev, profileId, name, url }));
    navigate('/');
    resetModal();
  };

  const getUserProfile = async () => {
    axios.defaults.headers.common['Authorization'] =
      sessionStorage.getItem('authorization') ||
      localStorage.getItem('authorization');
    axios
      .get(
        `/profiles/${
          sessionStorage.getItem('userId') || localStorage.getItem('userId')
        }`
      )
      .then(({ data }) => {
        setProfiles(data.data);
      })
      .catch(({ response }) => {
        console.log(response);
        console.log(response.status);
        console.log(response.data.message);
      });
  };

  useEffect(() => {
    getUserProfile();
  }, []);

  return (
    <div
      className={styles.container}
      aria-hidden="true"
      role="dialog"
      onClick={(e) => e.stopPropagation()}
    >
      {profiles.length === 0 ? (
        <div className={styles.alert}>
          <MdOutlineWarning />
          <p>
            프로필이 없습니다. <br /> 서비스 이용을 위해 프로필 생성 페이지로
            이동합니다.
          </p>
          <ButtonNightBlue
            text="확인"
            buttonHandler={() => {
              navigate('/addprofile');
              resetModal();
            }}
            name="yes"
          />
        </div>
      ) : (
        <>
          <h3>전환하실 프로필을 선택하세요.</h3>
          <ul className={styles.profilesList}>
            {profiles.map(({ profileId, name, url }) => (
              <li className={styles.profile} key={profileId}>
                <button
                  name={`profile${profileId}`}
                  onClick={() => switchHandler(profileId, name, url)}
                >
                  <img className={styles.userImage} src={url} alt="user" />
                  <p>{name}</p>
                </button>
              </li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
};

export default AdminModal;
