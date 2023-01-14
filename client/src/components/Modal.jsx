import styles from './Modal.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue, ButtonSilver } from './Button';
import { TextInput } from './Input';
import { useState } from 'react';
import { useResetRecoilState, useRecoilValue } from 'recoil';
import ModalState from '../recoil/modal';

export const AlertModal = ({ text }) => {
  const reset = useResetRecoilState(ModalState);
  return (
    <div
      className={styles.view}
      onClick={(e) => e.stopPropagation()}
      role="dialog"
      aria-hidden
    >
      <div className={styles.text}>{text}</div>
      <ButtonNightBlue buttonHandler={() => reset()} text="확인" />
    </div>
  );
};

AlertModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
};

export const ConfirmModal = ({ text, modalHandler }) => {
  const reset = useResetRecoilState(ModalState);
  return (
    <div
      className={styles.view}
      onClick={(e) => e.stopPropagation()}
      role="dialog"
      aria-hidden
    >
      <div className={styles.text}>{text}</div>
      <div className={styles.buttonBox}>
        <ButtonNightBlue name="yes" buttonHandler={modalHandler} text="확인" />
        <ButtonSilver name="no" buttonHandler={() => reset()} text="취소" />
      </div>
    </div>
  );
};

ConfirmModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
};

export const ConfirmValiModal = ({ text, modalHandler, validation }) => {
  const reset = useResetRecoilState(ModalState);
  const [value, setValue] = useState('');

  const validationHandler = (e) => {
    if (value === validation) modalHandler(e);
  };

  return (
    <div
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
        <ButtonNightBlue
          name="yes"
          buttonHandler={validationHandler}
          text="확인"
        />
        <ButtonSilver name="no" buttonHandler={() => reset()} text="취소" />
      </div>
    </div>
  );
};

ConfirmValiModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
  validation: PropTypes.string,
};

export const ConfirmTextModal = ({ text, modalHandler, placeHolder }) => {
  const reset = useResetRecoilState(ModalState);
  const [value, setValue] = useState('');

  const valueHandler = (e) => {
    setValue(e.target.value);
  };

  return (
    <div
      className={styles.view}
      onClick={(e) => e.stopPropagation()}
      role="dialog"
      aria-hidden
    >
      <div className={styles.text}>{text}</div>
      <div className={styles.input}>
        <TextInput
          id="confirmInput"
          placeHolder={placeHolder}
          value={value}
          handler={valueHandler}
        />
      </div>
      <div className={styles.buttonBox}>
        <ButtonNightBlue
          name="yes"
          buttonHandler={(e) => modalHandler(e, value)}
          text="확인"
        />
        <ButtonSilver name="no" buttonHandler={() => reset()} text="취소" />
      </div>
    </div>
  );
};

ConfirmTextModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
  placeHolder: PropTypes.string,
};

export const GlobalModal = () => {
  const reset = useResetRecoilState(ModalState);
  const { isOpen, modalType, props } = useRecoilValue(ModalState);
  if (!isOpen) return;

  const modal = {
    alert: <AlertModal {...props} />,
    confirm: <ConfirmModal {...props} />,
    confirmVali: <ConfirmValiModal {...props} />,
    confirmText: <ConfirmTextModal {...props} />,
  };

  return (
    <div className={styles.backdrop} onClick={() => reset()} aria-hidden="true">
      {modal[modalType]}
    </div>
  );
};
