import styles from './Modal.module.css';
import PropTypes from 'prop-types';

export const AlertModal = ({ text, modalHandler }) => {
  return (
    <div className={styles.backdrop} onClick={modalHandler} aria-hidden="true">
      <div
        className={styles.view}
        onClick={(e) => e.stopPropagation()}
        aria-hidden="true"
      >
        <div className={styles.text}>{text}</div>
        <button className={styles.button} onClick={modalHandler}>
          확인
        </button>
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
          <button className={styles.button} onClick={modalHandler}>
            확인
          </button>
          <button className={styles.cancleButton} onClick={modalHandler}>
            취소
          </button>
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
