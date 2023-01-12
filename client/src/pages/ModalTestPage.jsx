import {
  AlertModal,
  ConfirmModal,
  ConfirmValiModal,
  ConfirmTextModal,
} from '../components/Modal';
import { useState } from 'react';
import styles from './ModalTestPage.module.css';

// Modal Test용으로 만들어진 페이지입니다.
// Modal을 사용할 페이지에서는 아래와 같이 useState를 사용해야 합니다.

const TestPage = () => {
  const [isOpenAlert, setIsOpenAlert] = useState(false);
  const [isOpenConfirm, setIsOpenConfirm] = useState(false);
  const [isOpenValiConfirm, setIsOpenValiConfirm] = useState(false);
  const [isOpenTextConfirm, setIsOpenTextConfirm] = useState(false);
  const [value, setValue] = useState('');

  const alertHandler = () => {
    setIsOpenAlert(!isOpenAlert);
  };

  const confirmHandler = () => {
    setIsOpenConfirm(!isOpenConfirm);
  };
  const confirmTextHandler = () => {
    setIsOpenTextConfirm(!isOpenTextConfirm);
  };

  const confirmValiHandler = () => {
    setIsOpenValiConfirm(!isOpenValiConfirm);
  };

  return (
    <div>
      <button className={styles.button} onClick={alertHandler}>
        AlertModal Open
      </button>
      {isOpenAlert && (
        <AlertModal text="이건 alertModal 입니다" modalHandler={alertHandler} />
      )}

      <button className={styles.button} onClick={confirmHandler}>
        ConfirmModal Open
      </button>
      {isOpenConfirm && (
        <ConfirmModal
          text="이건 confirmModal 입니다"
          modalHandler={confirmHandler}
        />
      )}

      <button className={styles.button} onClick={confirmTextHandler}>
        ConfirmTextModal Open
      </button>
      {isOpenTextConfirm && (
        <ConfirmTextModal
          text="이건 confirmTextHandler 입니다"
          modalHandler={confirmTextHandler}
          value={value}
          valueHandler={(e) => setValue(e.target.value)}
          placeHolder="placeholder"
        />
      )}

      <button className={styles.button} onClick={confirmValiHandler}>
        ConfirmValiModal Open
      </button>
      {isOpenValiConfirm && (
        <ConfirmValiModal
          text="이건 confirmValiHandler 입니다"
          modalHandler={confirmValiHandler}
          validation="test"
        />
      )}
    </div>
  );
};

export default TestPage;
