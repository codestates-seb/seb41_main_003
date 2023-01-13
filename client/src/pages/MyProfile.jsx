import styles from './MyProfile.module.css';
import { useState, useCallback } from 'react';
import { ProfileContents, MyProfileCard } from '../components/profileSection';
import { ConfirmModal } from '../components/Modal.jsx';
import DummyData from '../components/profileSection/DummyData';

const MyProfile = () => {
  const [user, setUser] = useState(DummyData);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isAnnounceOn, setIsAnnounceOn] = useState(DummyData.wanted_status);

  const modaInnerHandler = (e) => {
    if (e.target.name === 'yes') {
      setIsAnnounceOn((prev) => !prev);
      setIsModalOpen((prev) => !prev);
      //TODO:바뀐 유저의 공고 상태를 서버에 전송 해야함
    } else {
      setIsModalOpen((prev) => !prev);
    }
  };

  // const modalOpenOnHandler = useCallback(() => {
  //   setIsModalOpen((prev) => !prev);
  // }, []);

  const modalText = `공고 상태를 변경하시겠습니까?`;

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <MyProfileCard
          user={user}
          isAnnounceOn={isAnnounceOn}
          setIsAnnounceOn={setIsAnnounceOn}
          setIsModalOpen={setIsModalOpen}
        />
        <ProfileContents user={user} />
      </div>
      {isModalOpen && (
        <div className={styles.modalWrapper}>
          <ConfirmModal
            modalHandler={modaInnerHandler}
            text={modalText.split('\n').map((line) => {
              return (
                <p key={line.id} className={styles.modalText}>
                  {line}
                </p>
              );
            })}
          />
        </div>
      )}
    </div>
  );
};

export default MyProfile;
