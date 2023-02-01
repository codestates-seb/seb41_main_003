import styles from './DefaultModal.module.css';
import PropTypes from 'prop-types';
import { ButtonSilver, ButtonRed } from '../Button';
import { TextInput } from '../Input';
import { useState } from 'react';
import { useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';

export const RedConfirmValiModal = ({ text, modalHandler, validation }) => {
  const reset = useResetRecoilState(ModalState);
  const [value, setValue] = useState('');

  const validationHandler = (e) => {
    e.preventDefault();
    if (value === validation) modalHandler(e);
  };

  return (
    <form
      className={styles.view}
      onClick={(e) => e.stopPropagation()}
      role="dialog"
      aria-hidden
    >
      <div className={styles.text}>{text}</div>
      <div className={styles.input}>
        <TextInput
          id="confirmInput"
          placeHolder={validation}
          value={value}
          handler={(e) => setValue(e.target.value)}
        />
        <span>
          {value !== validation && `${validation}를(을) 정확히 입력해주세요.`}
        </span>
      </div>
      <div className={styles.buttonBox}>
        <ButtonRed name="yes" buttonHandler={validationHandler} text="확인" />
        <ButtonSilver name="no" buttonHandler={() => reset()} text="취소" />
      </div>
    </form>
  );
};

RedConfirmValiModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
  validation: PropTypes.string,
};

export const RedHandlerAlertModal = ({ text, modalHandler }) => {
  return (
    <div
      className={styles.view}
      onClick={(e) => e.stopPropagation()}
      role="dialog"
      aria-hidden
    >
      <div className={styles.text}>{text}</div>
      <ButtonRed buttonHandler={modalHandler} text="확인" />
    </div>
  );
};

RedHandlerAlertModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
};
