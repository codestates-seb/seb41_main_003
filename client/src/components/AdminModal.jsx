import { useState } from 'react';
import styles from './AdminModal.module.css';
import defaultUser from '../assets/defaultUser.png';
import PropType from 'prop-types';

const initialState = {
  data: [
    {
      profileId: 1,
      name: '신승구',
      url: 'http://localhost:3000',
    },
    {
      profileId: 2,
      name: '김민경',
      url: 'http://localhost:3000',
    },
    {
      profileId: 3,
      name: '유영민',
      url: 'http://localhost:3000',
    },
    {
      profileId: 4,
      name: '김다은',
      url: 'http://localhost:3000',
    },
  ],
  pageInfo: {
    page: 1,
    size: 10,
    totalElements: 10,
    totalPages: 1,
  },
};

const AdminModal = ({ setIsOpenAdminModal }) => {
  const [profiles, setProfiles] = useState(initialState.data);
  const switchHandler = (e) => {
    let { name } = e.currentTarget;
    // TODO : 프로필 전환 로직 추가 필요
    console.log(name.slice(7));
  };
  return (
    <div
      className={styles.backdrop}
      onClick={() => {
        setIsOpenAdminModal((prev) => !prev);
      }}
      aria-hidden="true"
    >
      <div
        className={styles.container}
        aria-hidden="true"
        onClick={(e) => e.stopPropagation()}
      >
        <h3>전환하실 프로필을 선택하세요.</h3>
        <ul className={styles.profilesList}>
          {profiles.map((profile) => (
            // TODO : 프로필 이미지 경로 수정 필요
            <li className={styles.profile} key={profile.profileId}>
              <button
                name={`profile${profile.profileId}`}
                onClick={switchHandler}
              >
                <img
                  className={styles.userImage}
                  src={defaultUser}
                  alt="user"
                />
                <p>{profile.name}</p>
              </button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

AdminModal.propTypes = {
  setIsOpenAdminModal: PropType.func,
};

export default AdminModal;
