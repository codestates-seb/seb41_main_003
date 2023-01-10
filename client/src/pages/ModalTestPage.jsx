import { AlertModal, ConfirmModal } from '../components/Modal';
import { useState } from 'react';
import styles from './ModalTestPage.module.css';

// Modal Test용으로 만들어진 페이지입니다.
// Modal을 사용할 페이지에서는 아래와 같이 useState를 사용해야 합니다.

const TestPage = () => {
  const [isOpenAlert, setIsOpenAlert] = useState(false);
  const [isOpenConfirm, setIsOpenConfirm] = useState(false);

  const alertHandler = () => {
    setIsOpenAlert(!isOpenAlert);
  };

  const confirmHandler = () => {
    setIsOpenConfirm(!isOpenConfirm);
  };

  return (
    <div>
      <button className={styles.button} onClick={alertHandler}>
        AlertModal Open
      </button>
      {isOpenAlert ? (
        <AlertModal text="이건 alertModal 입니다" modalHandler={alertHandler} />
      ) : null}
      <button className={styles.button} onClick={confirmHandler}>
        ConfirmModal Open
      </button>
      {isOpenConfirm ? (
        <ConfirmModal
          text="이건 confirmModal 입니다"
          modalHandler={confirmHandler}
        />
      ) : null}
    </div>
  );
};

export default TestPage;
