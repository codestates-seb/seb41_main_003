import styles from './TutorProfile.module.css';
import { useState, useCallback } from 'react';
import { ProfileContents, ProfileCard } from '../components/profileSection';
import { ConfirmModal } from '../components/Modal.jsx';
import DummyData from '../components/profileSection/DummyData';
import { ButtonTop } from '../components/Button';

const TutorProfile = () => {
  const [isQuesModalOpen, setIsQuesModalOpen] = useState(false);

  const QuesModaInnerHandler = (e) => {
    if (e.target.name === 'yes') {
      setIsQuesModalOpen((prev) => !prev);
      //TODO:모달창에서 확인 버튼을 누르면 MessagePage로 이동
    } else {
      setIsQuesModalOpen((prev) => !prev);
    }
  };

  const QuestionModalHandler = useCallback(() => {
    setIsQuesModalOpen((prev) => !prev);
  }, []);

  let QuesModalText = `상대방에게 문의를 요청하시겠습니까?`;

  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <div className={styles.container}>
          <ProfileCard
            user={DummyData}
            QuestionModalHandler={QuestionModalHandler}
          />
          <ProfileContents user={DummyData} />
        </div>
      </div>
      {isQuesModalOpen && (
        <div className={styles.modalWrapper}>
          <ConfirmModal
            modalHandler={QuesModaInnerHandler}
            text={QuesModalText.split('\n').map((line) => {
              return (
                <p key={line.id} className={styles.modalText}>
                  {line}
                </p>
              );
            })}
          />
        </div>
      )}
      <ButtonTop />
    </div>
  );
};

export default TutorProfile;
