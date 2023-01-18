import { useEffect, useState } from 'react';
import styles from './AdminModal.module.css';
import axios from 'axios';
import reIssueToken from '../../util/reIssueToken';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import Profile from '../../recoil/profile';
import ModalState from '../../recoil/modal';

const AdminModal = () => {
  // TODO : 프로필 전환 API 연결 필요

  const [profiles, setProfiles] = useState([]);

  const setProfile = useSetRecoilState(Profile);
  const setModal = useSetRecoilState(ModalState);
  const resetProfile = useResetRecoilState(Profile);
  const resetModal = useResetRecoilState(ModalState);

  const switchHandler = (e) => {
    let { name } = e.currentTarget;
    console.log(name.slice(7));
    setProfile((prev) => ({ ...prev, profileId: name.slice(7) }));
    window.location.href = '/';
  };

  const getUserProfile = async () => {
    axios.defaults.baseURL = process.env.REACT_APP_BASE_URL;

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
        console.log(data);
        if (data.data.length === 0) {
          setModal({
            isOpen: true,
            modalType: 'handlerAlert',
            props: {
              text: `프로필이 없습니다.
              서비스 이용을 위해 프로필 생성 페이지로 이동합니다.`,
              modalHandler: () => {
                window.location.href = '/addprofile';
                resetModal();
              },
            },
          });
        } else {
          setProfiles(data.data);
        }
      })
      .catch(({ response }) => {
        console.log(response.status);
        console.log(response.data.message);
        if (response.data.message === 'EXPIRED REFRESH TOKEN')
          reIssueToken(getUserProfile).catch(() => {
            console.log('reset');
            resetProfile();
            window.location.href = '/login';
          });
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
      <h3>전환하실 프로필을 선택하세요.</h3>
      <ul className={styles.profilesList}>
        {profiles.map(({ profileId, name, url }) => (
          // TODO : 프로필 이미지 경로 수정 필요
          <li className={styles.profile} key={profileId}>
            <button name={`profile${profileId}`} onClick={switchHandler}>
              <img className={styles.userImage} src={url} alt="user" />
              <p>{name}</p>
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default AdminModal;
