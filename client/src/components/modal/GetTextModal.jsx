import styles from './DefaultModal.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue, ButtonSilver } from '../Button';
import { TextInput } from '../Input';
import { useState } from 'react';
import { useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';

const GetTextModal = ({ text, modalHandler, placeHolder, inputHandler }) => {
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
          buttonHandler={(e) => {
            modalHandler(e, value);
            inputHandler(e, value);
          }}
          text="확인"
        />
        <ButtonSilver name="no" buttonHandler={() => reset()} text="취소" />
      </div>
    </div>
  );
};

GetTextModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
  inputHandler: PropTypes.func,
  placeHolder: PropTypes.string,
};

export default GetTextModal;
