import styles from './Modal.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue, ButtonSilver } from './Button';
import { TextInput } from './Input';
import { useState } from 'react';

export const AlertModal = ({ text, modalHandler }) => {
  return (
    <div className={styles.backdrop} onClick={modalHandler} aria-hidden="true">
      <div
        className={styles.view}
        onClick={(e) => e.stopPropagation()}
        aria-hidden="true"
      >
        <div className={styles.text}>{text}</div>
        <ButtonNightBlue buttonHandler={modalHandler} text="확인" />
      </div>
    </div>
  );
};

AlertModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
};

export const ConfirmModal = ({ text, modalHandler }) => {
  return (
    <div className={styles.backdrop}>
      <div className={styles.view}>
        <div className={styles.text}>{text}</div>
        <div className={styles.buttonBox}>
          <ButtonNightBlue
            name="yes"
            buttonHandler={modalHandler}
            text="확인"
          />
          <ButtonSilver name="no" buttonHandler={modalHandler} text="취소" />
        </div>
      </div>
    </div>
  );
};

ConfirmModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
};

export const ConfirmValiModal = ({ text, modalHandler, validation }) => {
  const [value, setValue] = useState('');

  const validationHandler = (e) => {
    if (value === validation) modalHandler(e);
  };

  return (
    <div className={styles.backdrop}>
      <div className={styles.view}>
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
          <ButtonSilver name="no" buttonHandler={modalHandler} text="취소" />
        </div>
      </div>
    </div>
  );
};

ConfirmValiModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
  validation: PropTypes.string,
};

export const ConfirmTextModal = ({
  text,
  modalHandler,
  value,
  valueHandler,
  placeHolder,
}) => {
  return (
    <div className={styles.backdrop}>
      <div className={styles.view}>
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
            buttonHandler={modalHandler}
            text="확인"
          />
          <ButtonSilver name="no" buttonHandler={modalHandler} text="취소" />
        </div>
      </div>
    </div>
  );
};

ConfirmTextModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
  value: PropTypes.string,
  valueHandler: PropTypes.func,
  placeHolder: PropTypes.string,
};
