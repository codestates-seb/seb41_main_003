import styles from './Modal.module.css';
import PropTypes from 'prop-types';
import { ButtonNightBlue, ButtonSilver } from './Button';

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

AlertModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
};

ConfirmModal.propTypes = {
  text: PropTypes.string,
  modalHandler: PropTypes.func,
};
