import styles from './MyProfile.module.css';
import { useState, useCallback } from 'react';
import { DetailProfile, MyProfileCard } from '../components/profileSection';
import { ConfirmModal } from '../components/Modal.jsx';
const tutorDummyState = {
  profile_id: 1,
  user_id: 1,
  name: '신승구',
  rate: 4.4,
  bio: '하고싶은 대로 가르칩니다',
  want_date: '아무때나',
  pay: '많이 주면 감사',
  way: '알잘딱하게 합니다',
  profile_status: 'TUTOR',
  wanted_status: 'REQUEST',
  gender: '남자',
  pre_tutoring: '안할래요',
  difference: '뭔가 달라도 다르다니까요',
  school: '서울사이버대학교 성공시대학과 77학번',
  character: '건들면 뭅니다(진짜임)',
  subjects: [
    {
      subjectId: 1,
      subjectTitle: '영어',
      content:
        '영어 잘합니다영어 잘합니다영어 잘합니다영어 잘합니 잘합니다영어 잘합니다영어 잘 잘합니다영어 잘합니다영어 잘 잘합니다영어 잘합니다영어 잘 잘합니다영어 잘합니다영어 잘 잘합니다영어 잘합니다영어 잘다',
    },
    { subjectId: 2, subjectTitle: '수학', content: '수학 잘합니다' },
  ],
  reviews: [
    {
      reviewId: 1,
      professional: 4,
      readiness: 4,
      explanation: 5,
      punctuality: 5,
      reviewBody: 'TestBody',
      tuteeName: 'testTutee',
      createAt: '2023-01-11T04:11:43.078373',
      updateAt: '2023-01-11T04:11:43.078379',
    },
  ],
};
const MyProfile = () => {
  const [user, setUser] = useState(tutorDummyState);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isAnnounceOn, setIsAnnounceOn] = useState(
    tutorDummyState.wanted_status
  );

  const modaInnerHandler = (e) => {
    if (e.target.name === 'yes') {
      setIsAnnounceOn((prev) => !prev);
      setIsModalOpen((prev) => !prev);
      //바뀐 유저의 공고 상태를 서버에 전송 해야함
      // setUser((userData) => userData.wanted_status);
    } else {
      setIsModalOpen((prev) => !prev);
    }
  };

  const modalOpenOnHandler = useCallback(() => {
    setIsModalOpen((prev) => !prev);
  }, []);
  const announceOnHandler = useCallback(() => {
    setIsAnnounceOn((prev) => !prev);
  }, []);

  let modalText = `공고 상태를 
 \n  ${user.wanted_status}상태로 변경하시겠습니까?`;

  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <div className={styles.container}>
          <MyProfileCard
            user={user}
            isAnnounceOn={isAnnounceOn}
            modalOpenOnHandler={modalOpenOnHandler}
            announceOnHandler={announceOnHandler}
          />
          <DetailProfile user={user} />
        </div>
      </div>
      {isModalOpen && (
        <div className={styles.modalWrapper}>
          <ConfirmModal
            modalHandler={modaInnerHandler}
            text={modalText.split('\n').map((line) => {
              return <div key={line.id}>{line}</div>;
            })}
          />
        </div>
      )}
    </div>
  );
};

export default MyProfile;
